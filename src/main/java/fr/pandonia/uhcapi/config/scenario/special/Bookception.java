package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import fr.pandonia.uhcapi.utils.ItemCreator;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Bookception
        extends ScenarioManager
        implements Listener {
    @EventHandler(priority=EventPriority.HIGH)
    private void onDeath(PlayerDeathEvent event) {
        if (this.scenario.isEnabled()) {
            List<Enchantment> enchants = Arrays.asList(Enchantment.values());
            Enchantment enchantment = enchants.get(ThreadLocalRandom.current().nextInt(enchants.size()));
            int level = ThreadLocalRandom.current().nextInt(enchantment.getMaxLevel() + 1);
            if (level == 0) {
                level = 1;
            }
            event.getDrops().add(new ItemCreator(Material.ENCHANTED_BOOK).addStoredEnchantment(enchantment, level).getItem());
        }
    }

    @Override
    public void configure() {
        this.scenario = Scenario.BOOKCEPTION;
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
    }
}
