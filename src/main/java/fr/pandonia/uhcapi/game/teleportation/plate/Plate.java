package fr.pandonia.uhcapi.game.teleportation.plate;

import org.bukkit.Location;

public interface Plate {
    public void build();

    public void destroy();

    public Location getTeleportLocation();
}
