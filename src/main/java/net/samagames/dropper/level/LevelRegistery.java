package net.samagames.dropper.level;

import net.samagames.dropper.common.GameLocations;
import org.bukkit.Location;
import static org.bukkit.Bukkit.getWorlds;

/**
 * @author Vialonyx
 */

public class LevelRegistery {

    /*
     * This class register & instanciate all dropper's level.
     */

    public final AbstractLevel LEVEL_1;

    public LevelRegistery(){
        this.LEVEL_1 = new AbstractLevel(1, "Rainbow",GameLocations.LEVEL1_AREA.locationValue(),
                new Location(getWorlds().get(0), 535, 234, -37));
    }

}
