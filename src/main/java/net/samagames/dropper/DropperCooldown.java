package net.samagames.dropper;

import net.samagames.dropper.events.CooldownDoneEvent;
import net.samagames.dropper.level.DropperLevel;
import net.samagames.tools.Titles;
import net.samagames.tools.chat.ActionBarAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
public class DropperCooldown extends BukkitRunnable {

    /**
     * Here is managed all Dropper's specials cooldowns.
     * @author Vialonyx
     */

    private Dropper game;
    private Player player;
    private DropperLevel level;
    private Location next;
    private int act;
    private int type;
    private CooldownDoneEvent cooldownDoneEvent;

    /**
     * Start the level starting cooldown.
     * @param game The game class.
     * @param player The player.
     * @param level The player's current level.
     */

    public DropperCooldown(Dropper game, Player player, DropperLevel level){

        // Basic cooldown started before level joining.
        this.game = game;
        this.player = player;
        this.level = level;
        this.act = 6;
        this.type = 1;
        this.cooldownDoneEvent = new CooldownDoneEvent(this, player, this.type, this.level);

        // Enabling new cooldown for the player.
        DropperPlayer dpPlayer = this.game.getPlayer(player.getUniqueId());
        dpPlayer.enableCooldown(this);

    }

    /**
     * Start a special cooldown of 5 seconds.
     * @param player The player.
     * @param next The position where you must teleport the player at the end of cooldown.
     */

    public DropperCooldown(Player player, Location next){

        // Special cooldown.
        this.player = player;
        this.act = 31;
        this.next = next;
        this.type = 2;
        this.cooldownDoneEvent = new CooldownDoneEvent(this, player, this.type, this.level);

    }

    @Override
    public void run() {

        act--;
        if(this.type == 1){

            if(act == 0){

                // Cancelling the task & sending message to the player.
                this.cancel();

                Titles.sendTitle(this.player, 10, 20, 10, "", "" + ChatColor.DARK_RED + ChatColor.BOLD + "Début du niveau !");
                this.game.getInstance().getServer().getPluginManager().callEvent(cooldownDoneEvent);

            } else {

                // Sending cooldown evolution to the player in ActionBar.
                ActionBarAPI.sendMessage(player.getUniqueId(),"" + ChatColor.DARK_GRAY + ChatColor.BOLD + "Début du niveau dans " + ChatColor.GOLD + ChatColor.BOLD + act + ChatColor.DARK_GRAY + ChatColor.BOLD + " " + Dropper.formatSecondsText(act));
                this.player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 20, 20);
            }

        } else if(this.type == 2){

            if(act == 0){

                this.cancel();
                this.game.getInstance().getServer().getPluginManager().callEvent(cooldownDoneEvent);

            } else {
                ActionBarAPI.sendMessage(player.getUniqueId(), "" + ChatColor.DARK_GRAY + ChatColor.BOLD + "Téléportation dans " + ChatColor.GOLD + ChatColor.BOLD + act + ChatColor.DARK_GRAY + ChatColor.BOLD + " " + Dropper.formatSecondsText(act));
                this.player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 20, 20);
            }

        }

    }

    /**
     * Get the next location in case of type 2.
     * @return The location.
     */

    public Location getNext(){
        return this.next;
    }

    /**
     * Get the cooldown type.
     * @return The cooldown type.
     */

    public int getType(){
        return this.type;
    }

}
