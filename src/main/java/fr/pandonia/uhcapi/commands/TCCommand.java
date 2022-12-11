package fr.pandonia.uhcapi.commands;

import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.game.GameUtils;
import fr.pandonia.uhcapi.game.team.TeamManager;
import fr.pandonia.uhcapi.game.team.Teams;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TCCommand
        implements CommandExecutor {
    private final GameManager gameManager;
    private final TeamManager teamManager;

    public TCCommand(GameManager gameManager) {
        this.gameManager = gameManager;
        this.teamManager = gameManager.getTeamManager();
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player && !GameUtils.isSoloMode() && GameUtils.isGameStarted()) {
            Teams teams;
            Player player = (Player)commandSender;
            UUID uuid = player.getUniqueId();
            if (this.gameManager.getInGamePlayers().contains(uuid) && this.teamManager.getPlayerTeam().containsKey(uuid) && (teams = this.gameManager.getTeamManager().getPlayerTeam().get(uuid)) != null) {
                int x = player.getLocation().getBlockX();
                int y = player.getLocation().getBlockY();
                int z = player.getLocation().getBlockZ();
                for (Player players : this.teamManager.getPlayersInTeam(teams)) {
                    players.sendMessage("§f(§fÉquipe§f) " + teams.getColor() + teams.getName() + " " + player.getName() + " §8» " + (player.isOp() ? "§f" : "§f") + "X: " + x + ", Y: " + y + ", Z: " + z);
                }
            }
        }
        return false;
    }
}
