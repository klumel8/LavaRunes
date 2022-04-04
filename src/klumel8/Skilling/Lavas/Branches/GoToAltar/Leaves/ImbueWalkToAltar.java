package klumel8.Skilling.Lavas.Branches.GoToAltar.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.Lavas.KlumScripts;
import klumel8.Skilling.Lavas.store.LavaConstants;
import klumel8.Skilling.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Magic;
import org.powbot.api.rt4.Players;
import org.powbot.mobile.script.ScriptManager;

public class ImbueWalkToAltar extends Leaf {
    LavaConstants lc = new LavaConstants();
    KlumScripts ks = new KlumScripts();
    @Override
    public boolean validate() {
        return lc.altarTile.distanceTo(Players.local().tile()) > 7;
    }

    @Override
    public void execute() {

        boolean didMove = ks.moveToRandom(lc.altarTile);

        if(Store.magicImbue) {
            if (!Magic.LunarSpell.MAGIC_IMBUE.canCast()) {
                System.out.println("DOES NOT MEET MAGIC REQUIREMENTS (lunar / POUCH)");
                Condition.sleep(100);
                ScriptManager.INSTANCE.stop();
                return;
            } else {
                if(Magic.LunarSpell.MAGIC_IMBUE.cast("Cast")) {
                    //Would like to verify correct click but seems broken...
                    System.out.println("Did the imbue spell");
                    Store.didImbue();
                }
                //lavasMain.task = LavasMain.Task.CraftRunes;
            }
        }

        if(didMove) {
            Condition.wait(() -> lc.altarTile.distanceTo(Players.local().tile()) <= 7, 100, 40);
        }
    }

    @Override
    public String status() {
        return "walking to altar";
    }
}
