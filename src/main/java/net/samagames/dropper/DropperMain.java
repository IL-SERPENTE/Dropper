package net.samagames.dropper;

import org.bukkit.plugin.java.JavaPlugin;
import net.samagames.api.SamaGamesAPI;

/**
 * @author Vialonyx
 */

public class DropperMain extends JavaPlugin {
	
	private Dropper game;
	
    @Override
    public void onEnable(){
    	this.game = new Dropper("gameCode", "Dropper", "The Dropper", DropperPlayer.class, this);
    	SamaGamesAPI.get().getGameManager().registerGame(this.game);
    }

}
