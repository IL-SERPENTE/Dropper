package net.samagames.dropper.level;

import net.samagames.api.SamaGamesAPI;
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
    private String levelName;
    private Location levelLocation;
    private Location secretAsLocation;
    private ArmorStand secretAs;
    private List<Player> levelPlayers;

    public AbstractLevel(int levelNumber, String levelName, Location levelLocation, Location secretAsLocation){
        this.levelNumber = levelNumber;
        this.levelName = levelName;
        this.levelLocation = levelLocation;
        this.secretAsLocation = secretAsLocation;
        this.levelPlayers = new ArrayList<>();
        this.secretAs = getWorlds().get(0).spawn(this.secretAsLocation, ArmorStand.class);
        this.secretAs.setVisible(false);
        this.secretAs.setGravity(false);
        this.secretAs.setCustomName("§3§lNiveau §c§l" + this.levelNumber + " §f(§b§o" + this.levelName + "§f)");
        this.secretAs.setCustomNameVisible(true);
    }

    public int getNumber(){
        return this.levelNumber;
    }

    public String getLevelName(){
        return this.levelName;
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

    public void usualLeave(Player player) {
        if(this.levelPlayers.contains(player)){
            this.levelPlayers.remove(player);
        }
    }

}
