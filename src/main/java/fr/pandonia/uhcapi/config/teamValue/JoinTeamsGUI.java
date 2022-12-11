package fr.pandonia.uhcapi.config.teamValue;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.GameConfig;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.game.team.Teams;
import fr.pandonia.uhcapi.utils.ItemCreator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class JoinTeamsGUI
        implements Listener {
    private final API api;
    private final GameManager gameManager;
    public static Map<Player, Integer> player_page = new HashMap<Player, Integer>();

    public JoinTeamsGUI(API api) {
        this.api = api;
        this.gameManager = api.getGameManager();
    }

    public Inventory openInventory(Player player, int page) {
        player_page.put(player, page);
        Inventory inventory = Bukkit.createInventory(null, 54, "Équipes (" + page + "/2)");
        int[] glass = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
        ItemStack glassItem = new ItemCreator(Material.STAINED_GLASS_PANE).setDurability(15).getItem();
        for (int i : glass) {
            inventory.setItem(i, glassItem);
        }
        GameConfig gameConfig = this.api.getGameManager().getGameConfig();
        if (!Scenario.RANDOMTEAM.isEnabled()) {
            if (gameConfig.isShowAllTeams()) {
                for (Teams teams : Teams.values()) {
                    if (teams.getPage() != page) continue;
                    inventory.setItem(teams.getSlot(), this.getItem(teams));
                }
            } else {
                int slot = gameConfig.getGameSlot();
                int player_per_team = gameConfig.getPlayerPerTeam();
                ArrayList<Teams> available_teams = new ArrayList<Teams>();
                for (Teams teams : Teams.values()) {
                    available_teams.add(teams);
                }
                double result = slot / player_per_team;
                result += 1.0;
                int i = 0;
                while ((double)i < result) {
                    Teams teams;
                    teams = (Teams)((Object)available_teams.remove(0));
                    inventory.setItem(teams.getSlot(), this.getItem(teams));
                    ++i;
                }
            }
        } else {
            inventory.setItem(22, new ItemCreator(Material.BARRIER).setName("§cLe scénario RandomTeam est activé.").getItem());
        }
        if (page == 1 && inventory.getItem(43) != null && inventory.getItem(43).hasItemMeta() && inventory.getItem(43).getType() == Material.BANNER) {
            inventory.setItem(51, new ItemCreator(Material.PAPER).setName("§fPage suivante").getItem());
        }
        if (page > 1) {
            inventory.setItem(47, new ItemCreator(Material.PAPER).setName("§fPage précédente").getItem());
        }
        inventory.setItem(49, new ItemCreator(Material.ARROW).setName("§fRevenir en arrière").getItem());
        player.openInventory(inventory);
        return inventory;
    }

    @EventHandler
    private void onClick(InventoryClickEvent event) {
        Player player = (Player)((Object)event.getWhoClicked());
        ItemStack itemStack = event.getCurrentItem();
        Inventory inventory = event.getInventory();
        if (player == null || inventory == null || itemStack == null || itemStack.getType().equals(Material.AIR)) {
            return;
        }
        if (inventory.getName().contains("Équipes")) {
            event.setCancelled(true);
            int page = player_page.get(player);
            int slot = event.getSlot();
            block0 : switch (itemStack.getType()) {
                case ARROW: {
                    player.closeInventory();
                    break;
                }
                case BANNER: {
                    player.closeInventory();
                    for (Teams teams : Teams.values()) {
                        if (teams.getPage() != page || teams.getSlot() != slot) continue;
                        this.gameManager.getTeamManager().addPlayerToTeam(player, teams);
                        break block0;
                    }
                    break;
                }
                case PAPER: {
                    if (!itemStack.hasItemMeta()) break;
                    if (itemStack.getItemMeta().getDisplayName().contains("précédente")) {
                        if (page > 1) {
                            --page;
                        }
                    } else {
                        ++page;
                    }
                    this.openInventory(player, page);
                }
            }
        }
    }

    public ItemStack getItem(Teams teams) {
        ItemCreator item = teams.getItem();
        item.addLore("");
        item.addLore("§8Membre" + (this.gameManager.getTeamManager().getPlayerAmountInTeam(teams) > 1 ? "s" : "") + ":");
        for (Player pl : this.gameManager.getTeamManager().getPlayersInTeam(teams)) {
            item.addLore("  §f- " + teams.getColor() + pl.getName());
        }
        for (int j = this.gameManager.getTeamManager().getPlayersInTeam(teams).size(); j < this.gameManager.getGameConfig().getPlayerPerTeam(); ++j) {
            item.addLore("  §f- §8[Emplacement vide]");
        }
        item.addLore("");
        item.addLore("§f» §eCliquez pour rejoindre.");
        return item.getItem();
    }
}
