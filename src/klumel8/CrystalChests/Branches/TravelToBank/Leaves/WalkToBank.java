package klumel8.CrystalChests.Branches.TravelToBank.Leaves;

import klumel8.CrystalChests.CrystalConstants;
import klumel8.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Players;

import java.util.Random;

public class WalkToBank extends Leaf {

    @Override
    public boolean validate() {
        return !CrystalConstants.bankTile.matrix().inViewport();
    }

    @Override
    public void execute() {
        Random rand = new Random();
        Movement.step(CrystalConstants.bankTile.derive(-rand.nextInt(3), rand.nextInt(3)));
        Condition.wait(() -> CrystalConstants.bankTile.distanceTo(Players.local().tile()) <= 8,100,60);
    }

    @Override
    public String status() {
        return "walking to bank";
    }
}
