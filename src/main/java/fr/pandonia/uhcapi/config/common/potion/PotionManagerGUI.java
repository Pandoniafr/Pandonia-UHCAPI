package fr.pandonia.uhcapi.config.common.potion;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.ConfigOptionsGUI;
import fr.pandonia.uhcapi.config.GameConfig;
import fr.pandonia.uhcapi.config.value.CommonItems;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.utils.CustomInventory;
import fr.pandonia.uhcapi.utils.ItemCreator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class PotionManagerGUI
        implements CustomInventory {
    private final GameManager gameManager;
    private final GameConfig gameConfig;
    private final Map<Integer, Potions> itemsPotions;

    public PotionManagerGUI(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameConfig = gameManager.getGameConfig();
        this.itemsPotions = new HashMap<Integer, Potions>();
    }

    @Override
    public String getName() {
        return "Limite des potions";
    }

    private void setup() {
        this.itemsPotions.put(0, Potions.SPEED);
        this.itemsPotions.put(1, Potions.STRENGHT);
        this.itemsPotions.put(2, Potions.JUMP_BOOST);
        this.itemsPotions.put(3, Potions.POISON);
        this.itemsPotions.put(4, Potions.FIRE_RESISTANCE);
        this.itemsPotions.put(5, Potions.HEAL);
        this.itemsPotions.put(6, Potions.REGENERATION);
        this.itemsPotions.put(7, Potions.INVISIBILITY);
        this.itemsPotions.put(8, Potions.OTHER);
        this.itemsPotions.put(9, Potions.LONG_DURATION);
        this.itemsPotions.put(10, Potions.SPLASH);
        this.itemsPotions.put(11, Potions.LEVEL_II);
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack[] slots = new ItemStack[this.getSlots()];
        this.setup();
        for (Map.Entry<Integer, Potions> entry : this.itemsPotions.entrySet()) {
            slots[entry.getKey().intValue()] = entry.getValue().getItem();
        }
        slots[22] = CommonItems.GUI_BACK_ITEM.getItem();
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
        switch (clickedItem.getType()) {
            case ARROW: {
                this.gameManager.getApi().openInventory(player, ConfigOptionsGUI.class);
                return;
            }
        }
        if (this.itemsPotions.containsKey(slot)) {
            Potions potions = this.itemsPotions.get(slot);
            potions.toggleEnabled();
            API.getAPI().openInventory(player, this.getClass());
        }
    }

    @Override
    public int getRows() {
        return 3;
    }

    public static enum Potions {
        SPEED("Speed", Material.POTION, 8258, Material.SUGAR),
        STRENGHT("Strenght", Material.POTION, 8233, Material.BLAZE_POWDER),
        JUMP_BOOST("Jump Boost", Material.POTION, 8267, Material.RABBIT_FOOT),
        POISON("Poison", Material.POTION, 8260, Material.SPIDER_EYE),
        FIRE_RESISTANCE("Fire Resistance", Material.POTION, 8259, Material.MAGMA_CREAM),
        HEAL("Instant Health", Material.POTION, 8229, Material.SPECKLED_MELON),
        REGENERATION("Regeneration", Material.POTION, 8257, Material.GHAST_TEAR),
        INVISIBILITY("Invisibility", Material.POTION, 8270, Material.GOLDEN_CARROT),
        OTHER("Autres", Material.POTION, 8236, Material.FERMENTED_SPIDER_EYE),
        SPLASH("Splash", Material.SULPHUR, 0, Material.SULPHUR),
        LEVEL_II("Niveau II", Material.GLOWSTONE_DUST, 0, Material.GLOWSTONE_DUST),
        LONG_DURATION("Longue durée", Material.REDSTONE, 0, Material.REDSTONE);

        private boolean enabled = true;
        private final String name;
        private final Material itemMaterial;
        private final int idItem;
        private final Material material;

        private Potions(String name, Material itemMaterial, int idItem, Material material) {
            this.name = name;
            this.itemMaterial = itemMaterial;
            this.idItem = idItem;
            this.material = material;
        }

        public ItemStack getItem() {
            return new ItemCreator(this.getItemMaterial()).setName("§c" + this.getName() + " §8(" + (this.isEnabled() ? "§aActivée§8)" : "§cDésactivée§8)")).setDurability(this.getIdItem()).addItemFlags(ItemFlag.HIDE_POTION_EFFECTS).getItem();
        }

        public String getName() {
            return this.name;
        }

        public Material getItemMaterial() {
            return this.itemMaterial;
        }

        public int getIdItem() {
            return this.idItem;
        }

        public Material getMaterial() {
            return this.material;
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        public void toggleEnabled() {
            this.enabled = !this.enabled;
        }
    }
}
