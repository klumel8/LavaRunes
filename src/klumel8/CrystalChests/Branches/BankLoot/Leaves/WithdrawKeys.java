package klumel8.CrystalChests.Branches.BankLoot.Leaves;

import klumel8.CrystalChests.CrystalConstants;
import klumel8.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.mobile.script.ScriptManager;

public class WithdrawKeys extends Leaf {
    @Override
    public boolean validate() {
        return Inventory.stream().id(CrystalConstants.keyID).isEmpty();
    }

    @Override
    public void execute() {
        if(Bank.stream().name("Crystal key").first().stackSize() < CrystalConstants.keyAmount){
//            CrystalConstants.doGE = true;
            ScriptManager.INSTANCE.stop();
            System.out.println("Out of keys, stopping script");
            return;
        }
        Bank.withdraw("Crystal key", CrystalConstants.keyAmount);
        Condition.wait(() -> Inventory.stream().id(CrystalConstants.keyID).isNotEmpty(), 100, 20);
    }

    @Override
    public String status() {
        return "Withdrawing keys";
    }
}
