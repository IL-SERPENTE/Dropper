package net.samagames.dropper;

import net.samagames.dropper.events.CooldownDoneEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
public class SkipCommand implements CommandExecutor {

    /**
     * This is the SkipCommand.
     * Internally called when player is clicking on the message to skip the cooldown in level 15.
     */

    private DropperMain instance;
    public SkipCommand(DropperMain instance){
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(commandSender instanceof Player){

            Player player = (Player) commandSender;
            DropperPlayer dpPlayer = this.instance.get().getPlayer(player.getUniqueId());

            if(dpPlayer.hasActiveCooldown() && dpPlayer.getActiveCooldown().getType() == 2){
                this.instance.getServer().getPluginManager().callEvent(new CooldownDoneEvent(dpPlayer.getActiveCooldown(), player, dpPlayer.getActiveCooldown().getType(), dpPlayer.getCurrentLevel()));
                dpPlayer.getActiveCooldown().cancel();
            }

        }

        return false;
    }

}
