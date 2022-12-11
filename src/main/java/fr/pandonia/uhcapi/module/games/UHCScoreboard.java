package fr.pandonia.uhcapi.module.games;

import fr.pandonia.uhcapi.GamePlayer;
import fr.pandonia.uhcapi.common.rules.Rules;
import fr.pandonia.uhcapi.common.scoreboard.ScoreboardContents;
import fr.pandonia.uhcapi.config.GameConfig;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.utils.Chrono;
import fr.pandonia.uhcapi.utils.DaMath;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class UHCScoreboard implements ScoreboardContents {
    private final GameManager gameManager;

    private final GameConfig gameConfig;

    private long seconds;

    private boolean isPvp;

    private boolean isBorder;

    private String gameTime;

    private String pvpTime;

    private String borderTime;

    private int episode;

    private int kills;

    private int groupe;

    private int borderSize;

    private int locCenterPlayer;

    private int playersSize;

    private String arrow;

    public UHCScoreboard(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameConfig = gameManager.getGameConfig();
    }

    public void reloadData(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        this.seconds = this.gameManager.getGlobalTask().getGlobalTime();
        this.isPvp = Rules.pvp.isActive();
        this.isBorder = this.gameManager.getBorder().isStart();
        if (player.getLocation().getWorld() != null)
            if (player.getLocation().getWorld().getName().equalsIgnoreCase("world_nether")) {
                this.locCenterPlayer = (int)Bukkit.getWorld("world_nether").getWorldBorder().getCenter().distance(player.getLocation());
                this.arrow = DaMath.getArrow(player.getLocation().clone(), Bukkit.getWorld("world_nether").getWorldBorder().getCenter().clone());
            } else {
                this.locCenterPlayer = (int)this.gameManager.getApi().getLobbyPopulator().getCenter().distance(player.getLocation());
                this.arrow = DaMath.getArrow(player.getLocation().clone(), this.gameManager.getApi().getLobbyPopulator().getCenter().clone());
            }
        this.borderSize = (int)this.gameManager.getWorldPopulator().getGameWorld().getWorldBorder().getSize() / 2;
        this.gameTime = Chrono.timeToDigitalString(this.seconds);
        this.pvpTime = Chrono.timeToDigitalString(this.gameConfig.getPvpTime() - this.seconds);
        this.borderTime = Chrono.timeToDigitalString(this.gameConfig.getBorderTime() - this.seconds);
        this.episode = this.gameManager.getEpisodeManager().getEpisode();
        this.kills = GamePlayer.getPlayer(uuid).getKills();
        this.playersSize = this.gameManager.getInGamePlayers().size();
        this.groupe = this.gameManager.getGroupe();
    }

    public void setLines(BPlayerBoard board, UUID uuid, String ip) {
        int line = 14;
        board.setName("§6§lUHC");
        board.set("§a", Integer.valueOf(line--));
        board.set(" §8│ §fÉpisode §f: §c" + this.episode, Integer.valueOf(line--));
        board.set(" §8│ §fDurée §f: §c" + Chrono.timeToDigitalString(this.seconds), Integer.valueOf(line--));
        board.set(((this.playersSize == 1) ? " §8│ §fJoueur" : " §8│ §fJoueurs" ) + "§f: §c" + this.playersSize, Integer.valueOf(line--));
        board.set("§b", Integer.valueOf(line--));
        board.set(" §8│ §fPvP §f: §c" + (!this.isPvp ? this.pvpTime : "§a✔"), Integer.valueOf(line--));
        board.set(" §8│ §fBordure §f: §c" , Integer.valueOf(line--));
        board.set("   §8● §c"   + (!this.isBorder ? this.borderTime : "§a✔"), Integer.valueOf(line--));
        board.set("   §8● §c"   + this.borderSize + " §f/ §c" + this.borderSize, Integer.valueOf(line--));
        board.set("§c", Integer.valueOf(line--));
        board.set(" §8│ §fCentre §f: §c" + this.locCenterPlayer + "m " + this.arrow, Integer.valueOf(line--));
        if (this.kills > 0)
            board.set(((this.kills == 1) ? " §8│ §fKill" : " §8│ §fKills" ) + "§f: §c" + this.kills, Integer.valueOf(line--));
        board.set("§f", Integer.valueOf(line--));
        board.set(ChatColor.RED + ip, Integer.valueOf(line--));
    }
}
