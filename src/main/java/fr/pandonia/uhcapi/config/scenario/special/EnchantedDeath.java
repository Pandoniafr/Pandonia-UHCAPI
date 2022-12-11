package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import fr.pandonia.uhcapi.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class EnchantedDeath
        extends ScenarioManager
        implements Listener {
    @EventHandler
    private void onCraft(PrepareItemCraftEvent event) {
        CraftingInventory craftingInventory;
        if (this.scenario.isEnabled() && (craftingInventory = event.getInventory()).getResult().getType() == Material.ENCHANTMENT_TABLE) {
            craftingInventory.setResult(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    private void onDeath(PlayerDeathEvent event) {
        if (this.scenario.isEnabled()) {
            event.getDrops().add(new ItemCreator(Material.ENCHANTMENT_TABLE).getItem());
        }
    }

    @Override
    public void configure() {
        this.scenario = Scenario.ENCHANTEDDEATH;
    }

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, API.getAPI());
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public void onStart() {
    }
}
