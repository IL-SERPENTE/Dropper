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
        }

    }

    @Override
    public void onClose(Player player)
    {
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0F, 1.0F);
    }

}
