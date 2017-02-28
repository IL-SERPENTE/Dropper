package net.samagames.dropper.events;

import net.samagames.dropper.DropperCooldown;
import net.samagames.dropper.level.DropperLevel;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Vialonyx
 */

public class CooldownDoneEvent extends Event {

    /**
     * This event was called when a the cooldown is done.
     * @author Vialonyx
     */

    private static final HandlerList handlers = new HandlerList();
    private DropperCooldown cooldown;
    private Player player;
    private DropperLevel level;
    private int type;

    public CooldownDoneEvent(DropperCooldown cooldown, Player player, int cooldownType, DropperLevel level){
        this.cooldown = cooldown;
        this.player = player;
        this.level = level;
        this.type = cooldownType;
    }

    @Override
    public HandlerList getHandlers(){
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public DropperCooldown getCooldown(){
        return this.cooldown;
    }

    public Player getPlayer(){
        return this.player;
    }

    public DropperLevel getLevel(){
        return this.level;
    }

    public int getCooldownType(){
        return this.type;
    }


}
