package klumel8.MoneyMaking.CrystalChests.Branches.TravelToBank;

import klumel8.MoneyMaking.CrystalChests.CrystalConstants;
import klumel8.KlumAPI.Framework.Branch;
import klumel8.KlumAPI.Framework.Leaf;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Players;

import java.util.List;

public class TravelToBank extends Branch {
    public final List<Leaf> leaves;

    public TravelToBank(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        return CrystalConstants.bankArea.contains(Players.local().tile()) && !Bank.opened() && Inventory.stream().id(CrystalConstants.keyID).count() != CrystalConstants.keyAmount;
    }

    @Override
    public String status() {
        return "Going to bank";
    }
}
