package klumel8.Skilling.MonkeyTrainer.Branches.BankHandler.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.MonkeyTrainer.Shared;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Players;

public class TeleBank extends Leaf {

    @Override
    public boolean validate() {
        return !Shared.bankArea.contains(Players.local().tile());
    }

    @Override
    public void execute() {
        Item tele = Inventory.stream().name("Camelot teleport").first();
        if(tele.valid() && tele.interact("Break")) {
            Condition.wait(() -> Shared.bankArea.contains(Players.local().tile()), 100, 50);
        }
    }

    @Override
    public String status() {
        return "Tele to bank";
    }
}
