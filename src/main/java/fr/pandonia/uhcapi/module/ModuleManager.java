package fr.pandonia.uhcapi.module;

import fr.pandonia.uhcapi.game.GameManager;

public class ModuleManager {
    private final GameManager gameManager;

    private ModuleType currentModule;

    public ModuleManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.currentModule = ModuleType.UHC;
    }

    public ModuleType getCurrentModule() {
        return this.currentModule;
    }

    public void setCurrentModule(ModuleType currentModule) {
        this.currentModule = currentModule;
    }
}