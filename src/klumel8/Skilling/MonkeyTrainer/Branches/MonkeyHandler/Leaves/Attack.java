package klumel8.Skilling.MonkeyTrainer.Branches.MonkeyHandler.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.MonkeyTrainer.Shared;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Players;

public class Attack extends Leaf {
    @Override
    public boolean validate() {
        return Players.local().interacting().name().equals("") || !Players.local().facingTile().equals(Shared.bestMonkey.tile());
//                && Shared.boneTile.equals(Tile.getNil()) && !Shared.droppingBones;
    }

    @Override
    public void execute() {
        if(Shared.bestMonkey.interact("Attack")) {
            Condition.wait(() -> !Players.local().interacting().valid() && Players.local().facingTile().equals(Shared.bestMonkey.tile()), 100, 12);
        }
    }

    @Override
    public String status() {
        return "Attacking";
    }
}
