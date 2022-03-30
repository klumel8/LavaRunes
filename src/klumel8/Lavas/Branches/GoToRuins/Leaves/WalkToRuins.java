package klumel8.Lavas.Branches.GoToRuins.Leaves;

import klumel8.Framework.Leaf;
import klumel8.Lavas.KlumScripts;
import klumel8.Lavas.store.LavaConstants;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Objects;
import org.powbot.api.rt4.Players;

public class WalkToRuins extends Leaf {
    LavaConstants lc = new LavaConstants();
    KlumScripts ks = new KlumScripts();

    @Override
    public boolean validate() {
        return lc.ruinsArea.contains(Players.local()) && !Objects.stream().name("Mysterious ruins").first().inViewport();
    }

    @Override
    public void execute() {
        Tile originalTile = Players.local().tile();
        if(lc.mysteriousRuins.reachable()){
            if(ks.moveToRandom(lc.mysteriousRuins)) {
                if(Condition.wait(() -> !Players.local().tile().equals(originalTile), 100, 12)) {
                    Condition.wait(() -> lc.mysteriousRuins.distanceTo(Players.local().tile()) < 7 ||
                            Objects.stream().name("Mysterious ruins").first().inViewport(), 100, 70);
                }
            }
        }else{
            Movement.moveTo(lc.mysteriousRuins);
            if(Condition.wait(() -> !Players.local().tile().equals(originalTile), 100, 12)) {
                Condition.wait(() -> lc.mysteriousRuins.distanceTo(Players.local().tile()) < 6, 100, 60);
            }
        }
    }

    @Override
    public String status() {
        return "walking to ruins";
    }
}
