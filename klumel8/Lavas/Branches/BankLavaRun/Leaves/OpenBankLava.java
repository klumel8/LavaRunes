package klumel8.Lavas.Branches.BankLavaRun.Leaves;

import klumel8.Lavas.Framework.Leaf;
import klumel8.Lavas.store.LavaConstants;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Objects;

public class OpenBankLava extends Leaf {
    LavaConstants lc = new LavaConstants();

    @Override
    public boolean validate() {
        return !Bank.opened();
    }

    @Override
    public void execute() {
        GameObject bank = Objects.stream().name("Bank Chest").nearest().first();
        if (bank.interact("Use")){
            Condition.wait(() -> Bank.opened(), 100, 40);
        }
    }

    @Override
    public String status() {
        return "opening bank";
    }
}
