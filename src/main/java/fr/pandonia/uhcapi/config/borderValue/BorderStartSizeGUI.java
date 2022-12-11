package fr.pandonia.uhcapi.config.borderValue;

import fr.pandonia.uhcapi.config.GameConfig;
import fr.pandonia.uhcapi.config.value.CommonItems;
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

public class BorderStartSizeGUI
        implements CustomInventory {
    private final GameManager gameManager;
    private final GameConfig gameConfig;

    public BorderStartSizeGUI(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameConfig = gameManager.getGameConfig();
    }

    @Override
    public String getName() {
        return "Bordure initiale";
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack[] slots = new ItemStack[this.getSlots()];
        int value = this.gameConfig.getBorderStartSize();
        slots[0] = new ItemCreator(Material.BANNER).setDurability(1).setName("§c-500").getItem();
        slots[1] = new ItemCreator(Material.BANNER).setDurability(14).setName("§c-100").getItem();
        slots[2] = new ItemCreator(Material.BANNER).setDurability(11).setName("§c-50").getItem();
        slots[4] = new ItemCreator(Material.STAINED_GLASS).setDurability(3).setName("§6Bordure initiale §f- " + value + "x" + value).getItem();
        slots[6] = new ItemCreator(Material.BANNER).setDurability(12).setName("§a+50").getItem();
        slots[7] = new ItemCreator(Material.BANNER).setDurability(10).setName("§a+100").getItem();
        slots[8] = new ItemCreator(Material.BANNER).setDurability(2).setName("§a+500").getItem();
        slots[13] = CommonItems.GUI_BACK_ITEM.getItem();
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
        switch (clickedItem.getType()) {
            case BANNER: {
                if (clickedItem.getDurability() == 1) {
                    this.gameConfig.setBorderStartSize(this.gameConfig.getBorderStartSize() - 500);
                } else if (clickedItem.getDurability() == 14) {
                    this.gameConfig.setBorderStartSize(this.gameConfig.getBorderStartSize() - 100);
                } else if (clickedItem.getDurability() == 11) {
                    this.gameConfig.setBorderStartSize(this.gameConfig.getBorderStartSize() - 50);
                } else if (clickedItem.getDurability() == 12) {
                    this.gameConfig.setBorderStartSize(this.gameConfig.getBorderStartSize() + 50);
                } else if (clickedItem.getDurability() == 10) {
                    this.gameConfig.setBorderStartSize(this.gameConfig.getBorderStartSize() + 100);
                } else if (clickedItem.getDurability() == 2) {
                    this.gameConfig.setBorderStartSize(this.gameConfig.getBorderStartSize() + 500);
                }
                if (this.gameConfig.getBorderStartSize() > 2500) {
                    this.gameConfig.setBorderStartSize(2500);
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 10.0f, 1.0f);
                }
                if (this.gameConfig.getBorderStartSize() < 100) {
                    this.gameConfig.setBorderStartSize(100);
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 10.0f, 1.0f);
                }
                this.gameManager.getApi().openInventory(player, this.getClass());
                break;
            }
            case ARROW: {
                this.gameManager.getApi().openInventory(player, BorderManagerGUI.class);
            }
        }
    }

    @Override
    public int getRows() {
        return 2;
    }
}
