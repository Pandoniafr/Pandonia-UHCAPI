package fr.pandonia.uhcapi;

import com.google.common.base.Preconditions;
import fr.pandonia.uhcapi.common.Common;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.listener.ReconnectListener;
import fr.pandonia.uhcapi.module.Modules;
import fr.pandonia.uhcapi.module.games.UHCModule;
import fr.pandonia.uhcapi.utils.CustomInventory;
import fr.pandonia.uhcapi.utils.HologramCreate;
import fr.pandonia.uhcapi.utils.TabHandler;
import fr.pandonia.uhcapi.world.BiomeChanger;
import fr.pandonia.uhcapi.world.Generator;
import fr.pandonia.uhcapi.world.LobbyPopulator;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

public class API extends JavaPlugin {
    private static API api;

    private GameManager gameManager;

    private Common common;

    private Map<Class<? extends CustomInventory>, CustomInventory> registeredInventories = new HashMap<>();

    private LobbyPopulator lobbyPopulator;

    private Modules modules;

    private Scoreboard scoreboard;

    private TabHandler tabHandler;

    public static API getAPI() {
        return api;
    }

    public void onLoad() {
        BiomeChanger.init();
    }

    public void onEnable() {
        api = this;
        ((World)getServer().getWorlds().get(0)).getPopulators().add(new Generator());
        this.gameManager = new GameManager(this);
        this.lobbyPopulator = new LobbyPopulator(this);
        this.common = new Common(this);
        this.common.load();
        setModules((Modules)new UHCModule(this));
        this.scoreboard = getServer().getScoreboardManager().getMainScoreboard();
        this.tabHandler = new TabHandler();
        for (World world : Bukkit.getWorlds()) {
            world.setDifficulty(Difficulty.NORMAL);
            world.setGameRuleValue("naturalRegeneration", "false");
        }
        Bukkit.getWorld("Lobby").setDifficulty(Difficulty.PEACEFUL);
        (new HologramCreate()).create();
        Bukkit.getPluginManager().registerEvents((Listener)new ReconnectListener(this.gameManager), (Plugin)this);
    }

    public void onDisable() {
        this.common.onDisable();
    }

    public Common getCommon() {
        return this.common;
    }

    public Modules getModules() {
        return this.modules;
    }

    public void setModules(Modules modules) {
        (this.modules = modules).onLoad();
    }

    public LobbyPopulator getLobbyPopulator() {
        return this.lobbyPopulator;
    }

    public GameManager getGameManager() {
        return this.gameManager;
    }

    public CustomInventory getInventory(Class<? extends CustomInventory> inventoryClass) {
        Preconditions.checkState(this.registeredInventories.containsKey(inventoryClass));
        return this.registeredInventories.get(inventoryClass);
    }

    public CustomInventory getInventory(String inventoryName) {
        return this.registeredInventories.values().stream().filter(inventory -> inventory.getName().equals(inventoryName)).findFirst().orElse(null);
    }

    public Map<Class<? extends CustomInventory>, CustomInventory> getRegisteredInventories() {
        return this.registeredInventories;
    }

    public void openInventory(Player player, Class<? extends CustomInventory> inventoryClass) {
        CustomInventory customInventory = getInventory(inventoryClass);
        Inventory inventory = Bukkit.createInventory(null, customInventory.getSlots(), customInventory.getName());
        inventory.setContents(customInventory.getContents(player).get());
        player.openInventory(inventory);
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public TabHandler getTabHandler() {
        return this.tabHandler;
    }
}
