package klumel8.MonkeyTrainer.Branches.MonkeyHandler.Leaves;

import klumel8.Framework.Leaf;
import klumel8.MonkeyTrainer.Shared;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Players;

public class ReAggro extends Leaf {
    @Override
    public boolean validate() {
        double a = (double) Shared.monkeys.count() + 0.001;
        double b = (double) Shared.aggroMonkeys;
        long lastTime = System.currentTimeMillis() - Shared.lastAggroReset;

        if(Shared.monkeyCount == Shared.aggroMonkeys) {
            Shared.deAggroCount = 0;
            return false;
        }

        return Shared.monkeyCount != Shared.aggroMonkeys && lastTime > 600*1000
                && Shared.boneTile.equals(Tile.getNil());
    }

    @Override
    public void execute() {
        System.out.println("Aggro counter: " + Shared.deAggroCount);
        if(Shared.deAggroCount > 3){
            Tile farTile = new Tile(2753,9133,0);
            Movement.walkTo(farTile);
            Condition.wait(() -> farTile.distanceTo(Players.local().tile()) < 5, 100, 50);
            Movement.walkTo(Shared.baseTile);
            Condition.wait(() -> Shared.baseTile.distanceTo(Players.local().tile()) < 5, 100, 50);
            Shared.deAggroCount = 0;
            Shared.lastAggroReset = System.currentTimeMillis();
        }else{
            Shared.deAggroCount++;
            Condition.sleep(600);
        }


//        2783,9127;
//        2753,9133;
    }

    @Override
    public String status() {
        return "Checking aggro";
    }
}
