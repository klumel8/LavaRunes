package klumel8.Lavas.Branches.BankLavaRun.Leaves;

import com.android.tools.r8.t.b.K;
import klumel8.Lavas.Framework.Leaf;
import klumel8.Lavas.Handlers.GearHandler;
import klumel8.Lavas.KlumScripts;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;

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
            ks.makeSpace();
            Bank.withdraw("Binding necklace", 1);
            Condition.wait(() -> Inventory.stream().name("Binding necklace").isNotEmpty(),100, 30);
            if(Inventory.stream().name("Binding necklace").isNotEmpty()){
                Inventory.stream().name("Binding necklace").first().interact("Wear");
                Condition.wait(() -> gearHandler.neckOkay(),100, 30);
            }
        }
        if(!gearHandler.ringOkay()){
            Bank.withdraw("Ring of dueling(8)", 1);
            ks.makeSpace();
            Condition.wait(() -> Inventory.stream().name("Ring of dueling(8)").isNotEmpty(),100, 30);
            if(Inventory.stream().name("Ring of dueling(8)").isNotEmpty()){
                Inventory.stream().name("Ring of dueling(8)").first().interact("Wear");
                Condition.wait(() -> gearHandler.ringOkay(), 100, 30);
                if(Inventory.stream().name("Ring of dueling(1)").isNotEmpty()){
                    Bank.deposit("Ring of dueling(1)", Bank.Amount.ALL);
                    Condition.wait(() -> Inventory.stream().name("Ring of dueling(1)").isEmpty(), 100, 30);
                }
            }
        }
    }

    @Override
    public String status() {
        return "equiping new jewellery";
    }
}
