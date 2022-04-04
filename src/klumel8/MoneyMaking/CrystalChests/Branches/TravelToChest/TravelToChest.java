package klumel8.MoneyMaking.CrystalChests.Branches.TravelToChest;

import klumel8.MoneyMaking.CrystalChests.CrystalConstants;
import klumel8.KlumAPI.Framework.Branch;
import klumel8.KlumAPI.Framework.Leaf;
import org.powbot.api.rt4.Equipment;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Players;

import java.util.List;

public class TravelToChest extends Branch {
    public final List<Leaf> leaves;

    public TravelToChest(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {

        return Inventory.stream().id(CrystalConstants.keyID).count() >= CrystalConstants.keyAmount
                && !CrystalConstants.chestArea.contains(Players.local().tile())
                && Equipment.stream().filter(e -> e.name().contains("Ring of dueling")).count() != 0
                && Inventory.stream().name("Teleport to house").isNotEmpty();
    }

    @Override
    public String status() {
        return "Travelling to crystal chest";
    }
}
