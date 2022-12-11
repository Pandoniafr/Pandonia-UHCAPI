package fr.pandonia.uhcapi.game;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.GamePlayer;
import fr.pandonia.uhcapi.common.border.SimpleBorder;
import fr.pandonia.uhcapi.common.world.WorldPopulator;
import fr.pandonia.uhcapi.config.GameConfig;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.ScenarioManager;
import fr.pandonia.uhcapi.game.combatlog.CombatLogManager;
import fr.pandonia.uhcapi.game.cycle.CycleManager;
import fr.pandonia.uhcapi.game.episode.EpisodeManager;
import fr.pandonia.uhcapi.game.task.BeforeStartTask;
import fr.pandonia.uhcapi.game.task.GlobalTask;
import fr.pandonia.uhcapi.game.task.StartGameCountDown;
import fr.pandonia.uhcapi.game.team.TeamManager;
import fr.pandonia.uhcapi.game.team.Teams;
import fr.pandonia.uhcapi.game.teleportation.TeleportationManager;
import fr.pandonia.uhcapi.game.teleportation.form.CircleForm;
import fr.pandonia.uhcapi.game.teleportation.form.Form;
import fr.pandonia.uhcapi.game.teleportation.player.PlayerPlate;
import fr.pandonia.uhcapi.game.teleportation.player.SoloPlayerPlate;
import fr.pandonia.uhcapi.game.teleportation.player.TeamPlayerPlate;
import fr.pandonia.uhcapi.module.ModuleManager;
import fr.pandonia.uhcapi.module.standard.UHCFinisherGame;
import fr.pandonia.uhcapi.module.standard.UHCStandard;
import fr.pandonia.uhcapi.utils.InventoryAPI;
import fr.pandonia.uhcapi.utils.TabHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class GameManager {
    private final API api;
    private final GameConfig gameConfig;
    private final ModuleManager moduleManager;
    private final EpisodeManager episodeManager;
    private final TeamManager teamManager;
    private final WorldPopulator worldPopulator;
    private final CycleManager cycleManager;
    private final CombatLogManager combatLogManager;
    private final UHCStandard uhcStandard;
    private final UHCFinisherGame uhcFinisherGame;
    private final SimpleBorder border;
    private final List<UUID> inGamePlayers;
    private final List<Teams> aliveTeams;
    private final List<UUID> offlinePlayers;
    private final List<Scenario> enabledScenarios;
    private final List<UUID> playedPlayers;
    private final List<String> playedDomeiPlayers;
    private final List<String> whitelistedPlayers;
    private GameState gameState;
    private boolean announcedOnHub;
    private boolean preload;
    private boolean preloadFinished;
    private int groupe;
    private BeforeStartTask beforeStartTask;
    private GlobalTask globalTask;
    private StartGameCountDown startGameCountDown;
    private UUID gameHost;
    private final List<UUID> hosts;
    private final List<String> banList;
    private final List<UUID> vanishList;

    public GameManager(API api) {
        this.api = api;
        this.moduleManager = new ModuleManager(this);
        this.gameState = GameState.WAITING;
        this.inGamePlayers = new ArrayList<UUID>();
        this.playedPlayers = new ArrayList<UUID>();
        this.hosts = new ArrayList<UUID>();
        this.banList = new ArrayList<String>();
        this.vanishList = new ArrayList<UUID>();
        this.playedDomeiPlayers = new ArrayList<String>();
        this.aliveTeams = new ArrayList<Teams>();
        this.offlinePlayers = new ArrayList<UUID>();
        this.enabledScenarios = new ArrayList<Scenario>();
        this.whitelistedPlayers = new ArrayList<String>();
        this.gameConfig = new GameConfig(this);
        this.episodeManager = new EpisodeManager(this);
        this.teamManager = new TeamManager(this);
        this.worldPopulator = new WorldPopulator(this);
        this.uhcStandard = new UHCStandard(this);
        this.uhcFinisherGame = new UHCFinisherGame(this.api);
        this.border = new SimpleBorder(this.worldPopulator.getGameWorld().getWorldBorder());
        this.cycleManager = new CycleManager(api, this.worldPopulator.getGameWorld());
        this.combatLogManager = new CombatLogManager(this);
        this.groupe = 6;
        this.beforeStartTask = new BeforeStartTask(this);
        this.beforeStartTask.runTaskTimer(api, 60L, 40L);
        this.announcedOnHub = false;
        this.preload = false;
        this.preloadFinished = false;
        Scenario.TIMBER.getScenarioManager().activeScenario();
        Scenario.CUTCLEAN.getScenarioManager().activeScenario();
        Scenario.HASTEYBOYS.getScenarioManager().activeScenario();
        Scenario.SAFEMINER.getScenarioManager().activeScenario();
        Scenario.CAT_EYES.getScenarioManager().activeScenario();
        Scenario.BETAZOMBIE.getScenarioManager().activeScenario();
    }

    public void startWithTimer() {
        this.setGameState(GameState.STARTING);
        this.startGameCountDown = new StartGameCountDown(this);
        this.startGameCountDown.runTaskTimer(this.api, 0L, 20L);
    }

    public void startGame() {
        this.setGameState(GameState.PLAYING);
        this.api.getModules().onStart(this.api);
        this.globalTask = new GlobalTask(this);
        this.globalTask.runTaskTimer(this.api, 0L, 20L);
        this.cycleManager.startDayCycle(this.gameConfig.getDayNightDuration());
        Bukkit.broadcastMessage("       §f(§a!§f) §fBien le bonjour §f(§a!§f) ");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(" §8» §fVoici certaines §crègles§f à respecter.");
        Bukkit.broadcastMessage("   §8┃ §fLe respect des §cgroupes§f. §f(§fMode §f: §c§lSolo§f)");
        Bukkit.broadcastMessage("   §8┃ §fNe pas §csoundboard§f. §f(§fMode §f: §c§lSolo §fet §c§léquipes§f)");
        Bukkit.broadcastMessage("   §8┃ §fNe pas §ctuer§f sans raison. §f(§fMode §f: §c§lSolo §fet §c§léquipes§f)");
        Bukkit.broadcastMessage("   §8┃ §fNe pas §cdévoiler§f son rôle.");
        Bukkit.broadcastMessage("§4");
        Bukkit.broadcastMessage(" §8» §fVoici les §ccommandes§f à connaitre.");
        Bukkit.broadcastMessage("   §8┃ §f/§cdoc §8• §fPermet de voir le §cdocument explicatif§f du mode.");
        Bukkit.broadcastMessage("   §8┃ §f/§cmumble §8• §fPermet de voir le mumble de §c§lDOMEI");
        Bukkit.broadcastMessage("   §8┃ §f/§crules §8• §fPermet de voir les §crégles§f.");
        Bukkit.broadcastMessage("   §8┃ §f/§chelpop §8• §fPermet de §cdemander§f de l'aide.");
        Bukkit.broadcastMessage("§4");
        Bukkit.broadcastMessage(" §8» §fBonne §achance§f à tous !");
        Bukkit.broadcastMessage("§4");
        for (UUID uuid : this.getInGamePlayers()) {
            Player player2 = Bukkit.getPlayer(uuid);
            if (player2 == null) continue;
            player2.getInventory().clear();
            player2.getInventory().setArmorContents(null);
            player2.setFoodLevel(20);
            player2.setHealth(player2.getMaxHealth());
            player2.setExp(0.0f);
            player2.setLevel(0);
            TabHandler.removePrefixFor(player2);
            GameUtils.clearPlayerEffect(player2);
            InventoryAPI.giveInvent(player2);
        }
        Arrays.stream(Scenario.values()).filter(Scenario::isEnabled).map(Scenario::getScenarioManager).forEach(ScenarioManager::onStart);
        this.worldPopulator.getGameWorld().setGameRuleValue("randomTickSpeed", "3");
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> !this.inGamePlayers.contains(player.getUniqueId()) && player.isOp())
                .map(OfflinePlayer::getUniqueId)
                .map(GamePlayer::getPlayer)
                .forEach(gamePlayer -> gamePlayer.setAlerts(true));
    }

    public void tryStartGame() {
        if (!this.gameState.equals((Object)GameState.STARTING)) {
            return;
        }
        System.out.println("[UHC] Starting game..");
        this.setGameState(GameState.TELEPORTATION);
        this.inGamePlayers.clear();
        this.playedPlayers.clear();
        this.offlinePlayers.clear();
        this.aliveTeams.clear();
        this.enabledScenarios.clear();
        this.border.init(this.gameConfig.getBorderStartSize() * 2, 0, 0);
        ArrayList<SoloPlayerPlate> playerPlatesList = new ArrayList<SoloPlayerPlate>();
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.getGameMode().equals(GameMode.SPECTATOR)) continue;
            UUID uuid = players.getUniqueId();
            this.inGamePlayers.add(uuid);
            this.playedPlayers.add(uuid);
            this.playedDomeiPlayers.add(uuid.toString());
            playerPlatesList.add(new SoloPlayerPlate(players));
        }
        for (UUID uuid : this.inGamePlayers) {
            Player player = Bukkit.getPlayer(uuid);
            GamePlayer gamePlayer = GamePlayer.getPlayer(uuid);
            if (player == null || gamePlayer == null) {
                this.inGamePlayers.remove(uuid);
                continue;
            }
            if (this.gameConfig.getPlayerPerTeam() > 1 && gamePlayer.getTeams() == null) {
                for (Teams teams : Teams.values()) {
                    if (this.teamManager.getPlayerAmountInTeam(teams) >= this.gameConfig.getPlayerPerTeam()) continue;
                    this.teamManager.addPlayerToTeam(player, teams);
                    break;
                }
            }
            GameUtils.startPlayer(player, GameMode.ADVENTURE);
        }
        if (this.gameConfig.getPlayerPerTeam() > 1) {
            for (Teams iterator : Teams.values()) {
                if (this.teamManager.getPlayerAmountInTeam((Teams)((Object)iterator)) < 1) continue;
                this.getAliveTeams().add((Teams)((Object)iterator));
            }
        }
        for (Scenario iterator : Scenario.values()) {
            if (!((Scenario)((Object)iterator)).isEnabled()) continue;
            this.enabledScenarios.add((Scenario)((Object)iterator));
        }
        World gameWorld = this.worldPopulator.getGameWorld();
        CircleForm circleForm = new CircleForm(this.gameConfig.getBorderStartSize() - 10, 150, gameWorld, gameWorld.getWorldBorder().getCenter());
        if (GameUtils.isSoloMode()) {
            PlayerPlate[] playerPlates = (PlayerPlate[])playerPlatesList.toArray((Object[])new SoloPlayerPlate[0]);
            TeleportationManager teleportationManager = new TeleportationManager(playerPlates, 2L, (Form)circleForm);
            teleportationManager.teleportAllAndStart(this.api);
        } else {
            TeamPlayerPlate[] arrayOfTeamPlayerPlate = this.getTeamPlayerPlate(this.teamManager);
            TeleportationManager teleportationManager = new TeleportationManager(arrayOfTeamPlayerPlate, 2L, (Form)circleForm);
            teleportationManager.teleportAllAndStart(this.api);
        }
    }

    private TeamPlayerPlate[] getTeamPlayerPlate(TeamManager teamManager) {
        return (TeamPlayerPlate[])Arrays.stream(Teams.values()).limit(100L).filter(teams -> teamManager.getPlayerAmountInTeam((Teams)((Object)teams)) > 0).collect(Collectors.toList()).stream().map(team -> {
            ArrayList<UUID> players = new ArrayList<UUID>();
            teamManager.getPlayersInTeam((Teams)((Object)((Object)team)));
            return new TeamPlayerPlate(players, ((Teams)((Object)((Object)team))).getColor() + "Équipe " + ((Teams)((Object)((Object)team))).getColor() + ((Teams)((Object)((Object)team))).getName());
        }).collect(Collectors.toList()).toArray((Object[])new TeamPlayerPlate[0]);
    }

    public boolean isPreloadFinished() {
        return this.preloadFinished;
    }

    public void setPreloadFinished(boolean preloadFinished) {
    }

    public boolean isPreload() {
        return this.preload;
    }

    public void setPreload(boolean preload) {
        this.preload = preload;
    }

    public boolean isAnnouncedOnHub() {
        return this.announcedOnHub;
    }

    public CombatLogManager getCombatLogManager() {
        return this.combatLogManager;
    }

    public List<String> getWhitelistedPlayers() {
        return this.whitelistedPlayers;
    }

    public int getGroupe() {
        return this.groupe;
    }

    public void setGroupe(int groupe) {
        this.groupe = groupe;
    }

    public StartGameCountDown getStartGameCountDown() {
        return this.startGameCountDown;
    }

    public GlobalTask getGlobalTask() {
        return this.globalTask;
    }

    public UUID getGameHost() {
        return this.gameHost;
    }

    public void setGameHost(UUID gameHost) {
        this.gameHost = gameHost;
        this.whitelistedPlayers.add(Bukkit.getOfflinePlayer(gameHost).getName());
    }

    public SimpleBorder getBorder() {
        return this.border;
    }

    public UHCFinisherGame getUhcFinisherGame() {
        return this.uhcFinisherGame;
    }

    public UHCStandard getUhcStandard() {
        return this.uhcStandard;
    }

    public WorldPopulator getWorldPopulator() {
        return this.worldPopulator;
    }

    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }

    public TeamManager getTeamManager() {
        return this.teamManager;
    }

    public CycleManager getCycleManager() {
        return this.cycleManager;
    }

    public List<Scenario> getEnabledScenarios() {
        return this.enabledScenarios;
    }

    public List<UUID> getOfflinePlayers() {
        return this.offlinePlayers;
    }

    public List<Teams> getAliveTeams() {
        return this.aliveTeams;
    }

    public List<String> getPlayedDomeiPlayers() {
        return this.playedDomeiPlayers;
    }

    public List<UUID> getPlayedPlayers() {
        return this.playedPlayers;
    }

    public List<UUID> getInGamePlayers() {
        return this.inGamePlayers;
    }

    public GameConfig getGameConfig() {
        return this.gameConfig;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public EpisodeManager getEpisodeManager() {
        return this.episodeManager;
    }

    public List<UUID> getHosts() {
        return this.hosts;
    }

    public List<String> getBanList() {
        return this.banList;
    }

    public List<UUID> getVanishList() {
        return this.vanishList;
    }

    public API getApi() {
        return this.api;
    }

    public boolean hasHostAccess(Player player) {
        return this.gameHost != null && this.gameHost.equals(player.getUniqueId()) || player.isOp() || this.getHosts().contains(player.getUniqueId());
    }
}
