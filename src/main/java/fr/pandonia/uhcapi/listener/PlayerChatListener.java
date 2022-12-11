package fr.pandonia.uhcapi.listener;

import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.game.GameUtils;
import fr.pandonia.uhcapi.game.team.Teams;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class PlayerChatListener implements Listener {
    private final GameManager gameManager;

    private final List<Player> cooldown;

    private final Map<Player, String> lastMessage;

    public PlayerChatListener(GameManager gameManager) {
        this.gameManager = gameManager;
        this.cooldown = new ArrayList<>();
        this.lastMessage = new HashMap<>();
    }

    @EventHandler
    private void onPlayerChat(AsyncPlayerChatEvent event) {
        String[] gg;
        Random random;
        int r;
        String congrats;
        Player player = event.getPlayer();
        if (event.isCancelled())
            return;
        String message = event.getMessage();
        event.setCancelled(true);
        switch (this.gameManager.getGameState()) {
            case WAITING:
            case STARTING:
                if (!player.isOp()) {
                    if (this.lastMessage.containsKey(player) && ((String)this.lastMessage
                            .get(player)).equalsIgnoreCase(message)) {
                        player.sendMessage("§f[§eFlood§f] §fVous avez déjà envoyé ce message, n'insistez pas.");
                        return;
                    }
                    if (this.cooldown.contains(player)) {
                        player.sendMessage("§f[§eFlood§f] §fVeuillez patienter avant de pouvoir renvoyer un message.");
                        return;
                    }
                }
                if (this.gameManager.getGameConfig().isChat()) {
                    if (!GameUtils.isSoloMode() && this.gameManager.getTeamManager().getPlayerTeam().containsKey(player.getUniqueId())) {
                        Teams teams = (Teams)this.gameManager.getTeamManager().getPlayerTeam().get(player.getUniqueId());
                        for (Player players : Bukkit.getOnlinePlayers()) {
                            players.sendMessage(teams.getColor() + teams.getName() + " " + player.getName() + " §8»§f " + message
                                    .replaceAll(players.getName(), message.contains(players.getName()) ? ("§b@" + players.getName() + "§f") : players.getName()).replace(":)", "§e☺§r").replace("):", "§e☹§r"));
                            if (message.contains(players.getName()))
                                players.playSound(players.getLocation(), Sound.ORB_PICKUP, 5.0F, 0.0F);
                        }
                    } else {
                        for (Player players : Bukkit.getOnlinePlayers()) {
                            players.sendMessage((player.isOp() ? "§6§lORGA" : "§7") + player.getName() + " §8»§f " + message
                                    .replaceAll(players.getName(), message.contains(players.getName()) ? ("§b@"+ players.getName() + "§f") : players.getName()).replace(":)", "§e☺§r").replace("):", "§e☹§r"));
                            if (message.contains(players.getName()))
                                players.playSound(players.getLocation(), Sound.ORB_PICKUP, 5.0F, 0.0F);
                        }
                    }
                    this.lastMessage.put(player, event.getMessage());
                    this.cooldown.add(player);
                    Bukkit.getScheduler().runTaskLater((Plugin)this.gameManager.getApi(), () -> this.cooldown.remove(player), 40L);
                    break;
                }
                player.sendMessage("§cLe chat est actuellement désactivé");
                break;
            case TELEPORTATION:
                event.setCancelled(true);
                break;
            case PLAYING:
                event.setCancelled(true);
                this.gameManager.getApi().getModules().onPlayerChat(player, message);
                break;
            case FINISH:
                event.setCancelled(true);
                gg = new String[] { "§eGG!", "§aGG!", "§bGG!", "§cGG!", "§dGG!", "§fGG!" };
                random = new Random();
                r = random.nextInt(gg.length);
                congrats = gg[r];
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(congrats + " " + player.getName() + " §8»§f " + message.replaceAll(players.getName(), message.contains(players.getName()) ? ("§b@"+ players.getName() + "§f") : players.getName()).replace(":)", "§e☺§r").replace("):", "§e☹§r"));
                    if (message.contains(players.getName()))
                        players.playSound(players.getLocation(), Sound.ORB_PICKUP, 5.0F, 0.0F);
                }
                break;
        }
    }
}