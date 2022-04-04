package klumel8.Skilling.MonkeyTrainer.Branches.BankHandler.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.MonkeyTrainer.Shared;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.mobile.script.ScriptManager;

public class WithdrawFood extends Leaf {
    @Override
    public boolean validate() {
        return Inventory.stream().name(Shared.food).isEmpty() && Bank.opened();
    }

    @Override
    public void execute() {
        if(Bank.stream().name(Shared.food).isEmpty()){
            Shared.leafStatus = "OUT OF FOOD";
            System.out.println("OUT OF FOOD");
            Condition.sleep(100000);
            ScriptManager.INSTANCE.stop();
            return;
        }
        if(Bank.withdraw(Shared.food, 4)) {
            Condition.wait(() -> Inventory.stream().name(Shared.food).isNotEmpty(), 100, 24);
        }
    }

    @Override
    public String status() {
        return "Withdrawing food";
    }
}
