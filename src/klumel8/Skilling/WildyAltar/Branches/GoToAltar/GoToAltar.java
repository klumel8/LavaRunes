package klumel8.Skilling.WildyAltar.Branches.GoToAltar;

import klumel8.KlumAPI.Framework.Branch;
import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.WildyAltar.Shared;
import org.powbot.api.rt4.Combat;
import org.powbot.api.rt4.Players;

import java.util.List;

public class GoToAltar extends Branch {
    public final List<Leaf> leaves;

    public GoToAltar(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        return Combat.wildernessLevel() >= 0 && Shared.altarTile.distanceTo(Players.local().tile()) >= 3;
    }

    @Override
    public String status() {
        return "Travel to altar";
    }
}
