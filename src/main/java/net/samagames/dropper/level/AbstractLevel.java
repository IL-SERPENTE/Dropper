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
    private Location secretAsLocation;
    private ArmorStand secretAs;
    private List<UUID> levelPlayers;

    public AbstractLevel(int levelNumber, String levelName, String levelDescription, Location levelLocation, Location secretAsLocation){
        this.levelNumber = levelNumber;
        this.levelName = levelName;
        this.levelDescription = levelDescription;
        this.levelLocation = levelLocation;
        this.secretAsLocation = secretAsLocation;
        this.levelPlayers = new ArrayList<>();
        this.secretAs = (ArmorStand) getWorlds().get(0).spawnEntity(this.secretAsLocation, EntityType.ARMOR_STAND);
        this.secretAs.setVisible(false);
        this.secretAs.setGravity(false);
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

    public ArmorStand getSecretAs(){
        return this.secretAs;
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
