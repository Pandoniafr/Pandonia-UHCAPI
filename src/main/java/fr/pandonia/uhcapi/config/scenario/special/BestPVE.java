package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.common.rules.Rules;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import fr.pandonia.uhcapi.config.scenario.special.runnable.BestPVERunnable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class BestPVE
        extends ScenarioManager
        implements Listener {
    public static List<UUID> playersBestPVE = new ArrayList<UUID>();

    private void initBestPVE() {
        playersBestPVE.clear();
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (!API.getAPI().getGameManager().getInGamePlayers().contains(players.getUniqueId())) continue;
            playersBestPVE.add(players.getUniqueId());
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    private void onDamage(EntityDamageEvent event) {
        Player player;
        if (!event.isCancelled() && event.getEntity() instanceof Player && !Rules.noDamage.isActive() && playersBestPVE.contains((player = (Player)event.getEntity()).getUniqueId())) {
            playersBestPVE.remove(player.getUniqueId());
            player.sendMessage("§cVous ne faites plus parti de la liste BestPVE !");
            player.playSound(player.getLocation(), Sound.VILLAGER_HIT, 5.0f, 0.0f);
        }
    }

    @EventHandler
    private void onDeath(PlayerDeathEvent event) {
        Player killer;
        Player victim = event.getEntity();
        if (victim.getKiller() instanceof Player && !playersBestPVE.contains((killer = victim.getKiller()).getUniqueId())) {
            playersBestPVE.add(killer.getUniqueId());
        }
    }

    @Override
    public void configure() {
        this.scenario = Scenario.BESTPVE;
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, API.getAPI());
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public void onStart() {
        this.initBestPVE();
        new BestPVERunnable(API.getAPI().getGameManager()).runTaskTimer(API.getAPI(), Scenario.BESTPVE.getValue() * 1200, Scenario.BESTPVE.getValue() * 1200);
    }
}
