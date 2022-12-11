package fr.pandonia.uhcapi.config.scenario.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import fr.pandonia.uhcapi.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class GoldenHead
        extends ScenarioManager
        implements Listener {
    @EventHandler
    private void onDeath(PlayerDeathEvent event) {
        if (this.scenario.isEnabled()) {
            Player player = event.getEntity();
            event.getDrops().add(new ItemCreator(Material.SKULL_ITEM).setDurability(3).setName("§eTête de §6" + player.getName()).setOwner(player.getName()).getItem());
        }
    }

    @Override
    public void configure() {
        this.scenario = Scenario.GOLDENHEAD;
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
    public void onStart() {
        ItemStack itemStack = new ItemCreator(Material.GOLDEN_APPLE).setDurability(0).setName("§6Golden Head").getItem();
        ShapedRecipe goldenHeadRecipe = new ShapedRecipe(itemStack);
        goldenHeadRecipe.shape(new String[]{"@@@", "@#@", "@@@"});
        goldenHeadRecipe.setIngredient('@', Material.GOLD_INGOT);
        goldenHeadRecipe.setIngredient('#', Material.SKULL_ITEM, 3);
        Bukkit.getServer().addRecipe(goldenHeadRecipe);
    }
}
