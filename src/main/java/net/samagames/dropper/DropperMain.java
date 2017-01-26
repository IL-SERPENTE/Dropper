package net.samagames.dropper;

import org.bukkit.plugin.java.JavaPlugin;
import net.samagames.api.SamaGamesAPI;
import net.samagames.dropper.events.PlayerEventsListener;

/**
 * @author Vialonyx
 */

public class DropperMain extends JavaPlugin {
	
	private Dropper game;
	
    @Override
    public void onEnable(){
    	
    	this.game = new Dropper("gameCode", "Dropper", "The Dropper", DropperPlayer.class, this);
    	this.getServer().getPluginManager().registerEvents(new PlayerEventsListener(this.game), this);
    	SamaGamesAPI.get().getGameManager().registerGame(this.game);
    	
    }

}
