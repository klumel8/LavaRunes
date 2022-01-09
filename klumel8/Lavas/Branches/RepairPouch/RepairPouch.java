package klumel8.Lavas.Branches.RepairPouch;

import klumel8.Lavas.Framework.Branch;
import klumel8.Lavas.Framework.Leaf;
import klumel8.Lavas.store.LavaConstants;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Magic;

import java.util.List;

public class RepairPouch extends Branch {
    LavaConstants lc = new LavaConstants();

    public final List<Leaf> leaves;

    public RepairPouch(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        return Magic.LunarSpell.NPC_CONTACT.canCast() && Inventory.stream().id(lc.brokenPouchIds).isNotEmpty();
    }

    @Override
    public String status() {
        return "contacting dark mage";
    }
}
