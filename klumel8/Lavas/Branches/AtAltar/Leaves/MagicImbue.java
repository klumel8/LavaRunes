package klumel8.Lavas.Branches.AtAltar.Leaves;

import klumel8.Lavas.Framework.Leaf;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Magic;
import org.powbot.mobile.script.ScriptManager;

public class MagicImbue extends Leaf {
    @Override
    public boolean validate() {
        return (System.currentTimeMillis() - Store.lastImbue) > 13000 && Store.magicImbue;
    }

    @Override
    public void execute() {
        if(!Magic.LunarSpell.MAGIC_IMBUE.canCast()){
            System.out.println("DOES NOT MEET MAGIC REQUIREMENTS (lunar / POUCH)");
            Condition.sleep(100);
            ScriptManager.INSTANCE.stop();
            return;
        }else{
            Magic.LunarSpell.MAGIC_IMBUE.cast("Cast");
            //Would like to verify correct click but seems broken...
            System.out.println("Did the imbue spell");
            Store.didImbue();
            //lavasMain.task = LavasMain.Task.CraftRunes;
        }
    }

    @Override
    public String status() {
        return "casting magic imbue";
    }
}
