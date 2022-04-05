package klumel8.Skilling.WildyAltar.Branches.PKHandler.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.WildyAltar.Shared;
import org.powbot.api.Condition;
import org.powbot.api.rt4.*;

import java.util.Collections;

public class Hopping extends Leaf {
    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void execute() {
        if(Game.tab() != Game.Tab.LOGOUT) {
            Game.tab(Game.Tab.LOGOUT);
        }

        if(Widgets.widget(182).component(3).visible()){
            Widgets.widget(182).component(3).click();
        }

        Collections.shuffle(Shared.clickableWorlds);

        for (Component c : Shared.clickableWorlds){
            if(c.index() != Worlds.current().id()){
                System.out.println("Hopping to world " + c.index());
                if(Worlds.stream().id(c.index()).first().hop()){
                    System.out.println("Clicked on new world");
                    Condition.wait(() -> Players.stream().name(Shared.pkName).isEmpty(), 100, 60);
                    Shared.hopTimeout = System.currentTimeMillis();
                    return;
                }
            }
        }


    }

    @Override
    public String status() {
        return "Logging out";
    }
}
