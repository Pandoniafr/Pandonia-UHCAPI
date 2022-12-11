package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class SafeMiner
        extends ScenarioManager
        implements Listener {
    @Override
    public void configure() {
        this.scenario = Scenario.SAFEMINER;
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

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        Player player;
        Location playerLocation;
        if (event.getEntity() instanceof Player && (playerLocation = (player = (Player)event.getEntity()).getLocation().clone()).getY() <= (double)Scenario.SAFEMINER.getValue() && !(event.getDamager() instanceof Player)) {
            double damage = event.getDamage();
            event.setDamage(damage /= 2.0);
        }
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        Player player;
        Location playerLocation;
        if (event.getEntity() instanceof Player && (playerLocation = (player = (Player)event.getEntity()).getLocation().clone()).getY() <= (double)Scenario.SAFEMINER.getValue()) {
            EntityDamageEvent.DamageCause cause = event.getCause();
            switch (cause) {
                case FIRE:
                case FIRE_TICK:
                case LAVA: {
                    event.setCancelled(true);
                    break;
                }
                case FALL: {
                    double damage = event.getFinalDamage();
                    event.setDamage(damage /= 2.0);
                }
            }
        }
    }
}
