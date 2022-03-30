package klumel8.CrystalChests.Branches.TravelToChest.Leaves;

import klumel8.CrystalChests.CrystalConstants;
import klumel8.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Players;
import org.powbot.proto.rt4.Player;

public class WalkToDoor extends Leaf {
    @Override
    public boolean validate() {
        return CrystalConstants.doorTile.distanceTo(Players.local().tile()) > 8;
    }

    @Override
    public void execute() {
        Movement.step(CrystalConstants.doorTile);
        Condition.wait(() -> CrystalConstants.doorTile.distanceTo(Players.local().tile()) < 7, 100, 50);
    }

    @Override
    public String status() {
        return "Walking to door";
    }
}
