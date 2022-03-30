package klumel8.MonkeyTrainer.Branches.StatsUpkeep.Leaves;

import klumel8.Framework.Leaf;
import klumel8.MonkeyTrainer.Shared;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Prayer;
import org.powbot.api.rt4.Skills;
import org.powbot.api.rt4.stream.item.InventoryItemStream;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.mobile.script.ScriptManager;

public class RestorePrayer extends Leaf {

    @Override
    public boolean validate() {
        return Skills.realLevel(Skill.Prayer.getIndex()) - Skills.level(Skill.Prayer.getIndex()) > Shared.prayerRestored;
    }

    @Override
    public void execute() {
        InventoryItemStream potions = Inventory.stream().filtered(i -> i.name().contains("Prayer potion") || i.name().contains("Super restore"));
        if(potions.isEmpty()){
            Inventory.stream().name("Varrock teleport").first().interact("Grand Exchange");
            ScriptManager.INSTANCE.stop();
        }else{
            potions.first().interact("Drink");
            final int prayBefore = Prayer.prayerPoints();
            Condition.wait(() -> Prayer.prayerPoints() != prayBefore, 100, 12);
        }
        if(Inventory.stream().name("Vial").isNotEmpty()){
            Inventory.stream().name("Vial").first().interact("Drop");
        }
    }

    @Override
    public String status() {
        return "";
    }
}
