package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class CutClean
        extends ScenarioManager
        implements Listener {
    @EventHandler
    public void onHunt(EntityDeathEvent event) {
        if (this.scenario.isEnabled()) {
            EntityType type = event.getEntity().getType();
            switch (type) {
                case COW: {
                    this.replaceDrop(event, new ItemStack(Material.COOKED_BEEF, 2), new ItemStack(Material.LEATHER));
                    break;
                }
                case PIG: {
                    this.replaceDrop(event, new ItemStack(Material.COOKED_BEEF, 2));
                    break;
                }
                case CHICKEN: {
                    this.replaceDrop(event, new ItemStack(Material.COOKED_CHICKEN, 2), new ItemStack(Material.FEATHER));
                    break;
                }
                case RABBIT: {
                    this.replaceDrop(event, new ItemStack(Material.COOKED_RABBIT, 2));
                    break;
                }
                case SHEEP: {
                    this.replaceDrop(event, new ItemStack(Material.COOKED_MUTTON, 2));
                }
            }
        }
    }

    public void replaceDrop(EntityDeathEvent event, ItemStack ... items) {
        event.getDrops().clear();
        for (ItemStack item : items) {
            event.getDrops().add(item);
        }
    }

    @Override
    public void configure() {
        this.scenario = Scenario.CUTCLEAN;
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
