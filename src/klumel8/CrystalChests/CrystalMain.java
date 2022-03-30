package klumel8.CrystalChests;

import com.google.common.eventbus.Subscribe;
import klumel8.CrystalChests.Branches.BankLoot.BankLoot;
import klumel8.CrystalChests.Branches.BankLoot.Leaves.*;
import klumel8.CrystalChests.Branches.OpenChest.Leaves.*;
import klumel8.CrystalChests.Branches.OpenChest.OpenChest;
import klumel8.CrystalChests.Branches.PrepareGE.Leaves.WithdrawSellables;
import klumel8.CrystalChests.Branches.PrepareGE.PrepareGE;
import klumel8.CrystalChests.Branches.TradeLoot.Leaves.StopMovingOn;
import klumel8.CrystalChests.Branches.TradeLoot.TradeLoot;
import klumel8.CrystalChests.Branches.TravelToBank.Leaves.OpenBank;
import klumel8.CrystalChests.Branches.TravelToBank.Leaves.TurnToBank;
import klumel8.CrystalChests.Branches.TravelToBank.Leaves.WalkToBank;
import klumel8.CrystalChests.Branches.TravelToBank.TravelToBank;
import klumel8.CrystalChests.Branches.TravelToChest.Leaves.*;
import klumel8.CrystalChests.Branches.TravelToChest.TravelToChest;
import klumel8.Framework.Branch;
import klumel8.Framework.Leaf;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.Nameable;
import org.powbot.api.event.InventoryChangeEvent;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.stream.item.InventoryItemStream;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.AbstractScript;
import org.powbot.api.script.ScriptCategory;
import org.powbot.api.script.ScriptManifest;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.script.ScriptManager;
import org.powbot.mobile.service.ItemPriceService;
import org.powbot.mobile.service.ScriptUploader;
import org.powbot.proto.rt4.ItemPriceServiceGrpc;

import java.util.ArrayList;
import java.util.List;

@ScriptManifest(
        name = "Klumel's Crystal Chests",
        description = "Start with open bank and house tabs in inventory. Have stamina + ring of duelling + crystal keys next to each other in the bank. Have a house in Taverly!",
        version = "1.0",
        category = ScriptCategory.MoneyMaking,
        markdownFileName = "crystalchest.md",
        scriptId = "129c2462-d4c2-40ef-bb70-c1cdd252be64",
        priv = false
)

public class CrystalMain extends AbstractScript {
    public List<Branch> branches = new ArrayList<>();

    public List<Branch> geBranches = new ArrayList<>();

    public static String branchStatus = "";
    public static String leafStatus = "";

    public static void main(String[] args){
        //ProcessBuilder builder = new ProcessBuilder("C:\\Users\\flori\\.powbot\\android\\platform-tools\\adb.exe", "-s", "127.0.0.1:5565", "forward", "tcp:61666", "tcp:61666");
        //builder.start();
        new ScriptUploader().uploadAndStart("Klumel's Crystal Chests", "", "", true, false);
    }

    public String chestsPerHour(){
        double runTime = (double) ScriptManager.INSTANCE.getRuntime(true) / 1000 / 3600;
        double kph =  CrystalConstants.keysUsed / runTime;
        kph = (int) (kph);
        return "Chests per hour: " + kph;
    }

    @Override
    public void onStart(){
        Condition.sleep(300);
        System.out.println("Started the crystal chest script");

        Paint paint = new PaintBuilder()
                .addString(() -> chestsPerHour())
                .addString(() -> "GP earned: " + CrystalConstants.earnedValue)
                .addString(() -> "Branch status: " + branchStatus)
                .addString(() -> "Leaf status: " + leafStatus)
                .build();
        addPaint(paint);

        //initialize some constants
        CrystalConstants.lastClick = System.currentTimeMillis() - 10000;

        //add the branches
        //branches.add(new PrepareGE(new WithdrawSellables()));
        //branches.add(new TradeLoot(new StopMovingOn()));
        branches.add(new OpenChest(new AvoidDruids(), new TurnToChest(), new WalkToChest(), new UseKey(), new DropItems(), new TeleBank()));
        branches.add(new BankLoot(new DepositItems(), new WithdrawKeys(), new RefreshRing(), new SipStamina(), new WithdrawTabs()));
        branches.add(new TravelToBank(new WalkToBank(), new TurnToBank(), new OpenBank()));
        branches.add(new TravelToChest(new CloseBank(), new TeleToChest(), new WalkToDoor(), new OpenDoor(), new EnterBuilding()));

        CrystalConstants.gpValues.put("Crystal key", GrandExchange.getItemPrice(CrystalConstants.keyID));
        CrystalConstants.gpValues.put("Teleport to house", GrandExchangeItem.Companion.fromName("Teleport to house").getGuidePrice());
        branchStatus = "Doing GE price lookup";
        System.out.println("Starting GE lookup");
        for (String name : CrystalConstants.lootItems){
            if(name.equals("Coins")){
                CrystalConstants.gpValues.put(name, 1);
            }else {
                int price = GrandExchangeItem.Companion.fromName(name).getGuidePrice();
                if (price < 1) {
                    price = -price;
                }
                System.out.println("Estimated " + name + " to be worth " + price + " gp");
                CrystalConstants.gpValues.put(name, price);
            }
        }
        System.out.println("Zoom: "+Camera.getZoom());
        if(Camera.getZoom() > 10) {
            Camera.moveZoomSlider(0);
        }


    }

    @Override
    public void onStop(){
        System.out.println("Stopped the crystal chest script");
    }

    @Override
    public void poll() {
        for (Branch branch : branches) {
            if (branch.validate()) {
                branchStatus = branch.status();
                for (Leaf leaf : branch.leaves) {
                    if (leaf.validate()) {
                        leafStatus = leaf.status();
                        leaf.execute();
                        return;
                    }
                }
            }
        }

//        System.out.println("valid ring " + Equipment.stream().filter(e -> e.name().contains("Ring of dueling")).count());
    }

    @Subscribe
    public void InventoryChange(InventoryChangeEvent e){
        if(!Bank.opened()) {
            CrystalConstants.earnedValue += CrystalConstants.gpValues.get(e.getItemName()) * e.getQuantityChange();
            if(e.getItemName().equals("Crystal key")){
                CrystalConstants.keysUsed += 1;
            }
        }
    }
}
