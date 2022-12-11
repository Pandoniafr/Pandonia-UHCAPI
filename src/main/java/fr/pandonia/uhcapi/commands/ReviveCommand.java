package fr.pandonia.uhcapi.commands;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.GamePlayer;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.game.GameUtils;
import fr.pandonia.uhcapi.game.team.Teams;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class ReviveCommand
        implements CommandExecutor {
    private final API api;

    public ReviveCommand(API api) {
        this.api = api;
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] arguments) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player)sender;
        if (arguments.length > 0) {
            if (GameUtils.isGameStarted()) {
                if (this.api.getGameManager().hasHostAccess(player)) {
                    Player target = Bukkit.getPlayer(arguments[0]);
                    if (target == null || !target.isOnline()) {
                        player.sendMessage("§cAucun joueur avec le pseudo '" + arguments[0] + "' n'a été trouvé.");
                        return false;
                    }
                    final GamePlayer gameTarget = GamePlayer.getPlayer(target.getUniqueId());
                    if (gameTarget.isAlive()) {
                        player.sendMessage("§cCe joueur n'est pas mort.");
                        return false;
                    }
                    player.sendMessage("§aVous avez ressucité " + target.getName() + ".");
                    if (!this.api.getGameManager().getInGamePlayers().contains(target.getUniqueId())) {
                        this.api.getGameManager().getInGamePlayers().add(target.getUniqueId());
                    }
                    gameTarget.setAlive(true);
                    gameTarget.setInvincible(true);
                    int size = (int)this.api.getGameManager().getBorder().getWorldBorder().getSize() / 2;
                    target.teleport(new Location((World)Bukkit.getWorlds().get(0), ThreadLocalRandom.current().nextInt(size), 150.0, ThreadLocalRandom.current().nextInt(size)));
                    target.setGameMode(GameMode.SURVIVAL);
                    target.setHealth(target.getMaxHealth());
                    target.setFoodLevel(20);
                    for (ItemStack itemStack : gameTarget.getPlayerInv()) {
                        if (itemStack == null || itemStack.getType() == Material.AIR) continue;
                        target.getInventory().addItem(new ItemStack[]{itemStack});
                    }
                    if (gameTarget.getPotionEffects().size() > 0) {
                        for (PotionEffect potionEffect : gameTarget.getPotionEffects()) {
                            target.addPotionEffect(potionEffect);
                        }
                    }
                    if (!GameUtils.isSoloMode()) {
                        Teams teams = gameTarget.getTeams();
                        GameManager gameManager = this.api.getGameManager();
                        gameManager.getTeamManager().getPlayerTeam().put(target.getUniqueId(), teams);
                        gameManager.getApi().getCommon().getScoreboard().getTeam(teams.getName()).addPlayer(target);
                        if (!gameManager.getAliveTeams().contains((Object)teams)) {
                            gameManager.getAliveTeams().add(teams);
                        }
                    }
                    target.getInventory().setHelmet(gameTarget.getPlayerArmor()[3]);
                    target.getInventory().setChestplate(gameTarget.getPlayerArmor()[2]);
                    target.getInventory().setLeggings(gameTarget.getPlayerArmor()[1]);
                    target.getInventory().setBoots(gameTarget.getPlayerArmor()[0]);
                    target.setLevel(gameTarget.getPlayerExp());
                    target.playSound(target.getLocation(), Sound.ORB_PICKUP, 5.0f, 0.0f);
                    target.sendMessage("§aVous avez été ressuscité par un organisateur de la partie !");
                    new BukkitRunnable(){
                        int current = 0;

                        public void run() {
                            if (this.current >= 15) {
                                gameTarget.setInvincible(false);
                                this.cancel();
                            }
                            ++this.current;
                        }
                    }.runTaskTimer(API.getAPI(), 0L, 20L);
                } else {
                    player.sendMessage("§cPermission insuffisante.");
                    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 3.0f, 0.0f);
                }
            } else {
                player.sendMessage("§cLa partie n'a pas commencée.");
            }
        } else {
            player.sendMessage("§cVeuillez fournir le nom d'un joueur.");
        }
        return false;
    }
}
