package klumel8.MoneyMaking.CrystalChests.Branches.PrepareGE;

import klumel8.MoneyMaking.CrystalChests.CrystalConstants;
import klumel8.KlumAPI.Framework.Branch;
import klumel8.KlumAPI.Framework.Leaf;

import java.util.List;

public class PrepareGE extends Branch {
    public final List<Leaf> leaves;

    public PrepareGE(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        return CrystalConstants.doGE;
    }

    @Override
    public String status() {
        return "Prepping for GE";
    }
}
