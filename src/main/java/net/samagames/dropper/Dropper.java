package net.samagames.dropper;

import net.samagames.api.SamaGamesAPI;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Vialonyx
 */

public class Dropper extends JavaPlugin {
	
    private DropperGame dropperGame;

    @Override
    public void onEnable(){
    	
        // Registering game in SamaGames API
        this.dropperGame = new DropperGame("gameCode", "Dropper", "gameDesc", DropperPlayer.class, this);
        SamaGamesAPI.get().getGameManager().registerGame(this.dropperGame);
        
    }
    
    /**
     * Get an instance of DropperGame
     * @return an instance of DropperGame
     */
    
    public DropperGame getDropperGame(){
    	return this.dropperGame;
    }

}