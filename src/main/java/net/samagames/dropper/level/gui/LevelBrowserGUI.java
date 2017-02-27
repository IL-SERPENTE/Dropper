package net.samagames.dropper.level.gui;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.gui.AbstractGui;
import net.samagames.dropper.DropperMain;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LevelBrowserGUI extends AbstractGui {

    /**
     * This GUI is used to select a level to play.
     * @author Vialonyx
     */

    private DropperMain instance;
    private int category, i;
    public LevelBrowserGUI(DropperMain instance, int category) {
        this.instance = instance;
        this.category = category;
        this.i = 0;
    }

    @Override
    public void display(Player player) {

        this.inventory = this.instance.getServer().createInventory(null, InventoryType.CHEST, "Sélectionner un niveau");

        this.instance.get().getRegisteredLevels().stream().filter(level -> level.getCategory() == this.category).forEach(level -> {

            ItemStack stack =  new ItemStack(Material.NETHER_STAR, level.getID());
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA + level.getName() + ChatColor.RED + ChatColor.RED + ChatColor.ITALIC + " #" + level.getID());
            stack.setItemMeta(meta);
            this.setSlotData(stack, this.i, Integer.toString(level.getID()));
            i++;

        });

        ItemStack back = new ItemStack(Material.EMERALD, 1);
        ItemMeta meta = back.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "« Retour");
        back.setItemMeta(meta);
        this.setSlotData(back, this.getInventory().getSize()-1, "back");

        player.openInventory(this.inventory);
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0F, 1.0F);
    }

    @Override
    public void onClick(Player player, ItemStack stack, String action, ClickType clickType) {
        player.closeInventory();

        if(action.equals("back")){

            SamaGamesAPI.get().getGuiManager().openGui(player, new LevelCategorySelectorGUI(this.instance));

        } else {

            int level = Integer.parseInt(action);
            if (level < 0 || level > instance.get().getRegisteredLevels().size()){
                return;
            }

            this.instance.get().usualLevelJoin(player, this.instance.get().getDropperLevel(level - 1));

        }

    }

    @Override
    public void onClose(Player player)
    {
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0F, 1.0F);
    }

}
