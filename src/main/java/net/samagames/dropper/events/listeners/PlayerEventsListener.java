package net.samagames.dropper.events.listeners;

import net.samagames.api.SamaGamesAPI;
import net.samagames.dropper.GameType;
import net.samagames.dropper.LevelGUI;
import net.samagames.tools.chat.ActionBarAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import net.samagames.dropper.Dropper;
import net.samagames.dropper.DropperPlayer;

import static org.bukkit.Bukkit.broadcastMessage;

public class PlayerEventsListener implements Listener {

	/**
	 * This is the listener of the events called by players.
	 * @author Vialonyx
	 */

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

				if (item.isSimilar(Dropper.ITEM_QUIT_LEVEL)) {
					if (gamePlayer.getCurrentLevel() == null) {
						this.game.usualLevelLeave(player, true);
					} else {
						this.game.usualLevelLeave(player, false);
					}

				} else if(item.isSimilar(Dropper.ITEM_QUIT_GAME)){
					this.game.usualGameLeave(player);
				} else if(item.isSimilar(Dropper.ITEM_MODE_FREE)) {
					this.game.usualGameTypeUpdate(player, GameType.FREE);

				} else if(item.isSimilar(Dropper.ITEM_MODE_COMPETITION)){
					this.game.usualGameTypeUpdate(player, GameType.COMPETITION);

				} else if(item.isSimilar(Dropper.ITEM_SELECTGUI)){
					SamaGamesAPI.get().getGuiManager().openGui(player, new LevelGUI(this.game.getInstance()));
				}

			}

		}
	}

	@EventHandler
    public void onPlayerHeldItem(PlayerItemHeldEvent event){

	    Player player = event.getPlayer();
        ItemStack inHand = event.getPlayer().getInventory().getItem(event.getNewSlot());

        if(inHand != null){

            if(inHand.isSimilar(Dropper.ITEM_MODE_FREE)){
                ActionBarAPI.sendMessage(player.getUniqueId(), this.game.getItemsDescriptions().get(Dropper.ITEM_MODE_FREE));
            } else if(inHand.isSimilar(Dropper.ITEM_MODE_COMPETITION)){
                ActionBarAPI.sendMessage(player.getUniqueId(), this.game.getItemsDescriptions().get(Dropper.ITEM_MODE_COMPETITION));
            } else if(inHand.isSimilar(Dropper.ITEM_SELECTGUI)){
                ActionBarAPI.sendMessage(player.getUniqueId(), this.game.getItemsDescriptions().get(Dropper.ITEM_SELECTGUI));
            } else if(inHand.isSimilar(Dropper.ITEM_QUIT_LEVEL)){
                ActionBarAPI.sendMessage(player.getUniqueId(), this.game.getItemsDescriptions().get(Dropper.ITEM_MODE_FREE));
            } else if(inHand.isSimilar(Dropper.ITEM_QUIT_GAME)) {
                ActionBarAPI.sendMessage(player.getUniqueId(), this.game.getItemsDescriptions().get(Dropper.ITEM_QUIT_GAME));
            }

        }

    }

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){
		event.getPlayer().teleport(this.game.getSpawn());
		this.game.getEffectManager().restoreDefaultEffects(event.getPlayer());
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		DropperPlayer dpPlayer = this.game.getPlayer(event.getEntity().getUniqueId());
		event.setDeathMessage("");
		if(dpPlayer.getGameType().equals(GameType.FREE)){
			this.game.usualLevelLeave(event.getEntity(), false);
		} else if (dpPlayer.getGameType().equals(GameType.COMPETITION)){
			this.game.usualGameLeave(event.getEntity());
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event){
		// Disabling PVP.
		if(event.getEntity() instanceof Player && event.getDamager() instanceof Player){
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event){
		// Preventing players to drop anything.
		event.setCancelled(true);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		// Preventing players to move items in inventory.
		event.setCancelled(true);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		// Preventing players to place any block.
		event.setCancelled(true);
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		// Preventing players to break any block.
		event.setCancelled(true);
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event){
		// Disabling food.
		event.setCancelled(true);
	}

}
