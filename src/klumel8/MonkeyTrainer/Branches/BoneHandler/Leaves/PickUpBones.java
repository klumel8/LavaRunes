package klumel8.MonkeyTrainer.Branches.BoneHandler.Leaves;

import klumel8.Framework.Leaf;
import klumel8.MonkeyTrainer.Shared;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.GroundItem;
import org.powbot.api.rt4.GroundItems;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.stream.locatable.interactive.GroundItemStream;

public class PickUpBones extends Leaf {
    @Override
    public boolean validate() {
        return !Inventory.isFull() && !Shared.droppingBones;
    }

    @Override
    public void execute() {
        GroundItemStream bones = GroundItems.stream().name("Bones").filtered(b -> b.tile().equals(Shared.bestBoneTile));

        if(bones.isEmpty()){
            Shared.pickingBones = false;
            return;
        }

        GroundItem bone = bones.first();
        bone.interact("Take");
        Condition.wait(() -> !bone.valid(), 100, 10);
    }

    @Override
    public String status() {
        return "Taking bones";
    }
}
