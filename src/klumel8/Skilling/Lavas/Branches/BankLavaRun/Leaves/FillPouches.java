package klumel8.Skilling.Lavas.Branches.BankLavaRun.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;

import java.util.Collections;

public class FillPouches extends Leaf {
    @Override
    public boolean validate() {
//        System.out.println("Validating" + !checkPouchesFull() + Bank.opened() + Inventory.isFull());
        return Inventory.isFull() && !checkPouchesFull() && Bank.opened();
    }

    @Override
    public void execute() {
        Collections.shuffle(Store.pouches);
        long virtualPureEss = Inventory.stream().name("Pure essence").count();
        System.out.println("0 : " + Store.pouches.size());
        for (String pouch : Store.pouches){
            System.out.println("1");
            if(!Store.fullPouches.contains(pouch) && (virtualPureEss > Store.pouchSize(pouch) || pouch.equals("Colossal pouch"))){
                System.out.println("2");
                if(Inventory.stream().name(pouch).first().actions().contains("Fill")){
                    System.out.println("3");
                    if(Inventory.stream().name(pouch).first().interact("Fill")) {
                        System.out.println("4");
                        //Condition.wait(() -> slotsBefore != Inventory.emptySlotCount(), 100, 30);
                        if(!pouch.equals("Colossal pouch")){
                            Store.setPouchFull(pouch);
                            virtualPureEss -= Store.pouchSize(pouch);
                            Store.essMissing = true;
                        }else{
                            Store.colPouchFill += 1;
                            Store.colPouchFill = Math.min(2, Store.colPouchFill);
                            System.out.println("Filling col pouch " + Store.colPouchFill);
                            if(Store.colPouchFill == 2) {
                                System.out.println("5");
                                Store.setPouchFull(pouch);
                                virtualPureEss -= Store.pouchSize(pouch);
                                Store.essMissing = true;
                            }
                        }
                    }
                    Condition.wait(() -> !Inventory.isFull(), 100, 10);
                }else{
                    System.out.println("Forcefully set the pouch to full");
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
