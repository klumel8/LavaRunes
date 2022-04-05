package klumel8.Skilling.WildyAltar.Branches.GoToAltar.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.WildyAltar.Shared;
import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.Movement;

public class WalkTileDumb extends Leaf {
    @Override
    public boolean validate() {
        return !Movement.newTilePath(Shared.path).next().loaded();
    }

    @Override
    public void execute() {
        Movement.step(Shared.altarTile);
        Condition.sleep(Random.nextInt(2000, 4000));
    }

    @Override
    public String status() {
        return "force walking to altar";
    }
}
