package klumel8.Lavas.Branches.BankLavaRun.Leaves;

import klumel8.Lavas.Framework.Leaf;
import klumel8.Lavas.KlumScripts;
import klumel8.Lavas.store.Store;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;

public class WithdrawEarthTalisman extends Leaf {
    KlumScripts ks = new KlumScripts();
    @Override
    public boolean validate() {
        return (Inventory.stream().name("Earth talisman").count() < Store.earthTalismanAmount) && Bank.opened();
    }

    @Override
    public void execute() {
        ks.makeSpace();
        Bank.withdraw("Earth talisman", (int) (Store.earthTalismanAmount - Inventory.stream().name("Earth talisman").count()));
    }

    @Override
    public String status() {
        return "fetching earth talisman";
    }
}
