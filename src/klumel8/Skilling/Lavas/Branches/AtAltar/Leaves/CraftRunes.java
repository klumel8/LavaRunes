package klumel8.Skilling.Lavas.Branches.AtAltar.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.Lavas.store.Store;
import org.powbot.api.rt4.*;

public class CraftRunes extends Leaf {

    @Override
    public boolean validate() {
        return (Inventory.stream().name("Pure essence").isNotEmpty() || Store.readyToCraft)
                && (imbueActive() || Inventory.stream().name("Earth talisman").isNotEmpty())
                && Inventory.stream().name("Earth rune").isNotEmpty();
    }

    @Override
    public void execute() {

        GameObject altar = Objects.stream().name("Altar").first();
        Inventory.stream().name("Earth rune").first().interact("Use");
        altar.interact("Use");


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
