package klumel8.Lavas.nodes;

import klumel8.Lavas.Handlers.GearHandler;
import klumel8.Lavas.KlumScripts;
import klumel8.Lavas.LavasMain;
import klumel8.Lavas.store.LavaConstants;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.stream.locatable.interactive.GameObjectStream;

import java.util.Random;

public class TravelToAltar extends Node{
    private final LavasMain lavasMain;
    private final GearHandler gearHandler;
    private final LavaConstants lavaConstants;

    Random rand = new Random();
    KlumScripts ks = new KlumScripts();

    public TravelToAltar(LavasMain lavasMain){
        super(lavasMain);
        this.lavasMain = lavasMain;
        this.gearHandler = new GearHandler(lavasMain);
        this.lavaConstants = new LavaConstants();
    }

    @Override
    public boolean validate() {
        return lavasMain.task == LavasMain.Task.TravelToAltar;
    }

    @Override
    public void execute() {
        GameObjectStream outsideAltar = Objects.stream().name("Mysterious ruins");
        //if(!Inventory.isFull() || gearHandler.readyToCraft()){
        if (Bank.opened()) {
            Bank.close();
            Condition.wait(() -> !Bank.opened(), 100, 30);
        }else if((!Inventory.isFull() || !gearHandler.readyToCraft()) && !lavaConstants.ruinsArea.contains(Players.local().tile())){
            System.out.println("Inventory not full, or doesnt have required crafting gear");
            lavasMain.task = LavasMain.Task.TravelToBank;
        }else if(!lavaConstants.ruinsArea.contains(Players.local().tile())){
            System.out.println("Teleporting to dueling arena");
            gearHandler.ringTeleport("Duel arena");
            Condition.wait(() -> lavaConstants.ruinsArea.contains(Players.local().tile()), 100, 30);
        }else if(Movement.energyLevel() > 20 && !Movement.running()){
            Movement.running(true);
            Condition.wait(() -> Movement.running(), 100, 30);
        }else if(Movement.energyLevel() < 40 && Inventory.stream().filter(i -> i.name().contains("Stamina potion")).isNotEmpty()){
            Item stampot = Inventory.stream().filter(i -> i.name().contains("Stamina potion")).first();
            Condition.wait(() -> stampot.valid(), 100, 30);
        }else if(lavaConstants.ruinsArea.contains(Players.local().tile())){
            GameObject ruins = outsideAltar.first();
            if(ruins.inViewport()){
                if(enterAltar(ruins)) {
                    if(Condition.wait(() -> lavaConstants.altarArea.contains(Players.local().tile()), 100, 60)){
                        System.out.println("Moving on to making runes");
                        lavasMain.task = LavasMain.Task.CraftRunes;
                    }
                }
            }else if(ruins.tile().distanceTo(Players.local().tile()) < 10){
                Camera.turnTo(ruins.tile());
                ks.cWait(1.2);
            }else if(lavaConstants.mysteriousRuins.reachable()){
                ks.moveToRandom(lavaConstants.mysteriousRuins);
                ks.cWait(2);
            }else{
                Movement.moveTo(lavaConstants.mysteriousRuins);
                Condition.wait(() -> lavaConstants.mysteriousRuins.distanceTo(Players.local().tile()) < 6, 100, 60);
            }
        }
    }

    @Override
    public String status() {
        return "TravelToAltar";
    }

    public boolean enterAltar(GameObject ruins){
        if(gearHandler.hasTiara()){
            return ruins.interact("Enter");
        }else if(gearHandler.hasTalisman()){
            Inventory.stream().name("Fire talisman").first().interact("Use");
            ks.cWait(1);
            return ruins.interact("Use");
        }else{
            return false;
        }
    }
}
