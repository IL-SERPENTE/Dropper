package net.samagames.dropper.level;

import net.samagames.dropper.common.GameLocations;

/**
 * @author Vialonyx
 */

public class LevelRegistery {

    public final AbstractLevel LEVEL_1;

    public LevelRegistery(){
        this.LEVEL_1 = new AbstractLevel(1, GameLocations.LEVEL1_AREA.locationValue());
    }

}
