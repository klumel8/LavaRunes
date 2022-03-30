package klumel8.CrystalChests.Branches.OpenChest.Leaves;

import klumel8.CrystalChests.Branches.OpenChest.OpenChest;
import klumel8.CrystalChests.CrystalConstants;
import klumel8.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.stream.item.InventoryItemStream;
import org.powbot.api.rt4.stream.locatable.interactive.GameObjectStream;

public class UseKey extends Leaf {
    @Override
    public boolean validate() {
        return Inventory.emptySlotCount() >= 4 && Inventory.stream().id(CrystalConstants.keyID).count() > 0 && Objects.stream().id(CrystalConstants.chestID).first().inViewport();
    }

    @Override
    public void execute() {
        InventoryItemStream keys = Inventory.stream().id(CrystalConstants.keyID);

        GameObject chest = Objects.stream().id(CrystalConstants.chestID).first();

        if(keys.first().interact("Use")) {

            if(chest.interact("Use")){

                if(Condition.wait(() -> !chest.valid(), 100, 30)){
                    System.out.println("figured out a key was used");
                }

                System.out.println("Ended wait");
            }
        }
    }

    @Override
    public String status() {
        return "Using a key";
    }
}
