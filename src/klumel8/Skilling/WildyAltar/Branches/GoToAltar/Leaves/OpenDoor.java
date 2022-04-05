package klumel8.Skilling.WildyAltar.Branches.GoToAltar.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.WildyAltar.Shared;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Camera;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Players;

public class OpenDoor extends Leaf {
    @Override
    public boolean validate() {
        GameObject door = Objects.stream().name("Large door").first();
        return Shared.altarTile.distanceTo(Players.local().tile()) < 20
                && !Shared.altarTile.reachable()
                && door.valid()
                && door.tile().distanceTo(Players.local().tile()) < 12;
    }

    @Override
    public void execute() {
        GameObject door = Objects.stream().name("Large door").first();
        if(!door.inViewport()){
            Camera.turnTo(door);
        }
        door.interact("Open");
        Condition.wait(() -> Shared.altarTile.reachable(), 100, 24);
    }

    @Override
    public String status() {
        return "Opening door";
    }
}
