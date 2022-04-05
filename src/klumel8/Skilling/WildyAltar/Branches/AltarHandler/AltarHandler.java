package klumel8.Skilling.WildyAltar.Branches.AltarHandler;

import klumel8.KlumAPI.Framework.Branch;
import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.WildyAltar.Shared;
import org.powbot.api.rt4.Players;

import java.util.List;

public class AltarHandler extends Branch {
    public final List<Leaf> leaves;

    public AltarHandler(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        return Shared.altarTile.distanceTo(Players.local().tile()) < 3;
    }

    @Override
    public String status() {
        return "Altar handler";
    }
}
