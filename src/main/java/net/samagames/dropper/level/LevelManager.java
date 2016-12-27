package net.samagames.dropper.level;

import net.samagames.api.SamaGamesAPI;
import net.samagames.dropper.Dropper;
import net.samagames.dropper.common.GameLocations;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.logging.Level;

/**
 * @author Vialonyx
 */

public class LevelManager {

    /*
     * This class manages levels globaly.
     */

    public final AbstractLevel LEVEL_1;

    private Dropper instance;
    public LevelManager(Dropper instance){
        this.instance = instance;
        this.LEVEL_1 = new AbstractLevel(1, "Rainbow", GameLocations.LEVEL1_AREA.locationValue(),  new Location(this.instance.getWorld(), 535, 234, -37));
    }

    public void joinLevel(Player joiner, AbstractLevel level){
        if(level.getLevelPlayers().contains(joiner.getUniqueId())){
            this.instance.getLogger().log(Level.SEVERE, "Specified player is already playing in the level.");
            return;
        } else {
            level.usualJoin(joiner);
            this.instance.getDropperGame().getRegisteredGamePlayers().get(joiner.getUniqueId()).setCurrentlyLevel(level);
            joiner.teleport(level.getRelatedLocation());
            joiner.sendMessage(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getGameTag() +
            " §bVous avez rejoint le §cNiveau " + level.getNumber());
        }
    }

}
