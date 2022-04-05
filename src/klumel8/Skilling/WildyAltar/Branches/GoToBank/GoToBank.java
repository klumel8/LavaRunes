package klumel8.Skilling.WildyAltar.Branches.GoToBank;

import klumel8.KlumAPI.Framework.Branch;
import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.WildyAltar.Shared;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Combat;
import org.powbot.api.rt4.Inventory;

import java.util.List;

public class GoToBank extends Branch {
    public final List<Leaf> leaves;

    public GoToBank(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        return Combat.wildernessLevel() < 0 && !Bank.opened()
                && (!Inventory.isFull() || !Shared.hasAllItems())
                && (System.currentTimeMillis() - Shared.hopTimeout) > 1200;
    }

    @Override
    public String status() {
        return "Going to bank";
    }
}
