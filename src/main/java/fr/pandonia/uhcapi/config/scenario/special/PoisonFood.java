package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PoisonFood
        extends ScenarioManager
        implements Listener {
    @EventHandler
    private void onConsume(PlayerItemConsumeEvent event) {
        Random random;
        int next;
        if (this.scenario.isEnabled() && (next = (random = new Random()).nextInt(100)) <= this.scenario.getValue()) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
        }
    }

    @Override
    public void configure() {
        this.scenario = Scenario.POISONFOOD;
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
