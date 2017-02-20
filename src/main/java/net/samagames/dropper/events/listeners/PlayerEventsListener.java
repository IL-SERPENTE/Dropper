package net.samagames.dropper.events.listeners;

import net.samagames.api.SamaGamesAPI;
import net.samagames.dropper.GameType;
import net.samagames.dropper.LevelGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import net.samagames.dropper.Dropper;
import net.samagames.dropper.DropperPlayer;

import static org.bukkit.Bukkit.broadcastMessage;

public class PlayerEventsListener implements Listener {
	
	private Dropper game;
	public PlayerEventsListener(Dropper game) {
		this.game = game;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)){
			
            if(event.getItem() != null) {
				event.setCancelled(true);
				Player player = event.getPlayer();
				ItemStack item = event.getItem();
				DropperPlayer gamePlayer = this.game.getRegisteredGamePlayers().get(player.getUniqueId());

				if (item.isSimilar(this.game.getGameItem(3))) {
					if (gamePlayer.getCurrentLevel() != null) {
						this.game.usualLevelLeave(player);
					}

				} else if(item.isSimilar(this.game.getGameItem(2))){
					this.game.usualGameLeave(player);

				} else if(item.isSimilar(this.game.getGameItem(0))) {
                    this.game.usualGameTypeUpdate(player, GameType.FREE);

                } else if(item.isSimilar(this.game.getGameItem(1))){
				    this.game.usualGameTypeUpdate(player, GameType.COMPETITION);

				} else if(item.isSimilar(this.game.getGameItem(4))){
					SamaGamesAPI.get().getGuiManager().openGui(player, new LevelGUI(this.game.getInstance()));
				}
                
            }
            
		}
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event){
		// Disabling food.
		event.setCancelled(true);
	}
	
}
