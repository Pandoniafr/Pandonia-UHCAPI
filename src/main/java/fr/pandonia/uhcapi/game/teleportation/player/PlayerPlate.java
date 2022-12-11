package fr.pandonia.uhcapi.game.teleportation.player;

import fr.pandonia.uhcapi.game.teleportation.plate.Plate;

public interface PlayerPlate {
    public void assignPlate(Plate var1);

    public boolean removePlate();

    public String getName();
}
