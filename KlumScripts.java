package klumel8.Lavas;

import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.stream.item.InventoryItemStream;
import org.powbot.api.rt4.stream.locatable.interactive.GameObjectStream;
import org.powbot.mobile.script.ScriptManager;

import java.util.Random;

public class KlumScripts {
    Random rand = new Random();
    Player localPlayer = Players.local();

    public void cWait(double scale){
        int time = 200;
        double g = rand.nextGaussian()*scale;
        g = g*g*100;
        time = (int) (time + g);
        Condition.sleep(time);
    }

    double distanceTo(Tile t){
        return localPlayer.tile().distanceTo(t);
    }

    public boolean equip(String name, Equipment.Slot slt){
        if (!Equipment.itemAt(slt).name().equalsIgnoreCase(name)){
            InventoryItemStream weapon = Inventory.stream().name(name);
            if (weapon.isEmpty()){
                System.out.println("Could not find " + name + " in inventory. STOPPING");
                ScriptManager.INSTANCE.stop();
            }else{
                boolean valid = Inventory.stream().name(name).first().interact("Wield");
                cWait(0.9);
                return valid;
            }
        }
        return false;
    }

    public boolean isRendered(String name){
        GameObjectStream objs = Objects.stream().name(name);
        if (objs.isNotEmpty()){
            GameObject obj = objs.first();
            //System.out.println(name+"exists");
            if(obj.isRendered() && obj.inViewport()){
                //System.out.println("found "+name);
                return true;
            }
        }
        return false;
    }

    public int eatFood(){
        InventoryItemStream food = Inventory.stream().filter(i -> i.actions().contains("Eat"));
        if(food.isNotEmpty()){
            if(food.first().interact("Eat")){
                return 1;
            }else{
                return 0;
            }
        }else{
            return -1;
        }
    }

    public boolean invContains(String[] name){
        return Inventory.stream().name(name).isNotEmpty();
    }

    public boolean moveToRandom(Tile t){
        int x = t.getX() + 2 - rand.nextInt(5);
        int y = t.getY() + 2 - rand.nextInt(5);
        int floor = t.getFloor();

        Tile loc = new Tile(x,y,floor);

        if(loc.matrix().inViewport() && rand.nextInt(2)==0){
            return loc.matrix().interact("Walk here");
        }else{
            return Movement.step(loc);
        }
    }
}
