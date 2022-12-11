package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class WebCage
        extends ScenarioManager
        implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        for (Location block : this.getSphere(event.getEntity().getLocation(), 5, true)) {
            Block b = block.getBlock();
            if (b.getType() != Material.AIR && b.getType() != Material.LONG_GRASS) continue;
            b.setType(Material.WEB);
        }
    }

    private List<Location> getSphere(Location centerBlock, int radius, boolean hollow) {
        ArrayList<Location> circleBlocks = new ArrayList<Location>();
        int bX = centerBlock.getBlockX();
        int bY = centerBlock.getBlockY();
        int bZ = centerBlock.getBlockZ();
        for (int x = bX - radius; x <= bX + radius; ++x) {
            for (int y = bY - radius; y <= bY + radius; ++y) {
                for (int z = bZ - radius; z <= bZ + radius; ++z) {
                    double distance = (bX - x) * (bX - x) + (bZ - z) * (bZ - z) + (bY - y) * (bY - y);
                    if (!(distance < (double)(radius * radius)) || hollow && !(distance >= (double)((radius - 1) * (radius - 1)))) continue;
                    Location block = new Location(centerBlock.getWorld(), x, y, z);
                    circleBlocks.add(block);
                }
            }
        }
        return circleBlocks;
    }

    @Override
    public void configure() {
        this.scenario = Scenario.WEBCAGE;
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
