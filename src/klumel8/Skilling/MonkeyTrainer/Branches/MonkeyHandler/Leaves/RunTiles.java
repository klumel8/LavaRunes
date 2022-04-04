package klumel8.Skilling.MonkeyTrainer.Branches.MonkeyHandler.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.MonkeyTrainer.Shared;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.Camera;
import org.powbot.api.rt4.Players;

public class RunTiles extends Leaf {

    @Override
    public boolean validate() {
        double a = (double) Shared.monkeys.count() + 0.001;
        double b = (double) Shared.maxStack;
        long lastTime = System.currentTimeMillis() - Shared.lastStacked;
        return b/a <= 0.75 && b < 8 && lastTime > 10000
                && Shared.boneTile.equals(Tile.getNil());
    }

    @Override
    public void execute() {
        if(!Shared.stackTile.matrix().inViewport()){
            Camera.turnTo(Shared.stackTile.matrix());
        }
        if(Shared.stackTile.matrix().interact("Walk here")) {
            Condition.wait(() -> Players.local().tile().equals(Shared.stackTile), 100, 24);
            Condition.sleep(1200);

            if(!Shared.baseTile.matrix().inViewport()){
                Camera.turnTo(Shared.baseTile.matrix());
            }

            if(Shared.baseTile.matrix().interact("Walk here")) {
                Condition.wait(() -> Players.local().tile().equals(Shared.baseTile), 100, 24);
            }

            Shared.lastStacked = System.currentTimeMillis();
        }
    }

    @Override
    public String status() {
        return "";
    }
}
