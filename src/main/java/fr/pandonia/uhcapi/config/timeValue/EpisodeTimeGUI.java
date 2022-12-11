package fr.pandonia.uhcapi.config.timeValue;

import fr.pandonia.uhcapi.config.ConfigOptionsGUI;
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

public class EpisodeTimeGUI
        implements CustomInventory {
    private final GameManager gameManager;
    private final GameConfig gameConfig;

    public EpisodeTimeGUI(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameConfig = gameManager.getGameConfig();
    }

    @Override
    public String getName() {
        return "Episode - Temps";
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack[] slots = new ItemStack[this.getSlots()];
        slots[0] = new ItemCreator(Material.BANNER).setName("§c-60").setDurability(1).getItem();
        slots[1] = new ItemCreator(Material.BANNER).setName("§c-30").setDurability(14).getItem();
        slots[2] = new ItemCreator(Material.BANNER).setName("§c-10").setDurability(11).getItem();
        slots[4] = OpenVar.EPISODE_TIME.getItem();
        slots[6] = new ItemCreator(Material.BANNER).setName("§a+10").setDurability(12).getItem();
        slots[7] = new ItemCreator(Material.BANNER).setName("§a+30").setDurability(10).getItem();
        slots[8] = new ItemCreator(Material.BANNER).setName("§a+60").setDurability(2).getItem();
        slots[13] = new ItemCreator(Material.ARROW).setName("§fRevenir en arrière").getItem();
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
        switch (clickedItem.getType()) {
            case BANNER: {
                if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§c-10")) {
                    this.gameConfig.setEpisodeTime(this.gameConfig.getEpisodeTime() - 10);
                } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§c-30")) {
                    this.gameConfig.setEpisodeTime(this.gameConfig.getEpisodeTime() - 30);
                } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§c-60")) {
                    this.gameConfig.setEpisodeTime(this.gameConfig.getEpisodeTime() - 60);
                } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§a+10")) {
                    this.gameConfig.setEpisodeTime(this.gameConfig.getEpisodeTime() + 10);
                } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§a+30")) {
                    this.gameConfig.setEpisodeTime(this.gameConfig.getEpisodeTime() + 30);
                } else if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§a+60")) {
                    this.gameConfig.setEpisodeTime(this.gameConfig.getEpisodeTime() + 60);
                }
                if (this.gameConfig.getEpisodeTime() < 60) {
                    this.gameConfig.setEpisodeTime(60);
                    player.playSound(player.getLocation(), Sound.VILLAGER_NO, 10.0f, 1.0f);
                } else if (this.gameConfig.getEpisodeTime() > 2400) {
                    this.gameConfig.setEpisodeTime(2400);
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
