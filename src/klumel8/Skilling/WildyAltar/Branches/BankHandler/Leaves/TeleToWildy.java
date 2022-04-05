package klumel8.Skilling.WildyAltar.Branches.BankHandler.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.WildyAltar.Shared;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

public class TeleToWildy extends Leaf {
    @Override
    public boolean validate() {
        return Shared.hasAllItems() && Inventory.isFull();
    }

    @Override
    public void execute() {
        Bank.close();
        Condition.wait(() -> !Bank.opened(), 100, 24);
        if(Game.tab() != Game.Tab.EQUIPMENT){
            Game.tab(Game.Tab.EQUIPMENT);
        }
        Equipment.itemAt(Equipment.Slot.NECK).interact("Lava Maze");
        if(Condition.wait(() -> Chat.chatting(), 100, 30)){
            System.out.println("Found chatting " + Chat.chatting());
        }
        Chat.continueChat("Okay, teleport to level 41 Wilderness.");
        Condition.wait(() -> Combat.wildernessLevel() >= 0, 200, 30);
    }

    @Override
    public String status() {
        return "Tele to wildy";
    }
}
