package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;

public class DiamondLess
        extends ScenarioManager {
    @Override
    public void configure() {
        this.scenario = Scenario.DIAMOND_LESS;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onStart() {
    }
}
