package net.samagames.dropper.common;

import org.bukkit.Location;
import static org.bukkit.Bukkit.getWorlds;

/**
 * @author Vialonyx
 */

public enum GameLocations {

    /*
     * This is an enum of main locations.
     */

    SPAWN (new Location(getWorlds().get(0), 532, 234, -42)),
    LEVEL1_AREA (new Location(getWorlds().get(0), -313, 148, 620)),
    LEVEL2_AREA (new Location(getWorlds().get(0), 531, 220, 576)),
	LEVEL3_AREA (new Location(getWorlds().get(0), -683, 322, 10)),
	LEVEL4_AREA (new Location(getWorlds().get(0), -653, 119, -671)),
	LEVEL5_AREA (new Location(getWorlds().get(0), -3, 305, -718)),
	LEVEL6_AREA (new Location(getWorlds().get(0), 1874, 310, -760)),
	LEVEL7_AREA (new Location(getWorlds().get(0), 3494, 310, 609)),
	LEVEL8_AREA (new Location(getWorlds().get(0), 773, 135, -784));

    private Location location;

    GameLocations(Location location){
        this.location = location;
    }

    public Location locationValue(){
        return this.location;
    }

}
