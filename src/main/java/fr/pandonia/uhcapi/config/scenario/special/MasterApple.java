package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import fr.pandonia.uhcapi.config.scenario.special.runnable.MasterAppleRunnable;

public class MasterApple
        extends ScenarioManager {
    @Override
    public void configure() {
        this.scenario = Scenario.MASTERAPPLE;
    }

    @Override
    public void onStart() {
        MasterAppleRunnable masterAppleRunnable = new MasterAppleRunnable();
        masterAppleRunnable.runTaskTimer(API.getAPI(), this.scenario.getValue() * 1200, this.scenario.getValue() * 1200);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }
}
