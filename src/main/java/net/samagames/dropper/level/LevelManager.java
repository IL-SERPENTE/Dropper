package net.samagames.dropper.level;

import net.samagames.api.SamaGamesAPI;
import net.samagames.dropper.Dropper;
import org.bukkit.entity.Player;
import java.util.logging.Level;

/**
 * @author Vialonyx
 */

public class LevelManager {

    private LevelRegistery levelRegistery;

    private Dropper instance;
    public LevelManager(Dropper instance){
        this.instance = instance;
        this.levelRegistery = new LevelRegistery();
    }

    public LevelRegistery getLevelRegistery(){
        return this.levelRegistery;
    }

    public void joinLevel(Player joiner, AbstractLevel level){
        if(level.getLevelPlayers().contains(joiner)){
            this.instance.getLogger().log(Level.SEVERE, "Specified player is already playing in the level.");
            return;
        } else {
            level.usualJoin(joiner);
            joiner.teleport(level.getRelatedLocation());
            joiner.sendMessage(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getGameTag() +
            " §bVous avez rejoint le §cNiveau " + level.getNumber());
        }
    }

}
