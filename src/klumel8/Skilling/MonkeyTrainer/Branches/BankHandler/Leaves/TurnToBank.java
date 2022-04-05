package klumel8.Skilling.MonkeyTrainer.Branches.BankHandler.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.MonkeyTrainer.Shared;
import org.powbot.api.rt4.*;

public class TurnToBank extends Leaf{
    @Override
    public boolean validate() {
        GameObject bank = Objects.stream().name("Bank booth").first();
        return Shared.bankArea.contains(Players.local().tile()) && !Bank.opened() &&
                !bank.inViewport() && Shared.bankTargetTile.distanceTo(Players.local().tile()) <= 7;
    }

    @Override
    public void execute() {
        GameObject bank = Objects.stream().name("Bank booth").first();
        if(bank.valid()) {
            Camera.turnTo(bank);
        }
    }

    @Override
    public String status() {
        return "Turning to bank";
    }
}
