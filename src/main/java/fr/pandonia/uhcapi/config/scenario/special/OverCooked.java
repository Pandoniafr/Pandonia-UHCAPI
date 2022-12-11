package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class OverCooked
        extends ScenarioManager
        implements Listener {
    @EventHandler
    public void onCook(FurnaceSmeltEvent event) {
        ItemStack result = event.getResult();
        Furnace block = (Furnace)((Object)event.getBlock().getState());
        this.createExplosion(event.getBlock());
        for (ItemStack content : block.getInventory().getContents()) {
            content.setType(result.getType());
        }
    }

    private void createExplosion(final Block block) {
        new BukkitRunnable(){

            public void run() {
                block.getLocation().getWorld().createExplosion(block.getLocation(), 3.0f);
            }
        }.runTaskLater(API.getAPI(), 1L);
    }

    @Override
    public void configure() {
        this.scenario = Scenario.OVERCOOKED;
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
