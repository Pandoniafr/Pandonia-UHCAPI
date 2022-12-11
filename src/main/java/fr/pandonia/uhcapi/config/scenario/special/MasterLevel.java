package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MasterLevel
        extends ScenarioManager {
    @Override
    public void configure() {
        this.scenario = Scenario.MASTERLEVEL;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void init() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.setTotalExperience(this.scenario.getValue());
        }
    }

    @Override
    public void onStart() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.setLevel(Scenario.MASTERLEVEL.getValue());
        }
    }
}
