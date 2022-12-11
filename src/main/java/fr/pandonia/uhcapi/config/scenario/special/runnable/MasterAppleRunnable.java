package fr.pandonia.uhcapi.config.scenario.special.runnable;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.utils.ItemCreator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class MasterAppleRunnable
        extends BukkitRunnable {
    public void run() {
        for (UUID uuid : API.getAPI().getGameManager().getInGamePlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;
            player.getInventory().addItem(new ItemStack[]{new ItemCreator(Material.GOLDEN_APPLE).setDurability(1).getItem()});
        }
    }
}
