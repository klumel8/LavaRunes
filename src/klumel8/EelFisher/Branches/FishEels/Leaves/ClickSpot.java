package klumel8.EelFisher.Branches.FishEels.Leaves;

import klumel8.EelFisher.EelMain;
import klumel8.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Npc;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Players;
import org.powbot.api.rt4.stream.locatable.interactive.NpcStream;

public class ClickSpot extends Leaf {
    @Override
    public boolean validate() {
        NpcStream spots = Npcs.stream().id(EelMain.spotId);

        if(spots.isEmpty()){
            return false;
        }

        Npc fishSpot = spots.nearest().first();

        return fishSpot.tile().distanceTo(Players.local().tile()) < 8 && fishSpot.inViewport();
    }

    @Override
    public void execute() {
        Npc fishSpot = Npcs.stream().id(EelMain.spotId).nearest().first();
        fishSpot.interact("Bait");
        int fishAnimationId = 622;
        Condition.wait(() -> Players.local().movementAnimation() == fishAnimationId, 100, 50);
    }

    @Override
    public String status() {
        return "Clicking fishing spot";
    }
}
