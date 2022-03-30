package klumel8.CrystalChests.Branches.OpenChest.Leaves;

import klumel8.CrystalChests.CrystalConstants;
import klumel8.Framework.Leaf;
import org.powbot.api.rt4.Camera;
import org.powbot.api.rt4.Npc;
import org.powbot.api.rt4.Npcs;
import org.powbot.api.rt4.stream.locatable.interactive.NpcStream;

import java.util.Random;

public class AvoidDruids extends Leaf {
    @Override
    public boolean validate() {
        NpcStream druids = Npcs.stream().name("Druid");
        for (Npc druid : druids){
            if(CrystalConstants.druidBlocked.contains(druid.tile()) && (Camera.yaw() >= 190 || Camera.yaw() < 150)){
                return true;
            }
        }

        return false;
    }

    @Override
    public void execute() {
        Random rand = new Random();

        Camera.angle(150 + rand.nextInt(40));
    }

    @Override
    public String status() {
        return "Moving camera for druids";
    }
}
