package klumel8.Skilling.Lavas.Branches.RepairPouch.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Chat;
import org.powbot.api.rt4.Magic;
import org.powbot.api.rt4.Widgets;

public class ContactMage extends Leaf {
    int npcContactWidget = 75;
    int npcContactMageComponent = 12;
    int darkMageWidget1 = 231;
    int darkMageComponent1 = 4;

    @Override
    public boolean validate() {
        return !Bank.opened() && !Chat.chatting();
    }

    @Override
    public void execute() {
        if(Magic.LunarSpell.NPC_CONTACT.cast("Cast")) {
            if(Condition.wait(() -> Widgets.widget(npcContactWidget).component(npcContactMageComponent).visible(), 100, 80)) {
                if(Widgets.widget(npcContactWidget).component(npcContactMageComponent).interact("Dark Mage")) {
                    Condition.wait(() -> Widgets.widget(darkMageWidget1).component(darkMageComponent1).visible(), 100, 80);
                }
            }
        }
    }

    @Override
    public String status() {
        return "contacting mage";
    }
}
