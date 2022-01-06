package klumel8.Lavas.nodes;

import com.android.tools.r8.code.L;
import klumel8.Lavas.KlumScripts;
import klumel8.Lavas.LavasMain;
import klumel8.Lavas.store.LavaConstants;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.mobile.script.ScriptManager;

import java.util.Random;

public class RepairPouch extends Node{
    Random rand = new Random();
    KlumScripts ks = new KlumScripts();
    LavaConstants lc = new LavaConstants();
    private final LavasMain lavasMain;

    int widgetId1 = 75;
    int componentId1 = 12;
    int widgetId2 = 231;
    int componentId2 = 4;
    int widgetId3 = 162;
    int componentId3 = 1;
    int widgetId4 = 217;
    int componentId4 = 4;
    int widgetId5 = 231;
    int componentId5 = 4;

    public RepairPouch(LavasMain lavasMain) {
        super(lavasMain);
        this.lavasMain = lavasMain;
    }

    @Override
    public boolean validate() {
        return lavasMain.task == LavasMain.Task.RepairPouch;
    }

    @Override
    public void execute() {
        System.out.println("Started mage handler");
        if(Magic.book() != Magic.Book.LUNAR || Skills.realLevel(Skill.Magic.getIndex()) < 67){
            System.out.println("cant repair pouches without lunar book and 67 magic");
            ScriptManager.INSTANCE.stop();
        }else if(Inventory.stream().id(lc.brokenGiantId, lc.brokenLargeId, lc.brokenMediumId).isEmpty() && !Chat.chatting()){
            System.out.println("No broken pouches found");
            Store.contactedMage = false;
            lavasMain.task = LavasMain.Task.Banking;
        }else if(!Bank.opened() && !hasRunes()){
            //System.out.println("Bank should be open to get runes");
            lavasMain.task = LavasMain.Task.TravelToBank;
        }else if(Inventory.stream().name("Cosmic rune").isEmpty()){
            Bank.withdraw("Cosmic rune", Bank.Amount.ALL);
            ks.cWait(0.9);
        }else if(Inventory.stream().name("Air rune").isEmpty()){
            Bank.withdraw("Air rune", Bank.Amount.ALL);
            ks.cWait(0.9);
        }else if(Inventory.stream().name("Astral rune").isEmpty() && Inventory.stream().name("Rune pouch").isEmpty()){
            Bank.withdraw("Astral rune", Bank.Amount.ALL);
            ks.cWait(0.9);
        }else if(Bank.opened()) {
            Bank.close();
            //System.out.println("Got runes closing bank");
            Condition.wait(() -> !Bank.opened(), 100, 30);
            //System.out.println("closed bank");
        }else if(!Bank.opened() && hasRunes() && !Store.contactedMage){
            //System.out.println("contacting mage...");
            Magic.LunarSpell.NPC_CONTACT.cast("Cast");
            if(Condition.wait(() -> Widgets.widget(75).component(12).visible(), 100, 80)) {
                //System.out.println("Found valid widget!");
                Store.contactedMage = true;
            }
        }else if(Widgets.widget(widgetId1).component(componentId1).visible()){
            Widgets.widget(widgetId1).component(componentId1).interact("Dark Mage");
            Condition.wait(() -> Widgets.widget(widgetId2).component(componentId2).visible(), 100, 50);
            ks.cWait(0.7);
        }else if(Widgets.widget(widgetId2).component(componentId2).visible()){
            Chat.clickContinue();
            Condition.wait(() -> Widgets.widget(widgetId3).component(componentId3).visible(), 100, 50);
            ks.cWait(0.7);
        }else if(Widgets.widget(widgetId3).component(componentId3).visible()){
            Chat.continueChat("Can you repair my pouches?");
            Condition.wait(() -> Widgets.widget(widgetId4).component(componentId4).visible(), 100, 50);
            ks.cWait(0.7);
        }else if(Widgets.widget(widgetId4).component(componentId4).visible()){
            Chat.clickContinue();
            Condition.wait(() -> Widgets.widget(widgetId5).component(componentId5).visible(), 100, 50);
            ks.cWait(0.7);
        }else if(Widgets.widget(widgetId5).component(componentId5).visible()){
            Chat.continueChat();
            Condition.wait(() -> !Widgets.widget(widgetId5).component(componentId5).visible(), 100, 50);
            ks.cWait(0.7);
        }else{
            Store.contactedMage = false;
        }

    }

    @Override
    public String status() {
        return null;
    }

    //click dark mage) [INFO] Found component for WidgetActionEvent(widgetId=4915212) WidgetIndex=75, ComponentIndex=12
    //[] widgetID 4915212, widgetName , interaction Dark Mage
    //Click continue 1) [INFO] Found component for WidgetActionEvent(widgetId=15138820) WidgetIndex=231, ComponentIndex=4
    //[] widgetID 15138820, widgetName , interaction Continue
    //click repair pouches) [INFO] Found component for WidgetActionEvent(widgetId=14352385) WidgetIndex=162, ComponentIndex=1
    //[] widgetID 14352385, widgetName , interaction Continue
    //click continue 2) [INFO] Found component for WidgetActionEvent(widgetId=14221316) WidgetIndex=217, ComponentIndex=4
    //[] widgetID 14221316, widgetName , interaction Continue
    //click continue 3) [INFO] Found component for WidgetActionEvent(widgetId=15138820) WidgetIndex=231, ComponentIndex=4
    //[] widgetID 15138820, widgetName , interaction Continue

    public boolean hasRunes(){
        return Inventory.stream().name("Cosmic rune").isNotEmpty()
                && Inventory.stream().name("Air rune").isNotEmpty()
                && (Inventory.stream().name("Astral rune").isNotEmpty() || Inventory.stream().name("Rune pouch").isNotEmpty());
    }
}

