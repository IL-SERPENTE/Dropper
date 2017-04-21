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

		// Creating game.
		this.game = new Dropper("gameCode", "Dropper", "by Bigre", DropperPlayer.class, this);

		// Registering events.
		this.getServer().getPluginManager().registerEvents(new PlayerEventsListener(this.game), this);
		this.getServer().getPluginManager().registerEvents(new LevelActionsListener(this.game), this);

		// Registering command.
		this.getCommand("skip").setExecutor(new SkipCommand(this));

		// Editing gamerules & setting-up eternal night.
		getWorlds().get(0).setGameRuleValue("randomTickSpeed", "0");
		getWorlds().get(0).setGameRuleValue("doDaylightCycle", "false");
		getWorlds().get(0).setTime(19000);

		// Registering game on SamaGamesAPI.
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
