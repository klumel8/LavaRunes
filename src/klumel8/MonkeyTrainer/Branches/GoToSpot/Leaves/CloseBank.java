package klumel8.MonkeyTrainer.Branches.GoToSpot.Leaves;

import klumel8.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;

public class CloseBank extends Leaf {
    @Override
    public boolean validate() {
        return Bank.opened();
    }

    @Override
    public void execute() {
        Bank.close();
        Condition.wait(() -> !Bank.opened(), 100, 24);
    }

    @Override
    public String status() {
        return "Closing bank";
    }
}
