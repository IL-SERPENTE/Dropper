package net.samagames.dropper.events.listeners;

import net.samagames.api.SamaGamesAPI;
import net.samagames.dropper.GameType;
import net.samagames.dropper.LevelGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
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

				if (item.isSimilar(this.game.ITEM_QUIT_LEVEL)) {
					if (gamePlayer.getCurrentLevel() != null) {
						this.game.usualLevelLeave(player);
					}

				} else if(item.isSimilar(this.game.ITEM_QUIT_GAME)){
					this.game.usualGameLeave(player);
				} else if(item.isSimilar(this.game.ITEM_MODE_FREE)) {
                    this.game.usualGameTypeUpdate(player, GameType.FREE);

                } else if(item.isSimilar(this.game.ITEM_MODE_COMPETITION)){
				    this.game.usualGameTypeUpdate(player, GameType.COMPETITION);

				} else if(item.isSimilar(this.game.ITEM_SELECTGUI)){
					SamaGamesAPI.get().getGuiManager().openGui(player, new LevelGUI(this.game.getInstance()));
				}
                
            }
            
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event){
		event.setCancelled(true);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		event.setCancelled(true);
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event){
		// Disabling food.
		event.setCancelled(true);
	}
	
}
