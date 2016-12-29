package net.samagames.dropper.level;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.bukkit.Bukkit.getWorlds;

/**
 * @author Vialonyx
 */

public class AbstractLevel {

    /*
     * This class represents a Level.
     */

    private int levelNumber;
    private String levelName, levelDescription;
    private Location levelLocation;
    private Location secretAsStartLocation;
    private Location secretAsWinLocation;
    private ArmorStand secretAsWin;
    private ArmorStand secretAsStart;
    private List<UUID> levelPlayers;

    public AbstractLevel(int levelNumber, String levelName, String levelDescription, Location levelLocation, Location secretAsStart, Location secretAsWin){
        this.levelNumber = levelNumber;
        this.levelName = levelName;
        this.levelDescription = levelDescription;
        this.levelLocation = levelLocation;
        this.secretAsStartLocation = secretAsStart;
        this.secretAsWinLocation = secretAsWin;
        this.levelPlayers = new ArrayList<>();
        this.secretAsStart = (ArmorStand) getWorlds().get(0).spawnEntity(this.secretAsStartLocation, EntityType.ARMOR_STAND);
        this.secretAsStart.setVisible(false);
        this.secretAsStart.setGravity(false);
        this.secretAsWin = (ArmorStand) getWorlds().get(0).spawnEntity(this.secretAsWinLocation, EntityType.ARMOR_STAND);
        this.secretAsWin.setVisible(false);
        this.secretAsWin.setGravity(false);
    }
    
    /**
     * Get the number of the level
     * @return the level's number
     */

    public int getNumber(){
        return this.levelNumber;
    }
    
    /**
     * Get the name of the level
     * @return the level's name
     */

    public String getLevelName(){
        return this.levelName;
    }
    
    /**
     * Get the level description.
     * @return the level's description
     */
    
    public String getLevelDescription(){
    	return this.levelDescription;
    }
    
    /**
     * Get the location of we have to teleport the player when he joined
     * @return the location
     */

    public Location getRelatedLocation(){
        return this.levelLocation;
    }
    
    /**
     * Get the ArmorStand used to check level joining (proximity tasks)
     * @return the armor stand
     */

    public ArmorStand getStartSecretAs(){
        return this.secretAsStart;
    }
    
    /**
     * Get the ArmorStand used to check level win (proximity tasks)
     * @return the armor stand
     */
    
    public ArmorStand getWinSecretAs(){
        return this.secretAsWin;
    }
    
    /**
     * Get a list of players inside the level
     * @return the players list
     */

    public List<UUID> getLevelPlayers(){
        return this.levelPlayers;
    }
    
    /**
     * Add the player to level's player list
     * @param player the player
     */

    public void usualJoin(Player player){
        this.levelPlayers.add(player.getUniqueId());
    }
    
    /**
     * Remove the player to level's player list
     * @param player the player
     */

    public void usualLeave(Player player) {
        if(this.levelPlayers.contains(player.getUniqueId())){
            this.levelPlayers.remove(player.getUniqueId());
        }
    }

}
