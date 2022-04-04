package klumel8.Skilling.Lavas.Branches.GoToBank.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import org.powbot.api.rt4.Camera;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Players;

public class TurnToBank extends Leaf {
    @Override
    public boolean validate() {
        GameObject bank = Objects.stream().name("Bank Chest").nearest().first();
        return bank.tile().distanceTo(Players.local().tile()) <= 8 && !bank.inViewport();
    }

    @Override
    public void execute() {
        Camera.turnTo(Objects.stream().name("Bank Chest").nearest().first());
    }

    @Override
    public String status() {
        return "turn to bank";
    }
}
