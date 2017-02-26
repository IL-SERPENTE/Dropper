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

import static net.samagames.tools.GameUtils.broadcastMessage;

/**
 * @author Vialonyx
 */

public class LevelCategorySelectorGUI extends AbstractGui {

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

        switch(action){
            case "1":
                SamaGamesAPI.get().getGuiManager().openGui(player, new CategoryOneGUI(this.instance));
            case "2":
                SamaGamesAPI.get().getGuiManager().openGui(player, new CategoryTwoGUI(this.instance));
        }

    }

    @Override
    public void onClose(Player player)
    {
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0F, 1.0F);
    }

}
