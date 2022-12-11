package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import fr.pandonia.uhcapi.game.GameState;
import fr.pandonia.uhcapi.utils.ItemCreator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class NineSlots
        extends ScenarioManager
        implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (API.getAPI().getGameManager().getGameState().equals((Object) GameState.PLAYING)) {
            Player player = (Player)((Object)event.getWhoClicked());
            ItemStack itemStack = event.getCurrentItem();
            if (player == null || event.getInventory() == null || itemStack == null || event.getAction() == null) {
                return;
            }
            if (itemStack.hasItemMeta() && itemStack.getType() == Material.BARRIER) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH)
    private void onDeath(PlayerDeathEvent event) {
        event.getDrops().remove(Material.BARRIER);
    }

    @Override
    public void configure() {
        this.scenario = Scenario.NINE_SLOTS;
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
        ItemStack item = new ItemCreator(Material.BARRIER).setName("§cSlot verouillé").getItem();
        for (int i = 9; i < 36; ++i) {
            for (UUID uuid : API.getAPI().getGameManager().getInGamePlayers()) {
                Bukkit.getPlayer(uuid).getInventory().setItem(i, item);
            }
        }
    }
}
