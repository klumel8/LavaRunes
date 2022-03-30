package klumel8.Lavas.Branches.GoToAltar.Leaves;

import klumel8.Framework.Leaf;
import klumel8.Lavas.store.LavaConstants;
import org.powbot.api.rt4.Camera;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Players;

public class TurnToAltar extends Leaf {
    LavaConstants lc = new LavaConstants();
    @Override
    public boolean validate() {
        return lc.altarTile.distanceTo(Players.local().tile()) < 7 && !Objects.stream().name("Altar").first().inViewport();
    }

    @Override
    public void execute() {
        Camera.turnTo(Objects.stream().name("Altar").first());
    }

    @Override
    public String status() {
        return "turning to altar";
    }
}
