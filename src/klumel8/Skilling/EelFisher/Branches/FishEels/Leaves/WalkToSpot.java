package klumel8.Skilling.EelFisher.Branches.FishEels.Leaves;

import klumel8.Skilling.EelFisher.EelMain;
import klumel8.KlumAPI.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.stream.locatable.interactive.NpcStream;

public class WalkToSpot extends Leaf {

    @Override
    public boolean validate() {
        NpcStream fishSpots = Npcs.stream().id(EelMain.spotId).nearest();
        if(fishSpots.isEmpty()){
            return false;
        }
        Npc fishSpot = fishSpots.first();
        return fishSpot.tile().distanceTo(Players.local().tile()) >= 8;
    }

    @Override
    public void execute() {
        Npc fishSpot = Npcs.stream().id(EelMain.spotId).nearest().first();
        if(fishSpot.valid() && Movement.step(fishSpot.tile())) {
            Condition.wait(() -> fishSpot.tile().distanceTo(Players.local().tile()) < 8, 100, 50);
        }
    }

    @Override
    public String status() {
        return "Walking to spot";
    }
}
