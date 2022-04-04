package klumel8.Skilling.Lavas.Branches.GoToBank.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.Lavas.store.LavaConstants;
import klumel8.Skilling.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class TeleBank extends Leaf {
    LavaConstants lc = new LavaConstants();

    @Override
    public boolean validate() {
        return (!lc.feroxBankArea.contains(Players.local()) && !lc.bankArea.contains(Players.local()));
    }

    @Override
    public void execute() {
        if(Movement.energyLevel() > lc.runThreshold || Store.useStaminas) {
            if(ringTeleport("Castle Wars")) {
                Condition.wait(() -> lc.bankArea.contains(Players.local()), 100, 24);
            }
        }else{
            if(ringTeleport("Ferox Enclave")) {
                Condition.wait(() -> lc.feroxBankArea.contains(Players.local()), 100, 24);
            }
        }
    }

    @Override
    public String status() {
        return "teleporting to bank";
    }

    boolean ringTeleport(String destination){
        if(Game.tab() != Game.Tab.EQUIPMENT) {
            Game.tab(Game.Tab.EQUIPMENT);
            Condition.wait(() -> Game.tab() == Game.Tab.EQUIPMENT, 100, 18);
        }
        return Equipment.itemAt(Equipment.Slot.RING).interact(destination);
    }
}
