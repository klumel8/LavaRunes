package klumel8.MoneyMaking.CrystalChests.Branches.TravelToChest.Leaves;

import klumel8.MoneyMaking.CrystalChests.CrystalConstants;
import klumel8.KlumAPI.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Players;
import org.powbot.api.rt4.stream.item.InventoryItemStream;
import org.powbot.mobile.script.ScriptManager;

public class TeleToChest extends Leaf {
    @Override
    public boolean validate() {
        return !CrystalConstants.globalChestArea.contains(Players.local().tile()) && !Bank.opened();
    }

    @Override
    public void execute() {
        InventoryItemStream tabs = Inventory.stream().name("teleport to house");

        if(!tabs.first().valid()){
            System.out.println("Out of tabs!");
            ScriptManager.INSTANCE.stop();
            return;
        }

        if(tabs.first().interact("Outside")){
            Condition.wait(() -> CrystalConstants.globalChestArea.contains(Players.local().tile()), 100, 30);
        }
    }

    @Override
    public String status() {
        return "Teleporting to chest";
    }
}
