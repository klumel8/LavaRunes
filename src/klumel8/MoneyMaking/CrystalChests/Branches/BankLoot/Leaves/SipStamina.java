package klumel8.MoneyMaking.CrystalChests.Branches.BankLoot.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.stream.item.BankItemStream;
import org.powbot.api.rt4.stream.item.InventoryItemStream;

public class SipStamina extends Leaf {
    @Override
    public boolean validate() {
        return Movement.energyLevel() < 20 &&
                (Bank.stream().filtered(i -> i.name().contains("Stamina potion")).isNotEmpty() ||
                        Bank.stream().name("Super energy(4)").isNotEmpty());
    }

    @Override
    public void execute() {
        BankItemStream stams = Bank.stream().filtered(i -> i.name().contains("Stamina potion"));
        if(stams.isNotEmpty()){
            stams.first().interact("Withdraw-1");
            Condition.wait(() -> {
                InventoryItemStream invStams = Inventory.stream().filtered(i -> i.name().contains("Stamina potion"));
                return invStams.isNotEmpty();
            },100,20);

            Item invStam = Inventory.stream().filtered(i -> i.name().contains("Stamina potion")).first();
            if(invStam.interact("Drink")){
                if(Condition.wait(() -> Movement.energyLevel() > 20, 100, 20)){
                    Inventory.stream().filtered(i -> i.name().contains("Stamina potion") || i.name().contains("Vial")).first().interact("Deposit-All");
                }
            }
            return;
        }
    }

    @Override
    public String status() {
        return "Drinking stamina";
    }
}
