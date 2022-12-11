package fr.pandonia.uhcapi.config.intValue;

import fr.pandonia.uhcapi.config.ConfigOptionsGUI;
import fr.pandonia.uhcapi.config.GameConfig;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.utils.CommonString;
import fr.pandonia.uhcapi.utils.CustomInventory;
import fr.pandonia.uhcapi.utils.ItemCreator;
import java.util.function.Supplier;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DeconnexionTimeGUI
        implements CustomInventory {
    private final GameManager gameManager;
    private final GameConfig gameConfig;

    public DeconnexionTimeGUI(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameConfig = gameManager.getGameConfig();
    }

    @Override
    public String getName() {
        return "Temps avant mort de déconnexion";
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack[] slots = new ItemStack[this.getSlots()];
        slots[0] = new ItemCreator(Material.BANNER).setName("§c-10").setDurability(1).getItem();
        slots[1] = new ItemCreator(Material.BANNER).setName("§c-5").setDurability(14).getItem();
        slots[2] = new ItemCreator(Material.BANNER).setName("§c-1").setDurability(11).getItem();
        slots[4] = new ItemCreator(Material.COMPASS).setName("§8┃ §fTemps avant mort de §6déconnexion").addLore("").addLore("  §8┃ §fVous permet de configurer").addLore("  §8┃ §fle temps necéssaire pour").addLore("  §8┃ §fmourir de déconnexion.").addLore("").addLore(" §8» §fConfiguration: §c" + this.gameManager.getGameConfig().getDisconnectMinute() + " minute(s)").addLore("").addLore(CommonString.CLICK_HERE_TO_MODIFY.getMessage()).addLore("").getItem();
        slots[6] = new ItemCreator(Material.BANNER).setName("§a+1").setDurability(12).getItem();
        slots[7] = new ItemCreator(Material.BANNER).setName("§a+5").setDurability(10).getItem();
        slots[8] = new ItemCreator(Material.BANNER).setName("§a+10").setDurability(2).getItem();
        slots[13] = new ItemCreator(Material.ARROW).setName("§fRevenir en arrière").getItem();
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
        switch (clickedItem.getType()) {
            case BANNER: {
                if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§c-10")) {
                    this.gameConfig.setDisconnectMinute(this.gameConfig.getDisconnectMinute() - 10);
                } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§c-5")) {
                    this.gameConfig.setDisconnectMinute(this.gameConfig.getDisconnectMinute() - 5);
                } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§c-1")) {
                    this.gameConfig.setDisconnectMinute(this.gameConfig.getDisconnectMinute() - 1);
                } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§a+10")) {
                    this.gameConfig.setDisconnectMinute(this.gameConfig.getDisconnectMinute() + 10);
                } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§a+5")) {
                    this.gameConfig.setDisconnectMinute(this.gameConfig.getDisconnectMinute() + 5);
                } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§a+1")) {
                    this.gameConfig.setDisconnectMinute(this.gameConfig.getDisconnectMinute() + 1);
                }
                if (this.gameConfig.getDisconnectMinute() < 1) {
                    this.gameConfig.setDisconnectMinute(1);
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 10.0f, 1.0f);
                } else if (this.gameConfig.getDisconnectMinute() > 60) {
                    this.gameConfig.setDisconnectMinute(60);
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 10.0f, 1.0f);
                }
                this.gameManager.getApi().openInventory(player, this.getClass());
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
