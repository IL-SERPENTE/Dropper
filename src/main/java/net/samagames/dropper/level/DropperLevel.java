package net.samagames.dropper.level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import com.google.gson.JsonObject;
import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.LocationUtils;
import org.bukkit.World;

/*
 * This file is part of Dropper.
 *
 * Dropper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dropper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Dropper.  If not, see <http://www.gnu.org/licenses/>.
 */
public class DropperLevel {

	/**
	 * This is the representation of a Level.
	 */

	private int levelID, levelCategory;
	private String levelName, levelDescription;
	private World world;
	private Location levelPlayLocation;

	public DropperLevel(int levelID, int levelCategory, String levelName, String levelDescription){

		// Setting global data.
		this.levelID = levelID;
		this.levelCategory = levelCategory;
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
	 * Get the level's category.
	 * @return The level category.
	 */

	public int getCategory(){
		return this.levelCategory;
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
