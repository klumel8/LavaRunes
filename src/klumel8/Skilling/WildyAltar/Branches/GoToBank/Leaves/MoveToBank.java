package klumel8.Skilling.WildyAltar.Branches.GoToBank.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.WildyAltar.Shared;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.stream.locatable.interactive.GameObjectStream;

public class MoveToBank extends Leaf {

    @Override
    public boolean validate() {

        return Bank.nearest().tile().distanceTo(Players.local().tile()) > 14;
    }

    @Override
    public void execute() {
        if(Shared.lumbyArea.contains(Players.local().tile())){
            System.out.println("detected lumby " + Objects.stream().id(14880).count());
            GameObjectStream trapdoors = Objects.stream().id(14880);
            if (trapdoors.isEmpty()){
                System.out.println("Didnt find trapdoor, error");
                return;
            }

            GameObject trapdoor = trapdoors.first();
            if(trapdoor.valid() && !trapdoor.inViewport()){
                Camera.turnTo(trapdoor);
            }
            if(trapdoor.interact("Climb-down")) {
//            System.out.println(""+trapdoor.actions().get(0));
                Condition.wait(() -> Shared.basementArea.contains(Players.local().tile()), 100, 100);
            }
        }else {
            Movement.moveToBank();
        }
    }

    @Override
    public String status() {
        return "Walking to bank";
    }
}
