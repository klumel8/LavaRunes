package klumel8.CrystalChests.Branches.TravelToChest.Leaves;

import klumel8.CrystalChests.CrystalConstants;
import klumel8.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Players;
import org.powbot.api.rt4.stream.locatable.interactive.GameObjectStream;

public class OpenDoor extends Leaf {
    @Override
    public boolean validate() {
        GameObjectStream doors = Objects.stream().name("Door").nearest(CrystalConstants.doorTile);

        if(doors.isEmpty()){
            return false;
        }

        return CrystalConstants.doorTile.distanceTo(Players.local().tile()) < 6 && doors.first().actions().contains("Open");
    }

    @Override
    public void execute() {
        GameObject door = Objects.stream().name("Door").nearest(CrystalConstants.doorTile).first();
        door.interact("Open");
        Condition.wait(() -> !door.valid(), 100, 40);
    }

    @Override
    public String status() {
        return "opening door";
    }
}
