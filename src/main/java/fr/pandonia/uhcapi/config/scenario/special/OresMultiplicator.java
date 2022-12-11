package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;

public class OresMultiplicator
        extends ScenarioManager {
    @Override
    public void configure() {
        this.scenario = Scenario.ORESMULTIPLICATOR;
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
