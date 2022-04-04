package klumel8.Skilling.MonkeyTrainer.Branches.StatsUpkeep.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.MonkeyTrainer.Shared;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Skills;

public class BoostSkill extends Leaf {
    @Override
    public boolean validate() {
        if(!Shared.chinning){
            return false;
        }

        return Skills.level(Shared.trainedSkill.getIndex()) - Skills.realLevel(Shared.trainedSkill.getIndex()) <= Shared.maxDrain
                && Inventory.stream().filtered(i -> i.name().contains("ging potion")).isNotEmpty();
    }

    @Override
    public void execute() {
        if(Inventory.stream().filtered(i -> i.name().contains("ging potion")).first().interact("Drink")) {
            Condition.wait(() -> Skills.level(Shared.trainedSkill.getIndex()) - Skills.realLevel(Shared.trainedSkill.getIndex()) > Shared.maxDrain, 100, 12);
        }
    }

    @Override
    public String status() {
        return "Boosting skills";
    }
}
