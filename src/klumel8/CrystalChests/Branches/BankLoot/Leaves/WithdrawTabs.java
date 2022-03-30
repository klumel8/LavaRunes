package klumel8.CrystalChests.Branches.BankLoot.Leaves;

import klumel8.CrystalChests.CrystalConstants;
import klumel8.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.mobile.script.ScriptManager;

public class WithdrawTabs extends Leaf {
    @Override
    public boolean validate() {
        return Inventory.stream().name("Teleport to house").isEmpty();
    }

    @Override
    public void execute() {
        if(Bank.stream().name("Teleport to house").first().stackSize() < 2){
//            CrystalConstants.doGE = true;
            ScriptManager.INSTANCE.stop();
            System.out.println("Out of house teles");
            return;
        }
        Bank.withdraw("Teleport to house", Bank.Amount.ALL);
        Condition.wait(() -> Inventory.stream().name("Teleport to house").isNotEmpty(), 100, 20);
    }

    @Override
    public String status() {
        return "Withdrawing House teleport";
    }
}
