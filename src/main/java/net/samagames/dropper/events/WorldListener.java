package net.samagames.dropper.events;

import net.samagames.dropper.Dropper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * @author Vialonyx
 */

public class WorldListener implements Listener {

    /*
     * This Listener take care of events called by the world.
     */

    private Dropper instance;
    public WorldListener(Dropper instance){
        this.instance = instance;
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event){
        event.setCancelled(true);
    }

}
