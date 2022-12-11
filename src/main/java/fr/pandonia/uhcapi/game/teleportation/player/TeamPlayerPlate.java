package fr.pandonia.uhcapi.game.teleportation.player;

import fr.pandonia.uhcapi.game.GameUtils;
import fr.pandonia.uhcapi.game.teleportation.plate.Plate;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class TeamPlayerPlate
        implements PlayerPlate {
    private List<UUID> uuids;
    private Plate plate;
    private String teamName;

    public TeamPlayerPlate(List<UUID> uuids, String teamName) {
        this.uuids = uuids;
        this.teamName = teamName;
    }

    @Override
    public void assignPlate(Plate plate) {
        for (UUID uuid : this.uuids) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;
            this.plate = plate;
            plate.build();
            player.teleport(plate.getTeleportLocation());
            GameUtils.startPlayer(player, GameMode.ADVENTURE);
        }
    }

    @Override
    public boolean removePlate() {
        if (this.plate != null) {
            this.plate.destroy();
            for (UUID uuid : this.uuids) {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null) continue;
                GameUtils.startPlayer(player, GameMode.SURVIVAL);
            }
            return true;
        }
        return false;
    }

    public List<UUID> getUuids() {
        return this.uuids;
    }

    @Override
    public String getName() {
        return this.teamName;
    }
}
