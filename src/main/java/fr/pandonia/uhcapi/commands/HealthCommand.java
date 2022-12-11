package fr.pandonia.uhcapi.commands;

import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.utils.HealthUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealthCommand
        implements CommandExecutor {
    private final GameManager gameManager;

    public HealthCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] arguments) {
        block10: {
            if (!(commandSender instanceof Player)) {
                return true;
            }
            Player player = (Player)commandSender;
            if (this.gameManager.hasHostAccess(player)) {
                if (arguments.length >= 3) {
                    String target = arguments[0];
                    String action = arguments[1];
                    int amount = Integer.parseInt(arguments[2]);
                    try {
                        Player targetPlayer = Bukkit.getPlayer(target);
                        if (targetPlayer == null) {
                            player.sendMessage("§cAucun joueur avec le pseudo '" + arguments[0] + "' n'a été trouvé.");
                            break block10;
                        }
                        if (action.equals("add")) {
                            HealthUtils.addPermanentHeart(targetPlayer, amount);
                            player.sendMessage("§aVous avez donné " + amount + "❤ à " + targetPlayer.getName() + ".");
                            break block10;
                        }
                        if (action.equals("remove")) {
                            HealthUtils.removePermanentHeart(targetPlayer, amount);
                            player.sendMessage("§cVous avez retiré " + amount + "❤ à " + targetPlayer.getName() + ".");
                            break block10;
                        }
                        this.sendHelp(player);
                    }
                    catch (NumberFormatException exception) {
                        player.sendMessage(exception.getMessage());
                    }
                } else {
                    this.sendHelp(player);
                }
            } else {
                player.sendMessage("§cPermission insuffisante.");
            }
        }
        return false;
    }

    public void sendHelp(Player sender) {
        sender.sendMessage("§cErreur de syntaxe: /health <joueur> <add:remove> <quantité>");
    }
}
