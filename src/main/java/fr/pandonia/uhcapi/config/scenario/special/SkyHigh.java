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
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class SkyHigh
        extends ScenarioManager
        implements Listener {
    @EventHandler
    private void onPlaceBlock(BlockPlaceEvent event) {
        ItemStack itemStack;
        if (this.scenario.isEnabled() && event.getBlock().getType() == Material.DIRT && (itemStack = event.getPlayer().getItemInHand()) != null && itemStack.getType() != Material.AIR && itemStack.getType().equals(Material.DIRT)) {
            event.getPlayer().setItemInHand(new ItemCreator(Material.DIRT).setAmount(3).getItem());
        }
    }

    @Override
    public void configure() {
        this.scenario = Scenario.SKYHIGH;
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
