package fr.pandonia.uhcapi.game.teleportation.plate;

import fr.pandonia.uhcapi.utils.Cuboid;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class SquarePlate
        implements Plate {
    private final Location center;
    private final Location teleportLocation;
    private final int size;
    private final Material material;
    private final byte data;
    private Location loc1;
    private Location loc2;

    public SquarePlate(Location center, int size, Material material, int data) {
        this.center = center;
        this.teleportLocation = center.clone().add(0.0, 10.0, 0.0);
        this.size = size + (size + 1) % 2;
        this.material = material;
        this.data = (byte)data;
        double a = (double)size / 2.0;
        Location loc1 = center.clone().add(a, 0.0, a);
        Location loc2 = center.clone().add(-a, 0.0, -a);
        World world = center.getWorld();
        int x1 = loc1.getBlockX();
        int z1 = loc1.getBlockZ();
        int x2 = loc2.getBlockX();
        int z2 = loc2.getBlockZ();
        int y = center.getBlockY();
        this.loc1 = new Location(world, (x1 > x2 ? x2 : x1) - size, y, (z1 > z2 ? z2 : z1) - size);
        this.loc2 = new Location(world, (x1 < x2 ? x2 : x1) + size, y, (z1 < z2 ? z2 : z1) + size);
    }

    @Override
    public void build() {
        SquarePlate.fill(this.loc1, this.loc2, this.material, this.data);
        Cuboid walls = new Cuboid(this.loc1.clone().add(0.0, 1.0, 0.0), this.loc2.clone().add(0.0, 7.0, 0.0));
        walls.getWalls().forEach(b -> b.setType(Material.BARRIER));
    }

    @Override
    public void destroy() {
        SquarePlate.fill(this.loc1, this.loc2, Material.AIR, (byte)0);
        Cuboid walls = new Cuboid(this.loc1.clone().add(0.0, 1.0, 0.0), this.loc2.clone().add(0.0, 7.0, 0.0));
        walls.getWalls().forEach(b -> b.setType(Material.AIR));
    }

    @Override
    public Location getTeleportLocation() {
        return this.teleportLocation;
    }

    public static void fill(Location loc1, Location loc2, Material material, byte data) {
        Cuboid floor = new Cuboid(loc1, loc2);
        floor.getBlockList().forEach(b -> b.setTypeIdAndData(material.getId(), data, true));
    }
}
