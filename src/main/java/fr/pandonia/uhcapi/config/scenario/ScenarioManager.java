package fr.pandonia.uhcapi.config.scenario;

public abstract class ScenarioManager {
    protected Scenario scenario;

    public void activeScenario() {
        this.configure();
        this.scenario.toggleEnabled();
        if (this.scenario.isEnabled()) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public abstract void configure();

    public abstract void onStart();

    public abstract void onEnable();

    public abstract void onDisable();

    public void init() {
    }
}
