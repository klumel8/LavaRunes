package klumel8.MonkeyTrainer.Branches.BankHandler.Leaves;

import klumel8.Framework.Leaf;
import klumel8.MonkeyTrainer.Shared;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.mobile.script.ScriptManager;

public class WithdrawFood extends Leaf {
    @Override
    public boolean validate() {
        return Inventory.stream().name(Shared.food).isEmpty();
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
        Bank.withdraw(Shared.food, 3);
        Condition.wait(() -> Inventory.stream().name(Shared.food).isNotEmpty(), 100, 24);
    }

    @Override
    public String status() {
        return "Withdrawing food";
    }
}
