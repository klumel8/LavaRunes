package klumel8.Lavas.Branches.BankLavaRun;

import klumel8.Lavas.Framework.Branch;
import klumel8.Lavas.Framework.Leaf;
import klumel8.Lavas.store.LavaConstants;
import klumel8.Lavas.store.Store;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Objects;

import java.util.List;

public class BankLavaRun extends Branch {
    LavaConstants lc = new LavaConstants();

    public final List<Leaf> leaves;

    public BankLavaRun(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }
    @Override
    public boolean validate() {
        GameObject bank = Objects.stream().name("Bank Chest").nearest().first();
        return Inventory.stream().id(lc.brokenPouchIds).isEmpty() && bank.inViewport() && (!Inventory.isFull() || !checkPouchesFull());
    }

    @Override
    public String status() {
        return "BankLavaRun";
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
