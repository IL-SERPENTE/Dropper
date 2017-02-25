package net.samagames.dropper.events.listeners;

import net.samagames.api.SamaGamesAPI;
import net.samagames.dropper.GameType;
import net.samagames.dropper.LevelGUI;
import net.samagames.dropper.events.PlayerAFKEvent;
import net.samagames.dropper.level.DropperLevel;
import net.samagames.tools.chat.ActionBarAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import net.samagames.dropper.Dropper;
import net.samagames.dropper.DropperPlayer;
import org.bukkit.scheduler.BukkitRunnable;


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
					gamePlayer.setNeutralized(false);
					if (gamePlayer.getCurrentLevel() == null) {
						this.game.usualLevelLeave(player, true);
					} else {
						this.game.usualLevelLeave(player, false);
					}

				} else if(item.isSimilar(Dropper.ITEM_QUIT_GAME)){
					this.game.usualGameLeave(player);
					gamePlayer.setNeutralized(false);
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
	public void onPlayerMove(PlayerMoveEvent event){
		DropperPlayer dpPlayer = this.game.getPlayer(event.getPlayer().getUniqueId());
		Player player = (Player) event.getPlayer();
		if(dpPlayer.isNeutralized()){
            event.setCancelled(true);
        }else if (player.getLocation().getBlock().getType().equals(org.bukkit.Material.STATIONARY_WATER) && dpPlayer.getCurrentLevel() != null){

			// neutralize player en set his inventory
				dpPlayer.setNeutralized(true);

				player.getInventory().clear();

				player.getInventory().setHelmet(Dropper.stackBuilder("Pumkin", null, org.bukkit.Material.PUMPKIN,(byte)0));
				player.getInventory().setItem(3, Dropper.ITEM_QUIT_LEVEL);
				player.getInventory().setItem(5, Dropper.ITEM_QUIT_GAME);

				new BukkitRunnable() {

					DropperLevel dropperLevel;

					@Override
					public void run() {
						//check if player hasn't use the quit button and restart the level.
						if(player.getInventory().contains(Dropper.ITEM_QUIT_LEVEL)){
							player.teleport(game.getSpawn());
							player.getInventory().clear();

							dropperLevel = dpPlayer.getCurrentLevel();
							game.usualLevelLeave(player, false);

							dpPlayer.setNeutralized(false);

							game.usualStartLevel(dpPlayer,player,dropperLevel);
						}
					}
				}.runTaskLater(Dropper.getInstance(),100);

				if (dpPlayer.getGameType().equals(GameType.COMPETITION))
					this.game.usualGameLeave(player);

			}
    }

    @EventHandler
    public void onPlayerAFK(PlayerAFKEvent event){
        // Temporary we just send a message, more things here in the future.
        event.getPlayer().sendMessage("[AFKChecker] You has detected as AFK.");
    }

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){
		event.getPlayer().teleport(this.game.getSpawn());
		this.game.getEffectManager().restoreDefaultEffects(event.getPlayer());
	}

	@EventHandler
    public void onPlayerSwap(PlayerSwapHandItemsEvent event){
	    // Preventing players to swap item in hands.
	    event.setCancelled(true);
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
