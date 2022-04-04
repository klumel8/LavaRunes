package klumel8.Skilling.MonkeyTrainer.Branches.BankHandler.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.MonkeyTrainer.Shared;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.mobile.script.ScriptManager;

public class WithdrawPots extends Leaf {
    @Override
    public boolean validate() {
        return Inventory.stream().name("Prayer potion(4)").isEmpty()
                && Bank.opened();
    }

    @Override
    public void execute() {
        if(Shared.chinning) {
            if(Bank.stream().name("Ranging potion(4)").isEmpty()){
                Shared.leafStatus = "OUT OF RANGING POTS(4)";
                System.out.println("OUT OF RANGING POTS(4)");
                Condition.sleep(100000);
                ScriptManager.INSTANCE.stop();
                return;
            }

            if(Bank.withdraw("Ranging potion(4)", 3)) {
                Condition.wait(() -> Inventory.stream().name("Ranging potion(4)").isNotEmpty(), 100, 24);
            }
        }

        if (Bank.stream().name("Prayer potion(4)").isEmpty()) {
            Shared.leafStatus = "OUT OF PRAYER POTS(4)";
            System.out.println("OUT OF PRAYER POTS(4)");
            Condition.sleep(100000);
            ScriptManager.INSTANCE.stop();
            return;
        }

        if(Bank.withdraw("Prayer potion(4)", Bank.Amount.ALL)) {
            Condition.wait(() -> Inventory.stream().name("Prayer potion(4)").isNotEmpty(), 100, 24);
        }
    }

    @Override
    public String status() {
        return "Withdrawing potions";
    }
}
