package klumel8.Skilling.WildyAltar.Branches.BankHandler.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.WildyAltar.Shared;
import klumel8.Skilling.WildyAltar.WildyAltarMain;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Equipment;
import org.powbot.api.rt4.Inventory;
import org.powbot.mobile.script.ScriptManager;

public class WithdrawBones extends Leaf {
    @Override
    public boolean validate() {
        return !Inventory.isFull()
                && (Inventory.stream().name(Shared.selfDamageDevice).isNotEmpty() || Shared.selfDamageDevice.equals("None"))
                && Equipment.stream().filter(e -> e.name().contains("Burning amulet")).count() != 0;
    }

    @Override
    public void execute() {
        if(Bank.stream().name(Shared.boneName).isEmpty()){
            System.out.println("Out of bones closing");
            WildyAltarMain.branchStatus = "out of bones";
            Condition.sleep(10000);
            ScriptManager.INSTANCE.stop();
        }
        Bank.withdraw(Shared.boneName, Bank.Amount.ALL);
    }

    @Override
    public String status() {
        return "Withdrawing " + Shared.boneName;
    }
}
