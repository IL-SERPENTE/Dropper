package net.samagames.dropper.level;

import net.samagames.api.SamaGamesAPI;
import net.samagames.dropper.Dropper;
import net.samagames.dropper.common.GameLocations;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.UUID;
import java.util.logging.Level;

/**
 * @author Vialonyx
 */

public class LevelManager {

    /*
     * This class manages levels globaly.
     */

    public final AbstractLevel LEVEL_1;
    public final AbstractLevel LEVEL_2;
    public final AbstractLevel LEVEL_3;
    public final AbstractLevel LEVEL_4;
    public final AbstractLevel LEVEL_5;
    public final AbstractLevel LEVEL_6;
    public final AbstractLevel LEVEL_7;
    public final AbstractLevel LEVEL_8;
    
    public int task, value;
    public boolean timerIsStarted;

    private Dropper instance;
    public LevelManager(Dropper instance){
        this.instance = instance;
        this.LEVEL_1 = new AbstractLevel(1, "Rainbow", "Test", GameLocations.LEVEL1_AREA.locationValue(), new Location(this.instance.getWorld(), 535, 234, -37), new Location(this.instance.getWorld(), -352, 2, 589));
        this.LEVEL_2 = new AbstractLevel(2, "Isengard", "Test", GameLocations.LEVEL2_AREA.locationValue(), new Location(this.instance.getWorld(), 542, 234, -36), new Location(this.instance.getWorld(), 531, 2, 575));
        this.LEVEL_3 = new AbstractLevel(3, "Neo", "Test", GameLocations.LEVEL3_AREA.locationValue(), new Location(this.instance.getWorld(), 549, 234, -37), new Location(this.instance.getWorld(), -642, 1, 10));
        this.LEVEL_4 = new AbstractLevel(4, "Symbols", "Mémorisez les symboles !", GameLocations.LEVEL4_AREA.locationValue(), new Location(this.instance.getWorld(), 556, 234, -36), new Location(this.instance.getWorld(), -653, 1, -638));
        this.LEVEL_5 = new AbstractLevel(5, "The Tree", "Test", GameLocations.LEVEL5_AREA.locationValue(), new Location(this.instance.getWorld(), 563, 234, -37), new Location(this.instance.getWorld(), -14, 10, -711));
        this.LEVEL_6 = new AbstractLevel(6, "Embryo", "Test", GameLocations.LEVEL6_AREA.locationValue(), new Location(this.instance.getWorld(), 570, 234, -36), new Location(this.instance.getWorld(), 1871, 10, -765));
        this.LEVEL_7 = new AbstractLevel(7, "Brain", "Test", GameLocations.LEVEL7_AREA.locationValue(), new Location(this.instance.getWorld(), 577, 234, -36), new Location(this.instance.getWorld(), 3473, 1, 603));
        this.LEVEL_8 = new AbstractLevel(8, "Dimension Jumper", "Test", GameLocations.LEVEL8_AREA.locationValue(), new Location(this.instance.getWorld(), 584, 234, -36), new Location(this.instance.getWorld(), 523, 119, -784));
    }
    
    /**
     * This function respond to level joining context
     * @param joiner the player who join the level
     * @param level the level joined
     */

    public void joinLevel(Player joiner, AbstractLevel level){
    	
    	// Checking if player is already playing in the level (it's verry hard to do that)
        if(level.getLevelPlayers().contains(joiner.getUniqueId())){
            this.instance.getLogger().log(Level.SEVERE, "Specified player is already playing in the level.");
            return;
        } else {
        	
        	// This is the joining process. Sending title, messages, teleporting player... 
            level.usualJoin(joiner);
            this.instance.getDropperGame().getRegisteredGamePlayers().get(joiner.getUniqueId()).setCurrentlyLevel(level);
            this.instance.sendTitle(joiner, level.getLevelName(), level.getLevelDescription(), 60);
            joiner.teleport(level.getRelatedLocation());
            SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeCustomMessage(joiner.getName() + " §ba rejoint le §cNiveau " + level.getNumber(), true);
            
            // The level 1 is a little bit special beacause he include a cooldown during which the player must place himself correctly (level teleportation location depends of its placement in the "waiting room")
            if(level.getNumber() == 1 && this.timerIsStarted == false){
            	
            	 this.timerIsStarted = true;
            	 this.value = 21;
                 this.task = this.instance.getServer().getScheduler().scheduleSyncRepeatingTask(this.instance, new Runnable() {
     				
     				@Override
     				public void run() {
     					
     					if(value == 0){
     						
     						resetTimer(false);
     						for(UUID uuid : level.getLevelPlayers()){
     							Player tmpPlayer = instance.getServer().getPlayer(uuid);
     							tmpPlayer.teleport(new Location(instance.getWorld(),
     									tmpPlayer.getLocation().getX() - 37, 
     									tmpPlayer.getLocation().getY() + 200,
     									tmpPlayer.getLocation().getZ() - 38));
     						}
     						
     						return;
     					} else {
     						value--;
     						
     						if(value == 20 || value == 10 || value <= 5 && value != 1 && value != 0){
     							instance.getServer().broadcastMessage(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getGameTag() +
     									" §3Démarrage du §cNiveau " + level.getNumber() + " §3dans §b" + value + " §3secondes");
     						} else if (value == 1){
     							instance.getServer().broadcastMessage(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getGameTag() +
     									" §3Démarrage du §cNiveau " + level.getNumber() + " §3dans §b" + value + " §3seconde");
     						}
     					}
     					
     				}
     			}, 0L, 20L);
            	
            } else if (level.getNumber() == 5){
            	joiner.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1, false, false));
            }
            
        }
    }
    
    /**
     * This function remove the player of its current level
     * @param player the player
     * @param message sent the message "<player> a quitté le niveau <level_number>"
     */
    
    public void leaveLevel(Player player, boolean message) {
    	AbstractLevel leavedLevel = this.instance.getDropperGame().getRegisteredGamePlayers().get(player.getUniqueId()).getCurrentlyLevel();
        this.instance.getDropperGame().getRegisteredGamePlayers().get(player.getUniqueId()).getCurrentlyLevel().usualLeave(player);
        this.instance.getDropperGame().getRegisteredGamePlayers().get(player.getUniqueId()).setCurrentlyLevel(null);
        if(message){
        	SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeCustomMessage(player.getName() + " §ba quitté le §cNiveau " + leavedLevel.getNumber(), true);
        }
	}
    
    /**
     * In this game we assume that the player lost when he die.
     * @param player the dead player
     * @param playerLevel the level where the player played
     */
    
    public void setPlayerDead(Player player, AbstractLevel playerLevel){
    	SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeCustomMessage(
    			"§3[§cNiveau " + playerLevel.getNumber() + "§3] §f" + player.getName() + " §bc'est écrasé !", true);
    	this.leaveLevel(player, false);
    	player.teleport(GameLocations.SPAWN.locationValue());
    }
    
    /**
     * This function was called by proximity task when player is completing successfully a level.
     * @param player the player
     */
    
    public void setLevelWin(Player player){
    	AbstractLevel playerLevel = this.instance.getDropperGame().getRegisteredGamePlayers().get(player.getUniqueId()).getCurrentlyLevel();
    	
    	SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeCustomMessage(player.getName() + " §ba terminé le §cNiveau " + playerLevel.getNumber(), true);
    	for(UUID uuid : playerLevel.getLevelPlayers()){
    		this.instance.getServer().getPlayer(uuid).teleport(GameLocations.SPAWN.locationValue());
    	}
    }
    
    /**
     * This function allow to reset the level 1 cooldown.
     * @param message sent "Démarrage du jeu annulé" in-game and a log on console
     */
    
    public void resetTimer(boolean message){
    	this.instance.getServer().getScheduler().cancelTask(task);
		this.timerIsStarted = false;
		this.value = 21;
		
		if(message){
			this.instance.getServer().broadcastMessage(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getGameTag() + " §bDémarrage du §cNiveau 1 §cannulé");
			this.instance.getLogger().log(Level.INFO, "Cooldown of level 1 has been stopped");
		}
		
    }
    
}
