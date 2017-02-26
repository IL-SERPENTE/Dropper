package net.samagames.dropper.level.gui;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.gui.AbstractGui;
import net.samagames.dropper.Dropper;
import net.samagames.dropper.DropperMain;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
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
        this.inventory = this.instance.getServer().createInventory(null, InventoryType.HOPPER, "Sélectionner une catégorie");

        this.setSlotData(Dropper.stackBuilder(ChatColor.BLUE + "Dropper V1", Arrays.asList(ChatColor.GRAY + "Madness, The Fall, In The Middle..."), Material.EYE_OF_ENDER, (byte) 0), 1, "1");
        this.setSlotData(Dropper.stackBuilder(ChatColor.BLUE + "Dropper V2", Arrays.asList(ChatColor.GRAY + "Rainbow, Isengard, Neo..."), Material.EYE_OF_ENDER, (byte) 0), 3, "2");

        player.openInventory(this.inventory);
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0F, 1.0F);
    }

    @Override
    public void onClick(Player player, ItemStack stack, String action, ClickType clickType) {

        SamaGamesAPI.get().getGuiManager().openGui(player, new LevelBrowserGUI(this.instance, Integer.parseInt(action)));

    }

    @Override
    public void onClose(Player player)
    {
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0F, 1.0F);
    }

}
