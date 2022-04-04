package klumel8.Skilling.Lavas.Branches.GoToAltar;

import klumel8.KlumAPI.Framework.Branch;
import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.Lavas.store.LavaConstants;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Players;

import java.util.List;

public class GoToAltar extends Branch {
    LavaConstants lc = new LavaConstants();
    public final List<Leaf> leaves;

    public GoToAltar(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        System.out.println("Got earth runes " + Inventory.stream().name("Earth rune").isNotEmpty());
        return lc.altarArea.contains(Players.local())
                && (!Objects.stream().id(lc.altarId).first().inViewport()
                || Objects.stream().id(lc.altarId).first().tile().distanceTo(Players.local()) > 7);
    }

    @Override
    public String status() {
        return "GoToAltar";
    }
}
