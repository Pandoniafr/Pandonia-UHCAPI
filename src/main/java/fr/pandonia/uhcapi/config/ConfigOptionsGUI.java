package fr.pandonia.uhcapi.config;

import fr.pandonia.uhcapi.config.common.CycleManagerGUI;
import fr.pandonia.uhcapi.config.common.DefaultDeathInvGUI;
import fr.pandonia.uhcapi.config.common.DefaultInvGUI;
import fr.pandonia.uhcapi.config.common.EnchantMaxGUI;
import fr.pandonia.uhcapi.config.common.ores.OresLimitGUI;
import fr.pandonia.uhcapi.config.common.potion.PotionManagerGUI;
import fr.pandonia.uhcapi.config.common.rules.DropItemRateGUI;
import fr.pandonia.uhcapi.config.common.rules.GeneralRulesGUI;
import fr.pandonia.uhcapi.config.intValue.DamageGUI;
import fr.pandonia.uhcapi.config.intValue.DeconnexionTimeGUI;
import fr.pandonia.uhcapi.config.timeValue.BorderTimeGUI;
import fr.pandonia.uhcapi.config.timeValue.PvPTimeGUI;
import fr.pandonia.uhcapi.config.value.CommonItems;
import fr.pandonia.uhcapi.config.value.OpenVar;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.utils.CommonString;
import fr.pandonia.uhcapi.utils.CustomInventory;
import fr.pandonia.uhcapi.utils.ItemCreator;
import java.util.function.Supplier;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ConfigOptionsGUI
        implements CustomInventory {
    private GameManager gameManager;

    public ConfigOptionsGUI(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public String getName() {
        return "§f(§c!§f) §cOptions";
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        Integer[] glass;
        ItemStack[] slots = new ItemStack[this.getSlots()];
        Integer[] arrayOfInteger1 = glass = new Integer[]{0, 1, 7, 8, 9, 17, 27, 35, 36, 37, 43, 44};
        int i = arrayOfInteger1.length;
        for (int b = 0; b < i; b = (int)((byte)(b + 1))) {
            int j = arrayOfInteger1[b];
            slots[j] = new ItemCreator(Material.STAINED_GLASS_PANE).setDurability(11).setName("§f").getItem();
        }
        slots[10] = OpenVar.PVP_TIME.getItem();
        slots[11] = OpenVar.BORDER_TIME.getItem();
        slots[15] = new ItemCreator(Material.DIAMOND).setName("§8┃ §fLimite de §eminerais").addLore("").addLore("  §8┃ §fVous permet de limiter le nombre").addLore("  §8┃ §fde diamants et d'ors minables.").addLore("").addLore(CommonString.CLICK_HERE_TO_ACCESS.getMessage()).addLore("").getItem();
        slots[16] = new ItemCreator(Material.ENCHANTED_BOOK).setName("§8┃ §fLimite d'§benchantements").addLore("").addLore("  §8┃ §fVous permet de définir").addLore("  §8┃ §fla limite des tous").addLore("  §8┃ §fles enchantements.").addLore("").addLore(CommonString.CLICK_HERE_TO_MODIFY.getMessage()).addLore("").getItem();
        slots[18] = new ItemCreator(Material.COMPASS).setName("§8┃ §fTemps avant mort de §6déconnexion").addLore("").addLore("  §8┃ §fVous permet de configurer").addLore("  §8┃ §fle temps necéssaire pour").addLore("  §8┃ §fmourir de déconnexion.").addLore("").addLore(" §8» §fConfiguration: §c" + this.gameManager.getGameConfig().getDisconnectMinute() + " minute(s)").addLore("").addLore(CommonString.CLICK_HERE_TO_MODIFY.getMessage()).addLore("").getItem();
        slots[22] = new ItemCreator(Material.CHEST).setName("§8┃ §fInventaire par §cdéfaut").addLore("").addLore("  §8┃ §fVous permet de définir").addLore("  §8┃ §fl'inventaire par §cdéfaut").addLore("  §8┃ §fdonné en début de partie.").addLore("").addLore(CommonString.CLICK_HERE_TO_MODIFY.getMessage()).addLore("").getItem();
        slots[26] = new ItemCreator(Material.CHEST).setName("§8┃ §fInventaire de §cmort").addLore("").addLore("  §8┃ §fVous permet de définir").addLore("  §8┃ §fl'inventaire de §cmort").addLore("  §8┃ §fdonné lors d'une §cmort§f.").addLore("").addLore(CommonString.CLICK_HERE_TO_MODIFY.getMessage()).addLore("").getItem();
        slots[28] = OpenVar.CYCLE_DURATION.getItemCycle();
        slots[29] = new ItemCreator(Material.POTION).setName("§8┃ §fLimite de §9potions").addLore("").addLore("  §8┃ §fVous permet de limiter la").addLore("  §8┃ §ffabrication de certaines potions").addLore("").addLore(CommonString.CLICK_HERE_TO_ACCESS.getMessage()).addLore("").getItem();
        slots[33] = new ItemCreator(Material.PAPER).setName("§8┃ §7Règles").addLore("").addLore("  §8┃ §fVous permet de définir").addLore("  §8┃ §fcertaines règles pour la partie.").addLore("").addLore(CommonString.CLICK_HERE_TO_MODIFY.getMessage()).addLore("").getItem();
        slots[34] = new ItemCreator(Material.APPLE).setName("§8┃ §fTaux de §7drop").addLore("").addLore("  §8┃ §fVous permet de modifier les").addLore("  §8┃ §ftaux de drop de certains objets.").addLore("").addLore(CommonString.CLICK_HERE_TO_ACCESS.getMessage()).addLore("").getItem();
        slots[40] = CommonItems.GUI_BACK_ITEM.getItem();
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
        switch (clickedItem.getType()) {
            case DIAMOND_SWORD: {
                this.gameManager.getApi().openInventory(player, PvPTimeGUI.class);
                break;
            }
            case STAINED_GLASS: {
                this.gameManager.getApi().openInventory(player, BorderTimeGUI.class);
                break;
            }
            case CHEST: {
                if (slot == 22) {
                    this.gameManager.getApi().openInventory(player, DefaultInvGUI.class);
                }
                if (slot != 26) break;
                this.gameManager.getApi().openInventory(player, DefaultDeathInvGUI.class);
                break;
            }
            case WATCH: {
                this.gameManager.getApi().openInventory(player, CycleManagerGUI.class);
                break;
            }
            case PAPER: {
                this.gameManager.getApi().openInventory(player, GeneralRulesGUI.class);
                break;
            }
            case APPLE: {
                this.gameManager.getApi().openInventory(player, DropItemRateGUI.class);
                break;
            }
            case DIAMOND: {
                this.gameManager.getApi().openInventory(player, OresLimitGUI.class);
                break;
            }
            case POTION: {
                this.gameManager.getApi().openInventory(player, PotionManagerGUI.class);
                break;
            }
            case ENCHANTED_BOOK: {
                this.gameManager.getApi().openInventory(player, EnchantMaxGUI.class);
                break;
            }
            case ENDER_PEARL: {
                this.gameManager.getApi().openInventory(player, DamageGUI.class);
                break;
            }
            case COMPASS: {
                this.gameManager.getApi().openInventory(player, DeconnexionTimeGUI.class);
                break;
            }
            case ARROW: {
                this.gameManager.getApi().openInventory(player, ConfigMainGUI.class);
            }
        }
    }

    @Override
    public int getRows() {
        return 5;
    }
}
