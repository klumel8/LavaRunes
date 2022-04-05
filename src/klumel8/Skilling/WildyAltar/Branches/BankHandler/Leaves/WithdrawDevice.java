package klumel8.Skilling.WildyAltar.Branches.BankHandler.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.WildyAltar.Shared;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;

public class WithdrawDevice extends Leaf {
    @Override
    public boolean validate() {
        if(Shared.selfDamageDevice.equals("None")){
            return false;
        }
        return Inventory.stream().name(Shared.selfDamageDevice).isEmpty() && !Inventory.isFull();
    }

    @Override
    public void execute() {
        Bank.withdraw(Shared.selfDamageDevice, Bank.Amount.ONE);
    }

    @Override
    public String status() {
        return "Withdraw self-harm device";
    }
}
