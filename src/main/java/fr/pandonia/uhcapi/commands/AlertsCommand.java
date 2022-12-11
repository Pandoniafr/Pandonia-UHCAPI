package fr.pandonia.uhcapi.commands;

import fr.pandonia.uhcapi.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlertsCommand
        implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player)commandSender;
            if (player.isOp()) {
                GamePlayer gamePlayer = null;
                gamePlayer.setAlerts(!(gamePlayer = GamePlayer.getPlayer(player.getUniqueId())).isAlerts());
                if (gamePlayer.isAlerts()) {
                    player.sendMessage("§aVous avez activé vos alertes.");
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        if (!players.isOp()) continue;
                        players.sendMessage("§f§o[" + player.getName() + ": a activé ses alertes.]");
                    }
                } else {
                    player.sendMessage("§cVous avez désactivé vos alertes.");
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        if (!players.isOp()) continue;
                        players.sendMessage("§f§o[" + player.getName() + ": a désactivé ses alertes.]");
                    }
                }
            } else {
                player.sendMessage("§cPermission insuffisante.");
            }
        }
        return false;
    }
}
