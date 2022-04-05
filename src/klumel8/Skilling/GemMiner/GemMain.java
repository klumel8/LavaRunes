package klumel8.Skilling.GemMiner;

import klumel8.KlumAPI.Framework.Branch;
import klumel8.KlumAPI.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.*;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.service.ScriptUploader;

import java.util.ArrayList;
import java.util.List;

@ScriptManifest(
        name = "Klumel's GemMiner",
        description = "Mines gems in the private mine in shillo",
        version = "1.0",
        category = ScriptCategory.Other,
        priv = true
)

public class GemMain extends AbstractScript {
    public List<Branch> branches = new ArrayList<>();
    public static String branchStatus = "";
    public String leafStatus = "";

    int i = 0;

    Tile[] gems = {
            new Tile(2843, 9392, 0),
            new Tile(2844, 9391, 0),
            new Tile(2845, 9391, 0),
            new Tile(2846, 9390, 0),
            new Tile(2847, 9390, 0),
            new Tile(2848, 9390, 0),
            new Tile(2848, 9386, 0),
            new Tile(2849, 9387, 0),
            new Tile(2852, 9390, 0),
            new Tile(2853, 9389, 0),
            new Tile(2854, 9388, 0),
            new Tile(2857, 9386, 0),
            new Tile(2857, 9385, 0),
            new Tile(2857, 9382, 0),
            new Tile(2856, 9382, 0),
            new Tile(2855, 9382, 0),
            new Tile(2853, 9380, 0),
            new Tile(2851, 9377, 0),
            new Tile(2850, 9378, 0),
            new Tile(2848, 9379, 0),
            new Tile(2847, 9379, 0),
            new Tile(2847, 9381, 0)
    };

    //11390 depleted, 11380 gems

    public static void main(String[] args){
        //ProcessBuilder builder = new ProcessBuilder("C:\\Users\\flori\\.powbot\\android\\platform-tools\\adb.exe", "-s", "127.0.0.1:5565", "forward", "tcp:61666", "tcp:61666");
        new ScriptUploader().uploadAndStart("Klumel's GemMiner", "", null, false, true);
    }

    @Override
    public void onStart(){
        //branches.add(new Branch(new Leaf()))

        Paint paint = new PaintBuilder()
                .trackSkill(Skill.Mining)
                .addString(() -> "Branch status: " + branchStatus)
                .build();
        addPaint(paint);

    }

    @Override
    public void poll() {
        int n = gems.length;

        if(i < n && !DepositBox.opened() && !Inventory.isFull()) {
            GameObject gem = Objects.stream().id(11380, 11381).at(gems[i]).first();

            if (!gem.inViewport()) {
                Camera.turnTo(gem);
            }

            if (!gem.valid()) {
                i++;
            } else {
                if (gem.interact("Mine")) {
                    branchStatus = "Mining";
                    Condition.wait(() -> !gem.valid(), 100, 100);
                    i++;
                }
            }

        }else{
            if(DepositBox.open() || DepositBox.opened()){
                branchStatus = "Banking";
                if(DepositBox.depositInventory()){
                    if(DepositBox.close()){
                        i = 0;
                    }
                }
            }
        }



    }
}
