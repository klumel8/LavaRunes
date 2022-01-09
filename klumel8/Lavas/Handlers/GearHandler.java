package klumel8.Lavas.Handlers;

import klumel8.Lavas.LavasMain;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.Nameable;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.stream.item.EquipmentItemStream;
import org.powbot.api.rt4.stream.item.ItemStream;

import java.util.List;
import java.util.Random;

public class GearHandler {
    Random rand = new Random();
    private final LavasMain lavasMain;

    public GearHandler(LavasMain lavasMain) {
        this.lavasMain = lavasMain;
    }

    public boolean readyToCraft(){
        if(!canEnterAltar()){
            System.out.println("canEnterAltar() " + canEnterAltar());
        }
        if(!canMakeLavas()){
            System.out.println("canMakeLavas() " + canMakeLavas());
        }
        if(!ringOkay()){
            System.out.println("ringOkay() " + ringOkay());
        }
        if(!neckOkay()){
            System.out.println("neckOkay() " + neckOkay());
        }
        if(!hasEarthRunes()){
            System.out.println("hasEarthRunes() " + hasEarthRunes());
        }
        return canEnterAltar() && canMakeLavas() && ringOkay() && neckOkay() && hasEarthRunes();

    }

    public boolean canEnterAltar(){
        return hasTalisman() || hasTiara();
    }

    public boolean canMakeLavas(){
        return Store.magicImbue && Magic.LunarSpell.MAGIC_IMBUE.canCast()
                || (!Store.magicImbue && Inventory.stream().name("Earth talisman").isNotEmpty());
    }

    public boolean ringOkay(){
        return Equipment.stream().filter(i -> (i.name().contains("Ring of dueling") && !i.name().contains("(1)"))).isNotEmpty();
    }

    public boolean neckOkay(){
        return Equipment.stream().filter(i -> i.name().contains("Binding necklace")).isNotEmpty();
    }

    public boolean ringTeleport(String destination){
        if(Game.tab() != Game.Tab.EQUIPMENT) {
            Game.tab(Game.Tab.EQUIPMENT);
            Condition.sleep(200 + rand.nextInt(400));
        }
        return Equipment.itemAt(Equipment.Slot.RING).interact(destination);
    }

    public boolean hasTiara(){
        return Equipment.itemAt(Equipment.Slot.HEAD).name().equalsIgnoreCase("Fire tiara");
    }

    public boolean hasTalisman(){
        return Inventory.stream().name("Fire talisman").isNotEmpty();
    }

    public boolean hasEarthRunes(){
        return Inventory.stream().name("Earth rune").isNotEmpty();
    }

    public boolean hasImbueRunes(){
        return Magic.LunarSpell.MAGIC_IMBUE.canCast();
    }
}
