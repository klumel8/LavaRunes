package klumel8.Skilling.WildyAltar.Branches.GoToAltar.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.WildyAltar.Shared;
import org.powbot.api.rt4.Movement;

public class WalkToAltar extends Leaf {
    @Override
    public boolean validate() {
        return Movement.newTilePath(Shared.path).next().loaded();
    }

    @Override
    public void execute() {
        Movement.newTilePath(Shared.path).traverse();
    }

    @Override
    public String status() {
        return "Walking to altar";
    }
}
