package klumel8.Skilling.Lavas.Branches.BankLavaRun.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.Lavas.KlumScripts;
import klumel8.Skilling.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.Notifications;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.mobile.script.ScriptManager;

public class WithdrawPureEss extends Leaf {
    KlumScripts ks = new KlumScripts();

    @Override
    public boolean validate() {
        return (!Inventory.isFull() || Store.essMissing) && Store.lastEssWithdraw < System.currentTimeMillis() - 900 && Bank.opened();
    }

    @Override
    public void execute() {
        if(Inventory.stream().name("Earth rune").isEmpty()){
            if(Bank.stream().name("Earth rune").first().valid() && Bank.stream().name("Earth rune").first().stackSize() > 0){
                ks.makeSpace();
                if(!Bank.withdraw("Earth rune", Bank.Amount.ALL)){
                    return;
                }
            }else{
                Notifications.showNotification("Out of earth runes");
                ScriptManager.INSTANCE.stop();
                return;
            }
        }

        if(Bank.stream().name("Pure essence").isNotEmpty()) {
            if (Bank.withdraw("Pure essence", Bank.Amount.ALL)) {
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
