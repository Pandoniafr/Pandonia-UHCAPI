package fr.pandonia.uhcapi.config.intValue;

import fr.pandonia.uhcapi.config.ConfigMainGUI;
import fr.pandonia.uhcapi.config.GameConfig;
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

public class SlotsGUI
        implements CustomInventory {
    private final GameManager gameManager;
    private final GameConfig gameConfig;

    public SlotsGUI(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameConfig = gameManager.getGameConfig();
    }

    @Override
    public String getName() {
        return "Slots";
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack[] slots = new ItemStack[this.getSlots()];
        slots[0] = new ItemCreator(Material.BANNER).setName("§c-5").setDurability(14).getItem();
        slots[1] = new ItemCreator(Material.BANNER).setName("§c-1").setDurability(11).getItem();
        slots[4] = OpenVar.SLOTS.getItem();
        slots[7] = new ItemCreator(Material.BANNER).setName("§a+1").setDurability(12).getItem();
        slots[8] = new ItemCreator(Material.BANNER).setName("§a+5").setDurability(10).getItem();
        slots[13] = new ItemCreator(Material.ARROW).setName("§fRevenir en arrière").getItem();
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
        switch (clickedItem.getType()) {
            case BANNER: {
                if (clickedItem.getDurability() == 11) {
                    this.gameConfig.setGameSlot(this.gameConfig.getGameSlot() - 1);
                } else if (clickedItem.getDurability() == 14) {
                    this.gameConfig.setGameSlot(this.gameConfig.getGameSlot() - 5);
                } else if (clickedItem.getDurability() == 12) {
                    this.gameConfig.setGameSlot(this.gameConfig.getGameSlot() + 1);
                } else if (clickedItem.getDurability() == 10) {
                    this.gameConfig.setGameSlot(this.gameConfig.getGameSlot() + 5);
                }
                if (this.gameConfig.getGameSlot() > 60) {
                    this.gameConfig.setGameSlot(60);
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 10.0f, 1.0f);
                }
                if (this.gameConfig.getGameSlot() < 10) {
                    this.gameConfig.setGameSlot(10);
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 10.0f, 1.0f);
                }
                this.gameManager.getApi().openInventory(player, this.getClass());
                break;
            }
            case ARROW: {
                this.gameManager.getApi().openInventory(player, ConfigMainGUI.class);
            }
        }
    }

    @Override
    public int getRows() {
        return 2;
    }
}
