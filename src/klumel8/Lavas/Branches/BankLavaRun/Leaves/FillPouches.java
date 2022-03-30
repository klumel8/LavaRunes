package klumel8.Lavas.Branches.BankLavaRun.Leaves;

import klumel8.Framework.Leaf;
import klumel8.Lavas.store.Store;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;

import java.util.Collections;

public class FillPouches extends Leaf {
    @Override
    public boolean validate() {
        return Inventory.isFull() && !checkPouchesFull() && Bank.opened();
    }

    @Override
    public void execute() {
        Collections.shuffle(Store.pouches);
        long virtualPureEss = Inventory.stream().name("Pure essence").count();
        long initialPureEss = virtualPureEss;
        for (String pouch : Store.pouches){
            if(!Store.fullPouches.contains(pouch) && virtualPureEss > Store.pouchSize(pouch)){
                if(Inventory.stream().name(pouch).first().actions().contains("Fill")){
                    int slotsBefore = Inventory.emptySlotCount();
                    if(Inventory.stream().name(pouch).first().interact("Fill")) {
                        //Condition.wait(() -> slotsBefore != Inventory.emptySlotCount(), 100, 30);
                        Store.setPouchFull(pouch);
                        virtualPureEss -= Store.pouchSize(pouch);
                        Store.essMissing = true;
                    }
                }else{
                    Store.setPouchFull(pouch);
                }
            }
        }
        Store.lastEssWithdraw = System.currentTimeMillis() - 99999;
    }

    @Override
    public String status() {
        return "filling pouches";
    }

    public boolean checkPouchesFull(){
        for (String pouch : Store.pouches){
            if(!Store.fullPouches.contains(pouch)){
                return false;
            }
        }
        return true;
    }
}
