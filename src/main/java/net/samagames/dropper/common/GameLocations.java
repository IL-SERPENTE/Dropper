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

    SPAWN (new Location(getWorlds().get(0), 530, 235, -41));

    private Location location;

    GameLocations(Location location){
        this.location = location;
    }

    public Location locationValue(){
        return this.location;
    }

}
