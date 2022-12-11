package fr.pandonia.uhcapi.config.common.rules;

import fr.pandonia.uhcapi.common.rules.items.DropItemRate;
import fr.pandonia.uhcapi.config.ConfigOptionsGUI;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.utils.CustomInventory;
import fr.pandonia.uhcapi.utils.ItemCreator;
import java.util.function.Supplier;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DropItemRateGUI
        implements CustomInventory {
    private final GameManager gameManager;

    public DropItemRateGUI(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public String getName() {
        return "Taux de drop";
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack[] slots = new ItemStack[this.getSlots()];
        int slot = 2;
        for (DropItemRate dropItemRate : DropItemRate.values()) {
            slots[slot] = dropItemRate.getItem();
            ++slot;
        }
        slots[13] = new ItemCreator(Material.ARROW).setName("§fRevenir en arrière").getItem();
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
        for (DropItemRate dropItemRate : DropItemRate.values()) {
            if (dropItemRate.getMaterial() != clickedItem.getType() || clickedItem.getItemMeta().getDisplayName().equals("§fRevenir en arrière")) continue;
            dropItemRate.toggleAmount(clickType);
            this.gameManager.getApi().openInventory(player, this.getClass());
        }
        switch (slot) {
            case 13: {
                if (!clickedItem.hasItemMeta() || !clickedItem.getItemMeta().hasDisplayName() || !clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§fRevenir en arrière")) break;
                this.gameManager.getApi().openInventory(player, ConfigOptionsGUI.class);
            }
        }
    }

    @Override
    public int getRows() {
        return 2;
    }
}
