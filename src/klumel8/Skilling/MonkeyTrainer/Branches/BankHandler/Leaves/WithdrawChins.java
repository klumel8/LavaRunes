package klumel8.Skilling.MonkeyTrainer.Branches.BankHandler.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.MonkeyTrainer.Shared;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Equipment;
import org.powbot.api.rt4.Inventory;
import org.powbot.mobile.script.ScriptManager;

public class WithdrawChins extends Leaf {
    @Override
    public boolean validate() {
        return Shared.chinning && Equipment.stream().name(Shared.weaponName).first().stackSize() < Shared.chinAmount && Bank.opened();
    }

    @Override
    public void execute() {
        if(Bank.stream().name(Shared.weaponName).isEmpty()){
            Shared.leafStatus = "OUT OF " + Shared.weaponName;
            System.out.println("OUT OF " + Shared.weaponName);
            Condition.sleep(100000);
            ScriptManager.INSTANCE.stop();
            return;
        }

        if(Bank.stream().name(Shared.weaponName).first().stackSize() < Shared.chinAmount - Equipment.stream().name(Shared.weaponName).first().stackSize()){
            Shared.leafStatus = "Not enough " + Shared.weaponName;
            System.out.println("Not enough " + Shared.weaponName);
            Condition.sleep(100000);
            ScriptManager.INSTANCE.stop();
            return;
        }

        System.out.println("Withdrawing " + (Shared.chinAmount - Equipment.stream().name(Shared.weaponName).first().stackSize()) + " chins");
        if(Bank.withdraw(Shared.weaponName, Shared.chinAmount - Equipment.stream().name(Shared.weaponName).first().stackSize())) {
            if (Condition.wait(() -> Inventory.stream().name(Shared.weaponName).isNotEmpty(), 100, 24)) {
                Inventory.stream().name(Shared.weaponName).first().interact("Wield");
                Condition.wait(() -> Equipment.stream().name(Shared.weaponName).first().stackSize() >= Shared.chinAmount, 100, 30);
            }
        }
    }

    @Override
    public String status() {
        return "Withdrawing chins";
    }
}
