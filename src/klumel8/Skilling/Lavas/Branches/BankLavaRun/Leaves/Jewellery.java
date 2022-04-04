package klumel8.Skilling.Lavas.Branches.BankLavaRun.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.Lavas.Handlers.GearHandler;
import klumel8.Skilling.Lavas.KlumScripts;
import klumel8.Skilling.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.Notifications;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Equipment;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
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

                if(Bank.withdraw("Binding necklace", 1)) {
                    if(Condition.wait(() -> Inventory.stream().name("Binding necklace").isNotEmpty(), 100, 50)){
                        if(Inventory.stream().name("Binding necklace").first().interact("Wear")) {
                            Condition.wait(() -> gearHandler.neckOkay(), 100, 30);
                        }
                    }
                }
            }else{
                Store.errorMsg = "Out of binding necklace, stopping script";
                Condition.sleep(10000);
                ScriptManager.INSTANCE.stop();
            }
        }
        if(!gearHandler.ringOkay()){
            Item ring = Bank.stream().filtered(item -> item.name().startsWith("Ring of dueling") && !item.name().contains("(1)")).first();
            if(ring.valid()) {
                ks.makeSpace();
                if( Bank.withdraw(ring.name(), Bank.Amount.ONE)) {
                    Condition.wait(() -> Inventory.stream().name(ring.name()).isNotEmpty(), 100, 20);
                    Condition.sleep(600);
                    if(Inventory.stream().name(ring.name()).first().interact("Wear")) {
                        Condition.wait(() -> Equipment.stream().filter(e -> e.name().contains("Ring of dueling")).count() != 0, 100, 20);
                    }
                    if(Inventory.stream().name(ring.name()).isNotEmpty()){
                        Bank.deposit(ring.name(), Bank.Amount.ALL);
                    }
                }
            } else {
                Notifications.showNotification("No duel rings in bank, stopping script");
                ScriptManager.INSTANCE.stop();
            }
        }
    }

    @Override
    public String status() {
        return "equiping new jewellery";
    }
}
