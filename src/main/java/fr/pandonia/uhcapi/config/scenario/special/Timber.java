package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Timber
        extends ScenarioManager
        implements Listener {
    private World world;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (this.scenario.isEnabled()) {
            byte data;
            Block block = event.getBlock();
            if (!block.getWorld().equals(this.world)) {
                return;
            }
            int destroy = 1000;
            if (block.getType() == Material.LOG) {
                this.removeTreeAt(block, destroy);
            } else if (block.getType() == Material.LOG_2 && (data = block.getData()) == 0) {
                this.removeTreeAt(block, destroy);
            }
        }
    }

    public int removeTreeAt(Block block, final int blockMaxAmount) {
        Material mat = block.getType();
        final Location loc = block.getLocation();
        if (mat == Material.LOG || mat == Material.LOG_2) {
            block.getWorld().playSound(block.getLocation(), Sound.DIG_WOOD, 1.0f, 1.0f);
            block.breakNaturally();
            if (blockMaxAmount <= 0) {
                return 0;
            }
            new BukkitRunnable(){

                public void run() {
                    int i = blockMaxAmount;
                    for (int x = -1; x <= 1; ++x) {
                        for (int y = -1; y <= 1; ++y) {
                            for (int z = -1; z <= 1; ++z) {
                                if (x == 0 && y == 0 && z == 0) continue;
                                i = Timber.this.removeTreeAt(loc.clone().add(x, y, z).getBlock(), i);
                            }
                        }
                    }
                }
            }.runTaskLater(API.getAPI(), 2L);
            return blockMaxAmount - 1;
        }
        return blockMaxAmount;
    }

    @Override
    public void configure() {
        this.scenario = Scenario.TIMBER;
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
        this.world = API.getAPI().getGameManager().getWorldPopulator().getGameWorld();
    }
}
