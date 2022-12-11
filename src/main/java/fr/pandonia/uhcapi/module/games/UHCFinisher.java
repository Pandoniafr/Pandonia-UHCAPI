package fr.pandonia.uhcapi.module.games;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.game.GameUtils;
import fr.pandonia.uhcapi.game.team.Teams;
import fr.pandonia.uhcapi.module.ModuleType;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class UHCFinisher {
    private final API api;

    public UHCFinisher(API api) {
        this.api = api;
    }

    public void tryFinishGame() {
        String endMessage = null;
        if (GameUtils.isSoloMode()) {
            if (this.api.getGameManager().getInGamePlayers().size() == 1) {
                Player winner = Bukkit.getPlayer(this.api.getGameManager().getInGamePlayers().get(0));
                endMessage = "§fVictoire de §c§l"+ winner.getName() + " §f!";
                giveExperience(Collections.singletonList(winner.getUniqueId()), 100, 20);
            } else if (this.api.getGameManager().getInGamePlayers().size() == 0) {
                endMessage = "§fTous les joueurs sont éliminés !!";
            }
        } else if (this.api.getGameManager().getAliveTeams().size() == 1) {
            Teams teams = this.api.getGameManager().getAliveTeams().get(0);
            endMessage = "§fVictoire de l'équipe " + teams.getColor() + teams.getName() + " §f!";
            List<UUID> members = (List<UUID>)this.api.getGameManager().getTeamManager().getPlayersInTeam(teams).stream().map(Entity::getUniqueId).collect(Collectors.toList());
            giveExperience(members, 40, 15);
        } else if (this.api.getGameManager().getAliveTeams().size() == 0) {
            endMessage = "§fToutes les équipes sont éliminées !";
        }
        if (endMessage != null)
            this.api.getGameManager().getUhcFinisherGame().finishGame(endMessage);
    }

    private void giveExperience(List<UUID> winners, int winnerExp, int loserExp) {
        if (this.api.getGameManager().getModuleManager().getCurrentModule() != ModuleType.UHC)
            return;
        for (UUID playedPlayer : this.api.getGameManager().getPlayedPlayers()) {
            int exp = winners.contains(playedPlayer) ? winnerExp : loserExp;
            Player player = Bukkit.getPlayer(playedPlayer);
            if (player != null)
                player.sendMessage("§a+ " + exp + " XP");
        }
    }
}
