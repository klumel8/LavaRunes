package klumel8.Lavas.Branches.AtAltar.Leaves;

import klumel8.Lavas.Framework.Leaf;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;

import java.util.Collections;

public class EmptyPouches extends Leaf {

    @Override
    public boolean validate() {
        return !Store.fullPouches.isEmpty() && Inventory.stream().name("Pure essence").isEmpty();
    }

    @Override
    public void execute() {
        Collections.shuffle(Store.pouches);
        int initialSlots = Inventory.emptySlotCount();
        int virtualEmpty = initialSlots;
        for (String pouch : Store.pouches) {
            if (virtualEmpty >= Store.pouchSize(pouch) && Inventory.stream().name(pouch).isNotEmpty() && Store.fullPouches.contains(pouch)) {
                initialSlots = Inventory.emptySlotCount();
                if (Inventory.stream().name(pouch).first().interact("Empty")) {
                    Store.setPouchEmpty(pouch);
                    virtualEmpty -= Store.pouchSize(pouch);
                }
            }
        }
        int finalInitialSlots = initialSlots;
        Condition.wait(() -> (Inventory.emptySlotCount() != finalInitialSlots), 100, 20);
    }

    @Override
    public String status() {
        return "emptying pouches";
    }
}
