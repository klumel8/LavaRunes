package klumel8.Skilling.MonkeyTrainer.Branches.BankHandler.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.MonkeyTrainer.Shared;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Players;

public class OpenBank extends Leaf {
    @Override
    public boolean validate() {
        return Shared.bankArea.contains(Players.local().tile()) && !Bank.opened() &&
                Objects.stream().name("Bank booth").first().inViewport() && Shared.bankTargetTile.distanceTo(Players.local().tile()) <= 7;
    }

    @Override
    public void execute() {
        if(Bank.open()) {
            if (Condition.wait(() -> Bank.opened(), 100, 10)) {
                Bank.depositAllExcept(Shared.keepItems);
            }
        }
    }

    @Override
    public String status() {
        return "opening bank";
    }
}
