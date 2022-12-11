package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HasteyBabies
        extends ScenarioManager
        implements Listener {
    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        if (this.scenario.isEnabled()) {
            ItemStack itemStack = event.getInventory().getResult();
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                return;
            }
            switch (itemStack.getType()) {
                case WOOD_PICKAXE:
                case WOOD_AXE:
                case WOOD_SPADE:
                case STONE_PICKAXE:
                case STONE_AXE:
                case STONE_SPADE:
                case IRON_PICKAXE:
                case IRON_AXE:
                case IRON_SPADE:
                case GOLD_PICKAXE:
                case GOLD_AXE:
                case GOLD_SPADE:
                case DIAMOND_PICKAXE:
                case DIAMOND_AXE:
                case DIAMOND_SPADE: {
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.addEnchant(Enchantment.DIG_SPEED, 1, true);
                    itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                    itemStack.setItemMeta(itemMeta);
                }
            }
        }
    }

    @Override
    public void configure() {
        this.scenario = Scenario.HASTEYBABIES;
    }

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, API.getAPI());
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public void init() {
    }

    @Override
    public void onStart() {
    }
}
