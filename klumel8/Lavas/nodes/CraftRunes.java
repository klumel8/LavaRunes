package klumel8.Lavas.nodes;

import klumel8.Lavas.Handlers.GearHandler;
import klumel8.Lavas.KlumScripts;
import klumel8.Lavas.LavasMain;
import klumel8.Lavas.store.LavaConstants;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.mobile.script.ScriptManager;

import java.util.Collections;

public class CraftRunes extends Node{
    private final LavasMain lavasMain;
    private final GearHandler gearHandler;
    private final LavaConstants lavaConstants;

    KlumScripts ks = new KlumScripts();

    public CraftRunes(LavasMain lavasMain) {
        super(lavasMain);
        this.lavasMain = lavasMain;
        this.gearHandler = new GearHandler(lavasMain);
        this.lavaConstants = new LavaConstants();
    }

    @Override
    public boolean validate() {
        return lavasMain.task == LavasMain.Task.CraftRunes;
    }

    @Override
    public void execute() {
        System.out.println("made it to execute on CraftRunes");
        if(!gearHandler.hasEarthRunes()){
            System.out.println("DOES NOT MEET RUNE REQUIREMENTS (Earth runes)");
            Condition.sleep(100);
            ScriptManager.INSTANCE.stop();
            return;
        }

        if(!lavaConstants.altarArea.contains(Players.local().tile())){
            System.out.println("Should have been in altar area... reinitiating travel to altar");
            lavasMain.task = LavasMain.Task.TravelToAltar;
            return;
        }

        GameObject altar = Objects.stream().name("Altar").first();
        if(altar.inViewport() && Inventory.stream().name("Pure essence").isNotEmpty() && !imbueActive() && Store.magicImbue){
            lavasMain.task = LavasMain.Task.MagicImbue;
        }else if(altar.inViewport() && Inventory.stream().name("Pure essence").isNotEmpty() && gearHandler.canMakeLavas()){
            Inventory.stream().name("Earth rune").first().interact("Use");
            int invSlots = Inventory.emptySlotCount();
            altar.interact("Use");
            Condition.wait(() -> Inventory.emptySlotCount() != invSlots, 100, 30);
            ks.cWait(1);
        }else if(!Store.fullPouches.isEmpty()) {
            lavasMain.task = LavasMain.Task.EmptyPouches;
        }else{
            if(Movement.energyLevel() < 25 && !Store.useStaminas){
                System.out.println("detected low run, going to ferox");
                Store.goFerox = true;
            }
            lavasMain.task = LavasMain.Task.TravelToBank;
        }

    }

    @Override
    public String status() {
        return "Crafting runes";
    }

    public boolean imbueActive(){
        System.out.println("imbueActive"+System.currentTimeMillis() + ":"+Store.lastImbue + ":" + (System.currentTimeMillis() - Store.lastImbue));
        return (System.currentTimeMillis() - Store.lastImbue < 13000);
    }
}
