package fr.pandonia.uhcapi.commands;

import fr.pandonia.uhcapi.GamePlayer;
import fr.pandonia.uhcapi.common.player.PlayerUtils;
import fr.pandonia.uhcapi.config.common.DefaultDeathInvGUI;
import fr.pandonia.uhcapi.config.common.DefaultInvGUI;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.utils.InventoryAPI;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FinishCommand
        implements CommandExecutor {
    private final GameManager gameManager;

    public FinishCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player)commandSender;
            GamePlayer gamePlayer = GamePlayer.getPlayer(player.getUniqueId());
            if (gamePlayer.isEditing()) {
                InventoryAPI.saveInventory(player);
                gamePlayer.setEditing(false);
                player.sendMessage("§fL'inventaire par §fdéfaut a été modifié avec succès.");
                player.setGameMode(GameMode.ADVENTURE);
                player.getInventory().clear();
                player.getInventory().setArmorContents(null);
                PlayerUtils.giveDefaultItems(player);
                player.getInventory().setHeldItemSlot(4);
                this.gameManager.getApi().openInventory(player, DefaultInvGUI.class);
            } else if (gamePlayer.isEditingDeathInv()) {
                InventoryAPI.saveDeathInventory(player);
                gamePlayer.setEditingDeathInv(false);
                player.sendMessage("§fL'inventaire de §cmort§f a été modifié avec succès.");
                player.setGameMode(GameMode.ADVENTURE);
                player.getInventory().clear();
                player.getInventory().setArmorContents(null);
                PlayerUtils.giveDefaultItems(player);
                player.getInventory().setHeldItemSlot(4);
                this.gameManager.getApi().openInventory(player, DefaultDeathInvGUI.class);
            } else {
                player.sendMessage("§cVous n'êtes pas en train de définir l'inventaire par défaut..");
            }
        }
        return false;
    }
}
