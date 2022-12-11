package fr.pandonia.uhcapi.module.standard;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.game.GameState;
import fr.pandonia.uhcapi.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class UHCFinisherGame {
    private final API api;

    public UHCFinisherGame(API api) {
        this.api = api;
    }

    public void finishGame(String message) {
        this.api.getGameManager().setGameState(GameState.FINISH);
        Bukkit.getOnlinePlayers().forEach(players -> {
            Title.sendTitle(players, 10, 200, 10, "§c§lFin de la partie !", message);
            players.setAllowFlight(true);
        });
        (new BukkitRunnable() {
            public void run() {
                Bukkit.getOnlinePlayers().forEach(players -> {
                    players.teleport(UHCFinisherGame.this.api.getLobbyPopulator().getLobbyLocation());
                    players.playSound(players.getLocation(), Sound.FIREWORK_LAUNCH, 3.0F, 0.0F);
                });
            }
        }).runTaskLater((Plugin)this.api, 40L);
        this.api.getGameManager().getWorldPopulator().getGameWorld().getWorldBorder().setSize(3000.0D);
        (new BukkitRunnable() {
            public void run() {
                Bukkit.shutdown();
            }
        }).runTaskLater((Plugin)this.api, 300L);
    }
}