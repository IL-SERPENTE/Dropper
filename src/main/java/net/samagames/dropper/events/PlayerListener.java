package net.samagames.dropper.events;

import net.samagames.dropper.Dropper;
import net.samagames.dropper.level.AbstractLevel;
import net.samagames.dropper.playmode.PlayMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

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
                
                // This condition is completed when player use the BACK_LEVEL_HUB.
                if(item.isSimilar(this.instance.getDropperGame().BACK_LEVEL_HUB)) {
                	
                	AbstractLevel leavedLevel = this.instance.getDropperGame().getDPFromPlayer(player).getCurrentlyLevel();

                	// Here we check if player is playing in a level.
                    if(leavedLevel != null){
                    	this.instance.getDropperGame().getLevelManager().leaveLevel(player, true);
                    	
                        // This statement allow to check if the cooldown (level 1) is started when player leave the level.
                        if(leavedLevel.getNumber() == 1 && this.instance.getDropperGame().getLevelManager().timerIsStarted && leavedLevel.getLevelPlayers().size() == 0 ){
                        	this.instance.getDropperGame().getLevelManager().resetTimer(true);
                        }
                        
                    }
                    
                    this.instance.getDropperGame().resetPotionEffects(player);
                    player.teleport(this.instance.getDropperGame().getLevelHub());
                    
                    // Responding to PlayMode selection
                } else if(item.isSimilar(this.instance.getDropperGame().PLAYMODE_CHALLENGE)) {
                	this.instance.getDropperGame().getPlayModeManager().newGame(player, PlayMode.CHALLENGE);
                } else if(item.isSimilar(this.instance.getDropperGame().PLAYMODE_ENTERTAINMENT)) {
                	this.instance.getDropperGame().getPlayModeManager().newGame(player, PlayMode.ENTERTAINMENT);
                }

            }
        }
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
    	
		// Reseting potion effects & teleporting player depends to playing mode
    	this.instance.getServer().getScheduler().scheduleSyncDelayedTask(this.instance, new Runnable() {
	    		public void run() {
	    			instance.getDropperGame().resetPotionEffects(event.getPlayer());    
	    			if(instance.getDropperGame().getDPFromPlayer(event.getPlayer()).getPlayMode() != PlayMode.ENTERTAINMENT){
	    	    		event.getPlayer().teleport(instance.getDropperGame().getMapHub());
	    	    	}
	    		}
    		}, 20);
    	
    	}
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
    	
    	// Here we check if player is under any level when he died. If he is, he lost the game.
    	Player player = (Player) event.getEntity();
    	AbstractLevel playerLevel = this.instance.getDropperGame().getDPFromPlayer(player).getCurrentlyLevel();
    	if(playerLevel != null){
    		this.instance.getDropperGame().getLevelManager().setPlayerDead(player, playerLevel);
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
