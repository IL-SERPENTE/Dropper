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
    LEVEL2_AREA (new Location(getWorlds().get(0), 531, 220, 576));

    private Location location;

    GameLocations(Location location){
        this.location = location;
    }

    public Location locationValue(){
        return this.location;
    }

}
