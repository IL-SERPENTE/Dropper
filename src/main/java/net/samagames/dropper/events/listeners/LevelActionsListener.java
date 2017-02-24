package net.samagames.dropper.events.listeners;

import net.samagames.api.SamaGamesAPI;
import net.samagames.dropper.Dropper;
import net.samagames.dropper.DropperPlayer;
import net.samagames.dropper.GameType;
import net.samagames.dropper.TimeCalculator;
import net.samagames.dropper.events.CooldownDoneEvent;
import net.samagames.dropper.events.LevelJoinEvent;
import net.samagames.dropper.events.LevelQuitEvent;
import net.samagames.dropper.level.DropperLevel;
import net.samagames.dropper.level.LevelSpecialCooldown;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import static org.bukkit.Bukkit.broadcastMessage;
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

        if(dpPlayer.getGameType().equals(GameType.FREE)) {
            player.teleport(level.getPlayLocation());
            player.getInventory().setItem(4, Dropper.ITEM_QUIT_LEVEL);

        } else if (dpPlayer.getGameType().equals(GameType.COMPETITION)) {
            player.teleport(level.getPlayLocation());
        }

        if(level.getID() == 4){
            player.sendMessage(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getGameTag() + ChatColor.AQUA + " Vous disposez de " + ChatColor.RED + "30 secondes" + ChatColor.AQUA + " pour mémoriser ces symboles !");
            new LevelSpecialCooldown(player, new Location(getWorlds().get(0), -653, 330, -638)).runTaskTimer(this.game.getInstance(), 20L, 20L);
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

        if(dpPlayer.getGameType().equals(GameType.FREE)){
            player.teleport(this.game.getSpawn());
            player.getInventory().clear();
            player.getInventory().setItem(5, Dropper.ITEM_QUIT_GAME);
            player.getInventory().setItem(3, Dropper.ITEM_SELECTGUI);
            dpPlayer.updateCurrentLevel(null);

        } else if (dpPlayer.getGameType().equals(GameType.COMPETITION)){
            DropperLevel next = this.game.getNextFromCurrent(level);
            player.teleport(this.game.getSpawn());
            this.game.usualLevelJoin(player, next.getID());
        }

        TimeCalculator calculator = dpPlayer.getCurrentCalculator();
        calculator.stop();
        broadcastMessage("Vous avez mis " + calculator.getHours() + " heures, " + calculator.getMinutes() + " minutes, " + calculator.getSeconds() + " secondes à compléter ce niveau !");

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
