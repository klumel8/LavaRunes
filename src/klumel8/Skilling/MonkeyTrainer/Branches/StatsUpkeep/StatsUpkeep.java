package klumel8.Skilling.MonkeyTrainer.Branches.StatsUpkeep;

import klumel8.KlumAPI.Framework.Branch;
import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.MonkeyTrainer.Shared;
import org.powbot.api.rt4.Combat;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Prayer;
import org.powbot.api.rt4.Skills;
import org.powbot.api.rt4.walking.model.Skill;

import java.util.List;

public class StatsUpkeep extends Branch {
    public final List<Leaf> leaves;

    public StatsUpkeep(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        if(Skills.level(Shared.trainedSkill.getIndex()) - Skills.realLevel(Shared.trainedSkill.getIndex()) <= Shared.maxDrain
        && Shared.chinning){
            return true;
        }

        return
                Combat.maxHealth() - Combat.health() > 20 || //check for health
                Skills.realLevel(Skill.Prayer.getIndex()) - Skills.level(Skill.Prayer.getIndex()) > Shared.prayerRestored //Check if prayer is low;
                || !Movement.running()
                || !Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE);
    }

    @Override
    public String status() {
        return "Stat upkeep";
    }
}
