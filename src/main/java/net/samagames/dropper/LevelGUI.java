package net.samagames.dropper;

import net.samagames.api.gui.AbstractGui;
import net.samagames.dropper.level.DropperLevel;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * @author Vialonyx
 */

public class LevelGUI extends AbstractGui {

    private DropperMain instance;

    public LevelGUI(DropperMain instance) {
        this.instance = instance;
    }

    @Override
    public void display(Player player) {
        this.inventory = this.instance.getServer().createInventory(null, 54, "Sélectionner un niveau");

        for(DropperLevel level : this.instance.get().getRegisteredLevels()){
            this.setSlotData(level.getName(), Material.ENDER_PEARL, level.getID(), new String[]{ ChatColor.RED + level.getDescription()}, "" + level.getID());
        }

        player.openInventory(this.inventory);
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0F, 1.0F);
    }

    @Override
    public void onClick(Player player, ItemStack stack, String action, ClickType clickType) {
        player.closeInventory();
        switch (action) {
            case "1":
                this.instance.get().usualLevelJoin(player, 0);
                break;
            case "2":
                this.instance.get().usualLevelJoin(player, 1);
                break;
            case "3":
                this.instance.get().usualLevelJoin(player, 2);
                break;
            case "4":
                this.instance.get().usualLevelJoin(player, 3);
                break;
            case "5":
                this.instance.get().usualLevelJoin(player, 4);
                break;
            case "6":
                this.instance.get().usualLevelJoin(player, 5);
                break;
            case "7":
                this.instance.get().usualLevelJoin(player, 6);
                break;
            case "8":
                this.instance.get().usualLevelJoin(player, 7);
                break;
        }
    }

    @Override
    public void onClose(Player player)
    {
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0F, 1.0F);
    }

}
