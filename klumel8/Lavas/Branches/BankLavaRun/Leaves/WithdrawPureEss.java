package klumel8.Lavas.Branches.BankLavaRun.Leaves;

import klumel8.Lavas.Framework.Leaf;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;

public class WithdrawPureEss extends Leaf {
    @Override
    public boolean validate() {
        return !Inventory.isFull();
    }

    @Override
    public void execute() {
        Bank.withdraw("Pure essence", Bank.Amount.ALL);
    }

    @Override
    public String status() {
        return "fetching pure ess";
    }
}
