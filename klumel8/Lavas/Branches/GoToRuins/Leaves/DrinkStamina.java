package klumel8.Lavas.Branches.GoToRuins.Leaves;

import klumel8.Lavas.Framework.Leaf;
import klumel8.Lavas.store.LavaConstants;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Movement;

public class DrinkStamina extends Leaf {
    LavaConstants lc = new LavaConstants();

    @Override
    public boolean validate() {
        return Movement.energyLevel() < lc.runThreshold && Store.useStaminas;
    }

    @Override
    public void execute() {
        Item stam = Inventory.stream().filtered(s -> s.name().contains("Stamina")).first();
        stam.interact("Drink");
        Condition.wait(() -> !stam.valid(), 100, 12);
    }

    @Override
    public String status() {
        return "drinking stamina";
    }
}
