package fr.pandonia.uhcapi.game.combatlog;

import java.util.UUID;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class CombatLogEntity {
    private final UUID uuid;
    private final String name;
    private final Location location;
    private Entity entity;

    public CombatLogEntity(Player player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        this.location = player.getLocation();
        this.spawnVillager();
    }

    public void spawnVillager() {
        Entity entity = this.location.getWorld().spawnEntity(this.location, EntityType.VILLAGER);
        Villager villager = (Villager)entity;
        this.setEntityNoAI(villager);
        villager.setCustomNameVisible(true);
        villager.setCustomName("§8§l● §c§l" + this.name);
    }

    private void setEntityNoAI(Entity entity) {
        net.minecraft.server.v1_8_R3.Entity nms = ((CraftEntity)entity).getHandle();
        NBTTagCompound tag = new NBTTagCompound();
        nms.c(tag);
        tag.setBoolean("NoAI", true);
        EntityLiving entitys = (EntityLiving)nms;
        entitys.a(tag);
    }
}

