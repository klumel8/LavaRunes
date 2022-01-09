package klumel8.Lavas.Branches.GoToRuins.Leaves;

import klumel8.Lavas.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Movement;

public class DrinkStamina extends Leaf {
    @Override
    public boolean validate() {
        return Movement.energyLevel() < 40;
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
