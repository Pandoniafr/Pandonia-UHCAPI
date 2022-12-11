package fr.pandonia.uhcapi.config.scenario.special.runnable;

import fr.pandonia.uhcapi.config.scenario.special.BestPVE;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.utils.Title;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BestPVERunnable
        extends BukkitRunnable {
    private final GameManager game;

    public BestPVERunnable(GameManager game) {
        this.game = game;
    }

    public void run() {
        for (UUID uuid : this.game.getInGamePlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null || !BestPVE.playersBestPVE.contains(uuid)) continue;
            player.setMaxHealth(player.getMaxHealth() + 2.0);
            player.setHealth(player.getHealth() + 2.0);
            player.setHealthScale(player.getHealthScale() + 2.0);
            Title.sendActionBar(player, "§eVous avez gagné un coeur car vous êtes dans la liste BestPVE !");
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 5.0f, 0.0f);
        }
    }
}
