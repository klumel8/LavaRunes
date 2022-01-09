package klumel8.Lavas.nodes;

import klumel8.Lavas.LavasMain;
import klumel8.Lavas.store.LavaConstants;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.mobile.script.ScriptManager;

public class PrepareInvForMage extends Node{
    private final LavasMain lavasMain;
    LavaConstants lc = new LavaConstants();

    public PrepareInvForMage(LavasMain lavasMain) {
        super(lavasMain);
        this.lavasMain = lavasMain;
    }

    @Override
    public boolean validate() {
        return lavasMain.task == LavasMain.Task.PrepareInvForMage;
    }

    @Override
    public void execute() {
        if(Inventory.stream().id(lc.brokenGiantId, lc.brokenLargeId, lc.brokenMediumId).isEmpty() && !Chat.chatting()){
            System.out.println("No broken pouches found");
            Store.contactedMage = false;
            lavasMain.task = LavasMain.Task.Banking;
            return;
        }else if(!Bank.opened() && !Magic.LunarSpell.NPC_CONTACT.canCast()){
            lavasMain.task = LavasMain.Task.TravelToBank;
            return;
        }

        if(!Magic.LunarSpell.NPC_CONTACT.canCast()) {
            if (Inventory.stream().name("Cosmic rune").isEmpty()) {
                Bank.withdraw("Cosmic rune", Bank.Amount.ALL);
            }

            if (Inventory.stream().name("Air rune").isEmpty()) {
                Bank.withdraw("Air rune", Bank.Amount.ALL);
            }

            if (Inventory.stream().name("Astral rune").isEmpty() && Inventory.stream().name("Rune pouch").isEmpty()) {
                Bank.withdraw("Astral rune", Bank.Amount.ALL);
            }

            if (Bank.opened()) {
                Bank.close();
                //System.out.println("Got runes closing bank");
                Condition.wait(() -> !Bank.opened(), 100, 30);
                //System.out.println("closed bank");
            }
        }else{
            lavasMain.task = LavasMain.Task.RepairPouch;
        }
    }

    @Override
    public String status() {
        return null;
    }

    public boolean hasRunes(){
        return Inventory.stream().name("Cosmic rune").isNotEmpty()
                && Inventory.stream().name("Air rune").isNotEmpty()
                && (Inventory.stream().name("Astral rune").isNotEmpty() || Inventory.stream().name("Rune pouch").isNotEmpty());
    }
}
