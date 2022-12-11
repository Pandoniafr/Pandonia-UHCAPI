package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.common.rules.Rules;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import fr.pandonia.uhcapi.game.GameState;
import fr.pandonia.uhcapi.game.team.Teams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class SharedHealth
        extends ScenarioManager
        implements Listener {
    @EventHandler
    private void onDamage(EntityDamageEvent event) {
        if (!event.isCancelled() && API.getAPI().getGameManager().getGameState().equals((Object) GameState.PLAYING) && Rules.pvp.isActive() && event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            Teams teams = API.getAPI().getGameManager().getTeamManager().getPlayerTeam().get(player.getUniqueId());
            if (teams != null) {
                for (Player players : API.getAPI().getGameManager().getTeamManager().getPlayersInTeam(teams)) {
                    if (player.getUniqueId() == players.getUniqueId()) continue;
                    players.setHealth(player.getHealth() - event.getDamage() / 2.0);
                }
            }
        }
    }

    @EventHandler
    private void onDamage(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            Teams teams = API.getAPI().getGameManager().getTeamManager().getPlayerTeam().get(player.getUniqueId());
            if (teams != null) {
                for (Player players : API.getAPI().getGameManager().getTeamManager().getPlayersInTeam(teams)) {
                    players.setHealth(player.getHealth() + event.getAmount());
                }
            }
        }
    }

    @Override
    public void configure() {
        this.scenario = Scenario.SHAREDHEALTH;
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
    }
}
