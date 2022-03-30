package klumel8.MonkeyTrainer;

import org.powbot.api.Area;
import org.powbot.api.Tile;
import org.powbot.api.rt4.GroundItem;
import org.powbot.api.rt4.Magic;
import org.powbot.api.rt4.Npc;
import org.powbot.api.rt4.stream.locatable.interactive.GroundItemStream;
import org.powbot.api.rt4.stream.locatable.interactive.NpcStream;
import org.powbot.api.rt4.walking.model.Skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shared {
    public static int prayerRestored = 0;
    public static Tile baseTile = new Tile(2714, 9128, 0);
    public static Tile stackTile = new Tile(2718, 9131,0);
    public static boolean droppingBones = false;
    public static boolean pickingBones = false;
    public static GroundItemStream bones;
    public static Tile bestBoneTile = null;

    public static NpcStream monkeys ;
    public static long monkeyCount = 0;
    public static List<Tile> monkeyTiles;
    public static List<Tile> trapTiles = new ArrayList<>();
    public static Tile boneTile = Tile.getNil();
    public static Map<Tile, Integer> boneStacks = new HashMap<Tile, Integer>();
    public static Npc bestMonkey;
    public static int maxStack = 0;
    public static int aggroMonkeys = 0;
    public static int bestPileSize = 0;

    public static int targetID = 5237;

    public static long lastStacked;
    public static long lastAggroReset;

    public static Skill trainedSkill = Skill.Ranged;
    public static int maxDrain = 6;

    public static int deAggroCount = 0;

    public static Area bankArea = new Area(new Tile(2772, 3468, 0), new Tile(2715, 3498, 0));
    public static Tile bankTargetTile = new Tile(2725, 3492, 0);

    public static int chinAmount = 2500;
    public static String weaponName = "Red chinchompa";
    public static boolean chinning = true;

    public static String branchStatus = "";
    public static String leafStatus = "";
    public static String food = "";

    public static List<Tile> dropTiles = new ArrayList<>();

    public static String[] keepItems = {"Camelot teleport", "Ape atoll teleport"};
    public static Magic.AncientSpell spell;
}
