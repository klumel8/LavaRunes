package klumel8.Lavas.Branches.BankLavaRun.Leaves;

import klumel8.Lavas.Framework.Leaf;
import klumel8.Lavas.KlumScripts;
import klumel8.Lavas.store.Store;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;

public class WithdrawStamina extends Leaf {
    KlumScripts ks = new KlumScripts();
    @Override
    public boolean validate() {
        return Inventory.stream().filtered(i -> i.name().contains("Stamina potion")).isEmpty() && Store.useStaminas && Bank.opened();
    }

    @Override
    public void execute() {
        ks.makeSpace();
        Bank.withdraw("Stamina potion(4)", 1);
    }

    @Override
    public String status() {
        return "fetching stam pot";
    }
}
