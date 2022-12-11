package fr.pandonia.uhcapi.commands;

import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.utils.CommonString;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand
        implements CommandExecutor {
    private final GameManager gameManager;

    public VanishCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] arguments) {
        if (!(commandSender instanceof Player)) {
            return true;
        }
        Player player = (Player)commandSender;
        if (!player.isOp()) {
            player.sendMessage(CommonString.NO_PERMISSION.getMessage());
            return true;
        }
        if (this.gameManager.getVanishList().contains(player.getUniqueId())) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.showPlayer(player);
            }
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!this.gameManager.getVanishList().contains(p.getUniqueId())) continue;
                player.hidePlayer(p);
            }
            this.gameManager.getVanishList().remove(player.getUniqueId());
            player.sendMessage("§eVous n'êtes plus vanish !");
        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (this.gameManager.getVanishList().contains(p.getUniqueId())) continue;
                p.hidePlayer(player);
            }
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!this.gameManager.getVanishList().contains(p.getUniqueId())) continue;
                player.showPlayer(p);
            }
            this.gameManager.getVanishList().add(player.getUniqueId());
            player.sendMessage("§eVous êtes vanish !");
        }
        return false;
    }
}
