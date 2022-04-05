package klumel8.Skilling.WildyAltar;

import org.powbot.api.Area;
import org.powbot.api.Tile;
import org.powbot.api.rt4.*;

import java.util.ArrayList;
import java.util.List;

public class Shared {
    public static Item bone;
    public static String boneName = "";
    public static String selfDamageDevice;
    public static String teleMethod = "Ghorrock teleport";
    public static boolean stopOnPK;
    public static boolean hopping = false;

    public static Tile altarTile = new Tile(2948, 3821, 0);
    public static Tile doorTile = new Tile(2957, 3821, 0);

    public static boolean usedTick = false;

    public static Area lumbyArea = new Area(new Tile(3202, 3231, 0), new Tile(3231, 3206, 0));
    public static Area basementArea = new Area(new Tile(3230, 9636, 0), new Tile(3210, 9596, 0));

    public static final int[] worlds = {302,303,304,305};

    public static long deathTimeout = System.currentTimeMillis();
    public static long hopTimeout = System.currentTimeMillis();

    public static String pkName = "";

    public static List<World.Specialty> specialties = new ArrayList<>();
    public static World.Type type;

    public static List<Component> clickableWorlds = new ArrayList<>();

    public static Tile[] path = {
            new Tile(3032, 3834, 0),
            new Tile(3028, 3831, 0),
            new Tile(3023, 3829, 0),
            new Tile(3018, 3827, 0),
            new Tile(3014, 3826, 0),
            new Tile(3008, 3825, 0),
            new Tile(3004, 3824, 0),
            new Tile(2999, 3823, 0),
            new Tile(2994, 3822, 0),
            new Tile(2988, 3821, 0),
            new Tile(2983, 3821, 0),
            new Tile(2978, 3820, 0),
            new Tile(2972, 3819, 0),
            new Tile(2967, 3819, 0),
            new Tile(2962, 3820, 0),
            new Tile(2958, 3820, 0),
            altarTile
    };

    public static boolean hasAllItems(){
        return Inventory.stream().name(boneName).isNotEmpty()
                && Equipment.stream().filter(e -> e.name().contains("Burning amulet")).count() != 0
                && (Inventory.stream().name(selfDamageDevice).isNotEmpty() || selfDamageDevice.equals("None"));
    }
}
