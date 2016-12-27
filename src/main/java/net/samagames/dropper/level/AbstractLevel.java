package net.samagames.dropper.level;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
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
    private Location levelLocation;
    private Location secretAsLocation;
    private ArmorStand secretAs;
    private List<Player> levelPlayers;

    public AbstractLevel(int levelNumber, Location levelLocation, Location secretAsLocation){
        this.levelNumber = levelNumber;
        this.levelLocation = levelLocation;
        this.secretAsLocation = secretAsLocation;
        this.levelPlayers = new ArrayList<>();
        this.secretAs = getWorlds().get(0).spawn(this.secretAsLocation, ArmorStand.class);
        this.secretAs.setVisible(false);
        this.secretAs.setGravity(false);
    }

    public int getNumber(){
        return this.levelNumber;
    }

    public Location getRelatedLocation(){
        return this.levelLocation;
    }

    public ArmorStand getSecretAs(){
        return this.secretAs;
    }

    public List<Player> getLevelPlayers(){
        return this.levelPlayers;
    }

    public void usualJoin(Player player){
        this.levelPlayers.add(player);
    }

}
