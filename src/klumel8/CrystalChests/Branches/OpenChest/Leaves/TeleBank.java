package klumel8.CrystalChests.Branches.OpenChest.Leaves;

import klumel8.CrystalChests.Branches.OpenChest.OpenChest;
import klumel8.CrystalChests.CrystalConstants;
import klumel8.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Equipment;
import org.powbot.api.rt4.Game;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Players;

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
        Equipment.itemAt(Equipment.Slot.RING).interact("Castle wars");
        Condition.wait(() -> CrystalConstants.bankArea.contains(Players.local().tile()), 200, 30);
    }

    @Override
    public String status() {
        return "Teleporting to bank";
    }
}
