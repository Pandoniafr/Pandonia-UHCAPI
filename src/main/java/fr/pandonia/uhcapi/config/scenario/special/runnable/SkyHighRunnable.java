package fr.pandonia.uhcapi.config.scenario.special.runnable;

import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.utils.Title;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SkyHighRunnable
        extends BukkitRunnable {
    private final GameManager game;
    private int time = 30;

    public SkyHighRunnable(GameManager game) {
        this.game = game;
    }

    public void run() {
        if (this.time <= 0) {
            this.setTime(30);
            for (UUID uuid : this.game.getInGamePlayers()) {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null || !(player.getLocation().getY() < (double)Scenario.SKYHIGH.getValue())) continue;
                player.damage(4.0);
                Title.sendActionBar(player, "§cVeuillez vous rendre au dessus de la couche §l" + Scenario.SKYHIGH.getValue() + " §c!");
            }
        }
        --this.time;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
