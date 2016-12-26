package net.samagames.dropper.common;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;

/**
 * @author Vialonyx
 */

public enum GameItems {

    /*
     * This is an enum of the currently used items in game.
     */

    BACK_LEVEL_HUB(stackBuilder("§3Retour au §bChoix du niveau", null, Material.ENDER_CHEST, (byte) 0));

    private ItemStack iStack;
    GameItems(ItemStack iStack){
        this.iStack = iStack;
    }

    public ItemStack getStackValue(){
        return this.iStack;
    }

    private static ItemStack stackBuilder(String name, List<String> lore, Material material, byte data){

        org.bukkit.inventory.ItemStack tmpStack = new ItemStack(material, 1, data);
        ItemMeta tmpStackMeta = tmpStack.getItemMeta();
        tmpStackMeta.setDisplayName(name);
        tmpStackMeta.setLore(lore);
        tmpStack.setItemMeta(tmpStackMeta);

        return tmpStack;
    }

}
