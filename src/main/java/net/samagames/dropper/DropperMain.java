package net.samagames.dropper;

import net.samagames.dropper.events.listeners.LevelActionsListener;
import org.bukkit.plugin.java.JavaPlugin;
import net.samagames.api.SamaGamesAPI;
import net.samagames.dropper.events.listeners.PlayerEventsListener;

import static org.bukkit.Bukkit.getWorlds;

public class DropperMain extends JavaPlugin {

	/**
	 * This is the entry point of the Dropper game.
	 * @author Vialonyx
	 */

	private Dropper game;
	
    @Override
    public void onEnable(){

    	this.game = new Dropper("gameCode", "Dropper", "The Dropper", DropperPlayer.class, this);
    	this.getServer().getPluginManager().registerEvents(new PlayerEventsListener(this.game), this);
    	this.getServer().getPluginManager().registerEvents(new LevelActionsListener(this.game), this);
    	getWorlds().get(0).setGameRuleValue("randomTickSpeed", "0");
		SamaGamesAPI.get().getGameManager().setFreeMode(true);
    	SamaGamesAPI.get().getGameManager().registerGame(this.game);
    	
    }



	/**
	 * Get the internal representation of the game.
	 * @return A dropper instance.
	 */

	public Dropper get(){
    	return this.game;
	}

}
