package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class StockUp
        extends ScenarioManager
        implements Listener {
    private int health;

    @EventHandler
    private void onDeath(PlayerDeathEvent event) {
        if (this.scenario.isEnabled()) {
            Player player = event.getEntity();
            if (API.getAPI().getGameManager().getInGamePlayers().contains(player.getUniqueId())) {
                this.health += 2;
                for (UUID uuid : API.getAPI().getGameManager().getInGamePlayers()) {
                    Player players = Bukkit.getPlayer(uuid);
                    if (players == null) continue;
                    players.setMaxHealth(players.getMaxHealth() + (double)this.health);
                }
            }
        }
    }

    @Override
    public void configure() {
        this.scenario = Scenario.STOCKUP;
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
        this.health = 0;
    }
}
