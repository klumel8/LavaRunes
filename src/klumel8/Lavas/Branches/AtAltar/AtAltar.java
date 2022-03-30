package klumel8.Lavas.Branches.AtAltar;

import klumel8.Framework.Branch;
import klumel8.Framework.Leaf;
import klumel8.Lavas.store.LavaConstants;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Players;

import java.util.List;

public class AtAltar extends Branch {
    LavaConstants lc = new LavaConstants();

    public final List<Leaf> leaves;

    public AtAltar(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        //System.out.println("dist to altar" + lc.altarTile.distanceTo(Players.local().tile()));
        return Objects.stream().id(lc.altarId).first().inViewport() && lc.altarTile.distanceTo(Players.local().tile()) <= 7;
    }

    @Override
    public String status() {
        return "AtAltar";
    }
}
