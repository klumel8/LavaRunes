package klumel8.Skilling.MonkeyTrainer.Branches.MonkeyHandler;

import klumel8.KlumAPI.Framework.Branch;
import klumel8.KlumAPI.Framework.Leaf;
import klumel8.Skilling.MonkeyTrainer.Shared;
import org.powbot.api.Tile;
import org.powbot.api.rt4.*;

import java.util.ArrayList;
import java.util.List;

public class MonkeyHandler extends Branch {
    public final List<Leaf> leaves;

    public MonkeyHandler(Leaf... leaves) {
        super(leaves);
        this.leaves = List.of(leaves);
    }

    @Override
    public boolean validate() {
        if(Players.local().tile().distanceTo(Shared.baseTile) >= 1){
            return false;
        }
        updateMonkeys();
        setBestMonkey();
        updateTraps();
        return !Shared.droppingBones & !Shared.pickingBones;
    }

    @Override
    public String status() {
        return "Monkey Handler";
    }

    public void updateMonkeys(){
        Shared.monkeys = Npcs.stream().id(Shared.targetID).within(6);
        if(Shared.monkeys.isEmpty()){
            return;
        }

        //initialize some stuff
        Shared.monkeyTiles = new ArrayList<>();
        Shared.aggroMonkeys = 0;
        Shared.monkeyCount = Shared.monkeys.count();

        for (Npc monkey : Shared.monkeys){
            Shared.monkeyTiles.add(monkey.tile());

            if(monkey.interacting().name().equals(Players.local().name())){
                Shared.aggroMonkeys += 1;
            }
        }
    }

    public void setBestMonkey(){
        int bestStack = 0;
        for (Npc monkey : Shared.monkeys){
            int stack = 0;
            Tile monkeyTile = monkey.tile();
            for(Tile tile : Shared.monkeyTiles){
                if(tile.distanceTo(monkeyTile) < 1.8){
                    stack++;
                }
            }

            if(stack > bestStack){
                Shared.bestMonkey = monkey;
                bestStack = stack;
            }

            Shared.maxStack = bestStack;
        }
    }

    public void updateTraps(){
        if(Shared.trapTiles.size() < 2){
            System.out.println("Adding spike traps");
            for (GameObject spike : Objects.stream().name("Floor spikes")){
                Shared.trapTiles.add(spike.tile());
            }
        }
    }
}
