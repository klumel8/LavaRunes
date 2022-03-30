package klumel8.Lavas.Branches.BankLavaRun.Leaves;

import klumel8.Framework.Leaf;
import klumel8.Lavas.store.Store;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;

public class DepositRunes extends Leaf {
    @Override
    public boolean validate() {
        return (Inventory.stream().name("Lava rune").isNotEmpty() || Inventory.stream().name("Air rune").isNotEmpty() || Inventory.stream().name("Cosmic rune").isNotEmpty())
                && Bank.opened() && (System.currentTimeMillis() - Store.lastRuneDeposit) > 2000;
    }

    @Override
    public void execute() {
        if(Inventory.stream().name("Lava rune").isNotEmpty()){
            Inventory.stream().name("Lava rune").first().interact("Deposit-All");
        }
        if(Inventory.stream().name("Air rune").isNotEmpty()){
            Bank.deposit("Air rune", Bank.Amount.ALL);
        }
        if(Inventory.stream().name("Fire rune").isNotEmpty()){
            Bank.deposit("Fire rune", Bank.Amount.ALL);
        }
        if(Inventory.stream().name("Cosmic rune").isNotEmpty()){
            Bank.deposit("Cosmic rune", Bank.Amount.ALL);
        }

        Store.lastRuneDeposit = System.currentTimeMillis();
    }

    @Override
    public String status() {
        return "depositing runes";
    }
}
