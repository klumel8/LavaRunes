package klumel8.Skilling.WildyAltar;

import com.google.common.eventbus.Subscribe;
import klumel8.KlumAPI.Framework.Branch;
import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.WildyAltar.Branches.AltarHandler.AltarHandler;
import klumel8.Skilling.WildyAltar.Branches.AltarHandler.Leaves.OfferBones;
import klumel8.Skilling.WildyAltar.Branches.AltarHandler.Leaves.Suicide;
import klumel8.Skilling.WildyAltar.Branches.BankHandler.BankHandler;
import klumel8.Skilling.WildyAltar.Branches.BankHandler.Leaves.*;
import klumel8.Skilling.WildyAltar.Branches.GoToAltar.Leaves.OpenDoor;
import klumel8.Skilling.WildyAltar.Branches.GoToAltar.Leaves.WalkToAltar;
import klumel8.Skilling.WildyAltar.Branches.GoToAltar.GoToAltar;
import klumel8.Skilling.WildyAltar.Branches.GoToBank.GoToBank;
import klumel8.Skilling.WildyAltar.Branches.GoToBank.Leaves.MoveToBank;
import klumel8.Skilling.WildyAltar.Branches.GoToBank.Leaves.OpenBank;
import klumel8.Skilling.WildyAltar.Branches.PKHandler.Leaves.Hopping;
import klumel8.Skilling.WildyAltar.Branches.PKHandler.PKHandler;
import org.powbot.api.Condition;
import org.powbot.api.event.MessageEvent;
import org.powbot.api.event.TickEvent;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.*;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.SettingsManager;
import org.powbot.mobile.ToggleId;
import org.powbot.mobile.script.ScriptManager;
import org.powbot.mobile.service.ScriptUploader;

import java.util.ArrayList;
import java.util.List;

@ScriptManifest(
        name = "Klumel's Prayer",
        description = "Offers bones at the wildy altar. Have burning amulet in bank, and at least one bone in inventory. Scroll the world list such that at least three p2p worlds are VISIBLE.",
        version = "1.0",
        category = ScriptCategory.Prayer,
        priv = true
)

@ScriptConfiguration.List(
        {
                @ScriptConfiguration(
                        name = "device",
                        description = "Suicide damage device",
                        defaultValue = "None",
                        allowedValues = {"None", "Dwarven rock cake", "Locator orb"},
                        optionType = OptionType.STRING
                )
//                ,
//                @ScriptConfiguration(
//                        name = "pvp",
//                        description = "Use pvp worlds",
//                        defaultValue = "false",
//                        optionType = OptionType.BOOLEAN
//                ),
//                @ScriptConfiguration(
//                        name = "stop-on-PK",
//                        description = "Stop after getting pked (prevents being camped)",
//                        defaultValue = "false",
//                        optionType = OptionType.BOOLEAN
//                )
        }
)

public class WildyAltarMain extends AbstractScript {
    public List<Branch> branches = new ArrayList<>();
    public static String branchStatus = "";
    public String leafStatus = "";

    public static void main(String[] args){
        //ProcessBuilder builder = new ProcessBuilder("C:\\Users\\flori\\.powbot\\android\\platform-tools\\adb.exe", "-s", "127.0.0.1:5565", "forward", "tcp:61666", "tcp:61666");
        new ScriptUploader().uploadAndStart("Klumel's Prayer", "", null, false, false);
    }

    @Override
    public void onStart(){
        Condition.sleep(300);

        System.out.println("Starting script");
        for(Item i : Inventory.stream()){
            if(i.name().contains("bones")){
                Shared.boneName = i.name();
                System.out.print("Detected " + i.name());
                break;
            }
        }

        Shared.selfDamageDevice = getOption("device");
//        Shared.stopOnPK = getOption("stop-on-PK");

        Shared.specialties.add(World.Specialty.NONE);
        Shared.specialties.add(World.Specialty.TRADE);
        Shared.specialties.add(World.Specialty.MINI_GAME);
        Shared.type = World.Type.MEMBERS;

//        System.out.println(""+Worlds.current().getSpecialty() + Worlds.current().type());

        System.out.println("found " + Shared.boneName);
//        Shared.boneName = "Dragon bones";

        branches.add(new PKHandler(new Hopping()));
        branches.add(new GoToBank(new MoveToBank(), new OpenBank()));
        branches.add(new BankHandler(new ResetBank(), new WithdrawDevice(), new WithdrawTele(), new WithdrawBones(), new TeleToWildy()));
        branches.add(new GoToAltar(new OpenDoor(), new WalkToAltar()));
        branches.add(new AltarHandler(new OfferBones(), new Suicide()));

        Paint paint = new PaintBuilder()
                .addString(() -> "Branch status: " + branchStatus)
                .addString(() -> "Leaf status: " + leafStatus)
                .trackSkill(Skill.Prayer)
                .build();
        addPaint(paint);

        if(Shared.boneName.equals("")){
            System.out.println("Didnt find the bones");
            branchStatus = "Didnt find bones in inventory, quiting";
            Condition.sleep(10000);
            ScriptManager.INSTANCE.stop();
        }

        SettingsManager.set(ToggleId.DismissLevelUps, false);

        for(Item i : Equipment.stream()){
            if(!i.name().contains("Burning amulet")){
                branchStatus = "Deposit " + i.name() + " first!!";
                Condition.sleep(10000);
                ScriptManager.INSTANCE.stop();
            }
        }

        for(Item i : Inventory.stream()){
            if(!i.name().contains("Burning amulet")
                    && !i.name().contains("bones")
                    && !i.name().contains("Dwarven rock cake")
                    && !i.name().contains("Locator orb")
            ){
                branchStatus = "Deposit " + i.name() + " first!!";
                Condition.sleep(10000);
                ScriptManager.INSTANCE.stop();
            }
        }

        if(Bank.opened()){
            Bank.close();
        }

        if(Game.tab() != Game.Tab.LOGOUT) {
            Game.tab(Game.Tab.LOGOUT);
        }

        Widgets.widget(182).component(3).click();
        Condition.sleep(600);

        for(Component c : Widgets.widget(69).component(16).components()){
            //System.out.println("index " + c.index() + ", in frame " + (c.centerPoint().getY() > 261 && c.centerPoint().getY() < 408));
            if(
                    c.centerPoint().getY() > 261
                    && c.centerPoint().getY() < 408
                    && Worlds.stream().id(c.index()).first().getType().equals(Shared.type)
                    && Shared.specialties.contains(Worlds.stream().id(c.index()).first().specialty())
            ){
                System.out.println("Added world " + c.index() + " to the list");
                Shared.clickableWorlds.add(c);
            }
        }

        if(Shared.clickableWorlds.isEmpty()){
            System.out.println("Did not find any worlds!!");
            branchStatus = "Didnt find any normal worlds visible in world list";
            Condition.sleep(10000);
            ScriptManager.INSTANCE.stop();
        }

    }

    @Override
    public void poll() {
        //upper bound y_centre = 265, lower y = 408;

        for (Branch branch : branches) {
            if (branch.validate()) {
                branchStatus = branch.status();
                for (Leaf leaf : branch.leaves) {
//                    System.out.println("branch stat" + branchStatus);
                    if (leaf.validate()) {
                        leafStatus = leaf.status();
//                        System.out.println("leaf stat" + leafStatus);
                        leaf.execute();
                        return;
                    }
                }
            }
        }
    }

    @Subscribe
    public void msgListener(MessageEvent evt){
        String msg = evt.getMessage();
        String sender = evt.getSender();
        if(msg.equals("Oh dear, you are dead!") && sender.equals("")){
            System.out.println("Reset the death timeout");
            Shared.deathTimeout = System.currentTimeMillis();
        }
    }

    @Subscribe
    public void tickEvent(TickEvent evt){
        Shared.usedTick = false;
    }
}
