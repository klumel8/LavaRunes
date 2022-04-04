package klumel8.Skilling.Lavas.Branches.AtAltar.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.Lavas.store.Store;
import org.powbot.api.rt4.Inventory;

import java.util.Collections;

public class EmptyPouches extends Leaf {

    @Override
    public boolean validate() {
        return !Store.fullPouches.isEmpty() && Inventory.stream().name("Pure essence").isEmpty();
    }

    @Override
    public void execute() {
        Store.readyToCraft = false;
        Collections.shuffle(Store.pouches);
        int initialSlots = Inventory.emptySlotCount();
        int virtualEmpty = initialSlots;
        for (String pouch : Store.pouches) {
            if (
                    (virtualEmpty >= Store.pouchSize(pouch) || pouch.equals("Colossal pouch"))
                    && Inventory.stream().name(pouch).isNotEmpty()
                    && Store.fullPouches.contains(pouch)
            ){
                if (Inventory.stream().name(pouch).first().interact("Empty")) {
                    if(!pouch.equals("Colossal pouch")) {
                        Store.setPouchEmpty(pouch);
                        virtualEmpty -= Store.pouchSize(pouch);
                        Store.readyToCraft = true;
                    }else{
                        Store.colPouchFill -= 1;
                        Store.colPouchFill = Math.max(0, Store.colPouchFill);
                        if(Store.colPouchFill == 0) {
                            virtualEmpty -= Store.pouchSize(pouch);
                            Store.setPouchEmpty(pouch);
                            System.out.println("detected col pouch empty");
                        }
                        Store.readyToCraft = true;
                    }
                }
            }
        }
        //Condition.wait(() -> (Inventory.emptySlotCount() != finalInitialSlots), 100, 20);
    }

    @Override
    public String status() {
        return "emptying pouches";
    }
}
