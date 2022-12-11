package fr.pandonia.uhcapi.module.standard;

import fr.pandonia.uhcapi.game.GameManager;

public class UHCStandard {
    private final GameManager gameManager;

    public UHCStandard(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public GameManager getGameManager() {
        return this.gameManager;
    }
}


