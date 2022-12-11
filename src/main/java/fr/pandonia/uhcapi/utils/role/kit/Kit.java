package fr.pandonia.uhcapi.utils.role.kit;

import org.bukkit.inventory.ItemStack;

public interface Kit {
    ItemStack[] getKits();

    void useKit();

    boolean canUseKit();
}

