package klumel8.Skilling.MonkeyTrainer.Branches.StatsUpkeep.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.MonkeyTrainer.Shared;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Prayer;
import org.powbot.api.rt4.Skills;
import org.powbot.api.rt4.stream.item.InventoryItemStream;
import org.powbot.api.rt4.walking.model.Skill;

public class RestorePrayer extends Leaf {

    @Override
    public boolean validate() {
        return Skills.realLevel(Skill.Prayer.getIndex()) - Skills.level(Skill.Prayer.getIndex()) > Shared.prayerRestored;
    }

    @Override
    public void execute() {
        InventoryItemStream potions = Inventory.stream().filtered(i -> i.name().contains("Prayer potion"));
        if(potions.isNotEmpty()){
            final int prayBefore = Prayer.prayerPoints();

            if(potions.first().valid() && potions.first().interact("Drink")) {
                Condition.wait(() -> Prayer.prayerPoints() > prayBefore, 100, 12);
            }
        }

        Item vial = Inventory.stream().name("Vial").first();
        if(vial.valid()){
            vial.interact("Drop");
        }
    }

    @Override
    public String status() {
        return "Restoring prayer";
    }
}
