package fr.pandonia.uhcapi.game.teleportation;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.common.rules.Rules;
import fr.pandonia.uhcapi.game.teleportation.form.Form;
import fr.pandonia.uhcapi.game.teleportation.plate.Plate;
import fr.pandonia.uhcapi.game.teleportation.plate.SquarePlate;
import fr.pandonia.uhcapi.game.teleportation.player.PlayerPlate;
import fr.pandonia.uhcapi.utils.Title;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportationManager {
    private final List<PlayerPlate> players;
    private final long delay;
    private final Form form;
    private final Map<String, Plate> playerPlates;

    public TeleportationManager(PlayerPlate[] players, long delay, Form form) {
        this(Arrays.asList(players), delay, form);
    }

    public TeleportationManager(List<PlayerPlate> players, long delay, Form form) {
        this.players = players;
        this.delay = delay;
        this.form = form;
        this.playerPlates = new HashMap<String, Plate>();
    }

    public void teleportAllAndRun(Runnable runnable) {
        this.createPlate(runnable, this.delay, this.form, this.players);
    }

    public void startCoundown(final API main) {
        new BukkitRunnable(){
            int count = 0;

            public void run() {
                double[] tps = MinecraftServer.getServer().recentTps;
                if (this.count >= 20 || tps[0] >= 19.85) {
                    this.cancel();
                    new BukkitRunnable(){
                        int time = 10;

                        public void run() {
                            if (this.time <= 0) {
                                Bukkit.getOnlinePlayers().forEach(players -> {
                                    Title.sendTitle(players, 0, 20, 10, "§fBonne chance !", "§fQue le §cmeilleur gagne§f.");
                                    players.playSound(players.getLocation(), Sound.EXPLODE, 5.0f, 1.0f);
                                });
                                TeleportationManager.this.finishCountdown();
                                this.cancel();
                            } else if (this.time <= 5 || this.time == 10) {
                                Bukkit.getOnlinePlayers().forEach(players -> {
                                    Title.sendTitle(players, 0, 30, 0, "§fLancement dans", "§c" + this.time);
                                    players.playSound(players.getLocation(), Sound.NOTE_PLING, 5.0f, 1.0f);
                                });
                            }
                            --this.time;
                        }
                    }.runTaskTimer(main, 0L, 20L);
                } else {
                    Bukkit.getOnlinePlayers().forEach(player -> Title.sendActionBar(player, "§fPatientez quelques §csecondes§f..."));
                }
                ++this.count;
            }
        }.runTaskTimer(API.getAPI(), 0L, 20L);
    }

    public void teleportAllAndStart(API main) {
        Rules.noDamage.setActive(true);
        this.teleportAllAndRun(() -> this.startCoundown(main));
    }

    public void finishCountdown() {
        this.launchAll();
        API.getAPI().getGameManager().startGame();
    }

    public void teleportPlayersAndStart(final Runnable runnable) {
        new BukkitRunnable(){
            int count = 0;

            public void run() {
                double[] tps = MinecraftServer.getServer().recentTps;
                if (this.count >= 20 || tps[0] >= 19.85) {
                    this.cancel();
                    runnable.run();
                } else {
                    Bukkit.getOnlinePlayers().forEach(player -> Title.sendActionBar(player, "§fPatientez quelques §csecondes§f..."));
                }
                ++this.count;
            }
        }.runTaskTimer(API.getAPI(), 0L, 20L);
    }

    public void createPlate(final Runnable runnable, long delay, final Form form, Collection<PlayerPlate> players) {
        final int length = players.size();
        final Iterator<PlayerPlate> playerPlateIterator = players.iterator();
        new BukkitRunnable(){
            int i = 0;

            public void run() {
                if (playerPlateIterator.hasNext()) {
                    PlayerPlate playerPlate = (PlayerPlate)playerPlateIterator.next();
                    Plate plate = TeleportationManager.this.initPlate(playerPlate, form.calc(this.i, length));
                    TeleportationManager.this.playerPlates.put(playerPlate.getName(), plate);
                    Bukkit.getWorld("world").loadChunk(Bukkit.getWorld("world").getChunkAt(plate.getTeleportLocation()));
                    Bukkit.getOnlinePlayers().forEach(player -> Title.sendActionBar(player, "§c" + playerPlate.getName() + "§f a été téléporté §f[" + (this.i + 1) + "/" + length + "]"));
                    Bukkit.getWorld("world").loadChunk(Bukkit.getWorld("world").getChunkAt(plate.getTeleportLocation()));
                    playerPlate.getName();
                    ++this.i;
                } else {
                    this.cancel();
                    Bukkit.getScheduler().runTaskLater(API.getAPI(), () -> TeleportationManager.this.teleportPlayersAndStart(runnable), 40L);
                }
            }
        }.runTaskTimer(API.getAPI(), 0L, delay);
    }

    private Plate initPlate(PlayerPlate playerPlate, Location location) {
        SquarePlate squarePlate = new SquarePlate(location, 3, Material.STAINED_GLASS, 14);
        playerPlate.assignPlate(squarePlate);
        return squarePlate;
    }

    private void teleport(PlayerPlate playerPlate, Location location) {
        SquarePlate squarePlate = new SquarePlate(location, 3, Material.STAINED_GLASS, 14);
        playerPlate.assignPlate(squarePlate);
    }

    public void launchAll() {
        TeleportationManager.launchAll(this.players);
    }

    public static void launchAll(List<PlayerPlate> players) {
        players.forEach(PlayerPlate::removePlate);
    }

    public Collection<PlayerPlate> getPlayers() {
        return this.players;
    }
}
