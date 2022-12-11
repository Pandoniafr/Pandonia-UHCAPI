package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;

public class RandomTeam
        extends ScenarioManager {
    @Override
    public void configure() {
        this.scenario = Scenario.RANDOMTEAM;
    }

    @Override
    public void onEnable() {
        API.getAPI().getGameManager().getTeamManager().resetTeams();
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onStart() {
    }
}
