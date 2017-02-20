package net.samagames.dropper.events.listeners;

import net.samagames.api.SamaGamesAPI;
import net.samagames.dropper.Dropper;
import net.samagames.dropper.DropperPlayer;
import net.samagames.dropper.GameType;
import net.samagames.dropper.events.CooldownDoneEvent;
import net.samagames.dropper.events.LevelJoinEvent;
import net.samagames.dropper.events.LevelQuitEvent;
import net.samagames.dropper.level.DropperLevel;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Vialonyx
 */

public class LevelActionsListener implements Listener {

    private Dropper game;
    public LevelActionsListener(Dropper game){
        this.game = game;
    }

    @EventHandler
    public void onLevelJoin(LevelJoinEvent event){

        Player player = event.getPlayer();
        DropperPlayer dpPlayer = this.game.getPlayer(player.getUniqueId());
        DropperLevel level = event.getLevel();

        if(dpPlayer.getGameType().equals(GameType.FREE)) {
            player.teleport(level.getPlayLocation());
            player.getInventory().setItem(1, this.game.getGameItem(3));
            dpPlayer.updateCurrentLevel(level);

        } else if (dpPlayer.getGameType().equals(GameType.COMPETITION)) {
            player.teleport(level.getPlayLocation());
            dpPlayer.updateCurrentLevel(level);

        }

        level.hidePlatform();

        SamaGamesAPI.get().getGameManager().getCoherenceMachine().getMessageManager()
        .writeCustomMessage("" + ChatColor.BLUE + ChatColor.BOLD + player.getName() + ChatColor.RESET + " a rejoint le niveau " + ChatColor.RED + ChatColor.BOLD + "#" + level.getID() +  ChatColor.RED + "(" + ChatColor.ITALIC + level.getName() + ")" + ChatColor.RESET + " en mode " + this.game.getGameTypeFormatColor(dpPlayer.getGameType()),true);

    }

    @EventHandler
    public void onLevelQuit(LevelQuitEvent event){

        Player player = event.getPlayer();
        DropperPlayer dpPlayer = this.game.getPlayer(player.getUniqueId());
        DropperLevel level = event.getLevel();

        if(dpPlayer.getGameType().equals(GameType.FREE)){
            player.teleport(this.game.getMapHub());
            player.getInventory().clear();
            player.getInventory().setItem(0, this.game.getGameItem(2));
            player.getInventory().setItem(1, this.game.getGameItem(4));
            dpPlayer.updateCurrentLevel(null);

        } else if (dpPlayer.getGameType().equals(GameType.COMPETITION)){
            DropperLevel next = this.game.getNextFromCurrent(level);
            dpPlayer.updateCurrentLevel(next);
            player.teleport(next.getPlayLocation());
        }

    }

    @EventHandler
    public void onCooldownDone(CooldownDoneEvent event){
        // More things here in the future
        LevelJoinEvent levelJoinEvent = new LevelJoinEvent(event.getPlayer(), event.getLevel());
        this.game.getInstance().getServer().getPluginManager().callEvent(levelJoinEvent);
    }

}
