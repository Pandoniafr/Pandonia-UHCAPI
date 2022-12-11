package fr.pandonia.uhcapi.common;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.commands.AlertsCommand;
import fr.pandonia.uhcapi.commands.DisperseCommand;
import fr.pandonia.uhcapi.commands.EnchantCommand;
import fr.pandonia.uhcapi.commands.FinishCommand;
import fr.pandonia.uhcapi.commands.GroupeCommand;
import fr.pandonia.uhcapi.commands.HealthCommand;
import fr.pandonia.uhcapi.commands.HelpopCommand;
import fr.pandonia.uhcapi.commands.HostCommand;
import fr.pandonia.uhcapi.commands.InvCommand;
import fr.pandonia.uhcapi.commands.NearCommand;
import fr.pandonia.uhcapi.commands.PreLoadCommand;
import fr.pandonia.uhcapi.commands.ReviveCommand;
import fr.pandonia.uhcapi.commands.RulesCommand;
import fr.pandonia.uhcapi.commands.ScenarioCommand;
import fr.pandonia.uhcapi.commands.TCCommand;
import fr.pandonia.uhcapi.commands.TPRandomCommand;
import fr.pandonia.uhcapi.commands.VanishCommand;
import fr.pandonia.uhcapi.commands.ViewCommand;
import fr.pandonia.uhcapi.commands.ViewOfflineCommand;
import fr.pandonia.uhcapi.commands.VoteCommand;
import fr.pandonia.uhcapi.commands.WhitelistCommand;
import fr.pandonia.uhcapi.commands.special.RulesInventory;
import fr.pandonia.uhcapi.commands.special.TPHereCommand;
import fr.pandonia.uhcapi.common.rules.Rules;
import fr.pandonia.uhcapi.common.scoreboard.ScoreboardManager;
import fr.pandonia.uhcapi.config.AdminPanelGUI;
import fr.pandonia.uhcapi.config.ConfigMainGUI;
import fr.pandonia.uhcapi.config.ConfigOptionsGUI;
import fr.pandonia.uhcapi.config.borderValue.BorderEndSizeGUI;
import fr.pandonia.uhcapi.config.borderValue.BorderManagerGUI;
import fr.pandonia.uhcapi.config.borderValue.BorderSpeedGUI;
import fr.pandonia.uhcapi.config.borderValue.BorderStartSizeGUI;
import fr.pandonia.uhcapi.config.common.CycleManagerGUI;
import fr.pandonia.uhcapi.config.common.DefaultDeathInvGUI;
import fr.pandonia.uhcapi.config.common.DefaultInvGUI;
import fr.pandonia.uhcapi.config.common.EnchantMaxGUI;
import fr.pandonia.uhcapi.config.common.ores.DiamondMaxGUI;
import fr.pandonia.uhcapi.config.common.ores.GoldMaxGUI;
import fr.pandonia.uhcapi.config.common.ores.OresLimitGUI;
import fr.pandonia.uhcapi.config.common.potion.PotionManagerGUI;
import fr.pandonia.uhcapi.config.common.rules.DropItemRateGUI;
import fr.pandonia.uhcapi.config.common.rules.GeneralRulesGUI;
import fr.pandonia.uhcapi.config.intValue.DamageGUI;
import fr.pandonia.uhcapi.config.intValue.DateGUI;
import fr.pandonia.uhcapi.config.intValue.DeconnexionTimeGUI;
import fr.pandonia.uhcapi.config.intValue.SlotsGUI;
import fr.pandonia.uhcapi.config.scenario.ScenarioTimeGUI;
import fr.pandonia.uhcapi.config.scenario.ScenariosGUI;
import fr.pandonia.uhcapi.config.teamValue.JoinTeamsGUI;
import fr.pandonia.uhcapi.config.teamValue.TeamManagerGUI;
import fr.pandonia.uhcapi.config.timeValue.BorderTimeGUI;
import fr.pandonia.uhcapi.config.timeValue.EpisodeTimeGUI;
import fr.pandonia.uhcapi.config.timeValue.PvPTimeGUI;
import fr.pandonia.uhcapi.game.GameListener;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.game.team.Teams;
import fr.pandonia.uhcapi.listener.PlayerBrewItemListener;
import fr.pandonia.uhcapi.listener.PlayerChatListener;
import fr.pandonia.uhcapi.listener.PlayerEnchantListener;
import fr.pandonia.uhcapi.listener.PlayerInteractListener;
import fr.pandonia.uhcapi.listener.PlayerJoinListener;
import fr.pandonia.uhcapi.listener.world.ChunkUnloadListener;
import fr.pandonia.uhcapi.module.standard.listener.UHCStandardListener;
import fr.pandonia.uhcapi.utils.InventoryAPI;
import fr.pandonia.uhcapi.utils.InventoryConvetor;
import fr.pandonia.uhcapi.utils.nms.NMSPatcher;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Common {
    private final API main;
    private final GameManager gameManager;
    private final ScoreboardManager scoreboardManager;
    private final Scoreboard scoreboard;
    private JoinTeamsGUI joinTeamsGUI;
    private ScenariosGUI scenariosGUI;
    private EnchantCommand enchantCommand;
    private ScenarioCommand scenarioCommand;
    private InvCommand invCommand;

    public Common(API main) {
        this.main = main;
        this.gameManager = main.getGameManager();
        this.scoreboardManager = new ScoreboardManager(main);
        this.scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        new NMSPatcher(main);
    }

    public void load() {
        Rules.load(this.main);
        this.scoreboardManager.load();
        this.scenariosGUI = new ScenariosGUI(this.main);
        this.joinTeamsGUI = new JoinTeamsGUI(this.main);
        this.enchantCommand = new EnchantCommand(this.gameManager);
        this.scenarioCommand = new ScenarioCommand();
        this.invCommand = new InvCommand(this.gameManager);
        this.registerCommands();
        this.registerListeners();
        this.registerInventories();
        this.registerNameTag();
        this.gameManager.getWorldPopulator().getGameWorld().setGameRuleValue("doFireTick", "false");
        InventoryAPI.items = InventoryConvetor.inventoryFromBase64("rO0ABXcEAAAAKHBwcHBwcHNyABpvcmcuYnVra2l0LnV0aWwuaW8uV3JhcHBlcvJQR+zxEm8FAgABTAADbWFwdAAPTGphdmEvdXRpbC9NYXA7eHBzcgA1Y29tLmdvb2dsZS5jb21tb24uY29sbGVjdC5JbW11dGFibGVNYXAkU2VyaWFsaXplZEZvcm0AAAAAAAAAAAIAAlsABGtleXN0ABNbTGphdmEvbGFuZy9PYmplY3Q7WwAGdmFsdWVzcQB+AAR4cHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAJ0AAI9PXQABHR5cGV1cQB+AAYAAAACdAAeb3JnLmJ1a2tpdC5pbnZlbnRvcnkuSXRlbVN0YWNrdAAMV0FURVJfQlVDS0VUc3EAfgAAc3EAfgADdXEAfgAGAAAAA3EAfgAIcQB+AAl0AAZhbW91bnR1cQB+AAYAAAADcQB+AAt0AAtDT09LRURfQkVFRnNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAABAc3EAfgAAc3EAfgADdXEAfgAGAAAAA3EAfgAIcQB+AAlxAH4AEHVxAH4ABgAAAANxAH4AC3QABEJPT0tzcQB+ABMAAAAHcHBwcHBwcHBwcHBwcHBwcHBwcHBwcHBwcHBwcHBwcA==");
        InventoryAPI.inventoryContents = "rO0ABXcEAAAAKHBwcHBwcHNyABpvcmcuYnVra2l0LnV0aWwuaW8uV3JhcHBlcvJQR+zxEm8FAgABTAADbWFwdAAPTGphdmEvdXRpbC9NYXA7eHBzcgA1Y29tLmdvb2dsZS5jb21tb24uY29sbGVjdC5JbW11dGFibGVNYXAkU2VyaWFsaXplZEZvcm0AAAAAAAAAAAIAAlsABGtleXN0ABNbTGphdmEvbGFuZy9PYmplY3Q7WwAGdmFsdWVzcQB+AAR4cHVyABNbTGphdmEubGFuZy5PYmplY3Q7kM5YnxBzKWwCAAB4cAAAAAJ0AAI9PXQABHR5cGV1cQB+AAYAAAACdAAeb3JnLmJ1a2tpdC5pbnZlbnRvcnkuSXRlbVN0YWNrdAAMV0FURVJfQlVDS0VUc3EAfgAAc3EAfgADdXEAfgAGAAAAA3EAfgAIcQB+AAl0AAZhbW91bnR1cQB+AAYAAAADcQB+AAt0AAtDT09LRURfQkVFRnNyABFqYXZhLmxhbmcuSW50ZWdlchLioKT3gYc4AgABSQAFdmFsdWV4cgAQamF2YS5sYW5nLk51bWJlcoaslR0LlOCLAgAAeHAAAABAc3EAfgAAc3EAfgADdXEAfgAGAAAAA3EAfgAIcQB+AAlxAH4AEHVxAH4ABgAAAANxAH4AC3QABEJPT0tzcQB+ABMAAAAHcHBwcHBwcHBwcHBwcHBwcHBwcHBwcHBwcHBwcHBwcA==";
    }

    private void registerCommands() {
        this.main.getCommand("host").setExecutor(new HostCommand(this.gameManager));
        this.main.getCommand("finish").setExecutor(new FinishCommand(this.gameManager));
        this.main.getCommand("enchant").setExecutor(this.enchantCommand);
        this.main.getCommand("scenario").setExecutor(this.scenarioCommand);
        this.main.getCommand("helpop").setExecutor(new HelpopCommand());
        this.main.getCommand("revive").setExecutor(new ReviveCommand(this.main));
        this.main.getCommand("inv").setExecutor(this.invCommand);
        this.main.getCommand("view").setExecutor(new ViewCommand(this.gameManager));
        this.main.getCommand("rules").setExecutor(new RulesCommand(this.main));
        this.main.getCommand("tc").setExecutor(new TCCommand(this.gameManager));
        this.main.getCommand("vote").setExecutor(new VoteCommand(this.gameManager));
        this.main.getCommand("preload").setExecutor(new PreLoadCommand(this.gameManager));
        this.main.getCommand("tprandom").setExecutor(new TPRandomCommand());
        this.main.getCommand("whitelist").setExecutor(new WhitelistCommand(this.gameManager));
        this.main.getCommand("alerts").setExecutor(new AlertsCommand());
        this.main.getCommand("tphere").setExecutor(new TPHereCommand());
        this.main.getCommand("health").setExecutor(new HealthCommand(this.gameManager));
        this.main.getCommand("viewoffline").setExecutor(new ViewOfflineCommand(this.gameManager));
        this.main.getCommand("disperse").setExecutor(new DisperseCommand(this.gameManager));
        this.main.getCommand("groupe").setExecutor(new GroupeCommand(this.gameManager));
        this.main.getCommand("near").setExecutor(new NearCommand(this.gameManager));
        this.main.getCommand("vanish").setExecutor(new VanishCommand(this.gameManager));
    }

    private void registerListeners() {
        PluginManager pluginManager = this.main.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(this.main), this.main);
        pluginManager.registerEvents(new CommonListener(this), this.main);
        pluginManager.registerEvents(new GameListener(this.gameManager), this.main);
        pluginManager.registerEvents(new PlayerChatListener(this.gameManager), this.main);
        pluginManager.registerEvents(new PlayerInteractListener(this.gameManager), this.main);
        pluginManager.registerEvents(new UHCStandardListener(this.gameManager.getUhcStandard()), this.main);
        pluginManager.registerEvents(this.joinTeamsGUI, this.main);
        pluginManager.registerEvents(this.scenariosGUI, this.main);
        pluginManager.registerEvents(new ScenarioTimeGUI(), this.main);
        pluginManager.registerEvents(new ChunkUnloadListener(), this.main);
        pluginManager.registerEvents(new PlayerBrewItemListener(), this.main);
        pluginManager.registerEvents(new PlayerEnchantListener(), this.main);
    }

    private void registerInventories() {
        this.main.getRegisteredInventories().put(ConfigMainGUI.class, new ConfigMainGUI(this.main));
        this.main.getRegisteredInventories().put(TeamManagerGUI.class, new TeamManagerGUI(this.main));
        this.main.getRegisteredInventories().put(ConfigOptionsGUI.class, new ConfigOptionsGUI(this.gameManager));
        this.main.getRegisteredInventories().put(SlotsGUI.class, new SlotsGUI(this.gameManager));
        this.main.getRegisteredInventories().put(BorderStartSizeGUI.class, new BorderStartSizeGUI(this.gameManager));
        this.main.getRegisteredInventories().put(BorderEndSizeGUI.class, new BorderEndSizeGUI(this.gameManager));
        this.main.getRegisteredInventories().put(BorderManagerGUI.class, new BorderManagerGUI(this.gameManager));
        this.main.getRegisteredInventories().put(BorderSpeedGUI.class, new BorderSpeedGUI(this.gameManager));
        this.main.getRegisteredInventories().put(BorderTimeGUI.class, new BorderTimeGUI(this.gameManager));
        this.main.getRegisteredInventories().put(PvPTimeGUI.class, new PvPTimeGUI(this.gameManager));
        this.main.getRegisteredInventories().put(EpisodeTimeGUI.class, new EpisodeTimeGUI(this.gameManager));
        this.main.getRegisteredInventories().put(DefaultInvGUI.class, new DefaultInvGUI());
        this.main.getRegisteredInventories().put(DefaultDeathInvGUI.class, new DefaultDeathInvGUI());
        this.main.getRegisteredInventories().put(CycleManagerGUI.class, new CycleManagerGUI(this.gameManager));
        this.main.getRegisteredInventories().put(EnchantCommand.class, this.enchantCommand);
        this.main.getRegisteredInventories().put(this.scenarioCommand.getClass(), this.scenarioCommand);
        this.main.getRegisteredInventories().put(GeneralRulesGUI.class, new GeneralRulesGUI(this.gameManager));
        this.main.getRegisteredInventories().put(DropItemRateGUI.class, new DropItemRateGUI(this.gameManager));
        this.main.getRegisteredInventories().put(DiamondMaxGUI.class, new DiamondMaxGUI(this.gameManager));
        this.main.getRegisteredInventories().put(GoldMaxGUI.class, new GoldMaxGUI(this.gameManager));
        this.main.getRegisteredInventories().put(OresLimitGUI.class, new OresLimitGUI(this.gameManager));
        this.main.getRegisteredInventories().put(InvCommand.class, this.invCommand);
        this.main.getRegisteredInventories().put(RulesInventory.class, new RulesInventory(this.gameManager));
        this.main.getRegisteredInventories().put(PotionManagerGUI.class, new PotionManagerGUI(this.gameManager));
        this.main.getRegisteredInventories().put(AdminPanelGUI.class, new AdminPanelGUI());
        this.main.getRegisteredInventories().put(EnchantMaxGUI.class, new EnchantMaxGUI());
        this.main.getRegisteredInventories().put(DamageGUI.class, new DamageGUI());
        this.main.getRegisteredInventories().put(DateGUI.class, new DateGUI(this.getMain().getGameManager()));
        this.main.getRegisteredInventories().put(DeconnexionTimeGUI.class, new DeconnexionTimeGUI(this.gameManager));
    }

    private void registerNameTag() {
        for (Teams teams : Teams.values()) {
            if (this.scoreboard.getTeam(teams.getName()) != null) {
                this.scoreboard.getTeam(teams.getName()).unregister();
            }
            Team t = this.scoreboard.registerNewTeam(teams.getName());
            t.setPrefix(teams.getColor() + teams.getName() + " ");
            t.setCanSeeFriendlyInvisibles(false);
        }
    }

    public void onDisable() {
        this.scoreboardManager.onDisable();
    }

    public ScenariosGUI getScenariosGUI() {
        return this.scenariosGUI;
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public JoinTeamsGUI getJoinTeamsGUI() {
        return this.joinTeamsGUI;
    }

    public API getMain() {
        return this.main;
    }

    public ScoreboardManager getScoreboardManager() {
        return this.scoreboardManager;
    }
}
