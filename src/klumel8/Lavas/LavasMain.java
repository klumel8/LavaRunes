package klumel8.Lavas;

import klumel8.Lavas.Branches.AtAltar.AtAltar;
import klumel8.Lavas.Branches.AtAltar.Leaves.CraftRunes;
import klumel8.Lavas.Branches.AtAltar.Leaves.EmptyPouches;
import klumel8.Lavas.Branches.AtAltar.Leaves.MagicImbue;
import klumel8.Lavas.Branches.BankLavaRun.Leaves.*;
import klumel8.Lavas.Branches.GoToBank.Leaves.TeleBank;
import klumel8.Lavas.Branches.BankLavaRun.BankLavaRun;
import klumel8.Lavas.Branches.GoToAltar.GoToAltar;
import klumel8.Lavas.Branches.GoToAltar.Leaves.TurnToAltar;
import klumel8.Lavas.Branches.GoToAltar.Leaves.ImbueWalkToAltar;
import klumel8.Lavas.Branches.GoToRuins.GoToRuins;
import klumel8.Lavas.Branches.GoToBank.GoToBank;
import klumel8.Lavas.Branches.GoToBank.Leaves.TurnToBank;
import klumel8.Lavas.Branches.GoToBank.Leaves.WalkToBank;
import klumel8.Lavas.Branches.GoToRuins.Leaves.*;
import klumel8.Lavas.Branches.PrepInvDarkMage.Leaves.FetchRunes;
import klumel8.Lavas.Branches.PrepInvDarkMage.Leaves.OpenBankMage;
import klumel8.Lavas.Branches.PrepInvDarkMage.PrepInvDarkMage;
import klumel8.Lavas.Branches.RepairPouch.Leaves.CloseBankMage;
import klumel8.Lavas.Branches.RepairPouch.Leaves.ContactMage;
import klumel8.Lavas.Branches.RepairPouch.Leaves.HandleDialogue;
import klumel8.Lavas.Branches.RepairPouch.RepairPouch;
import klumel8.Framework.Branch;
import klumel8.Framework.Leaf;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.event.*;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.*;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.script.ScriptManager;
import org.powbot.mobile.service.ScriptUploader;

import java.util.ArrayList;
import java.util.List;

@ScriptManifest(
        name = "Klumel's Lavas",
        description = "Supports Earth talisman/magic imbue and staminas/ferox. Start anywhere with all items you want to use in your inventory. Pouches can only be repaired through contact NPC.",
        version = "1.3",
        category = ScriptCategory.Runecrafting,
        scriptId = "2766a346-9b39-4523-bf82-27cd4aa5a255",
        priv = false
)

//Decides not to include because who the fuck doesnt have stams and is going to take 4 earth talisman every time there...
@ScriptConfiguration.List(
        {
                @ScriptConfiguration(
                        name = "type",
                        description = "Do you want to use magic imbue or talismans?",
                        defaultValue = "Magic Imbue",
                        allowedValues = {"Magic Imbue", "Earth Talisman"},
                        optionType = OptionType.STRING
                ),
                @ScriptConfiguration(
                        name = "stamina",
                        description = "Do you want to use stamina potions or ferox enclave?",
                        defaultValue = "Stamina",
                        allowedValues = {"Stamina", "Ferox enclave"},
                        optionType = OptionType.STRING
                )
        }
)

public class LavasMain extends AbstractScript {
    public List<Branch> branches = new ArrayList<>();
    String branchStatus = "none";
    long lastSucces = System.currentTimeMillis();

    public static void main(String[] args){
        //ProcessBuilder builder = new ProcessBuilder("C:\\Users\\flori\\.powbot\\android\\platform-tools\\adb.exe", "-s", "127.0.0.1:5565", "forward", "tcp:61666", "tcp:61666");
        //builder.start();
            new ScriptUploader().uploadAndStart("Klumel's Lavas", "N/A", "127.0.0.1:5575", true, false);
    }

    @Override
    public void onStart(){
        Condition.sleep(300);
        System.out.println("Started " + getManifest().name() + " " + getManifest().version() + "!");
        String useStamina = getOption("stamina");
        String talismanType = getOption("type");

        Store.magicImbue = talismanType.equals("Magic Imbue");
        Store.useStaminas = useStamina.equals("Stamina");
        Store.getPouches();
        //System.out.println("pouch cap"+Store.pouchCapacity());
        Store.setInitialItems();
        Store.lastImbue = System.currentTimeMillis() - 20000;

        branches.add(new AtAltar(new EmptyPouches(),new CraftRunes(), new MagicImbue()));
        branches.add(new GoToBank(new TeleBank(), new TurnToBank(), new WalkToBank()));
        branches.add(new BankLavaRun(new OpenBankLava(), new WithdrawEarthTalisman(), new WithdrawStamina(), new DepositRunes(),
                new Jewellery(), new FillPouches(), new WithdrawPureEss()));
        branches.add(new GoToRuins(new CloseBankRun(), new EnterRuins(), new TeleDuelArena(), new TurnToRuins(), new WalkToRuins(), new AlwaysRun(), new DrinkStamina(), new DrinkPool()));
        branches.add(new GoToAltar(new TurnToAltar(), new ImbueWalkToAltar()));
        branches.add(new PrepInvDarkMage(new FetchRunes(), new OpenBankMage()));
        branches.add(new RepairPouch(new CloseBankMage(), new ContactMage(), new HandleDialogue()));

        Paint paint = new PaintBuilder().trackSkill(Skill.Runecrafting)
                .addString(() -> lavasMade())
                .addString(() -> leafMessage())
                .build();
        addPaint(paint);
    }

    public String lavasMade(){
        return "Lava runes made: " + Store.lavasMade;
    }

    @Override
    public void onStop(){
        System.out.println("");
    }

    @Override
    public void poll() {
        for (Branch branch : branches) {
            if (branch.validate()) {
                branchStatus = branch.status();
                for (Leaf leaf : branch.leaves){
                    if(leaf.validate()){
                        Store.leafStatus = leaf.status();
                        leaf.execute();
                        lastSucces = System.currentTimeMillis();
                    }
                }
            }
        }
        if(System.currentTimeMillis() - lastSucces > 30000 ){
            System.out.println("timed out, stopping script");
            System.out.println("last leaf: " + Store.leafStatus);
            ScriptManager.INSTANCE.stop();
        }
    }

    @com.google.common.eventbus.Subscribe
    void onInventoryChange(InventoryChangeEvent evt){
        if(evt.getItemName().equals("Lava rune") && evt.getQuantityChange() > 0){
            Store.lavasMade += evt.getQuantityChange();
        }
    }

    String leafMessage(){
        if (!Store.errorMsg.equals("")){
            return Store.errorMsg;
        }else{
            return Store.leafStatus;
        }
    }
}
