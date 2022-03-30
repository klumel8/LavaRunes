package klumel8.MonkeyTrainer.Branches.BankHandler.Leaves;

import klumel8.Framework.Leaf;
import klumel8.MonkeyTrainer.Shared;
import org.powbot.api.rt4.*;

public class GoToBank extends Leaf {
    @Override
    public boolean validate() {
        GameObject bank = Objects.stream().name("Bank booth").first();
        return Shared.bankArea.contains(Players.local().tile()) && !Bank.opened() &&
                Shared.bankTargetTile.distanceTo(Players.local().tile()) > 7;
    }

    @Override
    public void execute() {
        Movement.walkTo(Shared.bankTargetTile);
    }

    @Override
    public String status() {
        return "Walking to bank";
    }
}
