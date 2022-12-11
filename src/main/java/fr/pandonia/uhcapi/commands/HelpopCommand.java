package fr.pandonia.uhcapi.commands;

import fr.pandonia.uhcapi.GamePlayer;
import fr.pandonia.uhcapi.utils.msg.InteractiveMessage;
import fr.pandonia.uhcapi.utils.msg.TextComponentBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpopCommand
        implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] arguments) {
        if (commandSender instanceof Player) {
            Player player = (Player)commandSender;
            if (arguments.length == 0) {
                player.sendMessage("§cErreur: Veuillez mettre un message.");
                return false;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (String string : arguments) {
                stringBuilder.append(string).append(" ");
            }
            player.sendMessage("§a§l[Help-Op] §fVotre demande a bien été envoyée aux organisateurs de la partie.");
            for (Player players : Bukkit.getOnlinePlayers()) {
                if (!this.canViewHelpop(players)) continue;
                players.sendMessage("§a§l[Help-Op] §6§l" + (this.canViewIdentity(player) ? player.getName() : "Anonyme") + " §e: §f" + stringBuilder);
                new InteractiveMessage().add(new TextComponentBuilder("§a§l[Téléporter]").setHoverMessage("§f§l» §aCliquez ici pour vous téléporter").setClickAction(ClickEvent.Action.RUN_COMMAND, "/tp " + player.getName()).build()).add(new TextComponentBuilder("        §c§l[Inventaire]").setHoverMessage("§f§l» §cCliquez ici pour voir l'inventaire").setClickAction(ClickEvent.Action.RUN_COMMAND, "/view " + player.getName()).build()).add(new TextComponentBuilder("        §6§l[Message]").setHoverMessage("§f§l» §6Cliquez ici pour envoyer un message").setClickAction(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + player.getName() + " ").build()).add(new TextComponentBuilder("        §b§l[Rôle]").setHoverMessage("§f§l» §bCliquez ici pour voir le rôle").setClickAction(ClickEvent.Action.RUN_COMMAND, "/ds who " + player.getName() + " ").build()).sendMessage(players);
            }
        }
        return false;
    }

    private boolean canViewHelpop(Player player) {
        return player.isOp();
    }

    private boolean canViewIdentity(Player player) {
        GamePlayer gamePlayer = GamePlayer.getPlayer(player.getUniqueId());
        return gamePlayer.isAlerts();
    }
}
