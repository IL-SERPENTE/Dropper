package net.samagames.dropper.events;

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

    public WorldListener(){
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event){
    	
    	// Disabling ability to break blocks.
        event.setCancelled(true);
    }

}
