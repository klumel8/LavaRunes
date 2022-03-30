package klumel8.Lavas.Branches.GoToRuins.Leaves;

import klumel8.Framework.Leaf;
import klumel8.Lavas.store.LavaConstants;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class DrinkPool extends Leaf {
    LavaConstants lc = new LavaConstants();

    @Override
    public boolean validate() {
        return !Bank.opened() && Objects.stream().name("Pool of refreshment").isNotEmpty() &&
                 Movement.energyLevel() < lc.runThreshold + 10;
    }

    @Override
    public void execute() {
        GameObject pool = Objects.stream().name("Pool of refreshment").nearest().first();

        if(!pool.inViewport()){
            Camera.turnTo(pool.tile());
        }

        if(pool.interact("Drink")){
            Condition.wait(() -> Movement.energyLevel() > lc.runThreshold + 10, 100, 50);
        }

    }

    @Override
    public String status() {
        return "drinking from pool";
    }
}
