package fr.pandonia.uhcapi.common.scoreboard;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.utils.abs.ValueGetter;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class ScoreboardManager {
    private ValueGetter<ScoreboardContents> scoreboardContents;
    protected final API main;
    private final Netherboard netherboard;
    private final BukkitTask updateTask;

    public ScoreboardManager(API main) {
        this.main = main;
        this.netherboard = Netherboard.instance();
        this.scoreboardContents = () -> new DefaultScoreboardContents(main);
        this.updateTask = new ScoreboardUpdateTask(this).runTaskTimerAsynchronously(main, 0L, 2L);
    }

    public void load() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.onLogin(player);
        }
    }

    public void onDisable() {
        this.netherboard.getBoards().values().forEach(BPlayerBoard::delete);
        this.updateTask.cancel();
    }

    public void onLogin(Player player) {
        this.netherboard.createBoard(player, "§6§lUHC");
    }

    public void onLogout(Player player) {
        this.netherboard.deleteBoard(player);
    }

    public void setScoreboardContents(ValueGetter<ScoreboardContents> scoreboardContents) {
        this.scoreboardContents = scoreboardContents;
    }

    public ValueGetter<ScoreboardContents> getScoreboardContents() {
        return this.scoreboardContents;
    }

    public Netherboard getNetherboard() {
        return this.netherboard;
    }
}
