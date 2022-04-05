package klumel8.MoneyMaking.CrystalChests.Branches.OpenChest.Leaves;

import klumel8.MoneyMaking.CrystalChests.CrystalConstants;
import klumel8.KlumAPI.Framework.Leaf;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.stream.locatable.interactive.GameObjectStream;

import java.util.Random;

public class WalkToChest extends Leaf {
    @Override
    public boolean validate() {
        GameObjectStream chests = Objects.stream().id(CrystalConstants.chestID);

        if(chests.isEmpty()){
            return false;
        }

        GameObject chest = chests.first();

        if(chest.valid()) {
            return !chest.isRendered();
        }else{
            return false;
        }
    }

    @Override
    public void execute() {
        Random rand = new Random();
        Movement.step(CrystalConstants.chestArea.getCentralTile().derive(rand.nextInt(3)-1,rand.nextInt(3)-1));
    }

    @Override
    public String status() {
        return null;
    }
}
