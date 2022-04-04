package klumel8.Skilling.MonkeyTrainer.Branches.GoToSpot.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.MonkeyTrainer.Shared;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Players;

public class TeleToDungeon extends Leaf {
    @Override
    public boolean validate() {
        return Shared.bankArea.contains(Players.local().tile()) && !Bank.opened();
    }

    @Override
    public void execute() {
        if(Inventory.stream().name("Ape atoll teleport").first().interact("Break")) {
            Condition.wait(() -> !Shared.bankArea.contains(Players.local().tile()), 100, 50);
        }
    }

    @Override
    public String status() {
        return "Tele to tunnels";
    }
}
