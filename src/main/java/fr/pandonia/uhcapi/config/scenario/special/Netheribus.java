package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import fr.pandonia.uhcapi.config.scenario.special.runnable.NetheribusRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class Netheribus
        extends ScenarioManager
        implements Listener {
    @Override
    public void configure() {
        this.scenario = Scenario.NETHERIBUS;
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
        NetheribusRunnable netheribusRunnable = new NetheribusRunnable(API.getAPI().getGameManager());
        netheribusRunnable.runTaskTimer(API.getAPI(), 0L, 20L);
        Bukkit.broadcastMessage("§eLe scénario §6Netheribus §eest désormais actif !");
        Bukkit.broadcastMessage("§eVeuillez vous rendre dans le §c§lNether §e!");
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.playSound(players.getLocation(), Sound.BLAZE_DEATH, 3.0f, 0.0f);
        }
    }

    @Override
    public void onStart() {
        API.getAPI().getGameManager().getGameConfig().setNether(true);
    }
}
