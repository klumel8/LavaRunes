package klumel8.Lavas.nodes;

import klumel8.Lavas.LavasMain;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;

import java.util.Collections;

public class EmptyPouches extends Node{
    private final LavasMain lavasMain;

    public EmptyPouches(LavasMain lavasMain) {
        super(lavasMain);
        this.lavasMain = lavasMain;
    }
    @Override
    public boolean validate() {
        return lavasMain.task == LavasMain.Task.EmptyPouches;
    }

    @Override
    public void execute() {
        Collections.shuffle(Store.pouches);
        int initialSlots = Inventory.emptySlotCount();
        int virtualEmpty = initialSlots;
        for (String pouch : Store.pouches) {
            if (virtualEmpty >= Store.pouchSize(pouch) && Inventory.stream().name(pouch).isNotEmpty() && Store.fullPouches.contains(pouch)) {
                if (Inventory.stream().name(pouch).first().interact("Empty")) {
                    Store.setPouchEmpty(pouch);
                    virtualEmpty -= Store.pouchSize(pouch);
                }
            }
        }
        Condition.wait(() -> Inventory.emptySlotCount() != initialSlots, 100, 20);
        lavasMain.task = LavasMain.Task.CraftRunes;
    }

    @Override
    public String status() {
        return null;
    }
}
