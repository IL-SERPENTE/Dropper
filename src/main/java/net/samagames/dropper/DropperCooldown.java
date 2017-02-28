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

public class DropperCooldown extends BukkitRunnable {

    /**
     * This is the cooldown started to a player before a level.
     * @author Vialonyx
     */

    private Dropper game;
    private Player player;
    private DropperLevel level;
    private Location next;
    private int act;
    private int type;

    public DropperCooldown(Dropper game, Player player, DropperLevel level){

        // Basic cooldown started before level joining.
        this.game = game;
        this.player = player;
        this.level = level;
        this.act = 6;
        this.type = 1;

        // Enabling new cooldown for the player.
        DropperPlayer dpPlayer = this.game.getPlayer(player.getUniqueId());
        dpPlayer.enableCooldown(this);

    }

    public DropperCooldown(Player player, Location next){

        // Special cooldown.
        this.player = player;
        this.act = 31;
        this.next = next;
        this.type = 2;

    }

    @Override
    public void run() {

        act--;
        if(this.type == 1){

            if(act == 0){

                // Cancelling the task & sending message to the player.
                this.cancel();

                Titles.sendTitle(this.player, 10, 20, 10, "", "" + ChatColor.DARK_RED + ChatColor.BOLD + "Début du niveau !");

                // Calling the custom CoolDownEvent.
                CooldownDoneEvent cooldownDoneEvent = new CooldownDoneEvent(player, this.level);
                this.game.getInstance().getServer().getPluginManager().callEvent(cooldownDoneEvent);

            } else {

                // Sending cooldown evolution to the player in ActionBar.
                ActionBarAPI.sendMessage(player.getUniqueId(),"" + ChatColor.DARK_GRAY + ChatColor.BOLD + "Début du niveau dans " + ChatColor.GOLD + ChatColor.BOLD + act + ChatColor.DARK_GRAY + ChatColor.BOLD + " " + Dropper.formatSecondsText(act));
                this.player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 20, 20);
            }

        } else if(this.type == 2){

            if(act == 0){

                this.cancel();
                this.player.teleport(this.next);

            } else {
                ActionBarAPI.sendMessage(player.getUniqueId(), "" + ChatColor.DARK_GRAY + ChatColor.BOLD + "Téléportation dans " + ChatColor.GOLD + ChatColor.BOLD + act + ChatColor.DARK_GRAY + ChatColor.BOLD + " " + Dropper.formatSecondsText(act));
                this.player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 20, 20);
            }

        }

    }

}
