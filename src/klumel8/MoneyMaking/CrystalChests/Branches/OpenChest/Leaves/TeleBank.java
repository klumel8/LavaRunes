package klumel8.MoneyMaking.CrystalChests.Branches.OpenChest.Leaves;

import klumel8.MoneyMaking.CrystalChests.CrystalConstants;
import klumel8.KlumAPI.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class TeleBank extends Leaf {

    @Override
    public boolean validate() {
        return Inventory.stream().id(CrystalConstants.keyID).count() == 0 || (Inventory.emptySlotCount() <= 4 && Inventory.stream().name(CrystalConstants.unwantedItems).isEmpty());
    }

    @Override
    public void execute() {
        if(Game.tab() != Game.Tab.EQUIPMENT){
            Game.tab(Game.Tab.EQUIPMENT);
        }
        Item ring = Equipment.itemAt(Equipment.Slot.RING);
        if(ring.valid() && ring.interact("Castle wars")) {
            Condition.wait(() -> CrystalConstants.bankArea.contains(Players.local().tile()), 200, 30);
        }
    }

    @Override
    public String status() {
        return "Teleporting to bank";
    }
}
