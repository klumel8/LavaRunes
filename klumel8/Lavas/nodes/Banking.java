package klumel8.Lavas.nodes;

import klumel8.Lavas.Handlers.GearHandler;
import klumel8.Lavas.KlumScripts;
import klumel8.Lavas.LavasMain;
import klumel8.Lavas.store.LavaConstants;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import java.util.Collections;
import java.util.Random;

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
        Random rand = new Random();
        System.out.println("Banking");

        //check for critical errors;
        if(!Bank.opened()){
            lavasMain.task = LavasMain.Task.TravelToBank;
            return;
        }else if(Inventory.stream().id(lc.brokenGiantId, lc.brokenLargeId, lc.brokenMediumId).isNotEmpty()){ //5515 is broken pouch id
            System.out.println("Found broken pouch, repairing");
            makeSpace();
            lavasMain.task = LavasMain.Task.PrepareInvForMage;
            return;
        }

        //check for items that should be added to inventory
        if(Inventory.stream().filtered(i -> i.name().contains("Stamina potion")).isEmpty() && Store.useStaminas){
            makeSpace();
            if(!Bank.withdraw("Stamina potion(4)", 1)) {
                return;
            }
        }
        if(Inventory.stream().name("Earth rune").isEmpty()){
            makeSpace();
            if(!Bank.withdraw("Earth rune", Bank.Amount.ALL)) {
                return;
            }
        }
        if(Inventory.stream().name("Earth talisman").count() < Store.earthTalismanAmount){
            makeSpace();
            if(!Bank.withdraw("Earth talisman", (int) (Store.earthTalismanAmount - Inventory.stream().name("Earth talisman").count()))) {
                return;
            }
        }

        //check for items to deposit
        if(Inventory.stream().name("Lava rune").isNotEmpty()){
            if(!Bank.deposit("Lava rune", Bank.Amount.ALL)) {
                return;
            }
        }
        if(Inventory.stream().name("Air rune").isNotEmpty()){
            if(!Bank.deposit("Air rune", Bank.Amount.ALL)) {
                return;
            }
        }
        if(Inventory.stream().name("Cosmic rune").isNotEmpty()){
            if(!Bank.deposit("Cosmic rune", Bank.Amount.ALL)) {
                return;
            }
        }

        //check for depleted binding necklaces or ring of duelings
        if(!gearHandler.neckOkay()){
            makeSpace();
            Bank.withdraw("Binding necklace", 1);
            Condition.wait(() -> Inventory.stream().name("Binding necklace").isNotEmpty(),100, 30);
            if(Inventory.stream().name("Binding necklace").isNotEmpty()){
                Inventory.stream().name("Binding necklace").first().interact("Wear");
                Condition.wait(() -> gearHandler.neckOkay(),100, 30);
            }
        }
        if(!gearHandler.ringOkay()){
            Bank.withdraw("Ring of dueling(8)", 1);
            makeSpace();
            Condition.wait(() -> Inventory.stream().name("Ring of dueling(8)").isNotEmpty(),100, 30);
            if(Inventory.stream().name("Ring of dueling(8)").isNotEmpty()){
                Inventory.stream().name("Ring of dueling(8)").first().interact("Wear");
                Condition.wait(() -> gearHandler.ringOkay(), 100, 30);
                if(Inventory.stream().name("Ring of dueling(1)").isNotEmpty()){
                    Bank.deposit("Ring of dueling(1)", Bank.Amount.ALL);
                    Condition.wait(() -> Inventory.stream().name("Ring of dueling(1)").isEmpty(), 100, 30);
                }
            }
        }
        if(Inventory.isFull() && !checkPouchesFull()){
            Collections.shuffle(Store.pouches);
            long virtualPureEss = Inventory.stream().name("Pure essence").count();
            long initialPureEss = virtualPureEss;
            for (String pouch : Store.pouches){
                if(!Store.fullPouches.contains(pouch) && virtualPureEss > Store.pouchSize(pouch)){
                    if(Inventory.stream().name(pouch).first().actions().contains("Fill")){
                        int slotsBefore = Inventory.emptySlotCount();
                        if(Inventory.stream().name(pouch).first().interact("Fill")) {
                            //Condition.wait(() -> slotsBefore != Inventory.emptySlotCount(), 100, 30);
                            Store.setPouchFull(pouch);
                            virtualPureEss -= Store.pouchSize(pouch);
                        }
                    }else{
                        Store.setPouchFull(pouch);
                    }
                }
            }
            Condition.wait(() -> Inventory.stream().name("Pure essence").count() != initialPureEss, 100, 20);
        }

        //check to determine what to do as last action, either fill up pure essence, bank normal, or bank ferox
        if(!Inventory.isFull()){
            if(Bank.withdraw("Pure essence", Bank.Amount.ALL)){
                return;
            }
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
            GameObject pool = Objects.stream().name("Pool of refreshment").nearest().first();
            if(!pool.inViewport()){
                Camera.turnTo(pool.tile());
            }
            pool.interact("Drink");
            if(Condition.wait(() -> Movement.energyLevel() > 90, 100, 50)){
                System.out.println("Succesfully replenished run");
                Store.goFerox = false;
            }
            Condition.sleep(600+rand.nextInt(200));
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
            return true;
        }
        return false;
    }
}
