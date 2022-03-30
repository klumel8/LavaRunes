package klumel8.EelFisher.Branches.ProcessEels;

import klumel8.EelFisher.EelMain;
import klumel8.Framework.Branch;
import klumel8.Framework.Leaf;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Players;

import java.util.List;

public class ProcessEels extends Branch {
    public final List<Leaf> leaves;

    public ProcessEels(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        int fishAnimationId = 622;
        return Players.local().animation() != fishAnimationId && Inventory.stream().name(EelMain.fishName).isNotEmpty();
    }

    @Override
    public String status() {
        return "Processing eels";
    }
}
