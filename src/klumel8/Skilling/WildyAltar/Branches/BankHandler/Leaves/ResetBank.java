package klumel8.Skilling.WildyAltar.Branches.BankHandler.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.WildyAltar.Shared;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;

public class ResetBank extends Leaf {
    @Override
    public boolean validate() {
        return !Shared.hasAllItems() && Inventory.isFull();
    }

    @Override
    public void execute() {
        Bank.depositInventory();
        System.out.println("Resetting bank");
    }

    @Override
    public String status() {
        return "Resetting inv";
    }
}
