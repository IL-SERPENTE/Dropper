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
     * All levels are registred in LevelRegistery.
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

    public int getNumber(){
        return this.levelNumber;
    }

    public String getLevelName(){
        return this.levelName;
    }
    
    public String getLevelDescription(){
    	return this.levelDescription;
    }

    public Location getRelatedLocation(){
        return this.levelLocation;
    }

    public ArmorStand getStartSecretAs(){
        return this.secretAsStart;
    }
    
    public ArmorStand getWinSecretAs(){
        return this.secretAsWin;
    }

    public List<UUID> getLevelPlayers(){
        return this.levelPlayers;
    }

    public void usualJoin(Player player){
        this.levelPlayers.add(player.getUniqueId());
    }

    public void usualLeave(Player player) {
        if(this.levelPlayers.contains(player.getUniqueId())){
            this.levelPlayers.remove(player.getUniqueId());
        }
    }

}
