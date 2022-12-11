package fr.pandonia.uhcapi.config.scenario;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ScenarioTimeGUI
        implements Listener {
    public final API plugin;
    public final Player player;
    public final Scenario scenario;
    public final int time;

    public ScenarioTimeGUI() {
        this(null, null, null, 0);
    }

    public ScenarioTimeGUI(API plugin, Player player, Scenario scenario, int time) {
        this.plugin = plugin;
        this.player = player;
        this.scenario = scenario;
        this.time = time;
        if (player != null) {
            player.openInventory(this.getInventory());
        }
    }

    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(null, 18, "Scénario " + this.scenario.getName());
        inventory.setItem(0, new ItemCreator(Material.BANNER).setDurability(14).setName("§c-50").getItem());
        inventory.setItem(1, new ItemCreator(Material.BANNER).setDurability(11).setName("§c-1").getItem());
        inventory.setItem(4, this.scenario.getItem());
        inventory.setItem(7, new ItemCreator(Material.BANNER).setDurability(12).setName("§a+1").getItem());
        inventory.setItem(8, new ItemCreator(Material.BANNER).setDurability(10).setName("§a+50").getItem());
        inventory.setItem(13, new ItemCreator(Material.ARROW).setName("§fRevenir en arrière").getItem());
        this.player.openInventory(inventory);
        return inventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        Player player = (Player)((Object)event.getWhoClicked());
        ItemStack itemStack = event.getCurrentItem();
        InventoryAction action = event.getAction();
        if (itemStack == null || player == null || action == null || inv == null) {
            return;
        }
        for (Scenario scenario : Scenario.values()) {
            if (!inv.getName().contains(scenario.getName())) continue;
            event.setCancelled(true);
            switch (itemStack.getType()) {
                case BANNER: {
                    if (itemStack.getDurability() == 11) {
                        if (scenario.getValue() > scenario.getMin()) {
                            scenario.setValue(scenario.getValue() - 1);
                        } else {
                            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 5.0f, 1.0f);
                        }
                    } else if (itemStack.getDurability() == 12) {
                        if (scenario.getValue() < scenario.getMax()) {
                            scenario.setValue(scenario.getValue() + 1);
                        } else {
                            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 5.0f, 1.0f);
                        }
                    } else if (itemStack.getDurability() == 14) {
                        if (scenario.getValue() > scenario.getMin()) {
                            scenario.setValue(scenario.getValue() - 50);
                        } else {
                            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 5.0f, 1.0f);
                        }
                    } else if (itemStack.getDurability() == 10) {
                        if (scenario.getValue() < scenario.getMax()) {
                            scenario.setValue(scenario.getValue() + 50);
                        } else {
                            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 5.0f, 1.0f);
                        }
                    }
                    if (scenario.getValue() > scenario.getMax()) {
                        scenario.setValue(scenario.getMax());
                    } else if (scenario.getValue() < scenario.getMin()) {
                        scenario.setValue(scenario.getMin());
                    }
                    inv.setItem(4, scenario.getItem());
                    break;
                }
                case ARROW: {
                    API.getAPI().getCommon().getScenariosGUI().openInventory(player, scenario.getPage());
                }
            }
            break;
        }
    }

    public API getPlugin() {
        return this.plugin;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Scenario getScenario() {
        return this.scenario;
    }

    public int getTime() {
        return this.time;
    }
}
