package klumel8.Skilling.Lavas.Branches.BankLavaRun.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.Lavas.KlumScripts;
import klumel8.Skilling.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.mobile.script.ScriptManager;

public class WithdrawEarthTalisman extends Leaf {
    KlumScripts ks = new KlumScripts();
    @Override
    public boolean validate() {
        return (Inventory.stream().name("Earth talisman").count() < Store.earthTalismanAmount) && Bank.opened();
    }

    @Override
    public void execute() {
        if(Bank.stream().name("Earth talisman").isNotEmpty()) {
            ks.makeSpace();
            Bank.withdraw("Earth talisman", (int) (Store.earthTalismanAmount - Inventory.stream().name("Earth talisman").count()));
        }else{
            Store.errorMsg = "Error, no earth talisman found";
            Condition.sleep(10000);
            ScriptManager.INSTANCE.stop();
        }
    }

    @Override
    public String status() {
        return "fetching earth talisman";
    }
}
