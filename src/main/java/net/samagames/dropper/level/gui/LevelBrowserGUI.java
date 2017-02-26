package net.samagames.dropper.level.gui;

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

public class CategoryOneGUI extends AbstractGui {

    private DropperMain instance;
    public CategoryOneGUI(DropperMain instance) {
        this.instance = instance;
    }

    @Override
    public void display(Player player) {
        this.inventory = this.instance.getServer().createInventory(null, InventoryType.CHEST, "Sélectionner un niveau");

        this.instance.get().getRegisteredLevels().stream().filter(level -> level.getCategory() == 1).forEach(level -> {
            this.setSlotData(this.inventory, Dropper.stackBuilder(ChatColor.AQUA + level.getName() + ChatColor.RED + ChatColor.RED + ChatColor.ITALIC + " #" + level.getID(), null, Material.ENDER_PEARL,(byte) 0), level.getID(), "1");
        });

        this.instance.get().getRegisteredLevels().stream().filter(level -> level.getCategory() == 2).forEach(level -> {
            this.setSlotData(this.inventory, Dropper.stackBuilder(ChatColor.AQUA + level.getName() + ChatColor.RED + ChatColor.RED + ChatColor.ITALIC + " #" + level.getID(), null, Material.ENDER_PEARL,(byte) 0), level.getID(), "2");
        });

        player.openInventory(this.inventory);
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0F, 1.0F);
    }

    @Override
    public void onClick(Player player, ItemStack stack, String action, ClickType clickType) {
        player.closeInventory();

        int level = Integer.parseInt(action);
        if (level < 0 || level > instance.get().getRegisteredLevels().size()){
            return;
        }
        this.instance.get().usualLevelJoin(player, level - 1);

    }

    @Override
    public void onClose(Player player)
    {
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0F, 1.0F);
    }

}
