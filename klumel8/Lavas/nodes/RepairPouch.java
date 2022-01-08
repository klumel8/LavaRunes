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

    int npcContactWidget = 75;
    int npcContactMageComponent = 12;
    int darkMageWidget1 = 231;
    int darkMageComponent1 = 4;
    int darkMageWidget2 = 162;
    int darkMageComponent2 = 1;
    int darkMageWidget3 = 217;
    int darkMageComponent3 = 4;
    int darkMageWidget4 = 231;
    int darkMageComponent4 = 4;

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
        if(Inventory.stream().id(lc.brokenGiantId, lc.brokenLargeId, lc.brokenMediumId).isEmpty() && !Chat.chatting()){
            System.out.println("No broken pouches found");
            Store.contactedMage = false;
            lavasMain.task = LavasMain.Task.Banking;
        }else if(Magic.book() != Magic.Book.LUNAR || Skills.realLevel(Skill.Magic.getIndex()) < 67){
            System.out.println("cant repair pouches without lunar book and 67 magic");
            ScriptManager.INSTANCE.stop();
        }else if(!Bank.opened() && hasRunes() && !Store.contactedMage){
            Magic.LunarSpell.NPC_CONTACT.cast("Cast");
            if(Condition.wait(() -> Widgets.widget(npcContactWidget).component(npcContactMageComponent).visible(), 100, 80)) {
                Store.contactedMage = true;
            }
        }else if(Widgets.widget(npcContactWidget).component(npcContactMageComponent).visible()){
            Widgets.widget(npcContactWidget).component(npcContactMageComponent).interact("Dark Mage");
            Condition.wait(() -> Widgets.widget(darkMageWidget1).component(darkMageComponent1).visible(), 100, 20);
        }else if(Widgets.widget(darkMageWidget1).component(darkMageComponent1).visible()){
            Chat.clickContinue();
            Condition.wait(() -> Widgets.widget(darkMageWidget2).component(darkMageComponent2).visible(), 100, 20);
        }else if(Widgets.widget(darkMageWidget2).component(darkMageComponent2).visible()){
            Chat.continueChat("Can you repair my pouches?");
            Condition.wait(() -> Widgets.widget(darkMageWidget3).component(darkMageComponent3).visible(), 100, 20);
        }else if(Widgets.widget(darkMageWidget3).component(darkMageComponent3).visible()){
            Chat.clickContinue();
            Condition.wait(() -> Widgets.widget(darkMageWidget4).component(darkMageComponent4).visible(), 100, 20);
        }else if(Widgets.widget(darkMageWidget4).component(darkMageComponent4).visible()){
            Chat.continueChat();
            Condition.wait(() -> !Widgets.widget(darkMageWidget4).component(darkMageComponent4).visible(), 100, 20);
        }else if(Chat.pendingInput()){
            Chat.clickContinue();
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

