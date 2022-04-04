package klumel8.MoneyMaking.CrystalChests.Branches.OpenChest;

import klumel8.MoneyMaking.CrystalChests.CrystalConstants;
import klumel8.KlumAPI.Framework.Branch;
import klumel8.KlumAPI.Framework.Leaf;
import org.powbot.api.rt4.Players;

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
