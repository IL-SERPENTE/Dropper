package net.samagames.dropper.level;

import net.samagames.dropper.Dropper;
import net.samagames.tools.chat.ActionBarAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LevelSpecialCooldown extends BukkitRunnable {

    /**
     * This is the cooldown started at the beginning of the level 4.
     * @author Vialonyx
     */

    private Player player;
    private int act;
    private Location next;

    public LevelSpecialCooldown(Player player, Location next){
        this.player = player;
        this.act = 31;
        this.next = next;
    }

    @Override
    public void run() {

        act--;
        if(act == 0){

            this.cancel();
            this.player.teleport(this.next);

        } else {
            ActionBarAPI.sendMessage(this.player.getUniqueId(), "" + ChatColor.DARK_GRAY + ChatColor.BOLD + "Téléportation dans " + ChatColor.GOLD + ChatColor.BOLD + act + ChatColor.DARK_GRAY + ChatColor.BOLD + " " + Dropper.formatSecondsText(act));
            this.player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 20, 20);
        }

    }

}
