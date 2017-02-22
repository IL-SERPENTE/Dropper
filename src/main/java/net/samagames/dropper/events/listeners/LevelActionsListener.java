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
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LevelActionsListener implements Listener {

    /**
     * This is the listener of actions relative to the levels.
     * @author Vialonyx
     */

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
            player.getInventory().setItem(4, this.game.ITEM_QUIT_LEVEL);

        } else if (dpPlayer.getGameType().equals(GameType.COMPETITION)) {
            player.teleport(level.getPlayLocation());

        }

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
            player.getInventory().setItem(5, this.game.ITEM_QUIT_GAME);
            player.getInventory().setItem(3, this.game.ITEM_SELECTGUI);
            dpPlayer.updateCurrentLevel(null);

        } else if (dpPlayer.getGameType().equals(GameType.COMPETITION)){
            DropperLevel next = this.game.getNextFromCurrent(level);
            dpPlayer.updateCurrentLevel(next);
            player.teleport(next.getPlayLocation());
        }

    }

    @EventHandler
    public void onCooldownDone(CooldownDoneEvent event){

        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_NOTE_PLING, 20, 20);
        DropperPlayer dpPlayer = this.game.getPlayer(event.getPlayer().getUniqueId());
        LevelJoinEvent levelJoinEvent = new LevelJoinEvent(event.getPlayer(), event.getLevel());
        dpPlayer.resetCooldownData();
        this.game.getInstance().getServer().getPluginManager().callEvent(levelJoinEvent);
    }

}
