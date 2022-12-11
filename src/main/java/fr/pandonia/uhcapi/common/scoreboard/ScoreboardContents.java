package fr.pandonia.uhcapi.common.scoreboard;

import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import java.util.UUID;

public interface ScoreboardContents {
    public void reloadData(UUID var1);

    public void setLines(BPlayerBoard var1, UUID var2, String var3);
}
