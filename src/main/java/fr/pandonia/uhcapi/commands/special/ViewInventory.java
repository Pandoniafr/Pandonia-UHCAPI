package fr.pandonia.uhcapi.commands.special;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.GamePlayer;
import fr.pandonia.uhcapi.utils.Chrono;
import fr.pandonia.uhcapi.utils.ItemCreator;
import fr.pandonia.uhcapi.utils.TranslateEffect;
import fr.pandonia.uhcapi.utils.gui.GUIView;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class ViewInventory
        extends GUIView {
    private final Player target;
    private final List<Integer> slotsCancel;

    public ViewInventory(Player player, Player target) {
        super(54, player, target);
        this.target = target;
        this.slotsCancel = new ArrayList<Integer>();
        this.slotsCancel.add(36);
        this.slotsCancel.add(37);
        this.slotsCancel.add(38);
        this.slotsCancel.add(39);
        this.slotsCancel.add(45);
        this.slotsCancel.add(46);
        this.slotsCancel.add(47);
        this.slotsCancel.add(48);
        this.slotsCancel.add(49);
        this.slotsCancel.add(50);
        GamePlayer gamePlayer = GamePlayer.getPlayer(target.getUniqueId());
        for (int i = 0; i < 36; ++i) {
            ItemStack item = target.getInventory().getContents()[i];
            this.setItem(i, item);
        }
        this.setItem(36, target.getInventory().getHelmet());
        this.setItem(37, target.getInventory().getChestplate());
        this.setItem(38, target.getInventory().getLeggings());
        this.setItem(39, target.getInventory().getBoots());
        this.setItem(45, new ItemCreator(Material.SKULL_ITEM).setDurability(3).setOwner(target.getName()).setName("§6§l" + target.getName()).addLore(gamePlayer.isAlive() ? "§aVivant" : "§cMort").getItem());
        this.setItem(46, new ItemCreator(Material.APPLE).setName("§fVie§8 : §c" + (int)target.getHealth() + " §8/ §c" + (int)target.getMaxHealth()).setAmount((int)target.getHealth()).getItem());
        this.setItem(47, new ItemCreator(Material.COOKED_BEEF).setName("§fFaim§8 : §e" + target.getFoodLevel()).setAmount(target.getFoodLevel()).getItem());
        ItemCreator itemCreator = new ItemCreator(Material.POTION).setDurability(8265).setName("§eEffets").setAmount(target.getActivePotionEffects().size()).addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        if (target.getActivePotionEffects().isEmpty()) {
            itemCreator.addLore("§cAucun effet");
        } else {
            for (PotionEffect potionEffect : target.getActivePotionEffects()) {
                itemCreator.addLore("§f- §b" + TranslateEffect.translate(potionEffect.getType()) + " " + (potionEffect.getAmplifier() + 1) + " §f: " + Chrono.timeToDigitalString(potionEffect.getDuration() / 20));
            }
        }
        this.setItem(48, itemCreator.getItem());
        this.setItem(49, new ItemCreator(Material.DIAMOND).setAmount(gamePlayer.getDiamonds()).setName("§b" + gamePlayer.getDiamonds() + " §fminés").getItem());
        this.setItem(50, new ItemCreator(Material.GOLD_INGOT).setAmount(gamePlayer.getGolds()).setName("§e" + gamePlayer.getGolds() + " §fminés").getItem());
        new BukkitRunnable(){

            public void run() {
                if (ViewInventory.this.isClosed()) {
                    this.cancel();
                    return;
                }
            }
        }.runTaskTimer(API.getAPI(), 10L, 50L);
    }

    @Override
    public void guiInteractEvent(final int slot, ItemStack itemStack, InventoryClickEvent event) {
        if (slot >= 36) {
            event.setCancelled(true);
        }
        final Player target = Bukkit.getPlayer(this.getTarget());
        new BukkitRunnable(){

            public void run() {
                for (int i = 0; i < 36; ++i) {
                    ItemStack item = ViewInventory.this.getItem(slot);
                    target.getInventory().setItem(slot, item);
                }
            }
        }.runTaskLater(API.getAPI(), 10L);
    }

    @Override
    public void guiUserInteractEvent(int slot, ItemStack itemStack, InventoryClickEvent event) {
        new BukkitRunnable(){

            public void run() {
                for (int i = 0; i < 36; ++i) {
                    ItemStack item = ViewInventory.this.target.getInventory().getContents()[i];
                    ViewInventory.this.setItem(i, item);
                }
            }
        }.runTaskLater(API.getAPI(), 10L);
    }
}
