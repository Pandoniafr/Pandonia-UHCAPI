package fr.pandonia.uhcapi.commands;

import fr.pandonia.uhcapi.GamePlayer;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.game.GameUtils;
import fr.pandonia.uhcapi.utils.CommonString;
import fr.pandonia.uhcapi.utils.RandomUtils;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DisperseCommand
        implements CommandExecutor {
    private final GameManager gameManager;

    public DisperseCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] arguments) {
        if (commandSender instanceof Player) {
            Player player = (Player)commandSender;
            if (player.isOp()) {
                if (GameUtils.isGameStarted()) {
                    if (arguments.length > 0) {
                        if (arguments[0].equalsIgnoreCase("pos")) {
                            Location location = player.getLocation();
                            for (UUID uuid : this.gameManager.getInGamePlayers()) {
                                GamePlayer gamePlayers;
                                Player players = Bukkit.getPlayer(uuid);
                                if (players == null || !players.isOnline() || players.getUniqueId().equals(player.getUniqueId()) || !(players.getLocation().distance(location) <= 20.0) || (gamePlayers = GamePlayer.getPlayer(uuid)) == null) continue;
                                gamePlayers.addInvincibilityNoFallCount(10);
                                players.teleport(RandomUtils.getRandomLocationInBorder());
                                players.sendMessage("§cVous avez été téléporté aléatoirement par un modérateur.");
                                player.sendMessage("§cVous avez téléporté aléatoirement §l" + players.getName() + "§c.");
                            }
                        } else {
                            Player targetPosition = Bukkit.getPlayer(arguments[0]);
                            if (targetPosition != null) {
                                Location location = targetPosition.getLocation();
                                for (UUID uuid : this.gameManager.getInGamePlayers()) {
                                    GamePlayer gamePlayers;
                                    Player players = Bukkit.getPlayer(uuid);
                                    if (players == null || !players.isOnline() || players.getUniqueId().equals(player.getUniqueId()) || !players.getLocation().getWorld().equals(location.getWorld()) || !(players.getLocation().distance(location) <= 20.0) || (gamePlayers = GamePlayer.getPlayer(uuid)) == null) continue;
                                    gamePlayers.addInvincibilityNoFallCount(10);
                                    players.teleport(RandomUtils.getRandomLocationInBorder());
                                    players.sendMessage("§cVous avez été téléporté aléatoirement par un modérateur.");
                                    player.sendMessage("§cVous avez téléporté aléatoirement §l" + players.getName() + "§c.");
                                }
                            } else {
                                player.sendMessage("§cAucun joueur avec le pseudo '" + arguments[0] + "' n'a été trouvé.");
                            }
                        }
                    } else {
                        player.sendMessage("§cErreur de syntaxe: /disperse <pos:joueur>");
                    }
                } else {
                    player.sendMessage("§cLa partie n'a pas commencée.");
                }
            } else {
                player.sendMessage(CommonString.NO_PERMISSION.getMessage());
            }
        }
        return false;
    }
}
