package fr.pandonia.uhcapi.config;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.value.CommonItems;
import fr.pandonia.uhcapi.utils.CustomInventory;
import fr.pandonia.uhcapi.utils.ItemCreator;
import java.util.function.Supplier;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AdminPanelGUI
        implements CustomInventory {
    @Override
    public String getName() {
        return "§f(§c!§f) §cPanel";
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        Integer[] glass;
        ItemStack[] slots = new ItemStack[this.getSlots()];
        Integer[] arrayOfInteger1 = glass = new Integer[]{0, 1, 7, 8, 9, 17, 27, 35, 36, 37, 43, 44};
        int i = arrayOfInteger1.length;
        for (int b = 0; b < i; b = (int)((byte)(b + 1))) {
            int j = arrayOfInteger1[b];
            slots[j] = new ItemCreator(Material.STAINED_GLASS_PANE).setDurability(14).getItem();
        }
        slots[13] = new ItemCreator(Material.EMPTY_MAP).setName("§6Game Dev §f- " + (API.getAPI().getGameManager().getGameConfig().isGameDev() ? "§eOui" : "§cNon")).getItem();
        slots[40] = CommonItems.GUI_BACK_ITEM.getItem();
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
        switch (clickedItem.getType()) {
            case EMPTY_MAP: {
                API.getAPI().getGameManager().getGameConfig().setGameDev(!API.getAPI().getGameManager().getGameConfig().isGameDev());
                API.getAPI().openInventory(player, this.getClass());
                break;
            }
            case ARROW: {
                API.getAPI().openInventory(player, ConfigMainGUI.class);
            }
        }
    }

    @Override
    public int getRows() {
        return 5;
    }
}
