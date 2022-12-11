package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class Paranoia
        extends ScenarioManager
        implements Listener {
    @EventHandler
    private void onCraft(PrepareItemCraftEvent event) {
        HumanEntity humanEntity;
        ItemStack itemStack = event.getRecipe().getResult();
        if (itemStack.getType() != Material.AIR && itemStack.getType() == Material.ENCHANTMENT_TABLE && (humanEntity = event.getView().getPlayer()) instanceof Player) {
            Player player = (Player)((Object)humanEntity);
            Location location = player.getLocation();
            Bukkit.broadcastMessage("§a§l" + player.getName() + " §fa crafté une §ctable d'enchantement §faux coordonées suivantes : X: " + location.getBlockX() + ", Y: " + location.getBlockY() + ", Z: " + location.getBlockZ());
        }
    }

    @EventHandler
    private void onConsume(PlayerItemConsumeEvent event) {
        ItemStack itemStack = event.getItem();
        if (itemStack != null && itemStack.getType() == Material.GOLDEN_APPLE) {
            Location location = event.getPlayer().getLocation();
            Bukkit.broadcastMessage("§a§l" + event.getPlayer().getName() + " §fa consommé une §6pomme d'or §faux coordonées suivantes : X: " + location.getBlockX() + ", Y: " + location.getBlockY() + ", Z: " + location.getBlockZ());
        }
    }

    @EventHandler
    private void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (!event.isCancelled()) {
            Location location = event.getPlayer().getLocation();
            if (block.getType() == Material.DIAMOND_ORE) {
                Bukkit.broadcastMessage("§a§l" + event.getPlayer().getName() + " §fa cassé un §bminerais de diamant §faux coordonées suivantes : X: " + location.getBlockX() + ", Y: " + location.getBlockY() + ", Z: " + location.getBlockZ());
            } else if (block.getType() == Material.GOLD_ORE) {
                Bukkit.broadcastMessage("§a§l" + event.getPlayer().getName() + " §fa cassé un §eminerais d'or §faux coordonées suivantes : X: " + location.getBlockX() + ", Y: " + location.getBlockY() + ", Z: " + location.getBlockZ());
            }
        }
    }

    @Override
    public void configure() {
        this.scenario = Scenario.PARANOIA;
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
