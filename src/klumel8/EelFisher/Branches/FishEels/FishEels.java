package klumel8.EelFisher.Branches.FishEels;

import klumel8.EelFisher.EelMain;
import klumel8.Framework.Branch;
import klumel8.Framework.Leaf;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Players;

import java.util.List;

public class FishEels extends Branch {
    public final List<Leaf> leaves;

    public FishEels(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        int fishAnimationId = 622;
        return Players.local().animation() != fishAnimationId && Inventory.stream().name(EelMain.fishName).isEmpty()
                && Inventory.stream().name("Fishing bait").isNotEmpty();
    }

    @Override
    public String status() {
        return "Fishing branch";
    }
}
