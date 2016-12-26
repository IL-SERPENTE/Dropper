package net.samagames.dropper.events;

import net.samagames.dropper.Dropper;
import net.samagames.dropper.common.GameItems;
import net.samagames.dropper.common.GameLocations;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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
                    player.teleport(GameLocations.SPAWN.locationValue());
                }

            }
        }
    }

}
