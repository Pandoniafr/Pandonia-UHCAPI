package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import fr.pandonia.uhcapi.utils.ItemCreator;
import fr.pandonia.uhcapi.utils.Math2;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Sangsue
        extends ScenarioManager
        implements Listener {
    @EventHandler
    private void onDeath(PlayerDeathEvent event) {
        if (this.scenario.isEnabled()) {
            Player player = event.getEntity();
            Player killer = player.getKiller();
            this.heal(killer, 10.0);
            event.getDrops().add(new ItemCreator(Material.WOOD).setAmount(64).getItem());
            event.getDrops().add(new ItemCreator(Material.COBBLESTONE).setAmount(64).getItem());
        }
    }

    public void heal(Player player, double health) {
        player.setHealth(Math2.fit(0.0, player.getHealth() + health, player.getMaxHealth()));
    }

    @Override
    public void configure() {
        this.scenario = Scenario.SANGSUE;
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
