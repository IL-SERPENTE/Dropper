package net.samagames.dropper.level;

import net.samagames.dropper.Dropper;
import net.samagames.dropper.DropperPlayer;
import net.samagames.dropper.events.CooldownDoneEvent;
import net.samagames.tools.chat.ActionBarAPI;
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
        this.game = game;
        this.player = player;
        this.level = level;
        this.act = 6;

        DropperPlayer dpPlayer = this.game.getPlayer(player.getUniqueId());
        dpPlayer.enableCooldown(this);

    }

    @Override
    public void run() {

        act--;
        if(act == 0){

            this.cancel();
            ActionBarAPI.sendMessage(this.player.getUniqueId(), "" + ChatColor.DARK_RED + ChatColor.BOLD + "Début du niveau !" );
            CooldownDoneEvent cooldownDoneEvent = new CooldownDoneEvent(player, this.level);
            this.game.getInstance().getServer().getPluginManager().callEvent(cooldownDoneEvent);

        } else {
            ActionBarAPI.sendMessage(this.player.getUniqueId(), "" + ChatColor.DARK_GRAY + ChatColor.BOLD + "Début du niveau dans " + ChatColor.GOLD + ChatColor.BOLD + act + ChatColor.DARK_GRAY + ChatColor.BOLD + " " + this.formatSecondsText(act));
            this.player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 20, 20);
        }

    }

    private String formatSecondsText(int act){
        if(act > 1){
            return "secondes";
        } else {
            return "seconde";
        }
    }

}
