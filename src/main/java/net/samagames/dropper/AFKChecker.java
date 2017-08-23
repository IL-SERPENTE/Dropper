package net.samagames.dropper;

import net.samagames.dropper.events.PlayerAFKEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.logging.Level;

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
public class AFKChecker {

    /**
     * This is the AFKChecker class.
     * AFKChecker is a fairly simple system which makes it possible to detect if the player did not have any activity during the last 5 minutes.
     * @author Vialonyx.
     */

    private DropperMain instance;
    private Player player;
    private Location atOldCheck;
    private int minutesWithoutMove, task;
    boolean isAfk;

    public AFKChecker(DropperMain instance, Player player){

        this.instance = instance;
        this.player = player;
        this.atOldCheck = player.getLocation();
        this.minutesWithoutMove = 0;
        this.isAfk = false;
        PlayerAFKEvent playerAFKEvent = new PlayerAFKEvent(player);

        // Creating a task activated every minute
        this.task = instance.getServer().getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {
            @Override
            public void run() {

                // Check if the current location of player is the same as the last check
                if(player.getLocation().getX() == atOldCheck.getX() && player.getLocation().getY() == atOldCheck.getY() && player.getLocation().getZ() == atOldCheck.getZ()){
                    // If that is the case, add +1 to the minutesWithoutMove value
                    minutesWithoutMove++;
                } else {
                    // Else, reset the value and update afk state to false
                    minutesWithoutMove = 0;
                    isAfk = false;
                }

                // Now check the value of minutesWithoutMove. If the value equals to 6, the player is considered as an AFK player.
                if(minutesWithoutMove == 6){
                    instance.getServer().getPluginManager().callEvent(playerAFKEvent);
                    isAfk = true;
                }

                // Updating the location of old check by the current
                atOldCheck = player.getLocation();

            }
        }, 0L, 1200);

    }

    /**
     * Get the AFK state.
     * @return true if the player is actually AFK.
     */

    public boolean isAfk(){
        return this.isAfk;
    }

    /**
     * Cancel the internal task of checker.
     */

    public void cancel(){
        this.instance.getServer().getScheduler().cancelTask(this.task);
        this.instance.getLogger().log(Level.INFO, "AFKChecker are cancelled for player " + this.player.getDisplayName());
    }

}
