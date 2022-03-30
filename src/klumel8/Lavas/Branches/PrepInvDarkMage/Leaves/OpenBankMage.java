package klumel8.Lavas.Branches.PrepInvDarkMage.Leaves;

import klumel8.Framework.Leaf;
import klumel8.Lavas.store.LavaConstants;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Objects;

public class OpenBankMage extends Leaf {
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
