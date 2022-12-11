package fr.pandonia.uhcapi.game.combatlog;

import fr.pandonia.uhcapi.GamePlayer;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.game.GameUtils;
import fr.pandonia.uhcapi.utils.CombatUtilsAPI;
import org.bukkit.entity.Player;

public class CombatLogManager {
    private final GameManager gameManager;

    public CombatLogManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void onLogout(Player player) {
        if (!GameUtils.isGameStarted())
            return;
        if (this.gameManager.getInGamePlayers().contains(player.getUniqueId())) {
            GamePlayer gamePlayer = GamePlayer.getPlayer(player.getUniqueId());
            if (gamePlayer == null)
                return;
            long lastFight = CombatUtilsAPI.getTimeBeforeLastFight(gamePlayer);
            if (lastFight <= 60L) {
                CombatLogEntity combatLog = new CombatLogEntity(player);
                gamePlayer.setCombatLogEntity(combatLog);
            }
        }
    }
}
