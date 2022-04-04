package klumel8.Skilling.Lavas.store;

import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;

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
    public static boolean readyToCraft = false;
    public static long lastEssWithdraw = System.currentTimeMillis() - 10000;
    public static long lastRuneDeposit = System.currentTimeMillis() - 10000;

    public static boolean essMissing = false;

    public static String leafStatus = "";
    public static String errorMsg = "";

    public static int colPouchFill = 0;

    public static void didImbue(){
        Store.lastImbue = System.currentTimeMillis();
    }

    public static void getPouches(){
        List<Item> itemPouches = Inventory.stream().filter(i -> i.name().contains("pouch") && !i.name().contains("Rune")).list();
        for(Item pouch : itemPouches){
            Store.pouches.add(pouch.name());
            System.out.println("Added " + pouch.name());
        }
        Store.fullPouches = Store.pouches;
    }

    public static void setInitialItems(){
        for (Item i : Inventory.stream()){
            Store.initialItems.add(i.name());
        }
        Store.earthTalismanAmount = Inventory.stream().name("Earth talisman").count();
        System.out.println("pouch capacity " + pouchCapacity());

        if(!Store.magicImbue){
            Store.earthTalismanAmount = (int) Math.max(1, Inventory.stream().name("Earth talisman").count());
            System.out.println("Found " + Store.earthTalismanAmount + " earth talismans");
        }
    }

    public static void setPouchFull(String name){
        if(!fullPouches.contains(name)){
            fullPouches.add(name);
        }
        System.out.println("set " + name + " to full.");
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
        }else if(name.equals("Colossal pouch")){
            return 40;
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
