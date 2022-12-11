package fr.pandonia.uhcapi.utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

public class HologramCreate {
    public void create() {
        Bukkit.getWorld("Lobby").getEntitiesByClass(ArmorStand.class).forEach(Entity::remove);
        ArrayList<String> hologram = new ArrayList<String>();
        hologram.add("§f(§a!§f) §fBien le bonjour §f(§a!§f) ");
        hologram.add("");
        hologram.add(" §8» §8Voici certaines §crègles§f à respecter.");
        hologram.add("   §8┃ §fLe respect des §cgroupes§f. §f(§fMode §f: §c§lSolo§f)");
        hologram.add("   §8┃ §fNe pas §csoundboard§f. §f(§fMode §f: §c§lSolo §fet §c§léquipes§f)");
        hologram.add("   §8┃ §fNe pas §ctuer§f sans raison. §f(§fMode §f: §c§lSolo §fet §c§léquipes§f)");
        hologram.add("   §8┃ §fNe pas §cdévoile§f son rôle.");
        hologram.add("§4");
        hologram.add(" §8» §8Voici les §ccommandes§f à connaitre.");
        hologram.add("   §8┃ §f/§cdoc §8• §fPermet de voir le §cdocument explicatif§f du mode.");
        hologram.add("   §8┃ §f/§cmumble §8• §fPermet de voir le mumble de §c§lDOMEI");
        hologram.add("   §8┃ §f/§crules §8• §fPermet de voir les §crégles§f.");
        hologram.add("   §8┃ §f/§chelpop §8• §fPermet de §cdemander§f de l'aide.");
        hologram.add("§4");
        hologram.add(" §8» §fBonne §achance§f à tous !");
        hologram.add("§4");
        new Hologram(new Location(Bukkit.getWorld("Lobby"), -65.9, 103.6, 15.2), hologram, 800);
    }
}

