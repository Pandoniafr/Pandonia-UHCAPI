package fr.pandonia.uhcapi.config.intValue;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.ConfigOptionsGUI;
import fr.pandonia.uhcapi.config.GameConfig;
import fr.pandonia.uhcapi.config.value.CommonItems;
import fr.pandonia.uhcapi.utils.CustomInventory;
import fr.pandonia.uhcapi.utils.ItemCreator;
import java.util.function.Supplier;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DamageGUI
        implements CustomInventory {
    @Override
    public String getName() {
        return "Configuration des dégâts";
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack[] slots = new ItemStack[this.getSlots()];
        GameConfig gameConfig = API.getAPI().getGameManager().getGameConfig();
        slots[0] = new ItemCreator(Material.ENDER_PEARL).setName("§cEnder Pearl §8(§e+" + gameConfig.getEnderpearlDamage() + "§8)").setAmount(gameConfig.getEnderpearlDamage()).getItem();
        slots[13] = CommonItems.GUI_BACK_ITEM.getItem();
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
        GameConfig gameConfig = API.getAPI().getGameManager().getGameConfig();
        switch (slot) {
            case 0: {
                if (clickType.equals(ClickType.LEFT)) {
                    gameConfig.setEnderpearlDamage(gameConfig.getEnderpearlDamage() + 1);
                } else if (clickType.equals(ClickType.RIGHT)) {
                    gameConfig.setEnderpearlDamage(gameConfig.getEnderpearlDamage() + 1);
                }
                if (gameConfig.getEnderpearlDamage() >= 10) {
                    gameConfig.setEnderpearlDamage(0);
                } else if (gameConfig.getEnderpearlDamage() <= -1) {
                    gameConfig.setEnderpearlDamage(10);
                }
                gameConfig.getGameManager().getApi().openInventory(player, this.getClass());
                break;
            }
            case 13: {
                API.getAPI().openInventory(player, ConfigOptionsGUI.class);
            }
        }
    }

    @Override
    public int getRows() {
        return 2;
    }
}
