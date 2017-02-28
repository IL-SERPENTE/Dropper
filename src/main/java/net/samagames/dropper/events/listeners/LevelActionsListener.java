package net.samagames.dropper.events.listeners;

import net.samagames.api.SamaGamesAPI;
import net.samagames.dropper.*;
import net.samagames.dropper.events.CooldownDoneEvent;
import net.samagames.dropper.events.LevelJoinEvent;
import net.samagames.dropper.events.LevelQuitEvent;
import net.samagames.dropper.level.DropperLevel;
import net.samagames.tools.chat.ActionBarAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import static org.bukkit.Bukkit.getWorlds;

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

        this.game.getEffectManager().addEffectsForLevel(player, level);
        dpPlayer.neutralizePlayer(false);

        if(dpPlayer.getGameType().equals(GameType.FREE)) {
            player.teleport(level.getPlayLocation());
            player.getInventory().setItem(4, Dropper.ITEM_QUIT_LEVEL);

        } else if (dpPlayer.getGameType().equals(GameType.COMPETITION)) {
            player.teleport(level.getPlayLocation());
        }

        // Started special cooldown for level 15.
        if(level.getID() == 15){
            player.sendMessage(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getGameTag() + ChatColor.AQUA + " Vous disposez de " + ChatColor.RED + "30 secondes" + ChatColor.AQUA + " pour mémoriser ces symboles !");

            DropperCooldown cooldown = new DropperCooldown(player, new Location(getWorlds().get(0), -653, 330, -638));
            cooldown.runTaskTimer(this.game.getInstance(), 20L, 20L);
            dpPlayer.enableCooldown(cooldown);
        }

        player.sendMessage(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getGameTag() + ChatColor.BLUE + ChatColor.BLUE + " Vous avez rejoint le niveau " + ChatColor.GOLD + level.getID() + ChatColor.AQUA + " (" + level.getName() + ChatColor.AQUA + ")");
        dpPlayer.defineNewCalculator(new TimeCalculator());

    }

    @EventHandler
    public void onLevelQuit(LevelQuitEvent event){

        Player player = event.getPlayer();
        DropperPlayer dpPlayer = this.game.getPlayer(player.getUniqueId());
        DropperLevel level = event.getLevel();

        this.game.getEffectManager().restoreDefaultEffects(player);
        player.setHealth(20);
        dpPlayer.neutralizePlayer(false);

        if(dpPlayer.getGameType().equals(GameType.FREE)){
            player.teleport(this.game.getSpawn());
            player.getInventory().clear();
            player.getInventory().setItem(5, Dropper.ITEM_QUIT_GAME);
            player.getInventory().setItem(3, Dropper.ITEM_SELECTGUI);
            dpPlayer.updateCurrentLevel(null);

        } else if (dpPlayer.getGameType().equals(GameType.COMPETITION)){
            DropperLevel next = this.game.getNextFromCurrent(level);
            player.teleport(this.game.getSpawn());
            this.game.usualLevelJoin(player, this.game.getDropperLevel(next.getID()));
        }

        TimeCalculator calculator = dpPlayer.getCurrentCalculator();
        calculator.stop();
        ActionBarAPI.sendMessage(player.getUniqueId(), ChatColor.BLUE + "Vous avez complété ce niveau en " + ChatColor.RED + calculator.getHours() + "h " + calculator.getMinutes() + "m " + calculator.getSeconds() + "s ");

    }

    @EventHandler
    public void onCooldownDone(CooldownDoneEvent event){

        if(event.getCooldownType() == 1){
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_NOTE_PLING, 20, 20);
            DropperPlayer dpPlayer = this.game.getPlayer(event.getPlayer().getUniqueId());
            LevelJoinEvent levelJoinEvent = new LevelJoinEvent(event.getPlayer(), event.getLevel());
            dpPlayer.resetCooldownData();
            this.game.getInstance().getServer().getPluginManager().callEvent(levelJoinEvent);
        } else if(event.getCooldownType() == 2){
            event.getPlayer().teleport(event.getCooldown().getNext());
        }

    }

}
