package klumel8.Lavas.Branches.AtAltar.Leaves;

import klumel8.Lavas.Framework.Leaf;
import klumel8.Lavas.Handlers.GearHandler;
import klumel8.Lavas.KlumScripts;
import klumel8.Lavas.LavasMain;
import klumel8.Lavas.store.LavaConstants;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.mobile.script.ScriptManager;

public class CraftRunes extends Leaf {
    private LavasMain lavasMain;
    private GearHandler gearHandler;
    private LavaConstants lavaConstants;

    KlumScripts ks = new KlumScripts();


    @Override
    public boolean validate() {
        return Inventory.stream().name("Pure essence").isNotEmpty()
                && (imbueActive() || Inventory.stream().name("Earth talisman").isNotEmpty())
                && Inventory.stream().name("Earth rune").isNotEmpty();
    }

    @Override
    public void execute() {

        GameObject altar = Objects.stream().name("Altar").first();
        Inventory.stream().name("Earth rune").first().interact("Use");
        int invSlots = Inventory.emptySlotCount();
        if(altar.interact("Use")) {
            Condition.wait(() -> Inventory.emptySlotCount() != invSlots, 100, 20);
        }


    }

    @Override
    public String status() {
        return "crafting runes";
    }

    public boolean imbueActive(){
        //System.out.println("imbueActive"+System.currentTimeMillis() + ":"+Store.lastImbue + ":" + (System.currentTimeMillis() - Store.lastImbue));
        return (System.currentTimeMillis() - Store.lastImbue < 13000);
    }
}
