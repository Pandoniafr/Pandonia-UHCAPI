package fr.pandonia.uhcapi.common.rules;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.GamePlayer;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.config.scenario.special.runnable.SkyHighRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PvP
        implements Rule,
        Listener {
    private boolean pvp = false;

    public boolean isActive() {
        return this.pvp;
    }

    public void setActive(boolean active) {
        this.pvp = active;
        Bukkit.broadcastMessage("§f[§c§l!§f] §fLe PvP est désormais " + (active ? "§aactivé" : "§cdésactivé") + "§f.");
        if (this.pvp && Scenario.SKYHIGH.isEnabled()) {
            SkyHighRunnable skyHighRunnable = new SkyHighRunnable(API.getAPI().getGameManager());
            skyHighRunnable.runTaskTimer(API.getAPI(), 0L, 20L);
            Bukkit.broadcastMessage("§eLe scénario §6SkyHigh §eest désormais actif !");
            Bukkit.broadcastMessage("§eVeuillez vous rendre au dessus de la couche §c" + Scenario.SKYHIGH.getValue());
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.playSound(players.getLocation(), Sound.WITHER_SPAWN, 3.0f, 0.0f);
            }
        }
    }

    @EventHandler
    private void EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        if (!this.pvp) {
            Projectile projectile;
            if (entity instanceof Player && damager instanceof Player) {
                event.setCancelled(true);
            } else if (damager instanceof Projectile && (projectile = (Projectile)((Object)damager)).getShooter() instanceof Player && entity instanceof Player) {
                event.setCancelled(true);
            }
        } else {
            boolean playerVSPlayer = false;
            if (entity instanceof Player) {
                Projectile projectile;
                if (damager instanceof Player) {
                    playerVSPlayer = true;
                } else if (damager instanceof Projectile && (projectile = (Projectile)((Object)damager)).getShooter() instanceof Player) {
                    playerVSPlayer = true;
                }
                if (playerVSPlayer) {
                    Projectile projectile2;
                    Player attacker = null;
                    if (damager instanceof Player) {
                        attacker = (Player)damager;
                    } else if (damager instanceof Projectile && (projectile2 = (Projectile)((Object)damager)).getShooter() instanceof Player) {
                        attacker = (Player)((Object)projectile2.getShooter());
                    }
                    if (attacker == null) {
                        return;
                    }
                    Player victim = (Player)entity;
                    GamePlayer gameAttacker = GamePlayer.getPlayer(attacker.getUniqueId());
                    GamePlayer gameVictim = GamePlayer.getPlayer(victim.getUniqueId());
                    if (gameAttacker != null) {
                        gameAttacker.setLastFight(System.currentTimeMillis());
                    }
                    if (gameVictim != null) {
                        gameVictim.setLastFight(System.currentTimeMillis());
                    }
                }
            }
        }
    }

    @Override
    public void onLoad(API main) {
        main.getServer().getPluginManager().registerEvents(this, main);
    }
}
