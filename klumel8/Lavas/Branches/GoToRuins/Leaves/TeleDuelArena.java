package klumel8.Lavas.Branches.GoToRuins.Leaves;

import klumel8.Lavas.Framework.Leaf;
import klumel8.Lavas.store.LavaConstants;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class TeleDuelArena extends Leaf {
    LavaConstants lc = new LavaConstants();

    @Override
    public boolean validate() {
        return !Bank.opened() && !lc.ruinsArea.contains(Players.local()) && !lc.altarArea.contains(Players.local()) &&
                !(lc.feroxBankArea.contains(Players.local()) && Movement.energyLevel() < lc.runThreshold + 10);
    }

    @Override
    public void execute() {
        ringTeleport("Duel Arena");
        Condition.wait(() -> lc.ruinsArea.contains(Players.local()), 100, 24);
    }

    @Override
    public String status() {
        return "teleporting to duel arena";
    }

    boolean ringTeleport(String destination){
        if(Game.tab() != Game.Tab.EQUIPMENT) {
            Game.tab(Game.Tab.EQUIPMENT);
            Condition.wait(() -> Game.tab() == Game.Tab.EQUIPMENT, 100, 18);
        }
        return Equipment.itemAt(Equipment.Slot.RING).interact(destination);
    }
}
