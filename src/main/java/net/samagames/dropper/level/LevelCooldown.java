package net.samagames.dropper.level;

import net.samagames.dropper.Dropper;
import net.samagames.dropper.DropperPlayer;
import net.samagames.dropper.events.CooldownDoneEvent;
import net.samagames.tools.chat.ActionBarAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Vialonyx
 */

public class LevelCooldown extends BukkitRunnable {

    private Dropper game;
    private Player player;
    private DropperLevel level;
    private int act;
    private boolean done;

    public LevelCooldown(Dropper game, Player player, DropperLevel level){
        this.game = game;
        this.player = player;
        this.level = level;
        this.act = 11;
        this.done = false;

        DropperPlayer dpPlayer = this.game.getPlayer(player.getUniqueId());
        dpPlayer.enableCooldown(this);

    }

    @Override
    public void run() {

        act--;
        if(act == 0){

            this.cancel();
            this.done = true;
            ActionBarAPI.sendMessage(this.player.getUniqueId(), "" + ChatColor.AQUA + "Début du niveau !" );
            CooldownDoneEvent cooldownDoneEvent = new CooldownDoneEvent(player, this.level);
            this.game.getInstance().getServer().getPluginManager().callEvent(cooldownDoneEvent);

        } else {
            ActionBarAPI.sendMessage(this.player.getUniqueId(), "" + ChatColor.AQUA + "Début du niveau dans " + ChatColor.GOLD + this.formatSecondColor(act) + ChatColor.AQUA + " " + this.formatSecondsText(act));
        }

    }

    private String formatSecondsText(int act){
        if(act > 1){
            return "secondes";
        } else {
            return "seconde";
        }
    }

    private String formatSecondColor(int act){
        if(act > 5){
            return "" + ChatColor.GOLD + act;
        } else {
            return "" + ChatColor.DARK_RED + act;
        }
    }

}
