package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class NoEnchant
        extends ScenarioManager
        implements Listener {
    @EventHandler
    public void PrepareItemCraftEvent(PrepareItemCraftEvent event) {
        if (event.getRecipe().getResult() == null) {
            return;
        }
        Material material = event.getRecipe().getResult().getType();
        if (material == Material.ENCHANTMENT_TABLE) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    private void EnchantItemEvent(EnchantItemEvent event) {
        event.setCancelled(true);
        event.getEnchanter().sendMessage("§cLe scénario §6NoEnchant §cest activé.");
        event.getEnchanter().closeInventory();
    }

    @Override
    public void configure() {
        this.scenario = Scenario.NOENCAHNT;
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
