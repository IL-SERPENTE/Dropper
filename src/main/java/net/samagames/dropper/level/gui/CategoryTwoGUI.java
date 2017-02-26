package net.samagames.dropper.level.gui;

import net.samagames.api.gui.AbstractGui;
import net.samagames.dropper.DropperMain;
import net.samagames.dropper.level.DropperLevel;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CategoryTwoGUI extends AbstractGui {

    private DropperMain instance;
    public CategoryTwoGUI(DropperMain instance) {
        this.instance = instance;
    }

    @Override
    public void display(Player player) {
        this.inventory = this.instance.getServer().createInventory(null, 54, "Sélectionner un niveau");

        for(DropperLevel level : this.instance.get().getRegisteredLevels()){

            if(level.getCategory() == 2){

                ItemStack stack =  new ItemStack(Material.ENDER_PEARL, level.getID());
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName(ChatColor.AQUA + level.getName() + ChatColor.RED + ChatColor.RED + ChatColor.ITALIC + " #" + level.getID());
                stack.setItemMeta(meta);

                this.setSlotData(this.inventory, stack, level.getID(), Integer.toString(level.getID()));

            }

        }

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
