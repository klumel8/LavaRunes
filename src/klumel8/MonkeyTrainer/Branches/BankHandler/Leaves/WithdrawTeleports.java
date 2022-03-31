package klumel8.MonkeyTrainer.Branches.BankHandler.Leaves;

import klumel8.Framework.Leaf;
import klumel8.MonkeyTrainer.MonkeyTrainer;
import klumel8.MonkeyTrainer.Shared;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.mobile.script.ScriptManager;

public class WithdrawTeleports extends Leaf {
    @Override
    public boolean validate() {
        return (Inventory.stream().name("Ape atoll teleport").isEmpty() || Inventory.stream().name("Camelot teleport").isEmpty()) && Bank.opened();
    }

    @Override
    public void execute() {
        if(Inventory.stream().name("Ape atoll teleport").isEmpty()){
            if(Bank.stream().name("Ape atoll teleport").isNotEmpty()){
                Bank.withdraw("Ape atoll teleport", 10);
                Condition.wait(() -> Inventory.stream().name("Ape atoll teleport").isNotEmpty(), 100, 24);
            }else{
                Shared.leafStatus = "OUT OF APE ATOLL TELES";
                System.out.println("OUT OF APE ATOLL TELES");
                Condition.sleep(100000);
                ScriptManager.INSTANCE.stop();
            }
        }

        if(Inventory.stream().name("Camelot teleport").isEmpty()){
            if(Bank.stream().name("Camelot teleport").isNotEmpty()){
                Bank.withdraw("Camelot teleport", 10);
                Condition.wait(() -> Inventory.stream().name("Camelot teleport").isNotEmpty(), 100, 24);
            }else{
                Shared.leafStatus = "OUT OF Camelot TELES";
                System.out.println("OUT OF Camelot TELES");
                Condition.sleep(100000);
                ScriptManager.INSTANCE.stop();
            }
        }
    }

    @Override
    public String status() {
        return "Withdrawing tele's";
    }
}
