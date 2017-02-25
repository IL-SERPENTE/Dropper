package net.samagames.dropper.level;

import net.samagames.dropper.Dropper;
import net.samagames.dropper.DropperPlayer;
import net.samagames.dropper.events.CooldownDoneEvent;
import net.samagames.tools.Titles;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LevelCooldown extends BukkitRunnable {

    /**
     * This is the cooldown started to a player before a level.
     * @author Vialonyx
     */

    private Dropper game;
    private Player player;
    private DropperLevel level;
    private int act;

    public LevelCooldown(Dropper game, Player player, DropperLevel level){

        // Setting global data.
        this.game = game;
        this.player = player;
        this.level = level;
        this.act = 6;

        // Enabling new cooldown for the player.
        DropperPlayer dpPlayer = this.game.getPlayer(player.getUniqueId());
        dpPlayer.enableCooldown(this);

    }

    @Override
    public void run() {

        act--;
        if(act == 0){

            // Cancelling the task & sending message to the player.
            this.cancel();

            Titles.sendTitle(this.player, 10, 20, 10, "", "" + ChatColor.DARK_RED + ChatColor.BOLD + "Début du niveau !");

            // Calling the custom CoolDownEvent.
            CooldownDoneEvent cooldownDoneEvent = new CooldownDoneEvent(player, this.level);
            this.game.getInstance().getServer().getPluginManager().callEvent(cooldownDoneEvent);

        } else {

            // Sending cooldown evolution to the player using titles.

            Titles.sendTitle(this.player, 10, 20, 10, "", "" + ChatColor.DARK_GRAY + ChatColor.BOLD + "Début du niveau dans " + ChatColor.GOLD + ChatColor.BOLD + act + ChatColor.DARK_GRAY + ChatColor.BOLD + " " + Dropper.formatSecondsText(act));
            this.player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 20, 20);
        }

    }

}
