package klumel8.Skilling.WildyAltar.Branches.BankHandler;

import klumel8.KlumAPI.Framework.Branch;
import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.WildyAltar.Shared;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Combat;
import org.powbot.api.rt4.Inventory;

import java.util.List;

public class BankHandler extends Branch {
    public final List<Leaf> leaves;

    public BankHandler(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        return Bank.opened() || (Inventory.isFull() && Shared.hasAllItems() && Combat.wildernessLevel() == -1);
    }

    @Override
    public String status() {
        return "Bank Handler";
    }
}
