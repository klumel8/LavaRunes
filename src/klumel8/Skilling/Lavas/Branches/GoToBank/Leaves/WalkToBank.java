package klumel8.Skilling.Lavas.Branches.GoToBank.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.Lavas.KlumScripts;
import klumel8.Skilling.Lavas.store.LavaConstants;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.Players;

public class WalkToBank extends Leaf {
    LavaConstants lc = new LavaConstants();
    KlumScripts ks = new KlumScripts();

    @Override
    public boolean validate() {
        return !lc.bankTile.matrix().inViewport() && !lc.feroxBankTile.matrix().inViewport()
                && (lc.feroxBankArea.contains(Players.local()) || lc.bankArea.contains(Players.local()));
    }

    @Override
    public void execute() {
        Tile bankTile;
        if(lc.feroxBankArea.contains(Players.local())){
            bankTile = lc.feroxBankTile;
        }else{
            bankTile = lc.bankTile;
        }
        if(ks.moveToRandom(bankTile)) {
            Condition.wait(() -> bankTile.distanceTo(Players.local().tile()) <= 8, 100, 60);
        }
    }

    @Override
    public String status() {
        return "walking to bank";
    }
}
