package klumel8.Lavas.store;

import org.powbot.api.Area;
import org.powbot.api.Tile;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.mobile.script.Logger;

import java.util.ArrayList;
import java.util.List;

public class Store {
    public static boolean useStaminas;
    public static boolean magicImbue;
    public static long lastImbue;
    public static List<String> pouches = new ArrayList<>();
    public static List<String> fullPouches = new ArrayList<>();
    public static List<String> initialItems = new ArrayList<>();
    public static int lavasMade = 0;
    public static long earthTalismanAmount = 0;

    public static boolean contactedMage = false;
    public static boolean goFerox = false;

    public static void didImbue(){
        Store.lastImbue = System.currentTimeMillis();
    }

    public static void getPouches(){
        List<Item> itemPouches = Inventory.stream().filter(i -> i.name().contains("pouch") && !i.name().contains("Rune")).list();
        for(Item pouch : itemPouches){
            Store.pouches.add(pouch.name());
        }
        Store.fullPouches = Store.pouches;
    }

    public static void setInitialItems(){
        for (Item i : Inventory.stream()){
            Store.initialItems.add(i.name());
        }
        Store.earthTalismanAmount = Inventory.stream().name("Earth talisman").count();
    }

    public static void setPouchFull(String name){
        if(!fullPouches.contains(name)){
            fullPouches.add(name);
        }
    }

    public static void setPouchEmpty(String name){
        if(fullPouches.contains(name)){
            List<String> tempPouches = fullPouches;
            fullPouches = new ArrayList<>();
            for (String s : tempPouches){
                if(!s.equals(name)){
                    fullPouches.add(s);
                }
            }
        }
    }

    public static int pouchSize(String name){
        if(name.equals("Small pouch")){
            return 3;
        }else if(name.equals("Medium pouch")){
            return 6;
        }else if(name.equals("Large pouch")){
            return 9;
        }else if(name.equals("Giant pouch")){
            return 12;
        }else {
            return 0;
        }
    }

    public static int pouchCapacity(){
        int cap = 0;
        for (String pouch : pouches){
            cap += pouchSize(pouch);
        }
        return cap;
    }
}
