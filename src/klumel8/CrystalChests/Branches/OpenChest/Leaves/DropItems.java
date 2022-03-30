package klumel8.CrystalChests.Branches.OpenChest.Leaves;

import klumel8.CrystalChests.Branches.OpenChest.OpenChest;
import klumel8.CrystalChests.CrystalConstants;
import klumel8.Framework.Leaf;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.stream.item.InventoryItemStream;

public class DropItems extends Leaf {

    @Override
    public boolean validate() {
        System.out.println("DropItems.validate()");
        if (Inventory.emptySlotCount() >= 4 || Inventory.stream().id(CrystalConstants.keyID).count() == 0){
            System.out.println("No dropping needed");
            return false;
        }

        return Inventory.stream().name(CrystalConstants.unwantedItems).isNotEmpty();
    }

    @Override
    public void execute() {
        InventoryItemStream items = Inventory.stream().name(CrystalConstants.unwantedItems);

        for (Item item : items){
            item.interact("Drop");
        }
    }

    @Override
    public String status() {
        return "Dropping items";
    }
}
