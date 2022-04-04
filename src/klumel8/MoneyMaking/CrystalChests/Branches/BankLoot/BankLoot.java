package klumel8.MoneyMaking.CrystalChests.Branches.BankLoot;

import klumel8.KlumAPI.Framework.Branch;
import klumel8.KlumAPI.Framework.Leaf;
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
