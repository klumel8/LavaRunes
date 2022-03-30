package klumel8.Lavas.Branches.BankLavaRun.Leaves;

import klumel8.Framework.Leaf;
import klumel8.Lavas.KlumScripts;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.mobile.script.ScriptManager;

public class WithdrawStamina extends Leaf {
    KlumScripts ks = new KlumScripts();
    @Override
    public boolean validate() {
        return Inventory.stream().filtered(i -> i.name().contains("Stamina potion")).isEmpty() && Store.useStaminas && Bank.opened();
    }

    @Override
    public void execute() {
        if(Bank.stream().name("Stamina potion(4)").isNotEmpty()) {
            ks.makeSpace();
            Bank.withdraw("Stamina potion(4)", 1);
        }else{
            Store.errorMsg = "Error, no Stamina potion(4) found, restart and select ferox";
            Condition.sleep(10000);
            ScriptManager.INSTANCE.stop();
        }
    }

    @Override
    public String status() {
        return "fetching stam pot";
    }
}
