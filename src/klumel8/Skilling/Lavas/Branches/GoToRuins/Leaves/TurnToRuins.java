package klumel8.Skilling.Lavas.Branches.GoToRuins.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.Lavas.store.LavaConstants;
import org.powbot.api.rt4.Camera;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Players;

public class TurnToRuins extends Leaf {
    LavaConstants lc = new LavaConstants();

    @Override
    public boolean validate() {
        return lc.mysteriousRuins.distanceTo(Players.local()) < 10;
    }

    @Override
    public void execute() {
        Camera.turnTo(Objects.stream().name("Mysterious ruins").first());
    }

    @Override
    public String status() {
        return "turning to ruins";
    }
}
