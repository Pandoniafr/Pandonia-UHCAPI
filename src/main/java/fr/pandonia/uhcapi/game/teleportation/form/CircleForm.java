package fr.pandonia.uhcapi.game.teleportation.form;

import org.bukkit.Location;
import org.bukkit.World;

public class CircleForm
        implements Form {
    private double radius;
    private int y;
    private World world;
    private Location center;

    public CircleForm(double radius, int y, World world, Location center) {
        this.radius = radius;
        this.y = y;
        this.world = world;
        this.center = center;
    }

    @Override
    public Location calc(int now, int max) {
        double n = Math.PI * 2 - Math.PI * 2 / (double)max * (double)now;
        double x = this.radius * Math.cos(n);
        double z = this.radius * Math.sin(n);
        return new Location(this.world, x, this.y, z);
    }
}
