package klumel8.Skilling.GemMiner.Branches.TemplateBranch;

import klumel8.KlumAPI.Framework.Branch;
import klumel8.KlumAPI.Framework.Leaf;

import java.util.List;

public class TemplateBranch extends Branch {
    public final List<Leaf> leaves;

    public TemplateBranch(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public String status() {
        return "";
    }
}
