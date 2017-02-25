package net.samagames.dropper.level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import com.google.gson.JsonObject;
import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.LocationUtils;
import org.bukkit.World;

public class DropperLevel {

	/**
	 * This is the representation of a Level.
	 */

	private int levelID;
	private String levelName, levelDescription;
	private World world;
	private Location levelPlayLocation;

	public DropperLevel(int levelID, String levelName, String levelDescription){

		// Setting global data.
		this.levelID = levelID;
		this.levelName = levelName;
		this.levelDescription = levelDescription;

		// Getting level data from Json file.
		JsonObject object = SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs();
		this.world = Bukkit.getWorld(object.get("world-name").getAsString());
		Location loc = LocationUtils.str2loc(this.world.getName() + ", " + object.get("level" + levelID).getAsString());
		this.levelPlayLocation = loc.add(loc.getX() > 0 ? 0.5 : -0.5, 0.0, loc.getZ() > 0 ? 0.5 : -0.5);

		// Loading chunck.
		this.world.getChunkAt(levelPlayLocation.getBlock()).load();

	}

	/**
	 * Get the level's ID.
	 * @return The level ID.
	 */

	public int getID(){
		return this.levelID;
	}

	/**
	 * Get the level's name.
	 * @return The level's name.
	 */

	public String getName(){
		return this.levelName;
	}

	/**
	 * Get the level's description.
	 * @return The level's description.
	 */

	public String getDescription(){
		return this.levelDescription;
	}

	/**
	 * Get the level's play location.
	 * @return The level play location.
	 */

	public Location getPlayLocation(){
		return this.levelPlayLocation;
	}

}
