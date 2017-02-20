package net.samagames.dropper.level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import com.google.gson.JsonObject;
import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.LocationUtils;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class DropperLevel {
	
	private int levelID;
	private String levelName, levelDescription;
	private World world;
	private Location levelPlayLocation;
	private LevelPlatform levelPlatform;
	private ArmorStand LevelAs_End;
	
	public DropperLevel(int levelID, String levelName, String levelDescription){
		this.levelID = levelID;
		this.levelName = levelName;
		this.levelDescription = levelDescription;
		
		 // Getting level data from Json file
        JsonObject object = SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs();
        this.world = Bukkit.getWorld(object.get("world-name").getAsString());
        this.levelPlayLocation = LocationUtils.str2loc(this.world.getName() + ", " + object.get("level" + levelID).getAsString());
		this.LevelAs_End = this.armorStandBuilder(LocationUtils.str2loc(this.world.getName() +  ", " + object.get("level" + this.levelID + "-asWin").getAsString()));

        this.levelPlatform = new LevelPlatform(this.getPlayLocation().subtract(0, 1, 0));

	}

	private ArmorStand armorStandBuilder(Location spawn){
		ArmorStand as = (ArmorStand) this.world.spawnEntity(spawn, EntityType.ARMOR_STAND);
		as.setVisible(false);
		as.setGravity(false);
		return as;
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

	public LevelPlatform getPlatform(){
	    return this.levelPlatform;
    }

	public ArmorStand getSecretEnd(){
		return this.LevelAs_End;
	}

}
