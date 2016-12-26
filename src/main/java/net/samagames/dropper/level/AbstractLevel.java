package net.samagames.dropper.level;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vialonyx
 */

public class AbstractLevel {

    /*
     * This class represents a Level.
     * All levels are registred in LevelRegistery.
     */

    private int levelNumber;
    private Location levelLocation;
    private List<Player> levelPlayers;

    public AbstractLevel(int levelNumber, Location levelLocation){
        this.levelNumber = levelNumber;
        this.levelLocation = levelLocation;
        this.levelPlayers = new ArrayList<>();
    }

    public int getNumber(){
        return this.levelNumber;
    }

    public Location getRelatedLocation(){
        return this.levelLocation;
    }

    public List<Player> getLevelPlayers(){
        return this.levelPlayers;
    }

    public void usualJoin(Player player){
        this.levelPlayers.add(player);
    }

}
