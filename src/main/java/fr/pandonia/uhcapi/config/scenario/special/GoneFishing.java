package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import fr.pandonia.uhcapi.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GoneFishing
        extends ScenarioManager {
    @Override
    public void configure() {
        this.scenario = Scenario.GONEFISHING;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onStart() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.getInventory().addItem(new ItemStack[]{new ItemCreator(Material.FISHING_ROD).setUnbreakable(true).addEnchantment(Enchantment.LUCK, 250).addEnchantment(Enchantment.LURE, 7).getItem()});
            players.getInventory().addItem(new ItemStack[]{new ItemCreator(Material.ANVIL).setAmount(64).getItem()});
        }
    }
}
