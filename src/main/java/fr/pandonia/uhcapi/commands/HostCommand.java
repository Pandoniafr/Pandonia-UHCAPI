package fr.pandonia.uhcapi.commands;

import com.google.common.base.Joiner;
import fr.pandonia.uhcapi.GamePlayer;
import fr.pandonia.uhcapi.UHCInfos;
import fr.pandonia.uhcapi.common.player.PlayerUtils;
import fr.pandonia.uhcapi.common.rules.Rules;
import fr.pandonia.uhcapi.config.ConfigMainGUI;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.game.GameUtils;
import fr.pandonia.uhcapi.utils.CommonString;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HostCommand
        implements CommandExecutor {
    private final GameManager gameManager;

    public HostCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] arguments) {
        Player player;
        if (sender instanceof Player && this.gameManager.hasHostAccess(player = (Player)sender)) {
            if (arguments.length == 0) {
                this.sendHelp(player);
                return true;
            }
            switch (arguments[0]) {
                case "help": {
                    this.sendHelp(player);
                    break;
                }
                case "say": {
                    if (arguments.length == 1) {
                        player.sendMessage("§8┃ §4/§chost say <message> §f: §fEnvoyer un message d'annonce.");
                        break;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String str : arguments) {
                        stringBuilder.append(str).append(" ");
                    }
                    Bukkit.broadcastMessage("§a");
                    Bukkit.broadcastMessage("§c§lHOST " + player.getName() + " §f: " + stringBuilder.toString().replaceFirst("say", ""));
                    Bukkit.broadcastMessage("§a");
                    break;
                }
                case "name": {
                    if (arguments.length == 1) {
                        player.sendMessage("§8┃ §4/§chost name <message> §f: §fChanger le nom de l'host.");
                        break;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String str : arguments) {
                        stringBuilder.append(str).append(" ");
                    }
                    String name = stringBuilder.toString().replace("name", "").replace("&", "§").substring(1);
                    if (name.length() <= 32) {
                        player.sendMessage("§6§lOerthyon §8§l• §fVous venez de changer le nom de votre serveur en §6" + name);
                        break;
                    }
                    player.sendMessage("§6§lOerthyon §8§l• §fVous ne pouvez pas changer le nom de l'host, si vous mettez plus de 32 caractères.");
                    break;
                }
                case "chat": {
                    this.gameManager.getGameConfig().setChat(!this.gameManager.getGameConfig().isChat());
                    player.sendMessage("§8┃ §cLe chat est désormais " + (this.gameManager.getGameConfig().isChat() ? "§aactivé" : "§cdésactivé") + "§c.");
                    break;
                }
                case "give": {
                    if (GameUtils.isGameStarted()) {
                        if (arguments.length == 3) {
                            Material itemType = Material.BEDROCK;
                            switch (arguments[1]) {
                                case "log": {
                                    itemType = Material.LOG;
                                    break;
                                }
                                case "anvil": {
                                    itemType = Material.ANVIL;
                                    break;
                                }
                                case "xp": {
                                    itemType = Material.EXP_BOTTLE;
                                    break;
                                }
                                case "cobblestone": {
                                    itemType = Material.COBBLESTONE;
                                    break;
                                }
                                case "apple": {
                                    itemType = Material.APPLE;
                                    break;
                                }
                                case "arrow": {
                                    itemType = Material.ARROW;
                                    break;
                                }
                                case "book": {
                                    itemType = Material.BOOK;
                                    break;
                                }
                                case "cooked_beef": {
                                    itemType = Material.COOKED_BEEF;
                                }
                            }
                            if (itemType.equals(Material.BEDROCK)) {
                                player.sendMessage("§cObjet invalide.");
                                return false;
                            }
                            int quantity = 1;
                            try {
                                quantity = Integer.parseInt(arguments[2]);
                            }
                            catch (NumberFormatException e) {
                                player.sendMessage("§cMontant invalide.");
                            }
                            ItemStack giveItem = new ItemStack(itemType, quantity);
                            for (UUID uuid : this.gameManager.getInGamePlayers()) {
                                Player players2 = Bukkit.getPlayer(uuid);
                                if (players2 == null) continue;
                                players2.getInventory().addItem(new ItemStack[]{giveItem});
                            }
                            player.sendMessage("§aObjet donné à tous les joueurs.");
                            break;
                        }
                        player.sendMessage("§8┃ §4/§chost give §8[§7log§8/§7anvil§8/§7xp§8/§7cobblestone§8/§7apple§8/§7arrow§8/§7book§8/§7cooked_beef§8] §8<§7quantité§8> §8: §fDonne un ou plusieurs objets à tous les joueurs de la partie.");
                        break;
                    }
                    player.sendMessage("§cLa partie n'a pas commencée.");
                    break;
                }
                case "heal": {
                    if (GameUtils.isGameStarted()) {
                        if (arguments.length == 1) {
                            player.sendMessage("§8┃ §4/§chost heal §8[§7all§8:§7joueur§8] §8: §fSoigner tous les joueurs ou un joueur ciblé.");
                            break;
                        }
                        if (arguments[1].equalsIgnoreCase("all")) {
                            Bukkit.getOnlinePlayers().forEach(players -> {
                                players.setHealth(players.getMaxHealth());
                                players.setFoodLevel(20);
                            });
                            player.sendMessage("§8┃ §aVous avez soigné tous les joueurs.");
                            break;
                        }
                        Player player1 = Bukkit.getPlayer(arguments[1]);
                        if (player1 != null) {
                            player1.setHealth(player1.getMaxHealth());
                            player1.setFoodLevel(20);
                            player.sendMessage("§8┃ §aVous avez soigné §c" + player1.getName() + "§a.");
                            break;
                        }
                        player.sendMessage("§cAucun joueur avec le pseudo '" + arguments[1] + "' n'a été trouvé.");
                        break;
                    }
                    player.sendMessage("§cLa partie n'a pas commencée.");
                    break;
                }
                case "config": {
                    if (!player.isOp() && (this.gameManager.getGameHost() == null || !this.gameManager.getGameHost().equals(player.getUniqueId()))) break;
                    this.gameManager.getApi().openInventory(player, ConfigMainGUI.class);
                    break;
                }
                case "set": {
                    if (GameUtils.isGameStarted()) {
                        player.sendMessage("§cLa partie a déjà commencée, action impossible.");
                        return false;
                    }
                    if (player.isOp()) {
                        if (arguments.length == 1) {
                            player.sendMessage("§c §8┃ §4/§chost set §8(§7joueur§8) §8: §fChanger l'host à la partie.");
                            break;
                        }
                        Player player1 = Bukkit.getPlayer(arguments[1]);
                        if (player1 != null) {
                            Player oldHost = Bukkit.getPlayer(this.gameManager.getGameHost());
                            if (oldHost != null) {
                                oldHost.closeInventory();
                                oldHost.getInventory().remove(Material.REDSTONE_COMPARATOR);
                                GamePlayer.getPlayer(oldHost.getUniqueId()).setEditing(false);
                                oldHost.setGameMode(GameMode.ADVENTURE);
                                PlayerUtils.giveDefaultItems(oldHost);
                            }
                            this.gameManager.setGameHost(player1.getUniqueId());
                            PlayerUtils.giveDefaultItems(player1);
                            UHCInfos.hostName = player1.getName();
                            player.sendMessage("§cLe nouvel §6§lHost §cde la partie est désormais §6" + player1.getName() + "§c.");
                            player1.sendMessage("§cVous êtes le nouvel §6§lHost §cde la partie !");
                            break;
                        }
                        player.sendMessage("§cAucun joueur avec le pseudo '" + arguments[1] + "' n'a été trouvé.");
                        break;
                    }
                    this.sendHelp(player);
                    break;
                }
                case "add": {
                    if (GameUtils.isGameStarted()) {
                        player.sendMessage("§cLa partie a déjà commencée, action impossible.");
                        return false;
                    }
                    if (this.gameManager.getGameHost().equals(player.getUniqueId()) || player.isOp()) {
                        if (arguments.length == 1) {
                            player.sendMessage("§c §8┃ §4/§chost add §8(§7joueur§8) §8: §fAjouter un co-host.");
                            break;
                        }
                        Player player1 = Bukkit.getPlayer(arguments[1]);
                        if (player1 != null) {
                            if (player.getUniqueId().equals(player1.getUniqueId())) {
                                player.sendMessage("§cVous ne pouvez pas vous ajouter aux hosts vous-même.");
                                return true;
                            }
                            if (this.gameManager.getHosts().contains(player1.getUniqueId())) {
                                player.sendMessage("§cCe joueur est déjà co-host.");
                                return true;
                            }
                            this.gameManager.getHosts().add(player1.getUniqueId());
                            PlayerUtils.giveHostItems(player1);
                            player.sendMessage("§cVous avez ajouté §6" + player1.getName() + "§c en tant que co-host.");
                            player1.sendMessage("§cVous faites partie des co-hosts.");
                            break;
                        }
                        player.sendMessage("§cAucun joueur avec le pseudo '" + arguments[1] + "' n'a été trouvé.");
                        break;
                    }
                    this.sendHelp(player);
                    break;
                }
                case "remove": {
                    if (GameUtils.isGameStarted()) {
                        player.sendMessage("§cLa partie a déjà commencée, action impossible.");
                        return false;
                    }
                    if (this.gameManager.getGameHost().equals(player.getUniqueId()) || player.isOp()) {
                        if (arguments.length == 1) {
                            player.sendMessage("§c §8┃ §4/§chost remove §8(§7joueur§8) §f: Retirer un co-host.");
                            break;
                        }
                        Player player1 = Bukkit.getPlayer(arguments[1]);
                        if (player1 != null) {
                            if (player.getUniqueId().equals(player1.getUniqueId())) {
                                player.sendMessage("§cVous ne pouvez pas vous ajouter aux co-hosts");
                                return true;
                            }
                            if (!this.gameManager.getHosts().contains(player1.getUniqueId())) {
                                player.sendMessage("§cCe joueur n'est pas co-host.");
                                return true;
                            }
                            this.gameManager.getHosts().remove(player1.getUniqueId());
                            player1.getInventory().remove(Material.REDSTONE_COMPARATOR);
                            player.sendMessage("§cVous avez retiré §6" + player1.getName() + "§c des co-hosts.");
                            player1.sendMessage("§cVous n'êtes plus co-host.");
                            break;
                        }
                        player.sendMessage("§cAucun joueur avec le pseudo '" + arguments[1] + "' n'a été trouvé.");
                        break;
                    }
                    this.sendHelp(player);
                    break;
                }
                case "liste":
                case "list": {
                    if (GameUtils.isGameStarted()) {
                        player.sendMessage("§cLa partie a déjà commencée, action impossible.");
                        return false;
                    }
                    if (this.gameManager.getHosts().size() == 0) {
                        player.sendMessage("§cIl n'y a pas de co-host.");
                        return true;
                    }
                    ArrayList<String> hosts = new ArrayList<String>();
                    String msg = " §8┃  §7Liste des co-hosts §f: ";
                    for (UUID uuid : this.gameManager.getHosts()) {
                        hosts.add(Bukkit.getPlayer(uuid).getName());
                    }
                    player.sendMessage(CommonString.BAR.getMessage());
                    if (hosts.size() == 1) {
                        player.sendMessage(" §8┃  §7Liste des co-hosts §f: §e" + (String)hosts.get(0));
                    } else {
                        player.sendMessage(" §8┃  §7Liste des co-hosts §f: " + Joiner.on("§f, §e").join(hosts.subList(0, hosts.size() - 1)).concat(" §fet §e").concat((String)hosts.get(hosts.size() - 1)));
                    }
                    player.sendMessage(CommonString.BAR.getMessage());
                    break;
                }
                case "force": {
                    if (GameUtils.isGameStarted()) {
                        if (arguments.length == 2) {
                            if (arguments[1].equalsIgnoreCase("pvp")) {
                                if (Rules.pvp.isActive()) {
                                    player.sendMessage("§cErreur: Le PvP est déjà activé.");
                                    break;
                                }
                                Rules.pvp.setActive(true);
                                break;
                            }
                            if (!arguments[1].equalsIgnoreCase("bordure") && !arguments[1].equalsIgnoreCase("border")) break;
                            if (this.gameManager.getBorder().isStart()) {
                                player.sendMessage("§cErreur: La bordure est déjà activée.");
                                break;
                            }
                            this.gameManager.getBorder().startReduce(this.gameManager.getGameConfig().getBorderEndSize() * 2, this.gameManager.getGameConfig().getBorderBlocksPerSecond());
                            break;
                        }
                        player.sendMessage("§8┃ §4/§chost force §8[§7pvp§8:§7bordure§8] §8: §fVous permet d'activer le PvP ou la bordure de force.");
                        break;
                    }
                    player.sendMessage("§cLa partie n'a pas commencée.");
                    break;
                }
                case "killoffline": {
                    if (arguments.length == 1) {
                        if (this.gameManager.getOfflinePlayers().size() == 0) {
                            player.sendMessage("§cErreur: Il n'y a aucun joueur déconnecté.");
                            return false;
                        }
                        for (UUID uuid : this.gameManager.getOfflinePlayers()) {
                            this.gameManager.getApi().getModules().onPlayerDieByDisconnect(uuid);
                        }
                        this.gameManager.getOfflinePlayers().clear();
                        player.sendMessage("§cTous les joueurs déconnectés ont été éliminés !");
                        break;
                    }
                    String target = arguments[1];
                    GamePlayer gamePlayer = GamePlayer.getPlayer(target);
                    if (gamePlayer != null) {
                        if (this.gameManager.getOfflinePlayers().contains(gamePlayer.getUuid())) {
                            this.gameManager.getApi().getModules().onPlayerDieByDisconnect(gamePlayer.getUuid());
                            player.sendMessage("§c§l" + target + " §ca été éliminé.");
                            this.gameManager.getOfflinePlayers().remove(gamePlayer.getUuid());
                            break;
                        }
                        player.sendMessage("§cCe joueur n'est pas déconnecté.");
                        break;
                    }
                    player.sendMessage("§cCe joueur n'est pas déconnecté ou n'existe pas.");
                    break;
                }
                case "revive": {
                    if (arguments.length == 2) {
                        Player player1 = Bukkit.getPlayer(arguments[1]);
                        if (player1 == null) break;
                        if (this.gameManager.getGameConfig().getRoleTime() != 0 && this.gameManager.getGlobalTask().getGlobalTime() < this.gameManager.getGameConfig().getRoleTime()) {
                            Bukkit.dispatchCommand(player, "revive " + player1.getName());
                            player.sendMessage("§a" + player1.getName() + " a été ressuscité !");
                            break;
                        }
                        player.sendMessage("§cVous ne pouvez pas ressusciter un joueur après l'annonce des rôles.");
                        break;
                    }
                    player.sendMessage("§8┃ §4/§chost revive §8(§7joueur§8) §8: §fRessuscite le joueur ciblé.");
                    break;
                }
                case "kick": {
                    if (this.gameManager.getGameHost().equals(player.getUniqueId()) || player.isOp()) {
                        if (arguments.length == 1) {
                            player.sendMessage("§c §8┃ §4/§chost kick §8(§7joueur§8) §8: §fKick le joueur ciblé.");
                            break;
                        }
                        Player player1 = Bukkit.getPlayer(arguments[1]);
                        if (player1 == null) {
                            player.sendMessage("§cAucun joueur avec le pseudo '" + arguments[1] + "' n'a été trouvé.");
                            return true;
                        }
                        if (player.getUniqueId().equals(player1.getUniqueId())) {
                            player.sendMessage("§cVous ne pouvez pas le faire sur vous-même.");
                            return true;
                        }
                        if (this.isStaff(player1.getName())) {
                            player.sendMessage("§cVous ne pouvez pas kick un staff.");
                            return true;
                        }
                        player.sendMessage("§cVous avez expulsé §6" + player1.getName() + "§c de la partie.");
                        player1.kickPlayer("§cVous êtes explusé de la partie !");
                        break;
                    }
                    this.sendHelp(player);
                }
            }
        }
        return false;
    }

    private void sendHelp(Player player) {
        player.sendMessage("§8┃ §4/§chost give §8[§7log§8/§7anvil§8/§7xp§8/§7cobblestone§8/§7apple§8/§7arrow§8/§7book§8/§7cooked_beef§8] §8<§7quantité§8> §8: §fDonne un ou plusieurs objets à tous les joueurs de la partie.");
        player.sendMessage("§8┃ §4/§chost heal §8[§7all§8:§7joueur§8] §8: §fSoigner tous les joueurs ou un joueur ciblé.");
        player.sendMessage("§8┃ §4/§chost chat §8: §fActiver/désactiver le chat de la partie.");
        player.sendMessage("§8┃ §4/§chost force §8[§7pvp§8:§7bordure§8] §8: §fVous permet d'activer le PvP ou la bordure de force.");
        player.sendMessage("§8┃ §4/§chost killoffline §8(§7joueur§8) §8: §fÉlimine tous les joueurs déconnectés ou le joueur ciblé.");
        player.sendMessage("§8┃ §4/§chost revive §8(§7joueur§8) §8: §fRessuscite le joueur ciblé.");
        player.sendMessage("§8┃ §4/§chost §4[§cadd§4/§cremove§4/§clist§4] §8(§7joueur§8) §8: §fGestion des co-hosts.");
        player.sendMessage("§8┃ §4/§chost say §8<§7message§8> §8: §fEnvoyer un message d'annonce.");
        player.sendMessage("§8┃ §4/§chost name §8<§7titre§8> §8: §fChanger le nom de l'host.");
        player.sendMessage("§8┃ §4/§chost kick §8(§7joueur§8) §8: §fKick le joueur ciblé.");
        if (player.isOp()) {
            player.sendMessage("§c§lOP §f▎ §4/§chost set §8(§7joueur§8) §8: §fDéfinir l'host de la partie.");
        }
    }

    private boolean isStaff(String username) {
        Player player = Bukkit.getPlayer(username);
        if (player == null) {
            return false;
        }
        return player.hasPermission("uhc.staff");
    }
}
