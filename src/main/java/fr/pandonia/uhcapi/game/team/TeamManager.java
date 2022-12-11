package fr.pandonia.uhcapi.game.team;

import fr.pandonia.uhcapi.GamePlayer;
import fr.pandonia.uhcapi.common.player.PlayerUtils;
import fr.pandonia.uhcapi.config.GameConfig;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.game.GameUtils;
import fr.pandonia.uhcapi.utils.DaMath;
import fr.pandonia.uhcapi.utils.Title;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class TeamManager {
    private final GameManager gameManager;

    private final GameConfig gameConfig;

    private final Map<UUID, Teams> playerTeam;

    public TeamManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameConfig = gameManager.getGameConfig();
        this.playerTeam = new HashMap<>();
    }

    public void resetTeams() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            GamePlayer.getPlayer(players.getUniqueId()).setTeams(null);
            if (this.playerTeam.containsKey(players.getUniqueId())) {
                this.gameManager.getApi().getCommon().getScoreboard().getTeam(((Teams)getPlayerTeam().get(players.getUniqueId())).getName()).removePlayer((OfflinePlayer)players);
                this.playerTeam.remove(players.getUniqueId());
            }
            PlayerUtils.giveDefaultItems(players);
        }
    }

    public void addPlayerToTeam(Player player, Teams teams) {
        UUID uuid = player.getUniqueId();
        if (getPlayerAmountInTeam(teams) < this.gameConfig.getPlayerPerTeam()) {
            if (getPlayerTeam().containsKey(uuid) && ((Teams)getPlayerTeam().get(uuid)).equals(teams)) {
                player.sendMessage("§cErreur: Vous êtes déjà dans cette équipe.");
            } else {
                GamePlayer.getPlayer(player.getUniqueId()).setTeams(teams);
                getPlayerTeam().put(uuid, teams);
                player.playSound(player.getLocation(), Sound.ORB_PICKUP, 3.0F, 1.0F);
                this.gameManager.getApi().getCommon().getScoreboard().getTeam(teams.getName()).addPlayer((OfflinePlayer)player);
                PlayerUtils.giveDefaultItems(player);
            }
        } else {
            player.sendMessage("§cErreur: Cette équipe est complète");
        }
    }

    public void killTeam(Teams teams) {
        if (getPlayerAmountInTeam(teams) <= 0) {
            Bukkit.broadcastMessage("§8│ §fL'équipe " + teams.getColor() + teams.getName() + " §fest éliminée.");
            this.gameManager.getAliveTeams().remove(teams);
            Bukkit.getOnlinePlayers().forEach(players -> players.playSound(players.getLocation(), Sound.BLAZE_BREATH, 3.0F, 0.0F));
        }
    }

    public List<Player> getPlayersInTeam(Teams teams) {
        if (GameUtils.isGameStarted()) {
            List<Player> list = new ArrayList<>();
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (this.gameManager.getInGamePlayers().contains(pl.getUniqueId()) && this.playerTeam.containsKey(pl.getUniqueId()) && ((Teams)this.playerTeam.get(pl.getUniqueId())).equals(teams))
                    list.add(pl);
            }
            return list;
        }
        List<Player> players = new ArrayList<>();
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (this.playerTeam.containsKey(pl.getUniqueId()) && ((Teams)this.playerTeam.get(pl.getUniqueId())).equals(teams))
                players.add(pl);
        }
        return players;
    }

    public int getPlayerAmountInTeam(Teams teams) {
        int i = 0;
        if (GameUtils.isGameStarted()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (this.gameManager.getInGamePlayers().contains(player.getUniqueId()) && this.playerTeam.containsKey(player.getUniqueId()) && ((Teams)this.playerTeam.get(player.getUniqueId())).equals(teams))
                    i++;
            }
        } else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (this.playerTeam.containsKey(player.getUniqueId()) && ((Teams)this.playerTeam.get(player.getUniqueId())).equals(teams))
                    i++;
            }
        }
        return i;
    }

    public void setPlayersTeamArrow() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (this.gameManager.getInGamePlayers().contains(players.getUniqueId())) {
                Teams teams = getPlayerTeam().get(players.getUniqueId());
                if (teams == null || getPlayerAmountInTeam(teams) <= 1)
                    continue;
                String send = "";
                for (Player pl : getPlayersInTeam(teams)) {
                    if (pl == players)
                        continue;
                    if (pl.getLocation().getWorld().getName() == players.getLocation().getWorld().getName()) {
                        int distance = (int)players.getLocation().distance(pl.getLocation());
                        send = send + "§a"+ pl.getName() + "§f" + DaMath.getArrow(players.getLocation(), pl.getLocation()) + "§f" + distance + "m) ";
                        continue;
                    }
                    send = send + "§c"+ pl.getName() + " §cPas le même monde ";
                }
                Title.sendActionBar(players, send);
            }
        }
    }

    public Map<UUID, Teams> getPlayerTeam() {
        return this.playerTeam;
    }
}
