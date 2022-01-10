package klumel8.Lavas.Branches.RepairPouch.Leaves;

import klumel8.Lavas.Framework.Leaf;
import klumel8.Lavas.store.LavaConstants;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Chat;
import org.powbot.api.rt4.ChatOption;

public class HandleDialogue extends Leaf {

    LavaConstants lc = new LavaConstants();

    @Override
    public boolean validate() {
        return Chat.chatting();
    }

    @Override
    public void execute() {
        if (Chat.chatting()) {
            System.out.println("chat pending input");
            ChatOption chat = Chat.stream().first();
            if(Chat.continueChat("Can you repair my pouches?")){
                Condition.wait(() -> !chat.valid(), 100, 20);
            }else{
                Chat.clickContinue();
                Condition.wait(() -> !chat.valid(), 100, 20);
            }
        }
    }

    @Override
    public String status() {
        return "handling mage";
    }
}
