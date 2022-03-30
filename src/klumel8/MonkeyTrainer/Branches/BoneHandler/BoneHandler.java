package klumel8.MonkeyTrainer.Branches.BoneHandler;

import klumel8.Framework.Branch;
import klumel8.Framework.Leaf;
import klumel8.MonkeyTrainer.Shared;
import org.powbot.api.Tile;
import org.powbot.api.rt4.GroundItem;
import org.powbot.api.rt4.GroundItems;
import org.powbot.api.rt4.Inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoneHandler extends Branch {
    public final List<Leaf> leaves;

    public BoneHandler(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {

        //Start with checking if player should drop bones
        updateDropping();

        //Update bones on the ground bones on the ground
        updateBones();

        return Shared.pickingBones || Shared.droppingBones;
    }

    @Override
    public String status() {
        return "Bone Handler";
    }

    public void updateBones(){
        //Start by updating the bones on the ground
        Shared.bones = GroundItems.stream().name("Bones").filtered(b -> !Shared.trapTiles.contains(b.tile()));

        //update the bones hashmap
        Map<Tile, Integer> updatedStacks = new HashMap<Tile, Integer>();

        //Make a hashmap of the bones by looping through all of them
        for (GroundItem bone : Shared.bones) {
            int bonePileSize = 1;
            if (updatedStacks.containsKey(bone.tile())) {
                bonePileSize = updatedStacks.get(bone.tile()) + 1;
            }
            updatedStacks.put(bone.tile(), bonePileSize);
        }

        Shared.boneStacks = updatedStacks;

        //Bones at the best tile
        int bestPile = 0;

        if(Shared.bestBoneTile != null && Shared.boneStacks.containsKey(Shared.bestBoneTile)){
            bestPile = Shared.boneStacks.get(Shared.bestBoneTile);
        }

        //If old pile is running low search for new pile
        if(bestPile <= 2) {
            for (Tile boneTile : Shared.boneStacks.keySet()) {
                int pileSize = Shared.boneStacks.get(boneTile);
                if (pileSize > bestPile) {
                    bestPile = pileSize;
                    Shared.bestBoneTile = boneTile;
                }
            }
        }

        Shared.bestPileSize = bestPile;

        if(Shared.droppingBones || Inventory.isFull()){
            Shared.pickingBones = false;
        }else if(Shared.bestPileSize >= 4 && !Shared.pickingBones){
            Shared.pickingBones = true;
        }else if(Shared.bestPileSize <= 2 && Shared.pickingBones){
            Shared.pickingBones = false;
        }
    }

    public void updateDropping(){
        if((Inventory.isFull() && Inventory.stream().name("Bones").count() >= 3) || Inventory.stream().name("Bones").count() >= 6){
            Shared.droppingBones = true;
        }else if(Inventory.stream().name("Bones").isEmpty()){
            Shared.droppingBones = false;
        }
    }
}
