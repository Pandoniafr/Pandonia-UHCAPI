package fr.pandonia.uhcapi.common.player;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.game.GameUtils;
import fr.pandonia.uhcapi.game.team.TeamManager;
import fr.pandonia.uhcapi.module.ModuleType;
import fr.pandonia.uhcapi.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class PlayerUtils {
    public static void giveDefaultItems(Player player) {
        TeamManager teamManager = API.getAPI().getGameManager().getTeamManager();
        if (API.getAPI().getGameManager().getModuleManager().getCurrentModule().isHasRole()) {
            player.getInventory().setItem(1, new ItemCreator(Material.SKULL_ITEM).setDurability((short)3).setName("§6§lListe des rôles §8§l▪ §f§lClic-droit").setSkullURL("http://textures.minecraft.net/texture/d92dde9fff32f7c6d13818754b361e95cb98a19c482d5c535ccfe0c185bc6").addItemFlags(ItemFlag.HIDE_ENCHANTS).getItem());
        }
        if (API.getAPI().getGameManager().getModuleManager().getCurrentModule().equals((Object) ModuleType.DEMONSLAYER)) {
            player.getInventory().setItem(0, new ItemCreator(Material.NETHER_STAR).setName("§c§lRetourner au début du jump §8§l▪ §f§lClic-droit").getItem());
        }
        player.getInventory().setItem(8, new ItemCreator(Material.BED).setName("§c§lRetourner au lobby §8§l▪ §f§lClic-droit").addItemFlags(ItemFlag.HIDE_ENCHANTS).getItem());
        player.getInventory().setItem(2, new ItemCreator(Material.BOOK).setName("§a§lScénarios §8§l▪ §f§lClic-droit").addItemFlags(ItemFlag.HIDE_ENCHANTS).getItem());
        if (!GameUtils.isSoloMode()) {
            player.getInventory().setItem(6, new ItemCreator(Material.BANNER).setDurability(teamManager.getPlayerTeam().containsKey(player.getUniqueId()) ? teamManager.getPlayerTeam().get(player.getUniqueId()).getDataitem() : 15).setName("§f§lChoisir une équipe §8§l▪ §f§lClic-droit").getItem());
        } else {
            player.getInventory().remove(Material.BANNER);
        }
        PlayerUtils.giveHostItems(player);
    }

    public static void giveHostItems(Player player) {
        if (API.getAPI().getGameManager().getGameHost() != null && (API.getAPI().getGameManager().getGameHost().equals(player.getUniqueId()) || API.getAPI().getGameManager().getHosts().contains(player.getUniqueId()))) {
            player.getInventory().setItem(4, new ItemCreator(Material.REDSTONE_COMPARATOR).setName("§b§lConfigurer la partie §8§l▪ §f§lClic-droit").addEnchantment(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).getItem());
        }
    }

    public static void setAbsoHearths(Player p, int coeur) {
        ((CraftPlayer)((Object)p)).getHandle().setAbsorptionHearts(coeur);
    }

    public static double getAbsoHearths(Player p) {
        return ((CraftPlayer)((Object)p)).getHandle().getAbsorptionHearts();
    }

    public static void makePlayerSeePlayersHealthAboveHead(Player player) {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.getObjective("HP") == null ? scoreboard.registerNewObjective("HP", "health") : scoreboard.getObjective("HP");
        objective.setDisplayName(ChatColor.RED + "❤");
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        player.setScoreboard(scoreboard);
    }

    public static void stopSeeHealthHead(Player player) {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = player.getScoreboard();
        player.getScoreboard().clearSlot(DisplaySlot.BELOW_NAME);
    }
}
