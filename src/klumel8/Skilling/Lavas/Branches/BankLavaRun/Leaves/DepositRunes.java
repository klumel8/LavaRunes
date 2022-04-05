package klumel8.Skilling.Lavas.Branches.BankLavaRun.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.Lavas.store.Store;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;

public class DepositRunes extends Leaf {
    @Override
    public boolean validate() {
        return (Inventory.stream().name("Lava rune").isNotEmpty() || Inventory.stream().name("Air rune").isNotEmpty() || Inventory.stream().name("Cosmic rune").isNotEmpty())
                && Bank.opened() && (System.currentTimeMillis() - Store.lastRuneDeposit) > 2000;
    }

    @Override
    public void execute() {
        if(Inventory.stream().name(Store.rune).first().valid()){
            Inventory.stream().name(Store.rune).first().interact("Deposit-All");
        }
        if(Inventory.stream().name("Air rune").first().valid()){
            Bank.deposit("Air rune", Bank.Amount.ALL);
        }
        if(Inventory.stream().name("Fire rune").first().valid()){
            Bank.deposit("Fire rune", Bank.Amount.ALL);
        }
        if(Inventory.stream().name("Cosmic rune").first().valid()){
            Bank.deposit("Cosmic rune", Bank.Amount.ALL);
        }

        Item junk = Inventory.stream().filtered(i -> i.name().startsWith("Ring")
                || i.name().startsWith("Binding")
                || i.name().contains("otion")
                || i.name().contains("Vial")).first();

        if(junk.valid()){
            Bank.deposit(junk.name(), Bank.Amount.ALL);
        }
        Store.lastRuneDeposit = System.currentTimeMillis();
    }

    @Override
    public String status() {
        return "depositing runes";
    }
}
