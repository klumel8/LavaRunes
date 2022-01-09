package klumel8.Lavas.Branches.RepairPouch.Leaves;

import klumel8.Lavas.Framework.Leaf;
import klumel8.Lavas.store.LavaConstants;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Chat;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Widgets;

public class HandleMage extends Leaf {

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

    LavaConstants lc = new LavaConstants();

    @Override
    public boolean validate() {
        return Chat.chatting();
    }

    @Override
    public void execute() {
        System.out.println("Started mage handler");

        if (Widgets.widget(darkMageWidget1).component(darkMageComponent1).visible()) {
            Chat.clickContinue();
            Condition.wait(() -> Widgets.widget(darkMageWidget2).component(darkMageComponent2).visible(), 100, 20);
        }
        if (Widgets.widget(darkMageWidget2).component(darkMageComponent2).visible()) {
            Chat.continueChat("Can you repair my pouches?");
            Condition.wait(() -> Widgets.widget(darkMageWidget3).component(darkMageComponent3).visible(), 100, 20);
        }
        if (Widgets.widget(darkMageWidget3).component(darkMageComponent3).visible()) {
            Chat.clickContinue();
            Condition.wait(() -> Widgets.widget(darkMageWidget4).component(darkMageComponent4).visible(), 100, 20);
        }
        if (Widgets.widget(darkMageWidget4).component(darkMageComponent4).visible()) {
            Chat.continueChat();
            Condition.wait(() -> !Widgets.widget(darkMageWidget4).component(darkMageComponent4).visible(), 100, 20);
        }
        if (Chat.pendingInput()) {
            Chat.clickContinue();
        }
    }

    @Override
    public String status() {
        return "handling mage";
    }
}
