package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class FastSmelting
        extends ScenarioManager
        implements Listener {
    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        this.startUpdate((Furnace)((Object)event.getBlock().getState()), 5);
    }

    private void startUpdate(final Furnace block, final int speed) {
        new BukkitRunnable(){

            public void run() {
                if (block.getCookTime() > 0 || block.getBurnTime() > 0) {
                    block.setCookTime((short)(block.getCookTime() + speed));
                    block.update();
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(API.getAPI(), 1L, 1L);
    }

    @Override
    public void configure() {
        this.scenario = Scenario.FASTSMELTING;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, API.getAPI());
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
