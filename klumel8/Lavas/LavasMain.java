package klumel8.Lavas;

import klumel8.Lavas.Branches.AtAltar.AtAltar;
import klumel8.Lavas.Branches.AtAltar.Leaves.CraftRunes;
import klumel8.Lavas.Branches.AtAltar.Leaves.EmptyPouches;
import klumel8.Lavas.Branches.AtAltar.Leaves.MagicImbue;
import klumel8.Lavas.Branches.AtAltar.Leaves.TeleBank;
import klumel8.Lavas.Branches.BankLavaRun.BankLavaRun;
import klumel8.Lavas.Branches.BankLavaRun.Leaves.*;
import klumel8.Lavas.Branches.GoToAltar.GoToAltar;
import klumel8.Lavas.Branches.GoToAltar.Leaves.TurnToAltar;
import klumel8.Lavas.Branches.GoToAltar.Leaves.WalkToAltar;
import klumel8.Lavas.Branches.GoToRuins.GoToRuins;
import klumel8.Lavas.Branches.GoToRuins.Leaves.*;
import klumel8.Lavas.Branches.GoToBank.GoToBank;
import klumel8.Lavas.Branches.GoToBank.Leaves.TurnToBank;
import klumel8.Lavas.Branches.GoToBank.Leaves.WalkToBank;
import klumel8.Lavas.Branches.PrepInvDarkMage.Leaves.FetchRunes;
import klumel8.Lavas.Branches.PrepInvDarkMage.Leaves.OpenBankMage;
import klumel8.Lavas.Branches.PrepInvDarkMage.PrepInvDarkMage;
import klumel8.Lavas.Branches.RepairPouch.Leaves.CloseBankMage;
import klumel8.Lavas.Branches.RepairPouch.Leaves.ContactMage;
import klumel8.Lavas.Branches.RepairPouch.Leaves.HandleDialogue;
import klumel8.Lavas.Branches.RepairPouch.RepairPouch;
import klumel8.Lavas.Framework.Branch;
import klumel8.Lavas.Framework.Leaf;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.event.*;
import org.powbot.api.rt4.Magic;
import org.powbot.api.rt4.Widgets;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.AbstractScript;
import org.powbot.api.script.OptionType;
import org.powbot.api.script.ScriptConfiguration;
import org.powbot.api.script.ScriptManifest;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.service.ScriptUploader;

import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ScriptManifest(
        name = "KlumLavas",
        description = "Makes lava runes using magic imbue spell. Start anywhere with all items you want to use in your inventory.",
        version = "1.0"
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
    String leafStatus = "none";
    String branchStatus = "none";

    public static void main(String[] args){
            new ScriptUploader().uploadAndStart("KlumLavas", "N/A", "127.0.0.1:5575", true, false);
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
        Store.setInitialItems();
        Store.lastImbue = System.currentTimeMillis() - 20000;

        branches.add(new AtAltar(new CraftRunes(), new EmptyPouches(), new MagicImbue(), new TeleBank()));
        branches.add(new GoToBank(new OpenBankLava(), new TurnToBank(), new WalkToBank()));
        branches.add(new BankLavaRun(new OpenBankLava(), new WithdrawEarthTalisman(), new WithdrawStamina(), new DepositRunes(),
                new Jewellery(), new FillPouches(), new WithdrawPureEss(), new TeleDuelArena()));
        branches.add(new GoToRuins(new CloseBankRun(), new EnterRuins(), new TeleDuelArena(), new TurnToRuins(), new WalkToRuins(), new AlwaysRun(), new DrinkStamina(), new DrinkPool()));
        branches.add(new GoToAltar(new TurnToAltar(), new WalkToAltar()));
        branches.add(new PrepInvDarkMage(new FetchRunes(), new OpenBankMage()));
        branches.add(new RepairPouch(new CloseBankMage(), new ContactMage(), new HandleDialogue()));

        Paint paint = new PaintBuilder().trackSkill(Skill.Runecrafting)
                .addString(() -> lavasMade())
                .addString(() -> leafStatus)
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
                        leafStatus = leaf.status();
                        leaf.execute();
                    }
                }
            }
        }
    }

}
