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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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

                if(item.isSimilar(GameItems.BACK_LEVEL_HUB.getStackValue())) {

                    if(this.instance.getDropperGame().getRegisteredGamePlayers().get(player.getUniqueId()).getCurrentlyLevel() != null){
                    	
                    	AbstractLevel leavedLevel = this.instance.getDropperGame().getRegisteredGamePlayers().get(player.getUniqueId()).getCurrentlyLevel();
                    	
                    	SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager().writeCustomMessage(player.getName() + " §ba quitté le §cNiveau " + leavedLevel.getNumber(), true);
                        this.instance.getDropperGame().getRegisteredGamePlayers().get(player.getUniqueId()).getCurrentlyLevel().usualLeave(player);
                        this.instance.getDropperGame().getRegisteredGamePlayers().get(player.getUniqueId()).setCurrentlyLevel(null);
                        
                        if(leavedLevel.getNumber() == 1 && this.instance.getLevelManager().timerIsStarted && leavedLevel.getLevelPlayers().size() == 0 ){
                        	this.instance.getLevelManager().resetTimer();
                        }
                        
                    }

                    player.teleport(GameLocations.SPAWN.locationValue());
                }

            }
        }
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
    	
    	Player player = (Player) event.getEntity();
    	AbstractLevel playerLevel = this.instance.getDropperGame().getRegisteredGamePlayers().get(player.getUniqueId()).getCurrentlyLevel();
    	if(playerLevel != null){
    		this.instance.getLevelManager().setPlayerDead(player, playerLevel);
    	}
    	
    }

}
