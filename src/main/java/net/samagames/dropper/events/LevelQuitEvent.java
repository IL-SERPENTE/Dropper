package net.samagames.dropper.events;

import net.samagames.dropper.level.DropperLevel;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Vialonyx
 */

public class LevelQuitEvent extends Event {

    /**
     * This event was called when a player leave a level.
     * @author Vialonyx
     */

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private DropperLevel level;

    public LevelQuitEvent(Player player, DropperLevel level){
        this.player = player;
        this.level = level;
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

    public DropperLevel getLevel(){
        return this.level;
    }

}
