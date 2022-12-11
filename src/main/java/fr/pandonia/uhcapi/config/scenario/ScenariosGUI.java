package fr.pandonia.uhcapi.config.scenario;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.ConfigMainGUI;
import fr.pandonia.uhcapi.config.value.CommonItems;
import fr.pandonia.uhcapi.utils.ItemCreator;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ScenariosGUI
        implements Listener {
    private static Map<Player, Integer> player_page = new HashMap<Player, Integer>();
    private Map<Integer, Scenario> scenarioHash = new HashMap<Integer, Scenario>();
    private final API main;

    public ScenariosGUI(API main) {
        this.main = main;
    }

    public Inventory openInventory(Player player, int page) {
        int[] glass;
        player_page.put(player, page);
        Inventory inventory = Bukkit.createInventory(null, 54, "§f(§c!§f) §cScéna' - Page " + page);
        for (int n : glass = new int[]{36, 37, 38, 39, 40, 41, 42, 43, 44}) {
            inventory.setItem(n, new ItemCreator(Material.STAINED_GLASS_PANE).setDurability(7).getItem());
        }
        int totalPage = 1;
        int size = Scenario.values().length;
        if (size > 36 && size <= 72) {
            totalPage = 2;
        }
        this.scenarioHash.clear();
        for (Scenario scenario : Scenario.values()) {
            if (scenario.getPage() != page) continue;
            this.scenarioHash.put(scenario.getSlot(), scenario);
        }
        for (Map.Entry entry : this.scenarioHash.entrySet()) {
            inventory.setItem((Integer)entry.getKey(), ((Scenario)((Object)entry.getValue())).getItem());
        }
        ItemStack itemStack = inventory.getItem(35);
        if (itemStack != null && itemStack.getType() != Material.AIR) {
            inventory.setItem(43, new ItemCreator(Material.ITEM_FRAME).setName("§8┃ §fPage §asuivante §f(" + (page + 1) + "§a/§f" + totalPage + ")").getItem());
        }
        if (page > 1) {
            inventory.setItem(37, new ItemCreator(Material.ITEM_FRAME).setName("§8┃ §fPage §cprécédente §f(" + (page - 1) + "§c/§f" + totalPage + ")").getItem());
        }
        inventory.setItem(49, CommonItems.GUI_BACK_ITEM.getItem());
        player.openInventory(inventory);
        return inventory;
    }

    @EventHandler
    private void onClick(InventoryClickEvent event) {
        Player player = (Player)((Object)event.getWhoClicked());
        ItemStack itemStack = event.getCurrentItem();
        Inventory inventory = event.getInventory();
        ClickType clickType = event.getClick();
        int totalPage = 1;
        int size = Scenario.values().length;
        if (size > 36 && size <= 72) {
            totalPage = 2;
        }
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return;
        }
        if (inventory.getName().contains("§f(§c!§f) §cScéna' - Page")) {
            event.setCancelled(true);
            int slot = event.getSlot();
            int page = player_page.get(player);
            if (this.scenarioHash.containsKey(slot)) {
                Scenario scenario = this.scenarioHash.get(slot);
                if (scenario.isConfigurable()) {
                    if (clickType.isRightClick()) {
                        new ScenarioTimeGUI(this.main, player, scenario, scenario.getValue());
                        return;
                    }
                    if (clickType.isLeftClick()) {
                        scenario.getScenarioManager().activeScenario();
                    }
                } else {
                    scenario.getScenarioManager().activeScenario();
                }
                this.openInventory(player, page);
            } else {
                switch (slot) {
                    case 43: {
                        if (page < totalPage) {
                            ++page;
                        }
                        this.openInventory(player, page);
                        break;
                    }
                    case 37: {
                        if (page > 1) {
                            --page;
                        }
                        this.openInventory(player, page);
                        break;
                    }
                    case 49: {
                        this.main.openInventory(player, ConfigMainGUI.class);
                    }
                }
            }
        }
    }
}
