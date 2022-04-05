package klumel8.Skilling.WildyAltar.Branches.AltarHandler.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.WildyAltar.Shared;
import org.powbot.api.rt4.*;

public class OfferBones extends Leaf {
    @Override
    public boolean validate() {
        return Inventory.stream().name(Shared.boneName).isNotEmpty();
    }

    @Override
    public void execute() {
        GameObject altar = Objects.stream().id(411).first();
        Item bone = Inventory.stream().name(Shared.boneName).shuffled().get(0);

        if(!altar.inViewport()){
            Camera.turnTo(altar);
        }

        if(!bone.interact("Use")){
            return;
        }
        altar.interact("Use");
        Shared.usedTick = true;
    }

    @Override
    public String status() {
        return "Offering bones";
    }
}
