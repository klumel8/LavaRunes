package klumel8.MoneyMaking.CrystalChests;

import org.powbot.api.Area;
import org.powbot.api.Tile;

import java.util.HashMap;
import java.util.Map;

public class CrystalConstants {
    public static long lastClick;
    public static Area chestArea = new Area(new Tile(2915, 3452, 0), new Tile(2897, 3448, 0));
    public static Area bankArea = new Area(new Tile(2420, 3060, 0), new Tile(2460,3110, 0));
    public static Area globalChestArea = new Area(new Tile(2894-50, 3466-50, 0), new Tile(2894+50, 3466+50, 0));
    public static Area druidBlocked = new Area(new Tile(2914, 3451,0), new Tile(2914-1, 3451+1,0));
    public static Tile bankTile = new Tile(2444, 3083, 0);
    public static Tile doorTile = new Tile(2896,3450, 0);

    public static int chestID = 172;
    public static int keyID = 989;
    public static int castleBankID = 4483;
    public static int keyAmount = 11;
    public static int doorId = 1535;
    public static int earnedValue = 0;
    public static int keyValue;
    public static int keysUsed = 0;

    public static boolean doGE = false;

    public static String[] unwantedItems = {"Spinach roll", "Air rune", "Water rune","Earth rune","Fire rune","Body rune","Mind rune",
            "Raw swordfish"};

    public static String[] sellItems = {"Rune platelegs", "Rune plateskirt", "Uncut dragonstone", "Runite bar", "Iron ore",
            "Coal", "Death rune", "Nature rune", "Diamond", "Adamant sq shield", "Tooth half of key", "Loop half of key"};

    public static String[] lootItems = {"Rune platelegs", "Rune plateskirt", "Uncut dragonstone", "Runite bar", "Iron ore",
            "Coal", "Death rune", "Nature rune", "Law rune", "Diamond", "Adamant sq shield", "Spinach roll", "Air rune", "Water rune","Earth rune","Fire rune","Body rune","Mind rune",
            "Chaos rune", "Cosmic rune", "Ruby", "Raw swordfish", "Tooth half of key", "Loop half of key", "Coins"};

    public static Map<String, Integer> gpValues = new HashMap<String, Integer>();

}
