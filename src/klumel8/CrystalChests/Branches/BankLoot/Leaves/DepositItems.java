package klumel8.CrystalChests.Branches.BankLoot.Leaves;

import com.android.ddmlib.log.InvalidValueTypeException;
import klumel8.CrystalChests.CrystalConstants;
import klumel8.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.GrandExchange;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.stream.item.InventoryItemStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
