package fr.pandonia.uhcapi.config.common.ores;

import fr.pandonia.uhcapi.config.ConfigOptionsGUI;
import fr.pandonia.uhcapi.config.GameConfig;
import fr.pandonia.uhcapi.config.value.CommonItems;
import fr.pandonia.uhcapi.config.value.OpenVar;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.utils.CustomInventory;
import fr.pandonia.uhcapi.utils.ItemCreator;
import java.util.function.Supplier;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OresLimitGUI
        implements CustomInventory {
    private final GameManager gameManager;
    private final GameConfig gameConfig;

    public OresLimitGUI(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameConfig = gameManager.getGameConfig();
    }

    @Override
    public String getName() {
        return "Limite de minerais";
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack[] slots = new ItemStack[this.getSlots()];
        slots[3] = OpenVar.DIAMOND_MAX.getItem();
        slots[4] = new ItemCreator(Material.PAPER).setName("§cInformation").addLore("").addLore("  §8┃ §fSi la valeur est définie sur 0").addLore("  §8┃ §falors il n'y a pas de limite.").addLore("").getItem();
        slots[5] = OpenVar.GOLD_MAX.getItem();
        slots[13] = CommonItems.GUI_BACK_ITEM.getItem();
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
        switch (clickedItem.getType()) {
            case DIAMOND: {
                this.gameManager.getApi().openInventory(player, DiamondMaxGUI.class);
                break;
            }
            case GOLD_INGOT: {
                this.gameManager.getApi().openInventory(player, GoldMaxGUI.class);
                break;
            }
            case ARROW: {
                this.gameManager.getApi().openInventory(player, ConfigOptionsGUI.class);
            }
        }
    }

    @Override
    public int getRows() {
        return 2;
    }
}
