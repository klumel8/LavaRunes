package klumel8.CrystalChests.Branches.BankLoot;

import klumel8.CrystalChests.Branches.BankLoot.Leaves.DepositItems;
import klumel8.Framework.Branch;
import klumel8.Framework.Leaf;
import org.powbot.api.rt4.Bank;

import java.util.List;

public class BankLoot extends Branch {

    public final List<Leaf> leaves;

    public BankLoot(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        return Bank.opened();
    }

    @Override
    public String status() {
        return "Banking loot";
    }
}
