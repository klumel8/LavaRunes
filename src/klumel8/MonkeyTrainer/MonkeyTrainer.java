package klumel8.MonkeyTrainer;

import com.google.common.eventbus.Subscribe;
import klumel8.Framework.Branch;
import klumel8.Framework.Leaf;
import klumel8.MonkeyTrainer.Branches.BankHandler.BankHandler;
import klumel8.MonkeyTrainer.Branches.BankHandler.Leaves.*;
import klumel8.MonkeyTrainer.Branches.BoneHandler.BoneHandler;
import klumel8.MonkeyTrainer.Branches.BoneHandler.Leaves.DistributeBones;
import klumel8.MonkeyTrainer.Branches.BoneHandler.Leaves.PickUpBones;
import klumel8.MonkeyTrainer.Branches.GoToSpot.Leaves.CloseBank;
import klumel8.MonkeyTrainer.Branches.GoToSpot.Leaves.TeleToDungeon;
import klumel8.MonkeyTrainer.Branches.StatsUpkeep.Leaves.*;
import klumel8.MonkeyTrainer.Branches.StatsUpkeep.StatsUpkeep;
import klumel8.MonkeyTrainer.Branches.MonkeyHandler.Leaves.*;
import klumel8.MonkeyTrainer.Branches.MonkeyHandler.MonkeyHandler;
import klumel8.MonkeyTrainer.Branches.GoToSpot.Leaves.Walker;
import klumel8.MonkeyTrainer.Branches.GoToSpot.GoToSpot;
import org.powbot.api.Color;
import org.powbot.api.Condition;
import org.powbot.api.Tile;
import org.powbot.api.event.MessageEvent;
import org.powbot.api.event.RenderEvent;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.*;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.drawing.Rendering;
import org.powbot.mobile.script.ScriptManager;
import org.powbot.mobile.service.ScriptUploader;

import java.util.ArrayList;
import java.util.List;

@ScriptManifest(
        name = "Klumel's MM Trainer",
        description = "Trains ranged and magic in the mm1 tunnels. Start at seers bank, have pray pots(4), sharks, chins, camy-tabs and ape-atoll tabs side-to-side in the bank (+ ranging (4) if chinning). Set quick prayer to protect melee + offensive prayer of choice.",
        version = "1.3",
        category = ScriptCategory.Combat,
        scriptId = "5bc7e3e9-f56f-4daa-8870-dc242cbd9aeb",
        priv = false
)

@ScriptConfiguration.List(
        {
                @ScriptConfiguration(
                        name = "Weapon type",
                        description = "Select training style. If using ancients place all runes in inventory and autocast the spell BEFORE starting the script!",
                        defaultValue = "Red chinchompa",
                        allowedValues = {"Red chinchompa", "Black chinchompa", "Smoke Burst", "Ice Burst", "Smoke Barrage", "Ice Barrage"},
                        optionType = OptionType.STRING
                ),
                @ScriptConfiguration(
                        name = "Chin amount",
                        description = "If chinning, how many chins do you take per trip (more increases risk)",
                        defaultValue = "2000",
                        optionType = OptionType.INTEGER
                ),
                @ScriptConfiguration(
                        name = "Food source",
                        description = "Select which food is used",
                        defaultValue = "Shark",
                        allowedValues = {"Shark"},
                        optionType = OptionType.STRING
                )
        }
)

public class MonkeyTrainer extends AbstractScript {
    public List<Branch> branches = new ArrayList<>();

    public static void main(String[] args){
        //ProcessBuilder builder = new ProcessBuilder("C:\\Users\\flori\\.powbot\\android\\platform-tools\\adb.exe", "-s", "127.0.0.1:5565", "forward", "tcp:61666", "tcp:61666");
        new ScriptUploader().uploadAndStart("Klumel's MM Trainer", "", null, false, false);
    }

    @Override
    public void onStart(){
        Condition.sleep(500);

        //add the branches & leaves
        branches.add(new BankHandler(new OutOfRunes(), new GoToBank(), new OpenBank(), new TeleBank(), new TurnToBank(), new WithdrawChins(),
                new WithdrawTeleports(), new WithdrawFood(), new WithdrawPots()));
        branches.add(new StatsUpkeep(new RestorePrayer(), new BoostSkill(), new ProtMelee(), new RunOn(), new EatFood()));
        branches.add(new GoToSpot(new Walker(), new CloseBank(), new TeleToDungeon()));
        branches.add(new MonkeyHandler(new ReAggro(), new RunTiles(), new Attack()));//, new UpdateBones(), new PickUpBones(), new DistributeBones()));
        branches.add(new BoneHandler(new PickUpBones(), new DistributeBones()));

        //Set some constants.
        Shared.prayerRestored = (int) Math.floor((double) Skills.realLevel(Skill.Prayer.getIndex()) / 4) + 7;
        System.out.println("prayer pot will restore " + Shared.prayerRestored);

        Shared.lastStacked = System.currentTimeMillis();
        Shared.lastAggroReset = System.currentTimeMillis();// - 1000*1000;


        //String type = getOption("type");
        Shared.chinAmount = getOption("Chin amount");
        Shared.weaponName = getOption("Weapon type");
        Shared.food = getOption("Food source");

        if(Shared.weaponName.equalsIgnoreCase("Smoke burst")){
            Shared.keepItems = new String[]{"Camelot teleport", "Ape atoll teleport", "Air rune", "Fire rune", "Chaos rune", "Death rune"};
            Shared.spell = Magic.AncientSpell.SMOKE_BURST;
        }else if(Shared.weaponName.equalsIgnoreCase("Ice burst")){
            Shared.keepItems = new String[]{"Camelot teleport", "Ape atoll teleport", "Water rune", "Chaos rune", "Death rune"};
            Shared.spell = Magic.AncientSpell.ICE_BURST;
        }else if(Shared.weaponName.equalsIgnoreCase("Smoke barrage")){
            Shared.keepItems = new String[]{"Camelot teleport", "Ape atoll teleport", "Air rune", "Fire rune", "Blood rune", "Death rune"};
            Shared.spell = Magic.AncientSpell.SMOKE_BARRAGE;
        }else if(Shared.weaponName.equalsIgnoreCase("Ice barrage")){
            Shared.keepItems = new String[]{"Camelot teleport", "Ape atoll teleport", "Water rune", "Blood rune", "Death rune"};
            Shared.spell = Magic.AncientSpell.ICE_BARRAGE;
        }

        if(Shared.weaponName.contains("chinchompa")){
            Shared.chinning = true;
            System.out.println("Selected weapon " + Shared.weaponName + ", with amount " + Shared.chinAmount);
            Shared.trainedSkill = Skill.Ranged;
        }else{
            Shared.trainedSkill = Skill.Magic;
            Shared.chinning = false;
            System.out.println("Detected ancient magic settings");
        }

        Combat.autoRetaliate(false);

        //Set the tiles to drop the bones at
        int px = Shared.baseTile.getX();
        int py = Shared.baseTile.getY();
        for (int dist = 1; dist <= 4; dist++){
            for (int r = 0; r<=dist; r++) {
                if(dist % 2 == 0) {
                    Shared.dropTiles.add(new Tile(px + dist - r, py + r, 0));
                }else{
                    Shared.dropTiles.add(new Tile(px + r, py + dist - r, 0));
                }
            }
        }

        if(Bank.opened()){
            Bank.depositAllExcept(Shared.keepItems);
        }

        Paint paint = new PaintBuilder()
                .addString(() -> "Branch status: " + Shared.branchStatus)
                .addString(() -> "Leaf status: " + Shared.leafStatus)
                .trackSkill(Shared.trainedSkill)
                .addString(() -> Shared.maxStack + "/" + Shared.monkeyCount + " targeted")
                .addString(() -> "Taking bones: " + Shared.pickingBones)
                .addString(() -> "Dropping bones: " + Shared.droppingBones)
                .build();
        addPaint(paint);

    }

    @Override
    public void poll() {
        for (Branch branch : branches) {
            if (branch.validate()) {
                Shared.branchStatus = branch.status();
                for (Leaf leaf : branch.leaves) {
                    if (leaf.validate()) {
                        Shared.leafStatus = leaf.status();
                        leaf.execute();
                        return;
                    }
                }
            }
        }
    }

    @Subscribe
    public void onRender(RenderEvent evt){
        Rendering.setScale(1.0f);

        for (Tile tile : Shared.boneStacks.keySet()){
            if(tile.distanceTo(Players.local().tile()) < 6) {
                int r = Math.max(0, 255 - Shared.boneStacks.get(tile) * 50);
                Rendering.setColor(Color.rgb(255, r, 255));
                tile.matrix().draw();
            }
        }

        Rendering.setColor(Color.getORANGE());
        if(Shared.bestMonkey != null){
            Shared.bestMonkey.tile().matrix().draw();
        }

        Rendering.setColor(Color.getCYAN());
        Shared.baseTile.matrix().draw();
        Shared.stackTile.matrix().draw();
    }

    @com.google.common.eventbus.Subscribe
    void onMessage(MessageEvent evt){
        if(evt.getMessage().contains("Oh dear, you are dead!")){
            System.out.println("Player has died, stopping script.");
            ScriptManager.INSTANCE.stop();
        }
    }
}
