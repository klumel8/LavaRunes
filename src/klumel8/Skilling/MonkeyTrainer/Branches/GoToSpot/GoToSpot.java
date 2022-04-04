package klumel8.Skilling.MonkeyTrainer.Branches.GoToSpot;

import klumel8.KlumAPI.Framework.Branch;
import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.MonkeyTrainer.Shared;
import org.powbot.api.rt4.Players;

import java.util.List;

public class GoToSpot extends Branch {
    public final List<Leaf> leaves;

    public GoToSpot(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        return !Players.local().tile().equals(Shared.baseTile) && !Shared.pickingBones && !Shared.droppingBones;
    }

    @Override
    public String status() {
        return "Traveling spot";
    }
}
