package klumel8.Lavas.Branches.BankLavaRun.Leaves;

import klumel8.Framework.Leaf;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.mobile.script.ScriptManager;

public class WithdrawPureEss extends Leaf {
    @Override
    public boolean validate() {
        return (!Inventory.isFull() || Store.essMissing) && Store.lastEssWithdraw < System.currentTimeMillis() - 900 && Bank.opened();
    }

    @Override
    public void execute() {
        if(Bank.stream().name("Pure essence").isNotEmpty()) {
            if (Bank.stream().name("Pure essence").first().interact("Withdraw-All")) {
                Store.lastEssWithdraw = System.currentTimeMillis();
                Store.essMissing = false;
            }
        }else{
            Store.errorMsg = "out of pure essence, stopping";
            Condition.sleep(10000);
            ScriptManager.INSTANCE.stop();
        }
        //Bank.withdraw("Pure essence", Bank.Amount.ALL);
    }

    @Override
    public String status() {
        return "fetching pure ess";
    }
}
