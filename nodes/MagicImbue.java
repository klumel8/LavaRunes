package klumel8.Lavas.nodes;

import klumel8.Lavas.Handlers.GearHandler;
import klumel8.Lavas.LavasMain;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Magic;
import org.powbot.mobile.script.ScriptManager;

public class MagicImbue extends Node{
    private final LavasMain lavasMain;
    private final GearHandler gearHandler;

    public MagicImbue(LavasMain lavasMain) {
        super(lavasMain);
        this.lavasMain = lavasMain;
        this.gearHandler = new GearHandler(lavasMain);
    }
    @Override
    public boolean validate() {
        return lavasMain.task == LavasMain.Task.MagicImbue;
    }

    @Override
    public void execute() {
        if(Magic.book() != Magic.Book.LUNAR && !gearHandler.canMakeLavas()){
            System.out.println("DOES NOT MEET MAGIC REQUIREMENTS (lunar / POUCH)");
            Condition.sleep(100);
            ScriptManager.INSTANCE.stop();
            return;
        }else{
            Magic.LunarSpell.MAGIC_IMBUE.cast("Cast");
            //Would like to verify correct click but seems broken...
            System.out.println("Did the imbue spell");
            Store.didImbue();
            lavasMain.task = LavasMain.Task.CraftRunes;
        }
    }

    @Override
    public String status() {
        return null;
    }
}
