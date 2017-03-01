package net.samagames.dropper;

import net.samagames.dropper.events.CooldownDoneEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
