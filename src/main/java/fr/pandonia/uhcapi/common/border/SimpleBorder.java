package fr.pandonia.uhcapi.common.border;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class SimpleBorder
        extends AbstractBorder {
    private boolean isStart = false;
    private boolean isPause = true;
    private double finalSize;
    private double blocksSecond;

    public SimpleBorder(WorldBorder worldBorder) {
        super(worldBorder);
    }

    public void startReduce(double finalSize, double blocksSecond) {
        if (!this.isStart) {
            this.isStart = true;
            this.finalSize = finalSize;
            this.blocksSecond = blocksSecond;
            this.play();
            Bukkit.broadcastMessage("§f[§c§l!§f] §fLa bordure est désormais §aen mouvement§f.");
            World endWorld = Bukkit.getWorld("world_the_end");
            WorldBorder endWorldBorder = endWorld.getWorldBorder();
            endWorldBorder.setSize(1000000.0);
        }
    }

    public void play() {
        if (this.isStart && this.isPause) {
            this.isPause = false;
            WorldBorder worldBorder = this.getWorldBorder();
            double size = worldBorder.getSize();
            double dif = Math.abs(size - this.finalSize);
            double time = dif / this.blocksSecond;
            worldBorder.setSize(this.finalSize, (long)time);
            worldBorder.setDamageBuffer(0.0);
        }
    }

    public void pause() {
        if (this.isStart && !this.isPause) {
            this.isPause = true;
            WorldBorder worldBorder = this.getWorldBorder();
            worldBorder.setSize(worldBorder.getSize());
        }
    }

    public boolean isStart() {
        return this.isStart;
    }

    public boolean isPause() {
        return this.isPause;
    }
}
