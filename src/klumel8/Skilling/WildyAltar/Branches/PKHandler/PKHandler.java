package klumel8.Skilling.WildyAltar.Branches.PKHandler;

import klumel8.KlumAPI.Framework.Branch;
import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.WildyAltar.Shared;
import org.powbot.api.rt4.*;

import java.util.List;

public class PKHandler extends Branch {
    public final List<Leaf> leaves;

    public PKHandler(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        return pkDanger();
    }

    @Override
    public String status() {
        return "PK Handler";
    }

    public static boolean pkDanger(){
        int combatLVL = Players.local().getCombatLevel();
        int minCB = combatLVL - Combat.wildernessLevel();
        int maxCB = combatLVL + Combat.wildernessLevel();

        for (Player p : Players.stream()){
            if(!p.equals(Players.local())
                    && Combat.wildernessLevel() >= 0
                    && (System.currentTimeMillis() - Shared.deathTimeout) > 1200
                    && p.getCombatLevel() >= minCB
                    && p.getCombatLevel() <= maxCB
                    && Inventory.stream().name(Shared.boneName).isNotEmpty()
                    && Combat.health() == Combat.maxHealth()
            ){
                System.out.println("Found player within combat bracket " + p.getName());
                Shared.pkName = p.getName();
                return true;
            }
        }

        return false;
    }
}
