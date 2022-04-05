package klumel8.Skilling.WildyAltar.Branches.BankHandler.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Equipment;
import org.powbot.api.rt4.Inventory;
import org.powbot.mobile.script.ScriptManager;

public class WithdrawTele extends Leaf {
    @Override
    public boolean validate() {
        return Equipment.stream().filter(e -> e.name().contains("Burning amulet")).count() == 0;
    }

    @Override
    public void execute() {
        String ringName = "";
        for (int i = 5; i >= 1; i--) {
            System.out.println("Checking necklace " + i);
            if (Bank.stream().name("Burning amulet("+i+")").isNotEmpty()) {
                ringName = "Burning amulet("+i+")";
                i = 0;
            }
        }

        final String ring = ringName;

        if(Inventory.stream().filter(e -> e.name().contains("Burning amulet")).isEmpty()){
            if(ringName.equals("")){
                System.out.println("Didnt find Burning amulets, stopping script");
                ScriptManager.INSTANCE.stop();
            }

            Bank.withdraw(ring, Bank.Amount.ONE);
            Condition.wait(() -> Inventory.stream().name(ring).isNotEmpty(), 100, 20);
            Condition.sleep(600);
        }

        Inventory.stream().filter(e -> e.name().contains("Burning amulet")).first().interact("Wear");
        Condition.wait(() -> Equipment.stream().filter(e -> e.name().contains("Burning amulet")).count() != 0, 100, 20);

        if(Inventory.stream().name(ring).isNotEmpty()){
            Bank.deposit(ring, Bank.Amount.ALL);
        }
    }

    @Override
    public String status() {
        return "Refreshing burning ammy";
    }
}
