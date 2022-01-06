package klumel8.Lavas.nodes;

import klumel8.Lavas.Handlers.GearHandler;
import klumel8.Lavas.KlumScripts;
import klumel8.Lavas.LavasMain;
import klumel8.Lavas.store.LavaConstants;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import java.util.Collections;

public class Banking extends Node{

    private final LavasMain lavasMain;
    private final GearHandler gearHandler;

    LavaConstants lc = new LavaConstants();
    KlumScripts ks = new KlumScripts();

    public Banking(LavasMain lavasMain){
        super(lavasMain);
        this.lavasMain = lavasMain;
        this.gearHandler = new GearHandler(lavasMain);
    }

    @Override
    public boolean validate() {
        return lavasMain.task == LavasMain.Task.Banking;
    }

    @Override
    public void execute() {
        System.out.println("Banking");
        if(!Bank.opened()){
            lavasMain.task = LavasMain.Task.TravelToBank;
        }else if(Inventory.stream().id(lc.brokenPouchId).isNotEmpty()){ //5515 is broken pouch id
            System.out.println("Found broken pouch, repairing");
            makeSpace();
            lavasMain.task = LavasMain.Task.RepairPouch;
        }else if(Inventory.stream().filter(i -> i.name().contains("Stamina potion")).isEmpty() && Store.useStaminas){
            makeSpace();
            Bank.withdraw("Stamina potion(4)", 1);
            Condition.wait(() -> Inventory.stream().filter(i -> i.name().contains("Stamina potion")).isNotEmpty(), 100, 30);
            ks.cWait(0.7);
        }else if(Inventory.stream().name("Earth rune").isEmpty()){
            makeSpace();
            Bank.withdraw("Earth rune", Bank.Amount.ALL);
            Condition.wait(() -> Inventory.stream().name("Earth rune").isNotEmpty(), 100, 30);
            ks.cWait(0.5);
        }else if(Inventory.stream().name("Earth talisman").count() < Store.earthTalismanAmount){
            makeSpace();
            Bank.withdraw("Earth talisman", (int) (Store.earthTalismanAmount - Inventory.stream().name("Earth talisman").count()));
            Condition.wait(() -> Inventory.stream().name("Earth talisman").count() == Store.earthTalismanAmount, 100, 30);
            ks.cWait(0.5);
        }else if(Inventory.stream().name("Lava rune").isNotEmpty()){
            Bank.deposit("Lava rune", Bank.Amount.ALL);
            Condition.wait(() -> Inventory.stream().name("Lava rune").isEmpty(), 100, 30);
            ks.cWait(0.5);

        }else if(Inventory.stream().name("Air rune").isNotEmpty()){
            Bank.deposit("Air rune", Bank.Amount.ALL);
            Condition.wait(() -> Inventory.stream().name("Asrtal rune").isEmpty(), 100, 30);
            ks.cWait(0.5);

        }else if(Inventory.stream().name("Cosmic rune").isNotEmpty()){
            Bank.deposit("Cosmic rune", Bank.Amount.ALL);
            Condition.wait(() -> Inventory.stream().name("Cosmic rune").isEmpty(), 100, 30);
            ks.cWait(0.5);

        }else if(!gearHandler.neckOkay()){
            makeSpace();
            Bank.withdraw("Binding necklace", 1);
            Condition.wait(() -> Inventory.stream().name("Binding necklace").isNotEmpty(),100, 30);
            ks.cWait(0.7);
            if(Inventory.stream().name("Binding necklace").isNotEmpty()){
                Inventory.stream().name("Binding necklace").first().interact("Wear");
                Condition.wait(() -> gearHandler.neckOkay(),100, 30);
            }
        }else if(!gearHandler.ringOkay()){
            Bank.withdraw("Ring of dueling(8)", 1);
            makeSpace();
            Condition.wait(() -> Inventory.stream().name("Ring of dueling(8)").isNotEmpty(),100, 30);
            ks.cWait(0.7);
            if(Inventory.stream().name("Ring of dueling(8)").isNotEmpty()){
                Inventory.stream().name("Ring of dueling(8)").first().interact("Wear");
                Condition.wait(() -> gearHandler.ringOkay(), 100, 30);
                ks.cWait(0.7);
                if(Inventory.stream().name("Ring of dueling(1)").isNotEmpty()){
                    Bank.deposit("Ring of dueling(1)", Bank.Amount.ALL);
                    Condition.wait(() -> Inventory.stream().name("Ring of dueling(1)").isEmpty(), 100, 30);
                }
            }
        }else if(Inventory.isFull() && !checkPouchesFull()){
            Collections.shuffle(Store.pouches);
            for (String pouch : Store.pouches){
                if(!Store.fullPouches.contains(pouch) && Inventory.stream().name("Pure essence").count() > Store.pouchSize(pouch)){
                    if(Inventory.stream().name(pouch).first().actions().contains("Fill")){
                        int slotsBefore = Inventory.emptySlotCount();
                        Inventory.stream().name(pouch).first().interact("Fill");
                        Condition.wait(() -> slotsBefore != Inventory.emptySlotCount(), 100, 30);
                        ks.cWait(0.6);
                    }
                    System.out.println("Set " + pouch + " empty");
                    Store.setPouchFull(pouch);
                }
            }
        }else if(!Inventory.isFull()){
            Bank.withdraw("Pure essence", Bank.Amount.ALL);
            Condition.wait(() -> Inventory.isFull(), 100, 30);
            ks.cWait(0.8);
        }else if(!Store.goFerox){
            Bank.close();
            if(Condition.wait(() -> !Bank.opened(), 100, 30)) {
                lavasMain.task = LavasMain.Task.TravelToAltar;
            }
        }else if(Store.goFerox && Objects.stream().name("Pool of refreshment").isNotEmpty()){
            Bank.close();
            if(Condition.wait(() -> !Bank.opened(), 100, 30)) {
                lavasMain.task = LavasMain.Task.TravelToAltar;
            }
            ks.cWait(0.7);
            GameObject pool = Objects.stream().name("Pool of refreshment").nearest().first();
            if(!pool.inViewport()){
                Camera.turnTo(pool.tile());
                ks.cWait(0.8);
            }
            pool.interact("Drink");
            if(Condition.wait(() -> Movement.energyLevel() > 90, 100, 50)){
                System.out.println("Succesfully replenished run");
                Store.goFerox = false;
            }
            Condition.sleep(600);
            ks.cWait(1);
        }
    }

    @Override
    public String status() {
        return "Banking";
    }

    public boolean checkPouchesFull(){
        for (String pouch : Store.pouches){
            if(!Store.fullPouches.contains(pouch)){
                return false;
            }
        }
        return true;
    }

    public boolean makeSpace(){
        if(Inventory.emptySlotCount() <= 4 && Inventory.stream().name("Pure essence").isNotEmpty()){
            Bank.deposit("Pure essence", Bank.Amount.ALL);
            Condition.wait(() -> Inventory.isFull(), 100, 30);
            ks.cWait(0.7);
            return true;
        }
        return false;
    }
}
