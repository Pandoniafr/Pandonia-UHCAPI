package fr.pandonia.uhcapi.commands;

import fr.pandonia.uhcapi.GamePlayer;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.utils.CommonString;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ViewOfflineCommand
        implements CommandExecutor {
    private final GameManager gameManager;

    public ViewOfflineCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] arguments) {
        if (!(commandSender instanceof Player)) {
            return true;
        }
        Player player = (Player)commandSender;
        if (this.gameManager.hasHostAccess(player)) {
            commandSender.sendMessage("§aListe des joueurs déconnectés :");
            for (UUID uuid : this.gameManager.getOfflinePlayers()) {
                GamePlayer gamePlayer = GamePlayer.getPlayer(uuid);
                if (gamePlayer == null) continue;
                commandSender.sendMessage("§f- §c" + gamePlayer.getName());
            }
        } else {
            commandSender.sendMessage(CommonString.NO_PERMISSION.getMessage());
        }
        return false;
    }
}
