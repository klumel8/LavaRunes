package klumel8.MonkeyTrainer.Branches.StatsUpkeep.Leaves;

import klumel8.Framework.Leaf;
import klumel8.MonkeyTrainer.Shared;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Combat;
import org.powbot.api.rt4.Inventory;

public class EatFood extends Leaf {
    @Override
    public boolean validate() {
        return Combat.health() < Combat.maxHealth() - 20;
    }

    @Override
    public void execute() {
        if(Inventory.stream().name(Shared.food).isEmpty()){
            return;
        }
        Inventory.stream().name(Shared.food).first().interact("Eat");
        Condition.wait(() -> Combat.health() > Combat.maxHealth() - 20, 100, 18);
    }

    @Override
    public String status() {
        return "Eating food";
    }
}
