package net.samagames.dropper.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerAFKEvent extends Event {

    /**
     * This event was called when is detected as AFK by the AFKChecker system.
     * @author Vialonyx
     */

    private static final HandlerList handlers = new HandlerList();
    private Player player;

    public PlayerAFKEvent(Player player){
        this.player = player;
    }

    @Override
    public HandlerList getHandlers(){
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer(){
        return this.player;
    }

}
