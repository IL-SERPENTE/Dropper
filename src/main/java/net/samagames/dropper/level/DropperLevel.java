package net.samagames.dropper.level;

import org.bukkit.Location;
import com.google.gson.JsonObject;
import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.LocationUtils;

public class DropperLevel {
	
	private int levelID;
	private String levelName, levelDescription;
	private Location levelPlayLocation;
	
	public DropperLevel(int levelID, String levelName, String levelDescription){
		this.levelID = levelID;
		this.levelName = levelName;
		this.levelDescription = levelDescription;
		
		 // Getting level data from Json file
        JsonObject object = SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs();
        this.levelPlayLocation = LocationUtils.str2loc(object.get("world-name").getAsString() + ", " + object.get("level" + levelID).getAsString());
		
	}
	
	public int getID(){
		return this.levelID;
	}
	
	public String getName(){
		return this.levelName;
	}
	
	public String getDescription(){
		return this.levelDescription;
	}
	
	public Location getPlayLocation(){
		return this.levelPlayLocation;
	}

}
