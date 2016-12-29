package net.samagames.dropper.events;

import net.samagames.api.SamaGamesAPI;
import net.samagames.dropper.Dropper;
import net.samagames.dropper.common.GameItems;
import net.samagames.dropper.common.GameLocations;
import net.samagames.dropper.level.AbstractLevel;

import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Vialonyx
 */

public class PlayerListener implements Listener {

    /*
     * This Listener take care of events called by players.
     */

    private Dropper instance;
    public PlayerListener(Dropper instance){
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){

        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)){

            if(event.getItem() != null){

                event.setCancelled(true);
                Player player = event.getPlayer();
                ItemStack item = event.getItem();
                
                // This condition is completed when player use the BACK_LEVEL_HUB (in GameItems enum).
                if(item.isSimilar(GameItems.BACK_LEVEL_HUB.getStackValue())) {

                	// Here we check if player is playing in a level.
                    if(this.instance.getDropperGame().getRegisteredGamePlayers().get(player.getUniqueId()).getCurrentlyLevel() != null){
                    	
                    	AbstractLevel leavedLevel = this.instance.getDropperGame().getRegisteredGamePlayers().get(player.getUniqueId()).getCurrentlyLevel();
                    	
                    	SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeCustomMessage(player.getName() + " §ba quitté le §cNiveau " + leavedLevel.getNumber(), true);
                        this.instance.getDropperGame().getRegisteredGamePlayers().get(player.getUniqueId()).getCurrentlyLevel().usualLeave(player);
                        this.instance.getDropperGame().getRegisteredGamePlayers().get(player.getUniqueId()).setCurrentlyLevel(null);
                        
                        // This statement allow to check if the cooldown (level 1) is started when player leave the level.
                        if(leavedLevel.getNumber() == 1 && this.instance.getLevelManager().timerIsStarted && leavedLevel.getLevelPlayers().size() == 0 ){
                        	this.instance.getLevelManager().resetTimer(true);
                        }
                        
                    }
                    
                    player.getActivePotionEffects().clear();
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 2, false, false));
                    player.teleport(GameLocations.SPAWN.locationValue());
                }

            }
        }
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
    	
    	// Define player scoreboard
    	event.getPlayer().setScoreboard(this.instance.getDropperBoard().getScoreboard());
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
    	
    	// Here we check if player is under any level when he died. If he is, he lost the game.
    	Player player = (Player) event.getEntity();
    	AbstractLevel playerLevel = this.instance.getDropperGame().getRegisteredGamePlayers().get(player.getUniqueId()).getCurrentlyLevel();
    	if(playerLevel != null){
    		this.instance.getLevelManager().setPlayerDead(player, playerLevel);
    	}
    	
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
    	
    	// Just disabling damagames caused by drowning.
    	if(event.getEntity() instanceof Player && event.getCause() == DamageCause.DROWNING){
    		event.setCancelled(true);
    	}
    }
    
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event){
    	
    	// Disabling food.
    	event.setCancelled(true);
    }

}
