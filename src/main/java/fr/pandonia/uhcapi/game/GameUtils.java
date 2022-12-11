package fr.pandonia.uhcapi.game;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.common.rules.items.GeneralRules;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class GameUtils {
    public static void startPlayer(Player player, GameMode gameMode) {
        player.closeInventory();
        player.setGameMode(gameMode);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setMaxHealth(20.0);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setTotalExperience(0);
        player.setExp(0.0f);
        GameUtils.clearPlayerEffect(player);
    }

    public static void setSpectator(Player player) {
        GameUtils.startPlayer(player, GameMode.SPECTATOR);
    }

    public static void clearPlayerEffect(Player player) {
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }
    }

    public static int getPlayerAmount() {
        int amount = 0;
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.getGameMode().equals(GameMode.SPECTATOR)) continue;
            ++amount;
        }
        return amount;
    }

    public static boolean isSoloMode() {
        return API.getAPI().getGameManager().getGameConfig().getPlayerPerTeam() == 1;
    }

    public static boolean isGameStarted() {
        return API.getAPI().getGameManager().getGameState().equals((Object)GameState.PLAYING);
    }

    public static boolean hasGameStarted() {
        return API.getAPI().getGameManager().getGameState().equals((Object)GameState.PLAYING) || API.getAPI().getGameManager().getGameState().equals((Object)GameState.FINISH);
    }

    public static void registerHealth() {
        if (GeneralRules.HEALTH.isEnabled()) {
            Scoreboard scoreboard = API.getAPI().getServer().getScoreboardManager().getMainScoreboard();
            Objective objective = scoreboard.getObjective("health") == null ? scoreboard.registerNewObjective("health", "health") : scoreboard.getObjective("health");
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            objective.setDisplayName("§4❤");
            Objective objectiveTab = scoreboard.getObjective("vie") == null ? scoreboard.registerNewObjective("vie", "health") : scoreboard.getObjective("vie");
            objectiveTab.setDisplaySlot(DisplaySlot.PLAYER_LIST);
            for (Player players : Bukkit.getOnlinePlayers()) {
                double current = players.getHealth();
                players.setHealth(current - 1.0);
            }
            Bukkit.getScheduler().runTaskLater(API.getAPI(), () -> {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.setHealth(players.getMaxHealth());
                }
            }, 2L);
        }
    }
}
