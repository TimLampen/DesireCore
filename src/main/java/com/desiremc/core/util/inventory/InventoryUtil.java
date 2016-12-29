package com.desiremc.core.util.inventory;

import com.desiremc.core.lang.Lang;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Timothy Lampen on 11/12/2016.
 */
public class InventoryUtil {
    /**
     * @param target the player who is reciving the item
     * @param item the item being added to the inventory
     * @param itemName the name of the item being added, example: "keys"
     */
    public static void safeInventoryAdd(Player target, ItemStack item, String itemName){
        String form = itemName.endsWith("s") ? " were" : " was";
        if(target.getInventory().firstEmpty()==-1){
            target.getWorld().dropItem(target.getLocation(), item);
            target.sendMessage(Lang.INVENTORY_FULL_ITEM_DROP.replaceAll("%TYPE%", itemName + form));
        }
        else{
            target.getInventory().addItem(item);
        }
    }
}
