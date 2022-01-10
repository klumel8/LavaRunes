package klumel8.Lavas.store;

import org.powbot.api.Area;
import org.powbot.api.Tile;

public class LavaConstants {
    public Tile mysteriousRuins = new Tile(3309, 3250, 0);
    public Tile altarTile = new Tile(2583, 4839, 0);

    public Area ruinsArea = new Area(new Tile(3328, 3227, 0), new Tile(3290, 3267, 0));
    public Area altarArea = new Area(new Tile(2572, 4851, 0), new Tile(2591, 4830, 0));

    public int[] brokenPouchIds = {5515, 5513, 5511};

    public Area bankArea = new Area(new Tile(2420, 3060, 0), new Tile(2460,3110, 0));
    public Tile bankTile = new Tile(2444, 3083, 0);
    public Area feroxBankArea = new Area(new Tile(3154, 3639, 0), new Tile(3125, 3622,  0));
    public Tile feroxBankTile = new Tile(3130, 3632, 0);

    public int altarId = 34764;

    public int runThreshold = 85;
}
