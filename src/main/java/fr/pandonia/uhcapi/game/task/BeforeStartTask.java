package fr.pandonia.uhcapi.game.task;

import fr.pandonia.uhcapi.config.GameConfig;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.game.GameUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class BeforeStartTask extends BukkitRunnable {
    private final GameManager gameManager;

    public BeforeStartTask(GameManager gameManager) {
        this.gameManager = gameManager;
        GameConfig gameConfig = gameManager.getGameConfig();
    }

    public void run() {
        if (GameUtils.hasGameStarted())
            cancel();
    }
}

