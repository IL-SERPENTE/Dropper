package net.samagames.dropper;

import org.bukkit.Location;
import com.google.gson.JsonObject;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.Game;
import net.samagames.tools.LocationUtils;

public class Dropper extends Game<DropperPlayer> {
	
	private DropperMain instance;
	
	 public Dropper(String gameCodeName, String gameName, String gameDescription, Class<DropperPlayer> gamePlayerClass, DropperMain instance) {
		 super(gameCodeName, gameName, gameDescription, gamePlayerClass);
	 }
	 
	 public DropperMain getMainInstance(){
		 return this.instance;
	 }
	 
	 public Location getMapHub(){
	    JsonObject object = SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs();
	    return LocationUtils.str2loc(object.get("world-name").getAsString() + ", " + object.get("map-hub").getAsString());
	 }
	 
	 public Location getMapLevelHub(){
	    JsonObject object = SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs();
	    return LocationUtils.str2loc(object.get("world-name").getAsString() + ", " + object.get("level-hub").getAsString());
	 }
	
}
