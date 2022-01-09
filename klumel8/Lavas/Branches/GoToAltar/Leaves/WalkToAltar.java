package klumel8.Lavas.Branches.GoToAltar.Leaves;

import klumel8.Lavas.Framework.Leaf;
import klumel8.Lavas.KlumScripts;
import klumel8.Lavas.store.LavaConstants;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Players;

public class WalkToAltar extends Leaf {
    LavaConstants lc = new LavaConstants();
    KlumScripts ks = new KlumScripts();
    @Override
    public boolean validate() {
        return lc.altarTile.distanceTo(Players.local().tile()) > 7;
    }

    @Override
    public void execute() {
        ks.moveToRandom(lc.altarTile);
        Condition.wait(() -> lc.altarTile.distanceTo(Players.local().tile()) <= 7, 100, 40);
    }

    @Override
    public String status() {
        return "walking to altar";
    }
}
