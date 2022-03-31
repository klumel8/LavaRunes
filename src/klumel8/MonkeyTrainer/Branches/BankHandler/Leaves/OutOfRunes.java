package klumel8.MonkeyTrainer.Branches.BankHandler.Leaves;

import klumel8.Framework.Leaf;
import klumel8.MonkeyTrainer.Shared;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Players;
import org.powbot.api.script.ScriptConfiguration;
import org.powbot.mobile.script.ScriptManager;

public class OutOfRunes extends Leaf {
    @Override
    public boolean validate() {
        if(!Shared.bankArea.contains(Players.local().tile()) || Shared.chinning){
            return false;
        }

        if(!Shared.spell.canCast()){
            System.out.println("Cant cast spell");
            return true;
        }

        return false;
    }

    @Override
    public void execute() {
        Condition.sleep(100000);
        ScriptManager.INSTANCE.stop();
    }

    @Override
    public String status() {
        return "Out of runes";
    }
}
