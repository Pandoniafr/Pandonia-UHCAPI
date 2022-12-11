package fr.pandonia.uhcapi.listener;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.commands.ScenarioCommand;
import fr.pandonia.uhcapi.config.ConfigMainGUI;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.game.GameUtils;
import fr.pandonia.uhcapi.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class PlayerInteractListener implements Listener {
    private final GameManager gameManager;

    public PlayerInteractListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    private void BlockPhysicsEvent(BlockPhysicsEvent event) {
        if (!GameUtils.isGameStarted())
            event.setCancelled(true);
    }

    @EventHandler
    private void BlockSpreadEvent(BlockSpreadEvent event) {
        if (!GameUtils.isGameStarted())
            event.setCancelled(true);
    }

    @EventHandler
    private void BlockBurnEvent(BlockBurnEvent event) {
        if (!GameUtils.isGameStarted())
            event.setCancelled(true);
    }

    @EventHandler
    private void PlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemStack = event.getItem();
        Block block = event.getClickedBlock();
        if (player != null && block != null && !GameUtils.isGameStarted() &&
                !player.getGameMode().equals(GameMode.CREATIVE))
            switch (block.getType()) {
                case TRAP_DOOR:
                case DISPENSER:
                case DROPPER:
                case FENCE_GATE:
                case LEVER:
                case STONE_BUTTON:
                case WOOD_BUTTON:
                case WORKBENCH:
                case CHEST:
                case TRAPPED_CHEST:
                case ENDER_CHEST:
                case FURNACE:
                case ANVIL:
                case HOPPER:
                case DARK_OAK_DOOR:
                case ACACIA_DOOR:
                case BIRCH_DOOR:
                case JUNGLE_DOOR:
                case SPRUCE_DOOR:
                case WOOD_DOOR:
                case WOODEN_DOOR:
                case NOTE_BLOCK:
                case SPRUCE_FENCE_GATE:
                case ACACIA_FENCE_GATE:
                case BIRCH_FENCE_GATE:
                case DARK_OAK_FENCE_GATE:
                case JUNGLE_FENCE_GATE:
                    event.setCancelled(true);
                    break;
            }
        if (player == null || itemStack == null || itemStack.getType() == Material.AIR)
            return;
        if ((action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) &&
                !GameUtils.isGameStarted())
            switch (itemStack.getType()) {
                case REDSTONE_COMPARATOR:
                    if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().contains("§b§lConfigurer la partie §8§l● §f§lClic-droit")) {
                        event.setCancelled(true);
                    this.gameManager.getApi().openInventory(player, ConfigMainGUI.class);
            }
        break;
        case BED:
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().contains("§c§lRetourner au lobby §8§l● §f§lClic-droit")) {
                player.getInventory().setItem(8, (new ItemCreator(Material.BED)).setName("§c§lConfirmation §8§l● §f§lClic-droit").addItemFlags(ItemFlag.HIDE_ENCHANTS).getItem());
                        player.sendMessage("§3§lOerthyon §8§l● §fRe-Cliquez pour être §ctéléporté§f au lobby...");
        Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin) API.getAPI(), () -> player.getInventory().setItem(8, (new ItemCreator(Material.BED)).setName("§c§lRetourner au lobby §8§l● §f§lClic-droit").addItemFlags(ItemFlag.HIDE_ENCHANTS).getItem()), 60L);
        break;
    }
          if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().contains("§c§lConfirmation §8§l● §f§lClic-droit"))
              player.kickPlayer("§cAucun lobby n'est disponible");
          break;
        case BOOK:
            if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().contains("§a§lScénarios §8§l● §f§lClic-droit")) {
                event.setCancelled(true);
            this.gameManager.getApi().openInventory(player, ScenarioCommand.class);
}
          break;
                  case NETHER_STAR:
                  if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().contains("§c§lRetourner au début du jump §8§l● §f§lClic-droit")) {
                  event.setCancelled(true);
                  player.teleport(new Location(player.getWorld(), 0.0D, 100.0D, 1.0D));
                  }
                  break;
                  case BANNER:
                  if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName() && itemStack.getItemMeta().getDisplayName().contains("Choisir une équipe")) {
                  event.setCancelled(true);
                  this.gameManager.getApi().getCommon().getJoinTeamsGUI().openInventory(player, 1);
                  }
                  break;
                  }
                  }

@EventHandler
public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
        Inventory inv = event.getInventory();
        Player player = (Player)event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();
        InventoryAction action = event.getAction();
        if (itemStack == null || player == null || action == null || inv == null)
        return;
        this.gameManager.getApi().getRegisteredInventories().values().stream().filter(menu -> inv.getName().equalsIgnoreCase(menu.getName())).forEach(menu -> {
        menu.onClick(player, inv, itemStack, event.getSlot(), event.getClick());
        event.setCancelled(true);
        });
        }
        }
        }
