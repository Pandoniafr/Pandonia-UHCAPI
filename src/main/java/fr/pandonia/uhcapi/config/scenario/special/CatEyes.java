package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CatEyes
        extends ScenarioManager {
    @Override
    public void configure() {
        this.scenario = Scenario.CAT_EYES;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onStart() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false));
        }
    }
}
