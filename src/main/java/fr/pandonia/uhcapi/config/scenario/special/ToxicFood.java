package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import fr.pandonia.uhcapi.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class ToxicFood
        extends ScenarioManager
        implements Listener {
    @EventHandler
    private void onFood(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        player.damage(this.scenario.getValue() * 2);
        Title.sendActionBar(player, "§cLe scénario §6Toxic Food §cest activé, vous avez subit des dégâts !");
    }

    @Override
    public void configure() {
        this.scenario = Scenario.TOXICFOOD;
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
