package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import fr.pandonia.uhcapi.game.GameState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SuperHeroes
        extends ScenarioManager
        implements Listener {
    private final Map<UUID, String> player_effect = new HashMap<UUID, String>();

    @EventHandler
    private void onFood(FoodLevelChangeEvent event) {
        Player player = (Player)((Object)event.getEntity());
        if (this.player_effect.containsKey(player.getUniqueId()) && this.player_effect.get(player.getUniqueId()).equalsIgnoreCase("Jump")) {
            event.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (API.getAPI().getGameManager().getGameState().equals((Object) GameState.PLAYING) && event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if (this.scenario.isEnabled() && (event.getCause() == EntityDamageEvent.DamageCause.FALL || event.getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK) && this.player_effect.containsKey(player.getUniqueId()) && this.player_effect.get(player.getUniqueId()).equalsIgnoreCase("Jump")) {
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void configure() {
        this.scenario = Scenario.SUPERHEROES;
    }

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, API.getAPI());
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public void onStart() {
        ArrayList<Player> list = new ArrayList<Player>();
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (!API.getAPI().getGameManager().getInGamePlayers().contains(players.getUniqueId())) continue;
            list.add(players);
        }
        ArrayList<String> effects = new ArrayList<String>();
        effects.add("Strenght");
        effects.add("Speed");
        effects.add("Jump");
        effects.add("DoubleHealth");
        effects.add("Resistance");
        effects.add("Invisibility");
        for (Player players : list) {
            String effect = (String)effects.get(new Random().nextInt(effects.size()));
            this.player_effect.put(players.getUniqueId(), effect);
            switch (effect) {
                case "Strenght": {
                    players.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 255555, 0, true, false));
                }
                case "Speed": {
                    players.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 255555, 1, true, false));
                }
                case "Jump": {
                    players.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 255555, 3, true, false));
                    players.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 255555, 0, true, false));
                }
                case "Resistance": {
                    players.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 255555, 0, true, false));
                }
                case "DoubleHealth": {
                    players.setMaxHealth(40.0);
                    players.setHealth(40.0);
                }
                case "Invisibility": {
                    players.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 255555, 0, true, false));
                }
            }
        }
    }
}
