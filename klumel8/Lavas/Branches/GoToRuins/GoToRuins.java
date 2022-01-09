package klumel8.Lavas.Branches.GoToRuins;

import klumel8.Lavas.Framework.Branch;
import klumel8.Lavas.Framework.Leaf;
import klumel8.Lavas.store.LavaConstants;
import klumel8.Lavas.store.Store;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Players;

import java.util.List;

public class GoToRuins extends Branch {
    LavaConstants lc = new LavaConstants();
    public final List<Leaf> leaves;

    public GoToRuins(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        //System.out.print("GoToAltar" + (!lc.altarArea.contains(Players.local()) && Inventory.isFull() && Store.fullPouches == Store.pouches));
        return !lc.altarArea.contains(Players.local().tile()) && Inventory.isFull() && checkPouchesFull();
    }

    @Override
    public String status() {
        return "GoToRuins";
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
