package klumel8.CrystalChests.Branches.BankLoot.Leaves;

import klumel8.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Equipment;
import org.powbot.api.rt4.Inventory;
import org.powbot.mobile.script.ScriptManager;

public class RefreshRing extends Leaf {
    @Override
    public boolean validate() {
        return Equipment.stream().filter(e -> e.name().contains("Ring of dueling")).count() == 0;
    }

    @Override
    public void execute() {
        String ringName = "";
        for (int i = 8; i >= 1; i--) {
            System.out.println("Checking ring " + i);
            if (Bank.stream().name("Ring of dueling("+i+")").isNotEmpty()) {
                ringName = "Ring of dueling("+i+")";
                i = 0;
            }
        }

        if(ringName.equals("")){
            System.out.println("Didnt find ring of duelings, stopping script");
            ScriptManager.INSTANCE.stop();
        }

        final String ring = ringName;
        Bank.withdraw(ring, Bank.Amount.ONE);
        Condition.wait(() -> Inventory.stream().name(ring).isNotEmpty(), 100, 20);
        Condition.sleep(600);
        Inventory.stream().name(ring).first().interact("Wear");
        Condition.wait(() -> Equipment.stream().filter(e -> e.name().contains("Ring of dueling")).count() != 0, 100, 20);
        if(Inventory.stream().name(ring).isNotEmpty()){
            Bank.deposit(ring, Bank.Amount.ALL);
        }
    }

    @Override
    public String status() {
        return "Refreshing ring";
    }
}
