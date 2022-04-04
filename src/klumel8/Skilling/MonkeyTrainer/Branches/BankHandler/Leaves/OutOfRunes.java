package klumel8.Skilling.MonkeyTrainer.Branches.BankHandler.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.MonkeyTrainer.Shared;
import kotlin.Pair;
import org.powbot.api.Condition;
import org.powbot.api.Notifications;
import org.powbot.api.requirement.RunePowerRequirement;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Players;
import org.powbot.api.rt4.magic.Rune;
import org.powbot.api.rt4.magic.RunePouch;
import org.powbot.mobile.script.ScriptManager;

public class OutOfRunes extends Leaf {
    @Override
    public boolean validate() {
        if(!Shared.bankArea.contains(Players.local().tile()) || Shared.chinning || !Bank.opened()){
            return false;
        }

        if(Inventory.stream().name("Rune pouch").isNotEmpty()) {
            for (RunePowerRequirement rune : Shared.spell.getRequirements()) {
                System.out.println("Trying key" + rune.getPower().getFirstRune().getId());

                int N = 0;
                if(Shared.runeStacks.containsKey(rune.getPower().getFirstRune().getId())){
                    N = Shared.runeStacks.get(rune.getPower().getFirstRune().getId());
                }

                if (N < 100) {
                    System.out.print("Running low on runes");
                    return true;
                }
            }
        }else{
            for (RunePowerRequirement rune : Shared.spell.getRequirements()) {
                Item runes = Inventory.stream().id(rune.getPower().getFirstRune().getId()).first();
                if(runes.valid()){
                    if(runes.stackSize() < 100){
                        System.out.print("Running low on runes");
                        return true;
                    }
                }else{
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void execute() {
        if(Inventory.emptySlotCount() <= 8){
            if(!Bank.deposit("Prayer potion(4)", Bank.Amount.ALL)){
                return;
            }
        }

        for(RunePowerRequirement rune : Shared.spell.getRequirements()){
            int runeID = rune.getPower().getFirstRune().getId();


//            if(!Bank.stream().id(runeID).first().valid() || Bank.stream().id(runeID).first().stackSize() < 100){
//                Notifications.showNotification("Out of " + rune.getPower().name() + " runes.");
////                ScriptManager.INSTANCE.stop();
//                return;
//            }
            if(Bank.stream().id(runeID).first().stackSize() >= 1){
                if(!Bank.withdraw(runeID, Bank.Amount.ALL)){
                    return;
                }
            }
        }

        if(Inventory.stream().name("Rune pouch").isNotEmpty()) {

            if (!Bank.close()) {
                return;
            }

            for (RunePowerRequirement rune : Shared.spell.getRequirements()) {
                Item r = Inventory.stream().id(rune.getPower().getFirstRune().getId()).first();
                int nPouch = 0;

                if(Shared.runeStacks.containsKey(rune.getPower().getFirstRune().getId())){
                    nPouch = Shared.runeStacks.get(rune.getPower().getFirstRune().getId());
                }

                if(r.stackSize() + nPouch < 1000){
                    Notifications.showNotification("Critically low on " + rune.getPower().name() + " , stopping");
                    System.out.println("Critically low on " + rune.getPower().name() + " , stopping");
                    ScriptManager.INSTANCE.stop();
                    return;
                }

                if(r.interact("Use")){
                    Inventory.stream().name("Rune pouch").first().interact("Use");
                }
            }

            if(!Bank.open()){
                return;
            }

            for(RunePowerRequirement rune : Shared.spell.getRequirements()){
                if(Inventory.stream().id(rune.getPower().getFirstRune().getId()).isNotEmpty() && !Bank.deposit(rune.getPower().getFirstRune().getId(), Bank.Amount.ALL)){
                    return;
                }
            }

        }else{
            for (RunePowerRequirement rune : Shared.spell.getRequirements()) {
                if(Inventory.stream().id(rune.getPower().getFirstRune().getId()).first().stackSize() < 1000){
                    Notifications.showNotification("Critically low on " + rune.getPower().name() + " , stopping");
                    System.out.println("Critically low on " + rune.getPower().name() + " , stopping");
                    ScriptManager.INSTANCE.stop();
                    return;
                }
            }
        }

    }

    @Override
    public String status() {
        return "Out of runes";
    }
}
