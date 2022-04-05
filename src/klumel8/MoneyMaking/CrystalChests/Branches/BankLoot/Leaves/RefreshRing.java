package klumel8.MoneyMaking.CrystalChests.Branches.BankLoot.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Notifications;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Equipment;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.mobile.script.ScriptManager;

public class RefreshRing extends Leaf {
    @Override
    public boolean validate() {
        return Equipment.stream().filter(e -> e.name().contains("Ring of dueling")).count() == 0;
    }

    @Override
    public void execute() {
        Item ring = Bank.stream().filtered(item -> item.name().startsWith("Ring of dueling")).first();
        if(ring.valid()) {
            if( Bank.withdraw(ring.name(), Bank.Amount.ONE)) {
                Condition.wait(() -> Inventory.stream().name(ring.name()).isNotEmpty(), 100, 20);
                Condition.sleep(600);
                Item ringItem = Inventory.stream().name(ring.name()).first();
                if(ringItem.valid() && ringItem.interact("Wear")) {
                    Condition.wait(() -> Equipment.stream().filter(e -> e.name().contains("Ring of dueling")).count() != 0, 100, 20);
                }
                if(Inventory.stream().name(ring.name()).first().valid()){
                    Bank.deposit(ring.name(), Bank.Amount.ALL);
                }
            }
        } else {
            Notifications.showNotification("No duel rings in bank, stopping script");
            ScriptManager.INSTANCE.stop();
        }
    }

    @Override
    public String status() {
        return "Refreshing ring";
    }
}
