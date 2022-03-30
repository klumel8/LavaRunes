package klumel8.EelFisher.Branches.ProcessEels.Leaves;

import klumel8.EelFisher.EelMain;
import klumel8.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;

public class Process extends Leaf {
    @Override
    public boolean validate() {
        return true; //Only leaf in the process branch so why not
    }

    @Override
    public void execute() {
        Item tool = Inventory.stream().name(EelMain.toolName).first();
        tool.interact("Use");
        Item eel = Inventory.stream().name(EelMain.fishName).first();
        eel.interact("Use");
        Condition.wait(() -> !eel.valid(), 100, 12);
    }

    @Override
    public String status() {
        return "Using tool on eels";
    }
}
