package klumel8.MonkeyTrainer.Branches.BankHandler.Leaves;

import klumel8.Framework.Leaf;
import klumel8.MonkeyTrainer.Shared;
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
        Objects.stream().name("Bank booth").first().interact("Bank");
        if(Condition.wait(() -> Bank.opened(), 100, 60)){
            Bank.depositAllExcept(Shared.keepItems);
        }
    }

    @Override
    public String status() {
        return "opening bank";
    }
}
