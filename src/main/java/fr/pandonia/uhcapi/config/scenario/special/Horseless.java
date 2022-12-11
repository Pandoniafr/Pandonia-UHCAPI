package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityMountEvent;

public class Horseless
        extends ScenarioManager
        implements Listener {
    @EventHandler
    private void onDress(EntityMountEvent event) {
        if (this.scenario.isEnabled() && event.getMount().getType() == EntityType.HORSE) {
            event.setCancelled(true);
        }
    }

    @Override
    public void configure() {
        this.scenario = Scenario.HORSELESS;
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
    public void init() {
    }

    @Override
    public void onStart() {
    }
}
