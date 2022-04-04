package klumel8.MoneyMaking.CrystalChests.Branches.BankLoot.Leaves;

import klumel8.MoneyMaking.CrystalChests.CrystalConstants;
import klumel8.KlumAPI.Framework.Leaf;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;

public class DepositItems extends Leaf {

    @Override
    public boolean validate() {

        if(Inventory.stream().name(CrystalConstants.sellItems).isNotEmpty() || Inventory.stream().name(CrystalConstants.unwantedItems).isNotEmpty()){
            return true;
        }

        if(Inventory.occupiedSlotCount() > CrystalConstants.keyAmount + 5){
            return true;
        }

        return false;
    }

    @Override
    public void execute() {
        Bank.depositInventory();
    }

    @Override
    public String status() {
        return "Depositing items";
    }
}
