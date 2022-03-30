package klumel8.MonkeyTrainer.Branches.BankHandler;

import klumel8.Framework.Branch;
import klumel8.Framework.Leaf;
import klumel8.MonkeyTrainer.Shared;
import org.powbot.api.rt4.Combat;
import org.powbot.api.rt4.Equipment;
import org.powbot.api.rt4.Inventory;

import java.util.List;

public class BankHandler extends Branch {
    public final List<Leaf> leaves;

    public BankHandler(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        //Check specific for chinning / ancients
        if(Shared.chinning){
            if(Equipment.stream().filter(e -> e.name().contains("chinchompa")).count() == 0
                    || Inventory.stream().filtered(i -> i.name().contains("Ranging potion")).isEmpty()){
                System.out.println("Out of ranged supplies");
                return true;
            }
        }else{
            if(!Shared.spell.canCast()){
                return true;
            }
        }

        return Inventory.stream().filtered(i -> i.name().contains("Prayer potion")).isEmpty()
                || Inventory.stream().name("Ape atoll teleport").isEmpty()
                || Inventory.stream().name("Camelot teleport").isEmpty()
                || Inventory.stream().name(Shared.food).isEmpty();
    }

    @Override
    public String status() {
        return "Banking";
    }
}
