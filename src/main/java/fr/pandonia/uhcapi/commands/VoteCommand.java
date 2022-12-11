package fr.pandonia.uhcapi.commands;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.utils.msg.InteractiveMessage;
import fr.pandonia.uhcapi.utils.msg.TextComponentBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCommand
        implements CommandExecutor {
    private final GameManager gameManager;
    private final Map<UUID, Boolean> voted;
    private boolean vote;
    private int yes;
    private int no;

    public VoteCommand(GameManager gameManager) {
        this.gameManager = gameManager;
        this.voted = new HashMap<UUID, Boolean>();
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] arguments) {
        if (commandSender instanceof Player) {
            Player player = (Player)commandSender;
            if (arguments.length > 0) {
                if (arguments[0].equalsIgnoreCase("answer")) {
                    if (arguments.length >= 2) {
                        if (this.isVote()) {
                            if (!this.getVoted().containsKey(player.getUniqueId())) {
                                if (arguments[1].equalsIgnoreCase("yes")) {
                                    ++this.yes;
                                    this.getVoted().put(player.getUniqueId(), true);
                                    player.sendMessage("§aVous avez bien voté. §f(§f§lOui§f)");
                                } else if (arguments[1].equalsIgnoreCase("no")) {
                                    ++this.no;
                                    this.getVoted().put(player.getUniqueId(), true);
                                    player.sendMessage("§aVous avez bien voté. §f(§f§lNon§f)");
                                }
                            } else {
                                player.sendMessage("§cVous avez déjà voté.");
                            }
                        } else {
                            player.sendMessage("§cIl n'y a aucun vote en cours..");
                        }
                    }
                } else if (this.gameManager.hasHostAccess(player)) {
                    if (!this.isVote()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String msg : arguments) {
                            stringBuilder.append(msg + " ");
                        }
                        Bukkit.broadcastMessage("§f§m-----------------------------------------------");
                        Bukkit.broadcastMessage("§aUn vote a été proposé :");
                        Bukkit.broadcastMessage("§f§l» §b" + stringBuilder);
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("§fVeuillez cliquer ci-dessous pour y soumettre votre réponse :");
                        for (Player players : Bukkit.getOnlinePlayers()) {
                            new InteractiveMessage().add(new TextComponentBuilder("§a§lOUI").setHoverMessage("§f§l» §aCliquez ici pour voter Oui.").setClickAction(ClickEvent.Action.RUN_COMMAND, "/vote answer yes").build()).add(new TextComponentBuilder("        §c§lNON").setHoverMessage("§f§l» §cCliquez ici pour voter Non.").setClickAction(ClickEvent.Action.RUN_COMMAND, "/vote answer no").build()).sendMessage(players);
                        }
                        Bukkit.broadcastMessage("§f§m-----------------------------------------------");
                        this.setVote(true);
                        this.setYes(0);
                        this.setNo(0);
                        this.getVoted().clear();
                        for (Player players : Bukkit.getOnlinePlayers()) {
                            if (!this.getVoted().containsKey(players.getUniqueId())) continue;
                            this.getVoted().remove(players.getUniqueId());
                        }
                        Bukkit.getScheduler().runTaskLater(API.getAPI(), () -> {
                            this.setVote(false);
                            Bukkit.broadcastMessage("§6§l------------");
                            Bukkit.broadcastMessage("§fRéponse du §lvote§f:");
                            Bukkit.broadcastMessage(" §f» §aOui§f: " + this.getYes());
                            Bukkit.broadcastMessage(" §f» §cNon§f: " + this.getNo());
                            Bukkit.broadcastMessage("§6§l------------");
                        }, 300L);
                    } else {
                        player.sendMessage("§cIl y' a déjà un vote en cours.");
                    }
                }
            }
        }
        return true;
    }

    public int getYes() {
        return this.yes;
    }

    public void setYes(int yes) {
        this.yes = yes;
    }

    public int getNo() {
        return this.no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Map<UUID, Boolean> getVoted() {
        return this.voted;
    }

    public boolean isVote() {
        return this.vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }
}
