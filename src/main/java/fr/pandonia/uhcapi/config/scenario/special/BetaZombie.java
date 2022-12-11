package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import fr.pandonia.uhcapi.utils.ItemCreator;
import fr.pandonia.uhcapi.utils.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class BetaZombie
        extends ScenarioManager
        implements Listener {
    @EventHandler
    private void EntityDeathEvent(EntityDeathEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Zombie) {
            event.getDrops().add(new ItemCreator(Material.FEATHER).setAmount(RandomUtils.getRandomInt(1, 4)).getItem());
        }
    }

    @Override
    public void configure() {
        this.scenario = Scenario.BETAZOMBIE;
    }

    @Override
    public void onStart() {
        Bukkit.getServer().getPluginManager().registerEvents(this, API.getAPI());
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }
}
