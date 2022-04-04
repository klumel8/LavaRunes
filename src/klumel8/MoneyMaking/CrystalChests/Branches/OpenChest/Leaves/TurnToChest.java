package klumel8.MoneyMaking.CrystalChests.Branches.OpenChest.Leaves;

import klumel8.MoneyMaking.CrystalChests.CrystalConstants;
import klumel8.KlumAPI.Framework.Leaf;
import org.powbot.api.rt4.Camera;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.stream.locatable.interactive.GameObjectStream;

public class TurnToChest extends Leaf {
    @Override
    public boolean validate() {
        GameObjectStream chests = Objects.stream().id(CrystalConstants.chestID);

        if(chests.isEmpty()){
            return false;
        }

        GameObject chest = chests.first();

        return !chest.inViewport() && chest.isRendered();
    }

    @Override
    public void execute() {
        Camera.turnTo(Objects.stream().id(CrystalConstants.chestID).first());
    }

    @Override
    public String status() {
        return "Turning to chest";
    }
}
