package fr.pandonia.uhcapi.commands;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.commands.special.RulesInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RulesCommand
        implements CommandExecutor {
    private final API api;

    public RulesCommand(API api) {
        this.api = api;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player)commandSender;
            this.api.openInventory(player, RulesInventory.class);
        }
        return false;
    }
}
