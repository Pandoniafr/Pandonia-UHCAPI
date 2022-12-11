package fr.pandonia.uhcapi.config.value;

import fr.pandonia.uhcapi.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum CommonItems {
    GUI_BACK_ITEM("§8┃ §fRevenir en §carrière", Material.ARROW, 0),
    GUI_CLOSE_ITEM("§8┃ §fFermer l'inventaire", Material.ARROW, 0);

    private final String name;
    private final Material material;
    private final int data;

    private CommonItems(String name, Material material, int data) {
        this.name = name;
        this.material = material;
        this.data = data;
    }

    public String getName() {
        return this.name;
    }

    public Material getMaterial() {
        return this.material;
    }

    public int getData() {
        return this.data;
    }

    public ItemStack getItem() {
        return new ItemCreator(this.material).setDurability(this.data).setName(this.name).getItem();
    }
}
