package klumel8.Lavas.Branches.AtAltar.Leaves;

import com.android.tools.r8.code.L;
import klumel8.Lavas.Framework.Leaf;
import klumel8.Lavas.store.LavaConstants;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class TeleBank extends Leaf {
    LavaConstants lc = new LavaConstants();

    @Override
    public boolean validate() {
        return Store.fullPouches.isEmpty() && Inventory.stream().name("Pure essence").isEmpty();
    }

    @Override
    public void execute() {
        if(Movement.energyLevel() > 20 || Store.useStaminas) {
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
