package klumel8.CrystalChests.Branches.TravelToChest.Leaves;

import klumel8.Framework.Leaf;
import org.powbot.api.rt4.Bank;

public class CloseBank extends Leaf {
    @Override
    public boolean validate() {
        return Bank.opened();
    }

    @Override
    public void execute() {
        Bank.close();
    }

    @Override
    public String status() {
        return "Closing bank";
    }
}
