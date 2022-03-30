package klumel8.MonkeyTrainer.Branches.StatsUpkeep.Leaves;

import klumel8.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Prayer;

public class ProtMelee extends Leaf {
    @Override
    public boolean validate() {
        return !Prayer.prayerActive(Prayer.Effect.PROTECT_FROM_MELEE) && Prayer.prayerPoints() > 10 && !Bank.opened();
    }

    @Override
    public void execute() {
        Prayer.quickPrayer(true);
        Condition.wait(() -> Prayer.quickPrayer(), 100, 12);
    }

    @Override
    public String status() {
        return "activating prayers";
    }
}
