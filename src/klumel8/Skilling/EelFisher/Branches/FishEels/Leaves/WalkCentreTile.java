package klumel8.Skilling.EelFisher.Branches.FishEels.Leaves;

import klumel8.Skilling.EelFisher.EelMain;
import klumel8.KlumAPI.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.Players;
import org.powbot.mobile.script.ScriptManager;

public class WalkCentreTile extends Leaf {
    @Override
    public boolean validate() {
        return Npcs.stream().id(EelMain.spotId).isEmpty() && EelMain.fishName.equals("Sacred eel");
    }

    @Override
    public void execute() {
        if(Players.local().tile().distanceTo(EelMain.centreTile) < 40){
            if(Movement.step(EelMain.centreTile)) {
                Condition.wait(() -> Npcs.stream().id(EelMain.spotId).isNotEmpty(), 100, 50);
            }
        }else{
            EelMain.branchStatus = "Too far from fishing location";
            Condition.sleep(10000);
            ScriptManager.INSTANCE.stop();
        }
    }

    @Override
    public String status() {
        return "Walking centre tile";
    }
}
