package klumel8.Lavas.nodes;

import klumel8.Lavas.Handlers.GearHandler;
import klumel8.Lavas.KlumScripts;
import klumel8.Lavas.LavasMain;
import klumel8.Lavas.store.LavaConstants;
import klumel8.Lavas.store.Store;
import org.powbot.api.Area;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.*;

public class TravelToBank extends Node{
    private final LavasMain lavasMain;
    private final GearHandler gearHandler;

    KlumScripts ks = new KlumScripts();
    LavaConstants lc = new LavaConstants();

    public TravelToBank(LavasMain lavasMain) {
        super(lavasMain);
        this.lavasMain = lavasMain;
        this.gearHandler = new GearHandler(lavasMain);
    }

    @Override
    public boolean validate() {
        return lavasMain.task == LavasMain.Task.TravelToBank;
    }

    @Override
    public void execute() {
        GameObject bank = Objects.stream().name("Bank Chest").nearest().first();
        Area bankArea;
        Tile bankTile;
        if(Store.goFerox) {
            bankArea = lc.feroxBankArea;
            bankTile = lc.feroxBankTile;
        }else{
            bankArea = lc.bankArea;
            bankTile = lc.bankTile;
        }
        if(!bankArea.contains(Players.local().tile())){
            if(Store.goFerox) {
                gearHandler.ringTeleport("Ferox Enclave");
            }else{
                gearHandler.ringTeleport("Castle Wars");
            }

            Condition.wait(() -> bankArea.contains(Players.local().tile()), 100, 30);
        }else if(bank.inViewport()){
            bank.interact("Use");
            if(Condition.wait(() -> Bank.opened(),100, 50)){
                System.out.println("Ready to bank");
                lavasMain.task = LavasMain.Task.Banking;
            }
        }else if(bank.tile().distanceTo(Players.local().tile()) > 8){
            ks.moveToRandom(bankTile);
            ks.cWait(1.8);
        }else if(bank.tile().distanceTo(Players.local().tile()) <= 8){
            Camera.turnTo(bankTile);
            ks.cWait(1.2);
        }
    }

    @Override
    public String status() {
        return "Travelling to bank";
    }
}
