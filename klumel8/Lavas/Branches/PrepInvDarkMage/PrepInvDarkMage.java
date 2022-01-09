package klumel8.Lavas.Branches.PrepInvDarkMage;

import klumel8.Lavas.Framework.Branch;
import klumel8.Lavas.Framework.Leaf;
import klumel8.Lavas.KlumScripts;
import klumel8.Lavas.store.LavaConstants;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Magic;
import org.powbot.api.rt4.Objects;

import java.util.List;

public class PrepInvDarkMage extends Branch {
    LavaConstants lc = new LavaConstants();
    public final List<Leaf> leaves;

    public PrepInvDarkMage(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        GameObject bank = Objects.stream().name("Bank Chest").nearest().first();
        return !Magic.LunarSpell.NPC_CONTACT.canCast() && Inventory.stream().id(lc.brokenPouchIds).isNotEmpty() && bank.inViewport();
    }

    @Override
    public String status() {
        return "Prepping inv for dark mage";
    }
}
