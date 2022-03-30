package klumel8.Lavas.Branches.GoToBank;

import klumel8.Framework.Branch;
import klumel8.Framework.Leaf;
import klumel8.Lavas.store.LavaConstants;
import klumel8.Lavas.store.Store;
import org.powbot.api.rt4.*;

import java.util.List;

public class GoToBank extends Branch {
    LavaConstants lc = new LavaConstants();

    public final List<Leaf> leaves;

    public GoToBank(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        GameObject bank = Objects.stream().name("Bank Chest").nearest().first();
        return !bank.inViewport() && Inventory.stream().name("Pure essence").isEmpty() && Store.fullPouches.isEmpty()
                || (!Store.magicImbue && Inventory.stream().name("Earth talisman").isEmpty());
    }

    @Override
    public String status() {
        return "GoToBank";
    }
}
