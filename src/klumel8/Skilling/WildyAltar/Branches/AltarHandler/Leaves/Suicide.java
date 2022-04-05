package klumel8.Skilling.WildyAltar.Branches.AltarHandler.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.WildyAltar.Shared;
import org.powbot.api.rt4.Combat;
import org.powbot.api.rt4.GroundItem;
import org.powbot.api.rt4.GroundItems;
import org.powbot.api.rt4.Inventory;

public class Suicide extends Leaf {
    @Override
    public boolean validate() {
        return Inventory.stream().name(Shared.boneName).isEmpty() && Combat.health() > 0 && !Shared.usedTick;
    }

    @Override
    public void execute() {
        if(Shared.selfDamageDevice.equals("Dwarven rock cake") && Combat.health() > 1 && Inventory.stream().name(Shared.selfDamageDevice).isNotEmpty()){
            Inventory.stream().name(Shared.selfDamageDevice).first().interact("Guzzle");
        }else if(Shared.selfDamageDevice.equals("Locator orb") && Combat.health() > 1 && Inventory.stream().name(Shared.selfDamageDevice).isNotEmpty()){
            Inventory.stream().name(Shared.selfDamageDevice).first().interact("Feel");
        }else {
            if (GroundItems.stream().name("Wine of zamorak").isEmpty()) {
                System.out.println("Didnt find wine");
                return;
            }

            //weirdest bug of me life...
            GroundItem wine = GroundItems.stream().name("Wine of zamorak").nearest().first();
            wine.interact("Take");
        }

        Shared.usedTick = true;
    }

    @Override
    public String status() {
        return "Suiciding";
    }
}
