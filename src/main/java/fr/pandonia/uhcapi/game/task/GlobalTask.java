package fr.pandonia.uhcapi.game.task;

import fr.pandonia.uhcapi.GamePlayer;
import fr.pandonia.uhcapi.common.rules.Rules;
import fr.pandonia.uhcapi.config.GameConfig;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioValueType;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.module.Modules;
import fr.pandonia.uhcapi.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GlobalTask extends BukkitRunnable {
    private final GameManager gameManager;

    private final GameConfig gameConfig;

    private final Modules modules;

    private int episodeTime;

    private int globalTime;

    public GlobalTask(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameConfig = gameManager.getGameConfig();
        this.modules = gameManager.getApi().getModules();
    }

    public void run() {
        if (this.episodeTime >= (this.gameConfig.isGameDev() ? 60 : this.gameConfig.getEpisodeTime())) {
            this.episodeTime = 0;
            this.gameManager.getEpisodeManager().switchEpisode();
        }
        if (Rules.noDamage.isActive())
            if (this.globalTime >= 30) {
                Rules.noDamage.setActive(false);
                Bukkit.getOnlinePlayers().forEach(players -> {
                    Title.sendActionBar(players, "§8• §fL'invincibilité est désormais §cdésactivé§f. §8•");
                            players.playSound(players.getLocation(), Sound.VILLAGER_HIT, 3.0F, 1.0F);
                });
            } else {
                int timeLeft = 30 - this.globalTime;
                Bukkit.getOnlinePlayers().forEach(players -> Title.sendActionBar(players, "§8• §fFin de l'§cInvincibilité§f dans §c" + timeLeft + "§f seconde" + (timeLeft > 1 ? "§fs" : "§f") + " §8•"));
            }
        if (!Rules.pvp.isActive() &&
                getGlobalTime() >= this.gameConfig.getPvpTime())
            Rules.pvp.setActive(true);
        for (GamePlayer gamePlayers : GamePlayer.getGamePlayers()) {
            Player players = gamePlayers.getPlayer();
            if (players == null)
                continue;
            if (gamePlayers.getInvincibilityCount() > 0)
                gamePlayers.removeInvincibilityCount();
            if (gamePlayers.getInvincibilityNoFallCount() > 0)
                gamePlayers.removeInvincibilityNoFallCount();
        }
        for (Scenario scenario : this.gameManager.getEnabledScenarios()) {
            if (scenario.isEnabled() && (scenario
                    .getScenarioValueType() == ScenarioValueType.TIME || scenario.getScenarioValueType() == ScenarioValueType.TIMEMIN) && (
                    (scenario.getScenarioValueType() == ScenarioValueType.TIME) ? scenario.getValue() : (scenario.getValue() * 60)) == this.globalTime)
                scenario.getScenarioManager().init();
        }
        if (!this.gameManager.getBorder().isStart() &&
                getGlobalTime() >= this.gameConfig.getBorderTime())
            this.gameManager.getBorder().startReduce((this.gameConfig.getBorderEndSize() * 2), this.gameConfig.getBorderBlocksPerSecond());
        int sizeBorder = (int)(this.gameManager.getBorder().getWorldBorder().getSize() / 2.0D);
        Bukkit.getOnlinePlayers().forEach(players -> {
            if (!this.gameManager.getInGamePlayers().contains(players.getUniqueId())) {
                if (players.getLocation().getY() < 0.0D)
                    players.teleport(this.gameManager.getApi().getLobbyPopulator().getCenter());
                if (!isInBorder(players, sizeBorder) && players.getLocation().getWorld().getName().equals("world"))
                    players.teleport(this.gameManager.getApi().getLobbyPopulator().getCenter());
            }
        });
        this.modules.onClockUpdate(this.globalTime);
        this.episodeTime++;
        this.globalTime++;
    }

    private boolean isInBorder(Player player, int borderSize) {
        int borderSizeLess = borderSize - borderSize - borderSize;
        double x = player.getLocation().getX();
        double z = player.getLocation().getZ();
        if (x > borderSize)
            return false;
        if (z > borderSize)
            return false;
        if (x < borderSizeLess)
            return false;
        if (z < borderSizeLess)
            return false;
        return true;
    }

    public int getEpisodeTime() {
        return this.episodeTime;
    }

    public void setEpisodeTime(int episodeTime) {
        this.episodeTime = episodeTime;
    }

    public int getGlobalTime() {
        return this.globalTime;
    }

    public void setGlobalTime(int globalTime) {
        this.globalTime = globalTime;
    }
}
