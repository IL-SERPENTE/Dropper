package net.samagames.dropper.level;

import net.samagames.api.SamaGamesAPI;
import net.samagames.dropper.DropperGame;
import net.samagames.dropper.playmode.PlayMode;
import net.samagames.tools.Titles;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
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
    private Map<AbstractLevel, AbstractLevel> levelLogic;
    
    private DropperGame game;
    public LevelManager(DropperGame game){
    	
    	this.game = game;
        this.LEVEL_1 = new AbstractLevel(1, "Rainbow", "n/a", this.game.getWorld());
        this.LEVEL_2 = new AbstractLevel(2, "Isengard", "n/a", this.game.getWorld());
        this.LEVEL_3 = new AbstractLevel(3, "Neo", "n/a", this.game.getWorld());
        this.LEVEL_4 = new AbstractLevel(4, "Symbols", "Mémorisez les symboles !", this.game.getWorld());
        this.LEVEL_5 = new AbstractLevel(5, "The Tree", "n/a", this.game.getWorld());
        this.LEVEL_6 = new AbstractLevel(6, "Embryo", "n/a", this.game.getWorld());
        this.LEVEL_7 = new AbstractLevel(7, "Brain", "n/a", this.game.getWorld());
        this.LEVEL_8 = new AbstractLevel(8, "Dimension Jumper", "n/a", this.game.getWorld());
        
    	this.levelLogic = new HashMap<>();
    	this.levelLogic.put(LEVEL_1, LEVEL_2);
    	this.levelLogic.put(LEVEL_2, LEVEL_3);
    	this.levelLogic.put(LEVEL_3, LEVEL_4);
    	this.levelLogic.put(LEVEL_4, LEVEL_5);
    	this.levelLogic.put(LEVEL_5, LEVEL_6);
    	this.levelLogic.put(LEVEL_6, LEVEL_7);
    	this.levelLogic.put(LEVEL_7, LEVEL_8);
    
    }
    
    /**
     * This function respond to level joining context
     * @param joiner the player who join the level
     * @param level the level joined
     */

    public void joinLevel(Player joiner, AbstractLevel level){
    	
    	// Checking if player is already playing in the level (it's verry hard to do that)
        if(level.getLevelPlayers().contains(joiner.getUniqueId())){
            return;
        } else {
        	
        	// This is the joining process. Sending title, messages, teleporting player... 
            level.usualJoin(joiner);
            game.getRegisteredGamePlayers().get(joiner.getUniqueId()).setCurrentlyLevel(level);
            
            Titles.sendTitle(joiner, 10, 30, 10, level.getLevelName(), level.getLevelDescription());
            
            joiner.teleport(level.getRelatedLocation());
            SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeCustomMessage(joiner.getName() + ChatColor.DARK_AQUA + " a rejoint le " + ChatColor.RED + "Niveau " + level.getNumber(), true);
            
            // The level 1 is a little bit special beacause he include a cooldown during which the player must place himself correctly (level teleportation location depends of its placement in the "waiting room")
            if(level.getNumber() == 1 && this.timerIsStarted == false){
            	
            	 this.timerIsStarted = true;
            	 this.value = 21;
                 this.task = this.game.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(this.game.getInstance(), new Runnable() {
     				
     				@Override
     				public void run() {
     					
     					if(value == 0){
     						
     						resetTimer(false);
     						for(UUID uuid : level.getLevelPlayers()){
     							Player tmpPlayer = game.getInstance().getServer().getPlayer(uuid);
     							tmpPlayer.teleport(new Location(game.getWorld(),
     									tmpPlayer.getLocation().getX() - 37, 
     									tmpPlayer.getLocation().getY() + 200,
     									tmpPlayer.getLocation().getZ() - 38));
     						}
     						
     						return;
     					} else {
     						value--;
     						
     						if(value == 20 || value == 10 || value <= 5 && value != 1 && value != 0){
     							game.getInstance().getServer().broadcastMessage(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getGameTag() +
     									ChatColor.DARK_AQUA + " Démarrage du " + ChatColor.RED + "Niveau " + level.getNumber() + ChatColor.DARK_AQUA + " dans " + ChatColor.AQUA + value + " §3secondes");
     						} else if (value == 1){
     							game.getInstance().getServer().broadcastMessage(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getGameTag() +
     									ChatColor.DARK_AQUA + " Démarrage du " + ChatColor.RED + "Niveau " + level.getNumber() + ChatColor.DARK_AQUA + " dans " + ChatColor.AQUA + value + " §3seconde");
     						}
     					}
     					
     				}
     			}, 0L, 20L);
            	
            } else if (level.getNumber() == 5){
            	joiner.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1, false, false));
            } else if (level.getNumber() == 6){
            	joiner.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 180, 1, false, false));
            }
            
        }
    }
    
    /**
     * This function remove the player of its current level
     * @param player the player
     * @param message sent the message "<player> a quitté le niveau <level_number>"
     */
    
    public void leaveLevel(Player player, boolean message) {
    	
    	AbstractLevel leavedLevel = this.game.getRegisteredGamePlayers().get(player.getUniqueId()).getCurrentlyLevel();
        this.game.getRegisteredGamePlayers().get(player.getUniqueId()).getCurrentlyLevel().usualLeave(player);
        this.game.getRegisteredGamePlayers().get(player.getUniqueId()).setCurrentlyLevel(null);
        if(message){
        	SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeCustomMessage(player.getName() + ChatColor.DARK_AQUA + " a quitté le " + ChatColor.RED + "Niveau " + leavedLevel.getNumber(), true);
        }
	}
    
    /**
     * In this game we assume that the player lost when he die.
     * @param player the dead player
     * @param playerLevel the level where the player played
     */
    
    public void setPlayerDead(Player player, AbstractLevel playerLevel){
    	
    	SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeCustomMessage(
    			ChatColor.DARK_AQUA + "[" + ChatColor.RED + "Niveau " + playerLevel.getNumber() + ChatColor.DARK_AQUA +"] " + ChatColor.RESET + player.getName() + ChatColor.AQUA + " c'est écrasé !", true);
    	
    	if(this.game.getRegisteredGamePlayers().get(player.getUniqueId()).getPlayMode() == PlayMode.CHALLENGE){
    		playerLevel.usualLeave(player);
    		this.game.getRegisteredGamePlayers().get(player.getUniqueId()).setCurrentlyLevel(null);
    		this.game.getPlayModeManager().setChallengeLost(player);
    	}
    	
    }
    
    /**
     * This function was called by proximity task when player is completing successfully a level.
     * @param player the player
     */
    
    public void setLevelWin(Player player){
    	AbstractLevel playerLevel = this.game.getRegisteredGamePlayers().get(player.getUniqueId()).getCurrentlyLevel();
    	
    	SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeCustomMessage(player.getName() + ChatColor.AQUA + "a terminé le " + ChatColor.RED + "Niveau " + playerLevel.getNumber(), true);
    	
    	if(this.game.getRegisteredGamePlayers().get(player.getUniqueId()).getPlayMode() != PlayMode.ENTERTAINMENT){
        	this.game.getPlayModeManager().processLevelSuccess(player, playerLevel);
    	}
    	
    }
    
    /**
     * Get the levelLogic
     * @return level logic map
     */
    
    public Map<AbstractLevel, AbstractLevel> getLevelLogic(){
    	return this.levelLogic;
    }
    
    /**
     * This function allow to reset the level 1 cooldown.
     * @param message sent "Démarrage du jeu annulé" in-game and a log on console
     */
    
    public void resetTimer(boolean message){
    	this.game.getInstance().getServer().getScheduler().cancelTask(task);
		this.timerIsStarted = false;
		this.value = 21;
		
		if(message){
			this.game.getInstance().getServer().broadcastMessage(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getGameTag() + ChatColor.AQUA + " Démarrage du " + ChatColor.RED + "Niveau 1 annulé");
			this.game.getInstance().getLogger().log(Level.INFO, "Cooldown of level 1 has been stopped");
		}
		
    }
    
}
