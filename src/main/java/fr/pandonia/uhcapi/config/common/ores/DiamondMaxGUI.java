package fr.pandonia.uhcapi.config.common.ores;

import fr.pandonia.uhcapi.config.GameConfig;
import fr.pandonia.uhcapi.config.value.CommonItems;
import fr.pandonia.uhcapi.config.value.OpenVar;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.utils.CustomInventory;
import fr.pandonia.uhcapi.utils.ItemCreator;
import java.util.function.Supplier;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DiamondMaxGUI
        implements CustomInventory {
    private final GameManager gameManager;
    private final GameConfig gameConfig;

    public DiamondMaxGUI(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameConfig = gameManager.getGameConfig();
    }

    @Override
    public String getName() {
        return "Diamants";
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack[] slots = new ItemStack[this.getSlots()];
        slots[0] = new ItemCreator(Material.BANNER).setDurability(14).setName("§c-5").getItem();
        slots[1] = new ItemCreator(Material.BANNER).setDurability(11).setName("§c-1").getItem();
        slots[4] = OpenVar.DIAMOND_MAX.getItem();
        slots[7] = new ItemCreator(Material.BANNER).setDurability(12).setName("§a+1").getItem();
        slots[8] = new ItemCreator(Material.BANNER).setDurability(10).setName("§a+5").getItem();
        slots[13] = CommonItems.GUI_BACK_ITEM.getItem();
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
        switch (clickedItem.getType()) {
            case BANNER: {
                if (clickedItem.getDurability() == 14) {
                    this.gameConfig.setDiamondMax(this.gameConfig.getDiamondMax() - 5);
                } else if (clickedItem.getDurability() == 11) {
                    this.gameConfig.setDiamondMax(this.gameConfig.getDiamondMax() - 1);
                } else if (clickedItem.getDurability() == 12) {
                    this.gameConfig.setDiamondMax(this.gameConfig.getDiamondMax() + 1);
                } else if (clickedItem.getDurability() == 10) {
                    this.gameConfig.setDiamondMax(this.gameConfig.getDiamondMax() + 5);
                }
                if (this.gameConfig.getDiamondMax() > 100) {
                    this.gameConfig.setDiamondMax(100);
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 10.0f, 1.0f);
                }
                if (this.gameConfig.getDiamondMax() < 0) {
                    this.gameConfig.setDiamondMax(0);
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 10.0f, 1.0f);
                }
                this.gameManager.getApi().openInventory(player, this.getClass());
                break;
            }
            case ARROW: {
                this.gameManager.getApi().openInventory(player, OresLimitGUI.class);
            }
        }
    }

    @Override
    public int getRows() {
        return 2;
    }
}
