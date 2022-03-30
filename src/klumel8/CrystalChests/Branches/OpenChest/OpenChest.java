package klumel8.CrystalChests.Branches.OpenChest;

import klumel8.CrystalChests.CrystalConstants;
import klumel8.Framework.Branch;
import klumel8.Framework.Leaf;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Players;
import org.powbot.api.rt4.stream.locatable.interactive.GameObjectStream;

import java.util.List;

public class OpenChest extends Branch {

    public final List<Leaf> leaves;

    public OpenChest(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        return CrystalConstants.chestArea.contains(Players.local().tile());
    }

    @Override
    public String status() {
        return "Open chest";
    }
}
