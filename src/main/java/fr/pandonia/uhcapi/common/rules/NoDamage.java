package fr.pandonia.uhcapi.common.rules;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.game.GameUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class NoDamage
        implements Rule,
        Listener {
    private boolean noDamage = false;
    private final List<UUID> noDamagePlayers = new ArrayList<UUID>();

    public boolean isActive(UUID uuid) {
        return this.noDamagePlayers.contains(uuid);
    }

    public void active(UUID uuid, boolean activated) {
        this.noDamagePlayers.remove(uuid);
        if (activated) {
            this.noDamagePlayers.add(uuid);
        }
    }

    public boolean isActive() {
        return this.noDamage;
    }

    public void setActive(boolean active) {
        this.noDamage = active;
        if (!active) {
            Bukkit.broadcastMessage("§f[§a§l!§f] §fL'invincibilité est désormais §cdésactivée§f.");
            GameUtils.registerHealth();
        }
    }

    @EventHandler
    private void onFood(FoodLevelChangeEvent event) {
        Player player = (Player)((Object)event.getEntity());
        if (this.noDamage || this.noDamagePlayers.contains(player.getUniqueId())) {
            event.setCancelled(true);
            player.setFoodLevel(20);
        }
    }

    @EventHandler
    private void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if (this.noDamage || this.noDamagePlayers.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void onLoad(API main) {
        main.getServer().getPluginManager().registerEvents(this, main);
    }
}
