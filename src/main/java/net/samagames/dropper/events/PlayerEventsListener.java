package net.samagames.dropper.events;

import net.samagames.dropper.GameType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import net.samagames.dropper.Dropper;
import net.samagames.dropper.DropperPlayer;

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

				if (item.isSimilar(this.game.ITEM_ACTUAL_LEAVE)) {

					if (gamePlayer.getCurrentLevel() != null) {
						this.game.usualGameLeave(player, true);
					}

				} else if(item.isSimilar(this.game.ITEM_GAMETYPE_LEAVE)){

					this.game.usualGameLeave(player, true);

				} else if(item.isSimilar(this.game.ITEM_GAMETYPE_SELECT_FREE)){

                	this.game.usualGameTypeUpdate(player, GameType.FREE);
                	this.game.usualGameJoin(player);

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
