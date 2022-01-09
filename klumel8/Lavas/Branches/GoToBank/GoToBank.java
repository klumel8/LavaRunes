package klumel8.Lavas.Branches.GoToBank;

import klumel8.Lavas.Framework.Branch;
import klumel8.Lavas.Framework.Leaf;
import klumel8.Lavas.store.LavaConstants;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Players;

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
        return !bank.inViewport() && (lc.bankArea.contains(Players.local()) || lc.feroxBankArea.contains(Players.local()));
    }

    @Override
    public String status() {
        return "GoToBank";
    }
}
