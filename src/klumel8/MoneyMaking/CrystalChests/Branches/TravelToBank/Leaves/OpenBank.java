package klumel8.MoneyMaking.CrystalChests.Branches.TravelToBank.Leaves;

import klumel8.MoneyMaking.CrystalChests.CrystalConstants;
import klumel8.KlumAPI.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.stream.locatable.interactive.GameObjectStream;

public class OpenBank extends Leaf {
    @Override
    public boolean validate() {
        return CrystalConstants.bankTile.matrix().isRendered() && CrystalConstants.bankTile.matrix().inViewport();
    }

    @Override
    public void execute() {
        GameObjectStream chests = Objects.stream().id(CrystalConstants.castleBankID);

        if(chests.first().valid() && chests.first().interact("Use")) {
            Condition.wait(() -> Bank.opened(), 100, 40);
        }
    }

    @Override
    public String status() {
        return "Interacting with bank";
    }
}
