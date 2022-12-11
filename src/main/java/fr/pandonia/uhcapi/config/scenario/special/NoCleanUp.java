package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import fr.pandonia.uhcapi.utils.Math2;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class NoCleanUp
        extends ScenarioManager
        implements Listener {
    @EventHandler
    private void onDeath(PlayerDeathEvent event) {
        Player killer;
        if (this.scenario.isEnabled() && (killer = event.getEntity().getKiller()) != null) {
            killer.setHealth(Math2.fit(0.0, killer.getHealth() + (double)(this.scenario.getValue() * 2), killer.getMaxHealth()));
        }
    }

    @Override
    public void configure() {
        this.scenario = Scenario.NOCLEANUP;
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
