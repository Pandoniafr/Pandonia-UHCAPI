package fr.pandonia.uhcapi.common.scoreboard;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.UHCInfos;
import fr.pandonia.uhcapi.game.GameUtils;
import fr.pandonia.uhcapi.module.ModuleType;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;

import java.util.UUID;

public class DefaultScoreboardContents
        implements ScoreboardContents {
    private final API api;
    private int online;
    private int maxplayer;
    private String moduleName;
    private String hostName;
    private boolean teams;
    private ModuleType moduleType;

    public DefaultScoreboardContents(API api) {
        this.api = api;
    }

    @Override
    public void reloadData(UUID player) {
        this.maxplayer = this.api.getGameManager().getGameConfig().getGameSlot();
        this.online = GameUtils.getPlayerAmount();
        this.moduleName = this.api.getGameManager().getModuleManager().getCurrentModule().getName();
        this.teams = !GameUtils.isSoloMode();
        this.moduleType = this.api.getGameManager().getModuleManager().getCurrentModule();
        this.hostName = UHCInfos.hostName == null ? "Aucun" : UHCInfos.hostName;
    }

    @Override
    public void setLines(BPlayerBoard board, UUID player, String ip) {
        int line = 14;
        board.setName("§6§lUHC");
        board.set("§1", line--);
        board.set(" §8┃ §fJoueur" + (this.online == 1 ? "" : "s") + "§f: §c" + this.online + "§f/§c" + this.maxplayer, line--);
        board.set(" §8┃ §fHost §f: §c" + this.hostName, line--);
        board.set(" §8┃ §fJeu §f: §6" + this.moduleName, line--);
        if (this.teams) {
            int am = this.api.getGameManager().getGameConfig().getPlayerPerTeam();
            board.set(" §8┃ §fÉquipes §f: §c" + am + "§fvs§c" + am, line--);
        }
        board.set("§2", line--);
        board.set(ip, line--);
    }
}
