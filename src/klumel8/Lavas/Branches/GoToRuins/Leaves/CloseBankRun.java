package klumel8.Lavas.Branches.GoToRuins.Leaves;

import klumel8.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;

public class CloseBankRun extends Leaf {
    @Override
    public boolean validate() {
        return Bank.opened();
    }

    @Override
    public void execute() {
        Bank.close();
        Condition.wait(() -> !Bank.opened(), 100, 18);
    }

    @Override
    public String status() {
        return "closing bank";
    }
}
