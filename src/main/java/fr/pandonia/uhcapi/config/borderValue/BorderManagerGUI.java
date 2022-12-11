package fr.pandonia.uhcapi.config.borderValue;

import fr.pandonia.uhcapi.config.ConfigMainGUI;
import fr.pandonia.uhcapi.config.GameConfig;
import fr.pandonia.uhcapi.config.value.CommonItems;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.utils.CommonString;
import fr.pandonia.uhcapi.utils.CustomInventory;
import fr.pandonia.uhcapi.utils.ItemCreator;
import java.util.function.Supplier;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BorderManagerGUI
        implements CustomInventory {
    private final GameManager gameManager;
    private final GameConfig gameConfig;

    public BorderManagerGUI(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameConfig = gameManager.getGameConfig();
    }

    @Override
    public String getName() {
        return "§f(§c!§f) §cBordure";
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        Integer[] glass;
        ItemStack[] slots = new ItemStack[this.getSlots()];
        Integer[] arrayOfInteger1 = glass = new Integer[]{0, 1, 7, 8, 9, 17, 18, 19, 25, 26};
        int i = arrayOfInteger1.length;
        for (int b = 0; b < i; b = (int)((byte)(b + 1))) {
            int j = arrayOfInteger1[b];
            slots[j] = new ItemCreator(Material.STAINED_GLASS_PANE).setDurability(8).getItem();
        }
        slots[12] = new ItemCreator(Material.STAINED_GLASS).setDurability(3).setName("§8┃ §fBordure initiale §8(§c" + this.gameConfig.getBorderStartSize() + "§8)").addLore("").addLore("  §8┃ §fCliquez ici pour définir la taille").addLore("  §8┃ §fde la bordure initiale de la partie.").addLore("").addLore(CommonString.CLICK_HERE_TO_MODIFY.getMessage()).addLore("").getItem();
        slots[14] = new ItemCreator(Material.STAINED_GLASS).setDurability(14).setName("§8┃ §fBordure finale §8(§c" + this.gameConfig.getBorderEndSize() + "§8)").addLore("").addLore("  §8┃ §fCliquez ici pour définir la taille").addLore("  §8┃ §fde la bordure finale de la partie.").addLore("").addLore(CommonString.CLICK_HERE_TO_MODIFY.getMessage()).addLore("").getItem();
        slots[13] = new ItemCreator(Material.WATCH).setName("§8┃ §fVitesse de la bordure §8(§c" + this.gameConfig.getBorderBlocksPerSecond() + " bloc(s)/s§8)").addLore("").addLore("  §8┃ §fCliquez ici pour définir la vitesse").addLore("  §8┃ §fde réduction de la bordure.").addLore("").addLore(CommonString.CLICK_HERE_TO_MODIFY.getMessage()).addLore("").getItem();
        slots[22] = CommonItems.GUI_BACK_ITEM.getItem();
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
        switch (clickedItem.getType()) {
            case STAINED_GLASS: {
                if (clickedItem.getDurability() == 3) {
                    this.gameManager.getApi().openInventory(player, BorderStartSizeGUI.class);
                    break;
                }
                if (clickedItem.getDurability() != 14) break;
                this.gameManager.getApi().openInventory(player, BorderEndSizeGUI.class);
                break;
            }
            case WATCH: {
                this.gameManager.getApi().openInventory(player, BorderSpeedGUI.class);
                break;
            }
            case ARROW: {
                this.gameManager.getApi().openInventory(player, ConfigMainGUI.class);
            }
        }
    }

    @Override
    public int getRows() {
        return 3;
    }
}
