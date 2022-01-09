package klumel8.Lavas;

import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.magic.Rune;
import org.powbot.api.rt4.stream.item.InventoryItemStream;
import org.powbot.api.rt4.stream.locatable.interactive.GameObjectStream;
import org.powbot.mobile.script.ScriptManager;
import klumel8.Lavas.store.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KlumScripts {
    Random rand = new Random();

    public void cWait(double scale){
        int time = 50;
        double g = rand.nextGaussian()*scale;
        g = g*g*100;
        time = (int) (time + g);
        Condition.sleep(time);
    }

    public boolean moveToRandom(Tile t) {
        int x = t.getX() + 2 - rand.nextInt(5);
        int y = t.getY() + 2 - rand.nextInt(5);
        int floor = t.getFloor();

        Tile loc = new Tile(x, y, floor);

        if (loc.matrix().inViewport() && rand.nextInt(2) == 0) {
            return loc.matrix().interact("Walk here");
        } else {
            return Movement.step(loc);
        }
    }

    public boolean makeSpace(){
        if(Inventory.emptySlotCount() <= 4 && Inventory.stream().name("Pure essence").isNotEmpty()){
            Bank.deposit("Pure essence", Bank.Amount.ALL);
            Condition.wait(() -> Inventory.isFull(), 100, 30);
            return true;
        }
        return false;
    }
}
