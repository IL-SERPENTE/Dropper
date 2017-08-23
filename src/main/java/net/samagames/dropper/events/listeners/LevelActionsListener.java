package net.samagames.dropper.events.listeners;

import net.samagames.api.SamaGamesAPI;
import net.samagames.dropper.*;
import net.samagames.dropper.events.CooldownDoneEvent;
import net.samagames.dropper.events.LevelJoinEvent;
import net.samagames.dropper.events.LevelQuitEvent;
import net.samagames.dropper.level.DropperLevel;
import net.samagames.tools.chat.ActionBarAPI;
import net.samagames.tools.chat.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import static org.bukkit.Bukkit.getWorlds;

/*
 * This file is part of Dropper.
 *
 * Dropper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dropper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Dropper.  If not, see <http://www.gnu.org/licenses/>.
 */
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

        player.sendMessage(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getGameTag() + ChatColor.BLUE + ChatColor.BLUE + " Vous avez rejoint le niveau " + ChatColor.GOLD + level.getID() + ChatColor.AQUA + " (" + level.getName() + ChatColor.AQUA + ")");
        dpPlayer.defineNewCalculator(new TimeCalculator());

        // Started special cooldown for level 15.
        if(level.getID() == 15){
            player.sendMessage(SamaGamesAPI.get().getGameManager().getCoherenceMachine().getGameTag() + ChatColor.AQUA + " Vous disposez de " + ChatColor.RED + "30 secondes" + ChatColor.AQUA + " pour mémoriser ces symboles !");

            DropperCooldown cooldown = new DropperCooldown(player, new Location(getWorlds().get(0), -653, 330, -638));
            cooldown.runTaskTimer(this.game.getInstance(), 20L, 20L);
            dpPlayer.enableCooldown(cooldown);

            new FancyMessage("Cliquez ").color(ChatColor.WHITE).style(ChatColor.BOLD).then("[ICI]").command("/skip").color(ChatColor.GREEN).style(ChatColor.BOLD).then(" pour passer la phase de mémorisation !").color(ChatColor.WHITE).style(ChatColor.BOLD).send(player);

        }

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

            player.getInventory().clear();
            player.getInventory().setItem(5, Dropper.ITEM_QUIT_GAME);
            player.getInventory().setItem(3, Dropper.ITEM_SELECTGUI);
            dpPlayer.updateCurrentLevel(null);

        } else if (dpPlayer.getGameType().equals(GameType.COMPETITION)){

            DropperLevel next = this.game.getNextFromCurrent(level);

            if(dpPlayer.getCompetitionCategory() == 1 || dpPlayer.getCompetitionCategory() == 2){

                if(next.getCategory() == dpPlayer.getCompetitionCategory()){
                    player.teleport(this.game.getSpawn());
                    this.game.usualLevelJoin(player, this.game.getDropperLevel(next.getID()));
                } else {
                    // - END OF COMPETITION -
                    this.game.usualGameLeave(player);
                }

            } else if(dpPlayer.getCompetitionCategory() == 3){
                player.teleport(this.game.getSpawn());
                this.game.usualLevelJoin(player, this.game.getDropperLevel(next.getID()));
            }

        }

        TimeCalculator calculator = dpPlayer.getCurrentCalculator();
        calculator.stop();
        ActionBarAPI.sendMessage(player.getUniqueId(), ChatColor.BLUE + "Vous avez complété ce niveau en " + ChatColor.RED + calculator.getHours() + "h " + calculator.getMinutes() + "m " + calculator.getSeconds() + "s ");

    }

    @EventHandler
    public void onCooldownDone(CooldownDoneEvent event){

        if(event.getCooldownType() == 1){

            // Updating current level of player.
            this.game.getPlayer(event.getPlayer().getUniqueId()).updateCurrentLevel(event.getLevel());

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
