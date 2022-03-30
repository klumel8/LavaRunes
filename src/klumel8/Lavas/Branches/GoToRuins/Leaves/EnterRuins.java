package klumel8.Lavas.Branches.GoToRuins.Leaves;

import klumel8.Framework.Leaf;
import klumel8.Lavas.Handlers.GearHandler;
import klumel8.Lavas.store.LavaConstants;
import org.powbot.api.Condition;
import org.powbot.api.rt4.GameObject;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Players;

public class EnterRuins extends Leaf {
    GearHandler gearHandler = new GearHandler();
    LavaConstants lc = new LavaConstants();

    @Override
    public boolean validate() {
        return Objects.stream().name("Mysterious ruins").first().inViewport();
    }

    @Override
    public void execute() {
        if(enterAltar(Objects.stream().name("Mysterious ruins").first())) {
            Condition.wait(() -> lc.altarArea.contains(Players.local().tile()), 100, 60);
        }
    }

    @Override
    public String status() {
        return "entering ruins";
    }

    public boolean enterAltar(GameObject ruins){
        if(gearHandler.hasTiara()){
            return ruins.interact("Enter");
        }else if(gearHandler.hasTalisman()){
            Inventory.stream().name("Fire talisman").first().interact("Use");
            return ruins.interact("Use");
        }else{
            return false;
        }
    }
}
