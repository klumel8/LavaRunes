package klumel8.Skilling.MonkeyTrainer.Branches.BoneHandler.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.MonkeyTrainer.Shared;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.*;

public class DistributeBones extends Leaf {
    @Override
    public boolean validate() {
        if(Inventory.isFull() && Inventory.stream().name("Bones").isNotEmpty()){
            Shared.droppingBones = true;
        }

        if(Inventory.stream().name("Bones").isEmpty()){
            Shared.droppingBones = false;
        }
        return Shared.droppingBones;
    }

    @Override
    public void execute() {
        int N = Shared.dropTiles.size();
        for (int i = 0; i < N; i++){
            Tile dropTile = Shared.dropTiles.get(i);
            //System.out.println("bone count on droptile " + GroundItems.stream().name("Bones").filtered(b -> b.tile().equals(dropTile)).count());
            if(GroundItems.stream().name("Bones").at(dropTile).isEmpty() && !Shared.trapTiles.contains(dropTile)){
                if(dropTile.matrix().interact("Walk here")){
                Condition.wait(() -> dropTile.distanceTo(Players.local().tile()) < 1, 100, 16);
                Item bone = Inventory.stream().name("Bones").first();
                if(bone.valid() && bone.interact("Drop")) {
                    Condition.wait(() -> !bone.valid(), 100, 12);
                    return;
                }
                }
            }
        }
    }

    @Override
    public String status() {
        return "Redeploying bones";
    }
}
