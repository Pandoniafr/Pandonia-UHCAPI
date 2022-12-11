package fr.pandonia.uhcapi.common.world;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.game.GameState;
import fr.pandonia.uhcapi.listener.world.ChunkUnloadListener;
import fr.pandonia.uhcapi.utils.Title;
import fr.pandonia.uhcapi.utils.msg.ProgressBar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LoadingChunkTask
        extends BukkitRunnable {
    private double percent = 0.0;
    private int ancientPercent = 0;
    private double currentChunkLoad = 0.0;
    private int r;
    private double totalChunkToLoad = Math.pow(r += 150, 2.0) / 64.0;
    private int cx;
    private int cz;
    private final int radius;
    private boolean finished;
    private final World world;

    public LoadingChunkTask(World world, int r) {
        this.cx = -r;
        this.cz = -r;
        this.world = world;
        this.radius = r;
        this.finished = false;
        this.runTaskTimer(API.getAPI(), 0L, 5L);
    }

    public void run() {
        new Thread(() -> {
            for (int i = 0; i < 30 && !this.finished; ++i) {
                Location loc = new Location(this.world, this.cx, 0.0, this.cz);
                ChunkUnloadListener.keepChunk.add(loc.getChunk());
                loc.getWorld().loadChunk(loc.getChunk().getX(), loc.getChunk().getZ(), true);
                this.cx += 16;
                this.currentChunkLoad += 1.0;
                if (this.cx <= this.radius) continue;
                this.cx = -this.radius;
                this.cz += 16;
                if (this.cz <= this.radius) continue;
                this.currentChunkLoad = this.totalChunkToLoad;
                this.finished = true;
            }
            this.percent = this.currentChunkLoad / this.totalChunkToLoad * 100.0;
            for (Player player : Bukkit.getOnlinePlayers()) {
                Title.sendActionBar(player, ChatColor.GRAY + "Prégénération: " + ChatColor.GREEN + this.percent + "% §8[§r" + ProgressBar.getProgressBar((int)this.percent, 100, 40, "|", ChatColor.GREEN, ChatColor.GRAY) + "§8]");
            }
            if ((double)this.ancientPercent < this.percent) {
                this.ancientPercent = (int)this.percent;
            }
            if (this.finished) {
                API.getAPI().getGameManager().setGameState(GameState.WAITING);
                this.cancel();
            }
        }).run();
    }
}