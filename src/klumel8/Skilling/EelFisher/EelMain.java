package klumel8.Skilling.EelFisher;

import klumel8.Skilling.EelFisher.Branches.FishEels.Leaves.ClickSpot;
import klumel8.Skilling.EelFisher.Branches.FishEels.FishEels;
import klumel8.Skilling.EelFisher.Branches.FishEels.Leaves.TurnToSpot;
import klumel8.Skilling.EelFisher.Branches.FishEels.Leaves.WalkCentreTile;
import klumel8.Skilling.EelFisher.Branches.FishEels.Leaves.WalkToSpot;
import klumel8.Skilling.EelFisher.Branches.ProcessEels.Leaves.Process;
import klumel8.Skilling.EelFisher.Branches.ProcessEels.ProcessEels;
import klumel8.KlumAPI.Framework.Branch;
import klumel8.KlumAPI.Framework.Leaf;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.*;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.service.ScriptUploader;

import java.util.ArrayList;
import java.util.List;

@ScriptManifest(
        name = "Klumel's Eels",
        description = "Fishes infernal & sacred eels. Start at the fishing spot with all required equipment.",
        version = "1.0",
        category = ScriptCategory.Fishing,
        priv = false
)

@ScriptConfiguration.List(
        {
                @ScriptConfiguration(
                        name = "eel",
                        description = "Sacred or Infernal eels",
                        defaultValue = "Sacred eel",
                        allowedValues = {"Sacred eel", "Infernal eel"},
                        optionType = OptionType.STRING
                )
        }
)

public class EelMain extends AbstractScript {
    public List<Branch> branches = new ArrayList<>();
    public static String branchStatus = "";
    public String leafStatus = "";

    public static String fishName = "Sacred eel";
    public static String toolName = "Knife";
    public static int spotId = 6488;
    public static Tile centreTile = new Tile(2191, 3069, 0);
    public static int lootId = 12934;

    public static boolean processEels = false;
    public static void main(String[] args){
        //ProcessBuilder builder = new ProcessBuilder("C:\\Users\\flori\\.powbot\\android\\platform-tools\\adb.exe", "-s", "127.0.0.1:5565", "forward", "tcp:61666", "tcp:61666");
        new ScriptUploader().uploadAndStart("Klumel's Eels", "", null, false, false);
    }

    @Override
    public void onStart(){
        Condition.sleep(300);
        System.out.println("Starting eel fisher");
        branches.add(new FishEels(new ClickSpot(), new TurnToSpot(), new WalkToSpot(), new WalkCentreTile()));
        branches.add(new ProcessEels(new Process()));

        if(getOption("eel").equals("Infernal eel")){
            toolName = "Hammer";
            fishName = "Infernal eel";
            spotId = 7676;
            //lootId = 21293; //Not useful atm, since not displaying
            //centreTile = new Tile(2539,5089,0);
            System.out.println("Infernal eel!");
        }

        Paint paint = new PaintBuilder()
                .addString(() -> "Branch status: " + branchStatus)
                .addString(() -> "Leaf status: " + leafStatus)
                .trackSkill(Skill.Fishing)
                .trackSkill(Skill.Cooking)
                //.trackInventoryItem(eelId)
                .build();
        addPaint(paint);

    }

    @Override
    public void poll() {
        //System.out.println("ani" + Players.local().animation() + ":" + Players.local().movementAnimation());
        for (Branch branch : branches) {
            if (branch.validate()) {
                branchStatus = branch.status();
                //System.out.println("validated " + branchStatus);
                for (Leaf leaf : branch.leaves) {
                    if (leaf.validate()) {
                        leafStatus = leaf.status();
                        leaf.execute();
                        return;
                    }
                }
            }
        }
    }
}
