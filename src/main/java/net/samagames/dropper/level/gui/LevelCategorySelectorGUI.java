package net.samagames.dropper.level.gui;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.gui.AbstractGui;
import net.samagames.dropper.Dropper;
import net.samagames.dropper.DropperMain;
import net.samagames.dropper.GameType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import java.util.Arrays;

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
public class LevelCategorySelectorGUI extends AbstractGui {

    /**
     * This GUI is used to select the category of desired level.
     * @author Vialonyx
     */

    private DropperMain instance;
    public LevelCategorySelectorGUI(DropperMain instance){
        this.instance = instance;
    }

    @Override
    public void display(Player player) {
        this.inventory = this.instance.getServer().createInventory(null, 9, "Sélectionner une catégorie");

        this.setSlotData(Dropper.stackBuilder(ChatColor.BLUE + "Dropper V1", Arrays.asList(ChatColor.GRAY + "Madness, The Fall, In The Middle..."), Material.EYE_OF_ENDER, (byte) 0), 1, "1");
        this.setSlotData(Dropper.stackBuilder(ChatColor.BLUE + "Dropper V2", Arrays.asList(ChatColor.GRAY + "Rainbow, Isengard, Neo..."), Material.EYE_OF_ENDER, (byte) 0), 7, "2");

        if(this.instance.get().getPlayer(player.getUniqueId()).getGameType().equals(GameType.FREE)){
            this.setSlotData(Dropper.stackBuilder(ChatColor.GOLD + "Tutoriel", Arrays.asList(ChatColor.GREEN + "Découvrez comment jouer à TheDropper !"), Material.ENDER_CHEST, (byte) 0), 4,"tuto");
        } else {
            this.setSlotData(Dropper.stackBuilder(ChatColor.BLUE + "Enchaînement", Arrays.asList(ChatColor.GREEN + "Jouez les niveaux de Dropper 1 et Dropper 2 à la suite !"), Material.EYE_OF_ENDER, (byte) 0), 4,"chain");
        }

        player.openInventory(this.inventory);
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0F, 1.0F);
    }

    @Override
    public void onClick(Player player, ItemStack stack, String action, ClickType clickType) {

        player.closeInventory();

        if(action.equals("1") || action.equals("2")){

            if(this.instance.get().getPlayer(player.getUniqueId()).getGameType().equals(GameType.COMPETITION)){
                this.instance.get().usualCompetitionStart(player, Integer.parseInt(action));
                return;
            }

            SamaGamesAPI.get().getGuiManager().openGui(player, new LevelBrowserGUI(this.instance, Integer.parseInt(action)));

        } else if (action.equals("tuto")){
            player.getInventory().clear();
            this.instance.get().getPlayer(player.getUniqueId()).setPlayerInTutorial(true);
            this.instance.getServer().getScheduler().runTaskLater(this.instance, () -> this.instance.get().getTutorial().start(player.getUniqueId()), 20L);
            return;
        } else if (action.equals("chain")){

            if(this.instance.get().getPlayer(player.getUniqueId()).getGameType().equals(GameType.COMPETITION)){
                this.instance.get().usualCompetitionStart(player, 3);
                return;
            }

        }

    }

    @Override
    public void onClose(Player player)
    {
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0F, 1.0F);
    }

}
