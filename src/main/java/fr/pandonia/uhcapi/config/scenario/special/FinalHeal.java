package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import fr.pandonia.uhcapi.utils.Title;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class FinalHeal
        extends ScenarioManager {
    @Override
    public void init() {
        for (UUID uuid : API.getAPI().getGameManager().getInGamePlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;
            player.setHealth(player.getMaxHealth());
            Title.sendActionBar(player, "§aFinalHeal activé");
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 5.0f, 1.0f);
        }
    }

    @Override
    public void configure() {
        this.scenario = Scenario.FINALHEAL;
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
