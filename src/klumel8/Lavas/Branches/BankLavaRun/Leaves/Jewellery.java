package klumel8.Lavas.Branches.BankLavaRun.Leaves;

import klumel8.Framework.Leaf;
import klumel8.Lavas.Handlers.GearHandler;
import klumel8.Lavas.KlumScripts;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.mobile.script.ScriptManager;

public class Jewellery extends Leaf {
    GearHandler gearHandler = new GearHandler();
    KlumScripts ks = new KlumScripts();
    @Override
    public boolean validate() {
        return (!gearHandler.neckOkay() || !gearHandler.ringOkay()) && Bank.opened();
    }

    @Override
    public void execute() {
        if(!gearHandler.neckOkay()){
            if(Bank.stream().name("Binding necklace").isNotEmpty()) {
                ks.makeSpace();
                Bank.withdraw("Binding necklace", 1);
                Condition.wait(() -> Inventory.stream().name("Binding necklace").isNotEmpty(), 100, 30);
                if (Inventory.stream().name("Binding necklace").isNotEmpty()) {
                    Inventory.stream().name("Binding necklace").first().interact("Wear");
                    Condition.wait(() -> gearHandler.neckOkay(), 100, 30);
                }
            }else{
                Store.errorMsg = "Out of binding necklace, stopping script";
                Condition.sleep(10000);
                ScriptManager.INSTANCE.stop();
            }
        }
        if(!gearHandler.ringOkay()){
            if(Bank.stream().name("Ring of dueling(8)").isNotEmpty()) {
                ks.makeSpace();
                Bank.withdraw("Ring of dueling(8)", 1);
                Condition.wait(() -> Inventory.stream().name("Ring of dueling(8)").isNotEmpty(), 100, 30);
                if (Inventory.stream().name("Ring of dueling(8)").isNotEmpty()) {
                    Inventory.stream().name("Ring of dueling(8)").first().interact("Wear");
                    Condition.wait(() -> gearHandler.ringOkay(), 100, 30);
                    if (Inventory.stream().name("Ring of dueling(1)").isNotEmpty()) {
                        Bank.deposit("Ring of dueling(1)", Bank.Amount.ALL);
                        Condition.wait(() -> Inventory.stream().name("Ring of dueling(1)").isEmpty(), 100, 30);
                    }
                }
            }else{
                Store.errorMsg = "Out of rings of dueling(8), stopping script";
                Condition.sleep(10000);
                ScriptManager.INSTANCE.stop();
            }
        }
    }

    @Override
    public String status() {
        return "equiping new jewellery";
    }
}
