package fr.pandonia.uhcapi.commands;

import fr.pandonia.uhcapi.GamePlayer;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.utils.CustomInventory;
import fr.pandonia.uhcapi.utils.ItemCreator;
import java.util.function.Supplier;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantCommand
        implements CommandExecutor,
        CustomInventory {
    private final GameManager gameManager;

    public EnchantCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (GamePlayer.getPlayer(player.getUniqueId()).isEditing() || GamePlayer.getPlayer(player.getUniqueId()).isEditingDeathInv()) {
                if (player.getItemInHand().getType() != Material.AIR) {
                    this.gameManager.getApi().openInventory(player, this.getClass());
                } else {
                    player.sendMessage("§cVeuillez prendre un objet en mains.");
                }
            } else {
                player.sendMessage("§cVous n'êtes pas en train de définir l'inventaire par défaut..");
            }
        }
        return false;
    }

    @Override
    public String getName() {
        return "Enchantement";
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack current;
        ItemStack[] slots = new ItemStack[this.getSlots()];
        slots[3] = current = player.getItemInHand();
        slots[5] = new ItemCreator(Material.DIAMOND_AXE).setName("§8┃ §cIncassable : " + (current.getItemMeta().spigot().isUnbreakable() ? "§aOui" : "§cNon")).getItem();
        int slot = 9;
        for (EnchantEnum enchantEnum : EnchantEnum.values()) {
            int level = 0;
            if (current.getEnchantments().containsKey(enchantEnum.getEnchantment())) {
                level = current.getEnchantmentLevel(enchantEnum.getEnchantment());
            }
            ItemCreator item = new ItemCreator(Material.ENCHANTED_BOOK).setName(enchantEnum.getName());
            item.addLore("");
            item.addLore("  §8┃ §fNiveau : §c" + level);
            item.addLore("");
            if (level < enchantEnum.getMax()) {
                item.addLore(" §8» §fClic gauche : §aAjouter un niveau");
            }
            if (level > enchantEnum.getMin()) {
                item.addLore(" §8» §fClic droit : §cSupprimer un niveau");
            }
            item.addLore("");
            item.setAmount(level);
            slots[slot] = item.getItem();
            ++slot;
        }
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
        switch (clickedItem.getType()) {
            case DIAMOND_AXE: {
                if (!clickedItem.hasItemMeta() || !clickedItem.getItemMeta().hasDisplayName() || !clickedItem.getItemMeta().getDisplayName().contains("§8┃ §cIncassable")) break;
                ItemStack itemStack = player.getItemInHand();
                boolean unbreakable = itemStack.getItemMeta().spigot().isUnbreakable();
                if (unbreakable) {
                    ItemMeta itemMeta1 = itemStack.getItemMeta();
                    itemMeta1.spigot().setUnbreakable(false);
                    itemStack.setItemMeta(itemMeta1);
                    this.gameManager.getApi().openInventory(player, this.getClass());
                    break;
                }
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.spigot().setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                this.gameManager.getApi().openInventory(player, this.getClass());
                break;
            }
            case ENCHANTED_BOOK: {
                ItemStack item = player.getItemInHand();
                if (clickType.equals(ClickType.LEFT)) {
                    for (EnchantEnum enchantEnum : EnchantEnum.values()) {
                        int level;
                        if (!clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(enchantEnum.getName()) || (level = item.getEnchantmentLevel(enchantEnum.getEnchantment())) >= enchantEnum.getMax()) continue;
                        item.addUnsafeEnchantment(enchantEnum.getEnchantment(), level + 1);
                        this.gameManager.getApi().openInventory(player, this.getClass());
                    }
                } else {
                    if (!clickType.equals(ClickType.RIGHT)) break;
                    for (EnchantEnum enchantEnum : EnchantEnum.values()) {
                        int level;
                        if (!clickedItem.getItemMeta().getDisplayName().contains(enchantEnum.getName()) || (level = item.getEnchantmentLevel(enchantEnum.getEnchantment())) <= enchantEnum.getMin()) continue;
                        if (level == 1) {
                            item.removeEnchantment(enchantEnum.getEnchantment());
                            this.gameManager.getApi().openInventory(player, this.getClass());
                            continue;
                        }
                        item.addUnsafeEnchantment(enchantEnum.getEnchantment(), level - 1);
                        this.gameManager.getApi().openInventory(player, this.getClass());
                    }
                }
                break;
            }
        }
    }

    @Override
    public int getRows() {
        return 4;
    }

    public static enum EnchantEnum {
        PROTECTION_ENVIRONMENTAL(Enchantment.PROTECTION_ENVIRONMENTAL, "§8┃ §cProtection", 0, 50, 4),
        FIRE_PROTECTION(Enchantment.PROTECTION_FIRE, "§8┃ §cFire Protection", 0, 50, 4),
        FEATHER_FALLING(Enchantment.PROTECTION_FALL, "§8┃ §cFeather Falling", 0, 50, 4),
        BLAST_PROTECTION(Enchantment.PROTECTION_EXPLOSIONS, "§8┃ §cBlast Protection", 0, 50, 4),
        PROJECTILE_PROTECTION(Enchantment.PROTECTION_PROJECTILE, "§8┃ §cProjectile Protection", 0, 50, 4),
        RESPIRATION(Enchantment.OXYGEN, "§8┃ §cRespiration", 0, 50, 3),
        AQUA_AFFINITY(Enchantment.WATER_WORKER, "§8┃ §cAqua Affinity", 0, 50, 1),
        THORNS(Enchantment.THORNS, "§8┃ §cThorns", 0, 50, 3),
        DEPTH_STRIDERS(Enchantment.DEPTH_STRIDER, "§8┃ §cDepth Strider", 0, 50, 3),
        SHARPNESS(Enchantment.DAMAGE_ALL, "§8┃ §cSharpness", 0, 50, 5),
        SMITE(Enchantment.DAMAGE_UNDEAD, "§8┃ §cSmite", 0, 50, 5),
        BANE_OF_ARTHROPODS(Enchantment.DAMAGE_ARTHROPODS, "§8┃ §cBane of Arthropods", 0, 50, 5),
        KNOCKBACK(Enchantment.KNOCKBACK, "§8┃ §cKnockback", 0, 50, 2),
        FIRE_ASPECT(Enchantment.FIRE_ASPECT, "§8┃ §cFire Aspect", 0, 50, 2),
        LOOTING(Enchantment.LOOT_BONUS_MOBS, "§8┃ §cLooting", 0, 50, 3),
        POWER(Enchantment.ARROW_DAMAGE, "§8┃ §cPower", 0, 50, 5),
        PUNCH(Enchantment.ARROW_KNOCKBACK, "§8┃ §cPunch", 0, 50, 2),
        FLAME(Enchantment.ARROW_FIRE, "§8┃ §cFlame", 0, 1, 1),
        INFINITY(Enchantment.ARROW_INFINITE, "§8┃ §cInfinity", 0, 1, 1),
        EFFICIENCY(Enchantment.DIG_SPEED, "§8┃ §cEfficiency", 0, 50, 5),
        SILK_TOUCH(Enchantment.SILK_TOUCH, "§8┃ §cSilk Touch", 0, 1, 1),
        UNBREAKING(Enchantment.DURABILITY, "§8┃ §cUnbreaking", 0, 50, 3),
        FORTUNE(Enchantment.LOOT_BONUS_BLOCKS, "§8┃ §cFortune", 0, 50, 3),
        LUCK_OF_THE_SEA(Enchantment.LUCK, "§8┃ §cLuck of the Sea", 0, 100, 3),
        LURE(Enchantment.LURE, "§8┃ §cLure", 0, 100, 3);

        private final Enchantment enchantment;
        private final String name;
        private final int min;
        private final int max;
        private int configValue;

        private EnchantEnum(Enchantment enchantment, String name, int min, int max, int configValue) {
            this.enchantment = enchantment;
            this.name = name;
            this.min = min;
            this.max = max;
            this.configValue = configValue;
        }

        public Enchantment getEnchantment() {
            return this.enchantment;
        }

        public String getName() {
            return this.name;
        }

        public int getMin() {
            return this.min;
        }

        public int getMax() {
            return this.max;
        }

        public void setConfigValue(int configValue) {
            this.configValue = configValue;
        }

        public void addConfigValue() {
            if (this.configValue < this.enchantment.getMaxLevel()) {
                ++this.configValue;
            }
        }

        public void removeConfigValue() {
            if (this.configValue > 0) {
                --this.configValue;
            }
        }

        public int getConfigValue() {
            return this.configValue;
        }
    }
}
