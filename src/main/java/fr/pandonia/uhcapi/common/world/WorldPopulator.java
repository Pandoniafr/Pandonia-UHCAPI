package fr.pandonia.uhcapi.common.world;

import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.module.ModuleType;
import fr.pandonia.uhcapi.utils.Title;
import fr.pandonia.uhcapi.utils.msg.ProgressBar;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WorldPopulator {
    private final GameManager gameManager;
    private final World gameWorld;

    public WorldPopulator(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameWorld = (World)Bukkit.getWorlds().get(0);
    }

    public void setRoofed() {
        if (this.gameManager.getModuleManager().getCurrentModule().equals((Object) ModuleType.DEMONSLAYER)) {
            new BukkitRunnable(){
                int yInicial = 50;
                int progress = 0;
                int YChange = this.yInicial;

                public void run() {
                    int radius = 250;
                    if (this.progress == 0) {
                        System.out.println("[Oerthyon] Nettoyage du centre de la carte..");
                    }
                    for (int x = 0 - radius; x <= 0 + radius; ++x) {
                        for (int z = 0 - radius; z <= 0 + radius; ++z) {
                            Block block = WorldPopulator.this.gameWorld.getBlockAt(x, this.YChange, z);
                            block.setBiome(Biome.ROOFED_FOREST);
                            if (block.getType() == Material.LEAVES || block.getType() == Material.LEAVES_2 || block.getType() == Material.LOG || block.getType() == Material.LOG_2) {
                                block.setType(Material.AIR);
                                if (!block.getLocation().add(0.0, -1.0, 0.0).getBlock().getType().equals(Material.DIRT)) continue;
                                block.getLocation().add(0.0, -1.0, 0.0).getBlock().setType(Material.GRASS);
                                continue;
                            }
                            if (block.getType() != Material.WATER && block.getType() != Material.STATIONARY_WATER) continue;
                            block.setType(Material.GRASS);
                        }
                    }
                    ++this.YChange;
                    ++this.progress;
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        Title.sendActionBar(player, ChatColor.YELLOW + "Nettoyage du centre: " + ChatColor.GREEN + this.progress + "% §8[§r" + ProgressBar.getProgressBar(this.progress, 80, 20, "|", ChatColor.YELLOW, ChatColor.GRAY) + "§8]");
                    }
                    if (this.progress >= 80) {
                        this.cancel();
                        System.out.println("[Oerthyon] Nettoyage du centre de la carte terminé !");
                        WorldPopulator.this.addSapling();
                    }
                }
            }.runTaskTimer(this.gameManager.getApi(), 1L, 5L);
        } else {
            new LoadingChunkTask(this.gameWorld, 1200);
        }
    }

    private void addSapling() {
        System.out.println("[Oerthyon] Plantage d'arbres au centre de la carte..");
        new Thread(() -> new BukkitRunnable(){
            int yInicial = 50;
            int progress = 0;
            int YChange = this.yInicial;

            public void run() {
                int radius = 250;
                for (int x = 0 - radius; x <= radius; ++x) {
                    for (int z = 0 - radius; z <= radius; ++z) {
                        Block block = WorldPopulator.this.gameWorld.getBlockAt(x, this.YChange, z);
                        if (block.getType() != Material.AIR || !WorldPopulator.this.gameWorld.getBlockAt(x, this.YChange - 1, z).getType().equals(Material.DIRT) && !WorldPopulator.this.gameWorld.getBlockAt(x, this.YChange - 1, z).getType().equals(Material.GRASS)) continue;
                        int i = ThreadLocalRandom.current().nextInt(36);
                        if (i <= 2) {
                            block.getWorld().generateTree(block.getLocation(), TreeType.DARK_OAK);
                        }
                        if (i == 33) {
                            block.getWorld().generateTree(block.getLocation(), TreeType.BROWN_MUSHROOM);
                            continue;
                        }
                        if (i != 34) continue;
                        block.getWorld().generateTree(block.getLocation(), TreeType.RED_MUSHROOM);
                    }
                }
                ++this.YChange;
                ++this.progress;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Title.sendActionBar(player, ChatColor.YELLOW + "Création de la forêt: " + ChatColor.GREEN + this.progress + "% §8[§r" + ProgressBar.getProgressBar(this.progress, 60, 20, "|", ChatColor.YELLOW, ChatColor.GRAY) + "§8]");
                }
                if (this.progress >= 60) {
                    new LoadingChunkV2(WorldPopulator.this.gameWorld);
                    this.cancel();
                }
            }
        }.runTaskTimer(this.gameManager.getApi(), 1L, 5L)).run();
    }

    public World getGameWorld() {
        return this.gameWorld;
    }
}
