package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.common.rules.Rules;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BowSwap
        extends ScenarioManager
        implements Listener {
    @EventHandler
    private void onDamage(EntityDamageByEntityEvent event) {
        if (Rules.pvp.isActive() && this.scenario.isEnabled() && event.getEntity() instanceof Player && event.getDamager() instanceof Arrow && ((Arrow)((Object)event.getDamager())).getShooter() instanceof Player) {
            int next;
            Player shooter = (Player)((Object)((Arrow)((Object)event.getDamager())).getShooter());
            Player player = (Player)event.getEntity();
            if (shooter.getUniqueId() != player.getUniqueId() && (next = ThreadLocalRandom.current().nextInt(100)) <= this.scenario.getValue()) {
                Location l1 = shooter.getLocation();
                Location l2 = player.getLocation();
                player.teleport(l1);
                shooter.teleport(l2);
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 5.0f, 0.0f);
                shooter.playSound(shooter.getLocation(), Sound.ENDERMAN_TELEPORT, 5.0f, 0.0f);
            }
        }
    }

    @Override
    public void configure() {
        this.scenario = Scenario.BOWSWAP;
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
