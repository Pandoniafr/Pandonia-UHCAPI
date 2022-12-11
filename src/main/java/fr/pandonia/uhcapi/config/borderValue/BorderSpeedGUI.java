package fr.pandonia.uhcapi.config.borderValue;

import fr.pandonia.uhcapi.config.GameConfig;
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

public class BorderSpeedGUI
        implements CustomInventory {
    private final GameManager gameManager;
    private final GameConfig gameConfig;

    public BorderSpeedGUI(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameConfig = gameManager.getGameConfig();
    }

    @Override
    public String getName() {
        return "Vitesse de la bordure";
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack[] slots = new ItemStack[this.getSlots()];
        slots[0] = new ItemCreator(Material.BANNER).setDurability(1).setName("§c-10").getItem();
        slots[1] = new ItemCreator(Material.BANNER).setDurability(14).setName("§c-5").getItem();
        slots[2] = new ItemCreator(Material.BANNER).setDurability(11).setName("§c-1").getItem();
        slots[4] = new ItemCreator(Material.WATCH).setName("§6Vitesse de la bordure §f- " + this.gameConfig.getBorderBlocksPerSecond() + " bloc(s)/s").getItem();
        slots[6] = new ItemCreator(Material.BANNER).setDurability(12).setName("§a+1").getItem();
        slots[7] = new ItemCreator(Material.BANNER).setDurability(10).setName("§a+5").getItem();
        slots[8] = new ItemCreator(Material.BANNER).setDurability(2).setName("§a+10").getItem();
        slots[13] = new ItemCreator(Material.ARROW).setName("§fRevenir en arrière").getItem();
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
        switch (clickedItem.getType()) {
            case BANNER: {
                if (clickedItem.getDurability() == 1) {
                    this.gameConfig.setBorderBlocksPerSecond(this.gameConfig.getBorderBlocksPerSecond() - 10);
                } else if (clickedItem.getDurability() == 14) {
                    this.gameConfig.setBorderBlocksPerSecond(this.gameConfig.getBorderBlocksPerSecond() - 5);
                } else if (clickedItem.getDurability() == 11) {
                    this.gameConfig.setBorderBlocksPerSecond(this.gameConfig.getBorderBlocksPerSecond() - 1);
                } else if (clickedItem.getDurability() == 12) {
                    this.gameConfig.setBorderBlocksPerSecond(this.gameConfig.getBorderBlocksPerSecond() + 1);
                } else if (clickedItem.getDurability() == 10) {
                    this.gameConfig.setBorderBlocksPerSecond(this.gameConfig.getBorderBlocksPerSecond() + 5);
                } else if (clickedItem.getDurability() == 2) {
                    this.gameConfig.setBorderBlocksPerSecond(this.gameConfig.getBorderBlocksPerSecond() + 10);
                }
                if (this.gameConfig.getBorderBlocksPerSecond() > 15) {
                    this.gameConfig.setBorderBlocksPerSecond(15);
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 10.0f, 1.0f);
                }
                if (this.gameConfig.getBorderBlocksPerSecond() < 1) {
                    this.gameConfig.setBorderBlocksPerSecond(1);
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
