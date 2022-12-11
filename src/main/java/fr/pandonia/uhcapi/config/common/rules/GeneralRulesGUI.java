package fr.pandonia.uhcapi.config.common.rules;

import fr.pandonia.uhcapi.common.rules.items.GeneralRules;
import fr.pandonia.uhcapi.common.rules.items.UseItems;
import fr.pandonia.uhcapi.config.ConfigOptionsGUI;
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
import org.bukkit.inventory.ItemStack;

public class GeneralRulesGUI
        implements CustomInventory {
    private final GameManager gameManager;
    private final Map<Integer, GeneralRules> rules;
    private final Map<Integer, UseItems> itemsRules;

    public GeneralRulesGUI(GameManager gameManager) {
        this.gameManager = gameManager;
        this.rules = new HashMap<Integer, GeneralRules>();
        this.itemsRules = new HashMap<Integer, UseItems>();
    }

    private void setup() {
        this.rules.put(10, GeneralRules.DIAMOND_HELMET);
        this.rules.put(11, GeneralRules.DIAMOND_CHESTPLATE);
        this.rules.put(12, GeneralRules.DIAMOND_LEGGINGS);
        this.rules.put(13, GeneralRules.DIAMOND_BOOTS);
        this.rules.put(14, GeneralRules.DIAMOND_SWORD);
        this.rules.put(19, GeneralRules.STRIPMINING);
        this.rules.put(20, GeneralRules.IPVP);
        this.rules.put(21, GeneralRules.CROSSTEAM);
        this.rules.put(22, GeneralRules.TOWER);
        this.rules.put(23, GeneralRules.DIGDOWN);
        this.rules.put(24, GeneralRules.ROLLERCOASTER);
        this.rules.put(28, GeneralRules.HEALTH);
        this.rules.put(29, GeneralRules.MUMBLE);
        this.rules.put(30, GeneralRules.PRIVATEMSG);
        int slot = 37;
        for (UseItems itemsRules : UseItems.values()) {
            this.itemsRules.put(slot, itemsRules);
            ++slot;
        }
    }

    @Override
    public String getName() {
        return "Règles de la partie";
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        Integer[] glass;
        ItemStack[] slots = new ItemStack[this.getSlots()];
        Integer[] arrayOfInteger1 = glass = new Integer[]{0, 1, 7, 8, 9, 17, 36, 44, 45, 46, 52, 53};
        int i = arrayOfInteger1.length;
        for (int b = 0; b < i; b = (int)((byte)(b + 1))) {
            int j = arrayOfInteger1[b];
            slots[j] = new ItemCreator(Material.STAINED_GLASS_PANE).setDurability(7).getItem();
        }
        this.setup();
        for (Map.Entry<Integer, GeneralRules> entry : this.rules.entrySet()) {
            slots[entry.getKey().intValue()] = entry.getValue().getItem();
        }
        for (Map.Entry<Integer, UseItems> entry : this.itemsRules.entrySet()) {
            slots[entry.getKey().intValue()] = ((UseItems)entry.getValue()).getItem();
        }
        slots[49] = CommonItems.GUI_BACK_ITEM.getItem();
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
        Enum rules;
        if (this.rules.containsKey(slot)) {
            rules = this.rules.get(slot);
            ((GeneralRules)rules).toggleEnabled();
        }
        if (this.itemsRules.containsKey(slot)) {
            rules = this.itemsRules.get(slot);
            ((UseItems)rules).toggleEnabled();
        }
        this.gameManager.getApi().openInventory(player, this.getClass());
        switch (clickedItem.getType()) {
            case ARROW: {
                this.gameManager.getApi().openInventory(player, ConfigOptionsGUI.class);
            }
        }
    }

    @Override
    public int getRows() {
        return 6;
    }
}
