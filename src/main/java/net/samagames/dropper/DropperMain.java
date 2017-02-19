package net.samagames.dropper;

import net.samagames.dropper.events.listeners.LevelActionsListener;
import org.bukkit.plugin.java.JavaPlugin;
import net.samagames.api.SamaGamesAPI;
import net.samagames.dropper.events.listeners.PlayerEventsListener;

/**
 * @author Vialonyx
 */

public class DropperMain extends JavaPlugin {

	private Dropper game;
	
    @Override
    public void onEnable(){
    	
    	this.game = new Dropper("gameCode", "Dropper", "The Dropper", DropperPlayer.class, this);
    	this.getServer().getPluginManager().registerEvents(new PlayerEventsListener(this.game), this);
    	this.getServer().getPluginManager().registerEvents(new LevelActionsListener(this.game), this);
		SamaGamesAPI.get().getGameManager().setFreeMode(true);
    	SamaGamesAPI.get().getGameManager().registerGame(this.game);
    	
    }

    public Dropper get(){
    	return this.game;
	}

}
