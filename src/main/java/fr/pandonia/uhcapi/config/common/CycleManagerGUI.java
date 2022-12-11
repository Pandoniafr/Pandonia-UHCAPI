package fr.pandonia.uhcapi.config.common;

import fr.pandonia.uhcapi.config.ConfigOptionsGUI;
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

public class CycleManagerGUI
        implements CustomInventory {
    private GameManager gameManager;
    private GameConfig gameConfig;

    public CycleManagerGUI(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameConfig = gameManager.getGameConfig();
    }

    @Override
    public String getName() {
        return "Durée cycle jour/nuit";
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack[] slots = new ItemStack[this.getSlots()];
        slots[0] = new ItemCreator(Material.BANNER).setName("§c-5").setDurability(1).getItem();
        slots[1] = new ItemCreator(Material.BANNER).setName("§c-2").setDurability(14).getItem();
        slots[2] = new ItemCreator(Material.BANNER).setName("§c-1").setDurability(11).getItem();
        slots[4] = OpenVar.CYCLE_DURATION.getItemCycle();
        slots[6] = new ItemCreator(Material.BANNER).setName("§a+1").setDurability(12).getItem();
        slots[7] = new ItemCreator(Material.BANNER).setName("§a+2").setDurability(10).getItem();
        slots[8] = new ItemCreator(Material.BANNER).setName("§a+5").setDurability(2).getItem();
        slots[13] = CommonItems.GUI_BACK_ITEM.getItem();
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
        switch (clickedItem.getType()) {
            case BANNER: {
                switch (clickedItem.getDurability()) {
                    case 1: {
                        this.gameConfig.setDayNightDuration(this.gameConfig.getDayNightDuration() - 300L);
                        break;
                    }
                    case 14: {
                        this.gameConfig.setDayNightDuration(this.gameConfig.getDayNightDuration() - 120L);
                        break;
                    }
                    case 11: {
                        this.gameConfig.setDayNightDuration(this.gameConfig.getDayNightDuration() - 60L);
                        break;
                    }
                    case 12: {
                        this.gameConfig.setDayNightDuration(this.gameConfig.getDayNightDuration() + 60L);
                        break;
                    }
                    case 10: {
                        this.gameConfig.setDayNightDuration(this.gameConfig.getDayNightDuration() + 120L);
                        break;
                    }
                    case 2: {
                        this.gameConfig.setDayNightDuration(this.gameConfig.getDayNightDuration() + 300L);
                    }
                }
                break;
            }
            case ARROW: {
                this.gameManager.getApi().openInventory(player, ConfigOptionsGUI.class);
                return;
            }
        }
        if (this.gameConfig.getDayNightDuration() < 120L) {
            this.gameConfig.setDayNightDuration(120L);
            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 10.0f, 1.0f);
        } else if (this.gameConfig.getDayNightDuration() > 4800L) {
            this.gameConfig.setDayNightDuration(4800L);
            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 10.0f, 1.0f);
        }
        this.gameManager.getApi().openInventory(player, this.getClass());
    }

    @Override
    public int getRows() {
        return 2;
    }
}
