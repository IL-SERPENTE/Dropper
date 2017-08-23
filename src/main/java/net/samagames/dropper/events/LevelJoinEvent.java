package net.samagames.dropper.events;

import net.samagames.dropper.level.DropperLevel;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/*
 * This file is part of Dropper.
 *
 * Dropper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dropper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Dropper.  If not, see <http://www.gnu.org/licenses/>.
 */
public class LevelJoinEvent extends Event {

    /**
     * This event was called when a player join a level.
     * @author Vialonyx
     */

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private DropperLevel level;

    public LevelJoinEvent(Player player, DropperLevel level){
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
