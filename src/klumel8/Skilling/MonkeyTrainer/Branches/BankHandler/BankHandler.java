package klumel8.Skilling.MonkeyTrainer.Branches.BankHandler;

import klumel8.KlumAPI.Framework.Branch;
import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.MonkeyTrainer.Shared;
import kotlin.Pair;
import org.powbot.api.requirement.RunePowerRequirement;
import org.powbot.api.rt4.Equipment;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.magic.Rune;
import org.powbot.api.rt4.magic.RunePouch;

import java.util.HashMap;
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
            if(Inventory.stream().name("Rune pouch").isNotEmpty()) {
                for (RunePowerRequirement rune1 : Shared.spell.getRequirements()) {
                    String name1 = rune1.getPower().name();
//                    Shared.runeStacks = new HashMap<>();
                    for (Pair<Rune, Integer> pair : RunePouch.runes()) {
                        if (pair.getFirst().name().equals(name1)) {
                            Shared.runeStacks.put(pair.getFirst().getId(), pair.getSecond());
                            System.out.println("Added key " + pair.getFirst().getId());
                            int N = pair.getSecond();
                            if (N < 100) {
                                System.out.print("Running low on runes");
                                return true;
                            }
                        }
                    }
                }
            }else{
                for (RunePowerRequirement rune : Shared.spell.getRequirements()) {
                    Item runes = Inventory.stream().id(rune.getPower().getFirstRune().getId()).first();
                    if(runes.valid()){
                        if(runes.stackSize() < 100){
                            System.out.print("Running low on runes");
                            return true;
                        }
                    }else{
                        return true;
                    }
                }
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
