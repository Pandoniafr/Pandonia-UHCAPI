package fr.pandonia.uhcapi.common.border;

import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.util.Vector;

public class RecenterBorder
        extends SimpleBorder {
    public RecenterBorder(WorldBorder worldBorder) {
        super(worldBorder);
    }

    public void startReduceAtCenter(Location newCenter, double finalSize, double blocksSecond) {
        WorldBorder worldBorder = this.getWorldBorder();
        Location center = worldBorder.getCenter();
        Vector vector = center.toVector().add(newCenter.toVector());
        double x = Math.abs(vector.getX());
        double z = Math.abs(vector.getZ());
        double size = worldBorder.getSize();
        double newSize = size + Math.max(x, z);
        worldBorder.setSize(newSize);
        worldBorder.setCenter(newCenter);
        this.startReduce(finalSize, blocksSecond);
    }

    public void startReduceAtCenterInMinute(Location newCenter, double finalSize, double minuteDuration) {
        WorldBorder worldBorder = this.getWorldBorder();
        Location center = worldBorder.getCenter();
        Vector vector = center.toVector().add(newCenter.toVector());
        double x = Math.abs(vector.getX());
        double z = Math.abs(vector.getZ());
        double size = worldBorder.getSize();
        double newSize = size + Math.max(x, z);
        worldBorder.setSize(newSize);
        worldBorder.setCenter(newCenter);
        double blocksSecond = (newSize - finalSize) / minuteDuration / 60.0;
        this.startReduce(finalSize, blocksSecond);
    }
}
