package fr.pandonia.uhcapi.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemCreator {
    private ItemStack item;
    private Player possesseur;
    private String creator_name;
    private ArrayList<String> tag;
    private int slot;
    private ArrayList<Pattern> patterns;

    public ItemCreator(Material material) {
        this.item = new ItemStack(material);
    }

    public ItemCreator(ItemStack item) {
        this.setMaterial(item.getType());
        this.setAmount(item.getAmount());
        this.setDurability(item.getDurability());
        this.setName(item.getItemMeta().getDisplayName());
        this.setEnchantments(item.getItemMeta().getEnchants());
        this.setLores(item.getItemMeta().getLore());
    }

    public ItemCreator(ItemCreator itemcreator) {
        this.item = itemcreator.getItem();
        this.possesseur = itemcreator.getPossesseur();
        this.creator_name = itemcreator.getCreator_name();
        this.tag = new ArrayList<String>(itemcreator.getTag());
    }

    public ItemCreator(String itemcreatorstring) {
        this.item = new ItemStack(Material.STONE);
        this.fromString(itemcreatorstring);
    }

    public String toString() {
        StringBuilder itemcreator = new StringBuilder();
        itemcreator.append("ItemCreator:{");
        itemcreator.append("Slot:{" + Integer.toString(this.slot) + "}");
        itemcreator.append(",Amount:{" + this.getAmount() + "}");
        itemcreator.append(",Durability:{" + this.getDurability() + "}");
        if (this.getName() != null) {
            itemcreator.append(",Name:{" + this.getName() + "}");
        }
        if (this.getLores() != null) {
            itemcreator.append(",Lores:{");
            for (String string : this.getLores()) {
                itemcreator.append("['" + string + "'],");
            }
            itemcreator.replace(itemcreator.length() - 1, itemcreator.length(), "}");
        }
        if (this.getEnchantments() != null) {
            itemcreator.append(",Enchantments:{");
            for (Map.Entry entry : this.getEnchantments().entrySet()) {
                itemcreator.append("[" + ((Enchantment)entry.getKey()).toString() + "," + entry.getValue() + "],");
            }
            itemcreator.replace(itemcreator.length() - 1, itemcreator.length(), "}");
        }
        if (this.getItemFlags() != null) {
            itemcreator.append(",ItemFlags:{");
            for (ItemFlag itemFlag : this.getItemFlags()) {
                itemcreator.append("[" + itemFlag.toString() + "],");
            }
            itemcreator.replace(itemcreator.length() - 1, itemcreator.length(), "}");
        }
        if (this.getPossesseur() != null) {
            itemcreator.append(",Possesseur:{" + this.getPossesseur().getUniqueId().toString() + "}");
        }
        if (this.getCreator_name() != null) {
            itemcreator.append(",Creator_name:{" + this.getCreator_name() + "}");
        }
        if (this.getTag() != null) {
            itemcreator.append(",Tag:{");
            for (String string : this.getTag()) {
                itemcreator.append("[" + string + "],");
            }
            itemcreator.replace(itemcreator.length() - 1, itemcreator.length(), "}");
        }
        itemcreator.append("}");
        return itemcreator.toString();
    }

    public ItemCreator fromString(String itemcreatorstring) {
        if (itemcreatorstring.substring(0, 10).equals("ItemCreator")) {
            itemcreatorstring = itemcreatorstring.substring(12, itemcreatorstring.length() - 2);
            while (itemcreatorstring != "") {
                int i = 0;
                while (itemcreatorstring.charAt(i) != ':') {
                    ++i;
                }
                String currentname = itemcreatorstring.substring(0, i - 1);
                itemcreatorstring = itemcreatorstring.substring(i);
                Integer f = 0;
                boolean instring = false;
                while (itemcreatorstring.charAt(f) != '}' && !instring) {
                    if (itemcreatorstring.substring(f, f).equals("'")) {
                        instring = !instring;
                    }
                    Integer integer1 = f;
                    f = f + 1;
                    Object object = f;
                }
                String currentpacket = itemcreatorstring.substring(0, f);
                itemcreatorstring = itemcreatorstring.substring(f + 1);
                System.out.println("  ");
                System.out.println("  ");
                System.out.println("  ");
                System.out.println("  ITEM CREATOR   ");
                System.out.println(currentname);
                System.out.println("  ");
                switch (currentname) {
                    case "Type": {
                        System.out.println("  TYPE: " + Material.valueOf(currentpacket.substring(0, currentpacket.length() - 1)).toString());
                        this.setMaterial(Material.valueOf(currentpacket.substring(0, currentpacket.length() - 1)));
                    }
                    case "Slot": {
                        this.setSlot(Integer.valueOf(currentpacket.substring(1, currentpacket.length() - 2)));
                    }
                    case "Amount": {
                        this.setAmount(Integer.valueOf(currentpacket.substring(1, currentpacket.length() - 1)));
                    }
                    case "Durability": {
                        this.setDurability(Short.valueOf(currentpacket.substring(1, currentpacket.length() - 1)));
                    }
                    case "Name": {
                        this.setName(currentpacket.substring(1, currentpacket.length() - 1));
                    }
                    case "Lores": {
                        Integer n;
                        Integer integer12;
                        Integer c;
                        ArrayList<String> lores = new ArrayList<String>();
                        for (currentpacket = currentpacket.substring(1, currentpacket.length() - 1); currentpacket != ""; currentpacket = currentpacket.substring(c)) {
                            Boolean inlore = false;
                            c = 0;
                            if (currentpacket.charAt(0) == ',') {
                                currentpacket = currentpacket.substring(1);
                            }
                            while (currentpacket.charAt(c) != ']' && !inlore.booleanValue()) {
                                if (currentpacket.substring(c, c).equals("'")) {
                                    inlore = inlore == false;
                                }
                                integer12 = c;
                                n = c = Integer.valueOf(c + 1);
                            }
                            lores.add(currentpacket.substring(1, c - 1));
                        }
                        this.setLores(lores);
                    }
                    case "Glow": {
                        this.setGlow(Boolean.valueOf(currentpacket.substring(1, currentpacket.length() - 1)));
                    }
                    case "Enchantments": {
                        String current;
                        Integer integer1;
                        Integer c;
                        Integer integer12;
                        HashMap<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
                        for (currentpacket = currentpacket.substring(1, currentpacket.length() - 1); currentpacket != ""; currentpacket = currentpacket.substring(c)) {
                            if (currentpacket.charAt(0) == ',') {
                                currentpacket = currentpacket.substring(1);
                            }
                            c = 0;
                            while (currentpacket.charAt(c) != ']') {
                                integer1 = c;
                                integer12 = c = Integer.valueOf(c + 1);
                            }
                            current = currentpacket.substring(1, c - 1);
                            enchantments.put(Enchantment.getByName(current.split(",")[0]), Integer.valueOf(current.split(",")[1]));
                        }
                        this.setEnchantments(enchantments);
                    }
                    case "ItemFlags": {
                        String current;
                        Integer integer1;
                        Integer c;
                        Integer integer12;
                        ArrayList<ItemFlag> itemflags = new ArrayList<ItemFlag>();
                        for (currentpacket = currentpacket.substring(1, currentpacket.length() - 1); currentpacket != ""; currentpacket = currentpacket.substring(c)) {
                            if (currentpacket.charAt(0) == ',') {
                                currentpacket = currentpacket.substring(1);
                            }
                            c = 0;
                            while (currentpacket.charAt(c) != ']') {
                                integer1 = c;
                                integer12 = c = Integer.valueOf(c + 1);
                            }
                            current = currentpacket.substring(1, c - 1);
                            itemflags.add(ItemFlag.valueOf(current));
                        }
                        this.setItemFlags(itemflags);
                    }
                    case "Owner": {
                        this.setOwner(currentpacket.substring(1, currentpacket.length() - 1));
                    }
                    case "BaseColor": {
                        this.setBasecolor(DyeColor.valueOf(currentpacket.substring(1, currentpacket.length() - 1)));
                    }
                    case "Patterns": {
                        String current;
                        Integer integer1;
                        Integer c;
                        Integer integer12;
                        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
                        for (currentpacket = currentpacket.substring(1, currentpacket.length() - 1); currentpacket != ""; currentpacket = currentpacket.substring(c)) {
                            if (currentpacket.charAt(0) == ',') {
                                currentpacket = currentpacket.substring(1);
                            }
                            c = 0;
                            while (currentpacket.charAt(c) != ']') {
                                integer1 = c;
                                integer12 = c = Integer.valueOf(c + 1);
                            }
                            current = currentpacket.substring(1, c - 1);
                            patterns.add(new Pattern(DyeColor.valueOf(current.split(",")[0]), PatternType.valueOf(current.split(",")[1])));
                        }
                        this.setPatterns(patterns);
                    }
                    case "StoredEnchantments": {
                        String current;
                        Integer integer1;
                        Integer c;
                        Integer integer12;
                        HashMap<Enchantment, Integer> storedenchantments = new HashMap<Enchantment, Integer>();
                        for (currentpacket = currentpacket.substring(1, currentpacket.length() - 1); currentpacket != ""; currentpacket = currentpacket.substring(c)) {
                            if (currentpacket.charAt(0) == ',') {
                                currentpacket = currentpacket.substring(1);
                            }
                            c = 0;
                            while (currentpacket.charAt(c) != ']') {
                                integer1 = c;
                                integer12 = c = Integer.valueOf(c + 1);
                            }
                            current = currentpacket.substring(1, c - 1);
                            storedenchantments.put(Enchantment.getByName(current.split(",")[0]), Integer.valueOf(current.split(",")[1]));
                        }
                        this.setStoredEnchantments(storedenchantments);
                    }
                    case "Possesseur": {
                        this.setPossesseur(Bukkit.getPlayer(UUID.fromString(currentpacket.substring(1, currentpacket.length() - 1))));
                    }
                    case "Creator_name": {
                        this.setCreator_name(currentpacket.substring(1, currentpacket.length() - 1));
                    }
                    case "Tag": {
                        Integer n;
                        Integer integer12;
                        Integer c;
                        ArrayList<String> taglist = new ArrayList<String>();
                        for (currentpacket = currentpacket.substring(1, currentpacket.length() - 1); currentpacket != ""; currentpacket = currentpacket.substring(c)) {
                            Boolean intag = false;
                            c = 0;
                            if (currentpacket.charAt(0) == ',') {
                                currentpacket = currentpacket.substring(1);
                            }
                            while (currentpacket.charAt(c) != ']' && !intag.booleanValue()) {
                                if (currentpacket.substring(c, c).equals("'")) {
                                    intag = intag == false;
                                }
                                integer12 = c;
                                n = c = Integer.valueOf(c + 1);
                            }
                            taglist.add(currentpacket.substring(1, c - 1));
                        }
                        this.setTag(taglist);
                    }
                }
            }
        }
        return this;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public ItemCreator setMaterial(Material material) {
        if (this.item == null) {
            this.item = new ItemStack(material);
        } else {
            this.item.setType(material);
        }
        return this;
    }

    public ItemCreator setUnbreakable(Boolean unbreakable) {
        ItemMeta meta = this.item.getItemMeta();
        meta.spigot().setUnbreakable(unbreakable);
        this.item.setItemMeta(meta);
        return this;
    }

    public Integer getAmount() {
        return this.item.getAmount();
    }

    public ItemCreator setAmount(Integer amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemCreator setSkull(String paramString1) {
        SkullMeta localSkullMeta = (SkullMeta)this.item.getItemMeta();
        GameProfile localGameProfile = new GameProfile(UUID.randomUUID(), "domei_heads");
        PropertyMap localPropertyMap = localGameProfile.getProperties();
        localPropertyMap.put("textures", new Property("textures", paramString1));
        try {
            Field localField = localSkullMeta.getClass().getDeclaredField("profile");
            localField.setAccessible(true);
            localField.set(localSkullMeta, localGameProfile);
        }
        catch (IllegalAccessException | NoSuchFieldException localNoSuchFieldException) {
            localNoSuchFieldException.printStackTrace();
        }
        this.item.setItemMeta(localSkullMeta);
        return this;
    }

    public Short getDurability() {
        return this.item.getDurability();
    }

    public ItemCreator setDurability(Short durability) {
        this.item.setDurability(durability);
        return this;
    }

    public ItemCreator setDurability(Integer durability) {
        Short shortdurability = (short)durability.intValue();
        this.item.setDurability(shortdurability);
        return this;
    }

    public short getDurabilityInteger() {
        return this.item.getDurability();
    }

    public ItemMeta getMeta() {
        return this.item.getItemMeta();
    }

    public ItemCreator setMeta(ItemMeta meta) {
        this.item.setItemMeta(meta);
        return this;
    }

    public String getName() {
        return this.item.getItemMeta().getDisplayName();
    }

    public ItemCreator setName(String name) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name);
        this.item.setItemMeta(meta);
        return this;
    }

    public ArrayList<String> getLores() {
        return (ArrayList)this.item.getItemMeta().getLore();
    }

    public ItemCreator setLores(List<String> list) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setLore(list);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator clearLores() {
        ItemMeta meta = this.item.getItemMeta();
        meta.setLore(new ArrayList());
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator setSkullURL(String url) {
        if (url.isEmpty()) {
            return this;
        }
        SkullMeta headMeta = (SkullMeta)this.item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "domei_heads");
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        }
        catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException e1) {
            e1.printStackTrace();
        }
        this.item.setItemMeta(headMeta);
        return this;
    }

    public ItemCreator insertLores(String lore, Integer position) {
        ItemMeta meta = this.item.getItemMeta();
        ArrayList<String> lores = (ArrayList<String>)meta.getLore();
        if (lores == null) {
            lores = new ArrayList<String>();
        }
        lores.add(position, lore);
        meta.setLore(lores);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator addLore(String lore) {
        ItemMeta meta = this.item.getItemMeta();
        ArrayList<String> lores = (ArrayList<String>)meta.getLore();
        if (lores == null) {
            lores = new ArrayList<String>();
        }
        if (lore != null) {
            lores.add(lore);
        } else {
            lores.add(" ");
        }
        meta.setLore(lores);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator removeLore(String lore) {
        ItemMeta meta = this.item.getItemMeta();
        ArrayList lores = (ArrayList)meta.getLore();
        if (lores != null && lores.contains(lore)) {
            lores.remove(lore);
            meta.setLore(lores);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public String[] getTableauLores() {
        String[] tableaulores = new String[]{};
        if (this.item.getItemMeta().getLore() != null) {
            Integer i = 0;
            Iterator iterator = this.item.getItemMeta().getLore().iterator();
            while (iterator.hasNext()) {
                String lore;
                tableaulores[i.intValue()] = lore = (String)iterator.next();
                Integer integer1 = i;
                Integer n = i = Integer.valueOf(i + 1);
            }
        }
        return tableaulores;
    }

    public ItemCreator setTableauLores(String[] lores) {
        ArrayList<String> tableaulores = new ArrayList<String>();
        for (String lore : lores) {
            tableaulores.add(lore);
        }
        ItemMeta meta = this.item.getItemMeta();
        meta.setLore(tableaulores);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator replaceallLores(String replacelore, String newlore) {
        ItemMeta meta = this.item.getItemMeta();
        ArrayList lores = (ArrayList)meta.getLore();
        if (lores != null && lores.contains(replacelore)) {
            meta.setLore(lores);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator replaceoneLore(Integer ligne, String newlore) {
        ItemMeta meta = this.item.getItemMeta();
        ArrayList lores = (ArrayList)meta.getLore();
        if (lores != null && lores.get(ligne) != null) {
            lores.remove(ligne);
            lores.add(ligne, newlore);
            meta.setLore(lores);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator replacefirstLores(String replacelore, String newlore, Integer nombre) {
        ItemMeta meta = this.item.getItemMeta();
        ArrayList lores = (ArrayList)meta.getLore();
        if (lores != null && lores.contains(replacelore)) {
            Integer replaced = 0;
            meta.setLore(lores);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator replacelastLores(String replacelore, String newlore, Integer nombre) {
        ItemMeta meta = this.item.getItemMeta();
        ArrayList lores = (ArrayList)meta.getLore();
        if (lores != null && lores.contains(replacelore)) {
            Integer replaced = 0;
            meta.setLore(lores);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator setGlow(Boolean glow) {
        if (glow.booleanValue()) {
            net.minecraft.server.v1_8_R3.ItemStack minecraftitemstack = CraftItemStack.asNMSCopy(this.item);
            NBTTagCompound tag = null;
            if (!minecraftitemstack.hasTag()) {
                tag = new NBTTagCompound();
                minecraftitemstack.setTag(new NBTTagCompound());
            } else {
                tag = minecraftitemstack.getTag();
            }
            NBTTagList ench = new NBTTagList();
            tag.set("ench", ench);
            minecraftitemstack.setTag(tag);
            this.item = CraftItemStack.asCraftMirror(minecraftitemstack);
        } else {
            net.minecraft.server.v1_8_R3.ItemStack minecraftitemstack = CraftItemStack.asNMSCopy(this.item);
            NBTTagCompound tag = null;
            if (!minecraftitemstack.hasTag() && (tag = minecraftitemstack.getTag()).hasKey("ench")) {
                tag.remove("ench");
                minecraftitemstack.setTag(tag);
                this.item = CraftItemStack.asCraftMirror(minecraftitemstack);
            }
        }
        return this;
    }

    public HashMap<Enchantment, Integer> getEnchantments() {
        return new HashMap<Enchantment, Integer>(this.item.getItemMeta().getEnchants());
    }

    public ItemCreator setEnchantments(Map<Enchantment, Integer> map) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta.getEnchants() != null) {
            ArrayList cloneenchantments = new ArrayList(meta.getEnchants().keySet());
            for (Object enchantment : cloneenchantments) {
                meta.removeEnchant((Enchantment) enchantment);
            }
        }
        for (Map.Entry<Enchantment, Integer> e : map.entrySet()) {
            meta.addEnchant(e.getKey(), e.getValue(), true);
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator clearEnchantments() {
        ItemMeta meta = this.item.getItemMeta();
        if (meta.getEnchants() != null) {
            ArrayList cloneenchantments = new ArrayList(meta.getEnchants().keySet());
            for (Object enchantment : cloneenchantments) {
                meta.removeEnchant((Enchantment) enchantment);
            }
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator addEnchantment(Enchantment enchantment, Integer lvl) {
        ItemMeta meta = this.item.getItemMeta();
        meta.addEnchant(enchantment, lvl, true);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator removeEnchantment(Enchantment enchantment) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta.getEnchants() != null && meta.getEnchants().containsKey(enchantment)) {
            meta.removeEnchant(enchantment);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public Enchantment[] getTableauEnchantments() {
        Enchantment[] enchantments = new Enchantment[]{};
        if (this.item.getItemMeta().getEnchants() != null) {
            Integer i = 0;
            Iterator iterator = this.item.getItemMeta().getEnchants().keySet().iterator();
            while (iterator.hasNext()) {
                Enchantment enchantment;
                enchantments[i.intValue()] = enchantment = (Enchantment)iterator.next();
                Integer integer1 = i;
                Integer n = i = Integer.valueOf(i + 1);
            }
        }
        return enchantments;
    }

    public Integer[] getTableauEnchantmentslvl() {
        Integer[] enchantmentslvl = new Integer[]{};
        if (this.item.getItemMeta().getEnchants() != null) {
            Integer i = 0;
            Iterator iterator = this.item.getItemMeta().getEnchants().values().iterator();
            while (iterator.hasNext()) {
                Integer enchantmentlvl;
                enchantmentslvl[i.intValue()] = enchantmentlvl = (Integer)iterator.next();
                Integer integer1 = i;
                Integer n = i = Integer.valueOf(i + 1);
            }
        }
        return enchantmentslvl;
    }

    public ItemCreator setTableauEnchantments(Enchantment[] enchantments, Integer[] enchantmentslvl) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta.getEnchants() != null) {
            ArrayList cloneenchantments = new ArrayList(meta.getEnchants().keySet());
            for (Object enchantment : cloneenchantments) {
                meta.removeEnchant((Enchantment) enchantment);
            }
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public ArrayList<ItemFlag> getItemFlags() {
        ArrayList<ItemFlag> itemflags = new ArrayList<ItemFlag>();
        if (this.item.getItemMeta().getItemFlags() != null) {
            for (ItemFlag itemflag : this.item.getItemMeta().getItemFlags()) {
                itemflags.add(itemflag);
            }
        }
        return itemflags;
    }

    public ItemCreator setItemFlags(ArrayList<ItemFlag> itemflags) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta.getItemFlags() != null) {
            ArrayList<ItemFlag> cloneitemflags = new ArrayList<ItemFlag>();
            for (ItemFlag itemflag : meta.getItemFlags()) {
                cloneitemflags.add(itemflag);
            }
            for (ItemFlag itemflag : cloneitemflags) {
                meta.removeItemFlags(new ItemFlag[]{itemflag});
            }
        }
        for (ItemFlag itemflag : itemflags) {
            meta.addItemFlags(new ItemFlag[]{itemflag});
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator addFlag(ItemFlag ... flag) {
        ItemMeta im = this.item.getItemMeta();
        im.addItemFlags(flag);
        this.item.setItemMeta(im);
        return this;
    }

    public ItemCreator clearItemFlags() {
        ItemMeta meta = this.item.getItemMeta();
        if (meta.getItemFlags() != null) {
            ArrayList<ItemFlag> cloneitemflags = new ArrayList<ItemFlag>();
            for (ItemFlag itemflag : meta.getItemFlags()) {
                cloneitemflags.add(itemflag);
            }
            for (ItemFlag itemflag : cloneitemflags) {
                meta.removeItemFlags(new ItemFlag[]{itemflag});
            }
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator addItemFlags(ItemFlag itemflag) {
        ItemMeta meta = this.item.getItemMeta();
        meta.addItemFlags(new ItemFlag[]{itemflag});
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator removeItemFlags(ItemFlag itemflag) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta.getItemFlags() != null && meta.getItemFlags().contains(itemflag)) {
            meta.removeItemFlags(new ItemFlag[]{itemflag});
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemFlag[] getTableauItemFlags() {
        ItemMeta meta = this.item.getItemMeta();
        ItemFlag[] itemflags = new ItemFlag[]{};
        Integer i = 0;
        if (meta.getItemFlags() != null) {
            Iterator iterator = meta.getItemFlags().iterator();
            while (iterator.hasNext()) {
                ItemFlag itemflag;
                itemflags[i.intValue()] = itemflag = (ItemFlag)iterator.next();
                Integer integer1 = i;
                Integer n = i = Integer.valueOf(i + 1);
            }
        }
        return itemflags;
    }

    public ItemCreator setTableauItemFlags(ItemFlag[] itemflags) {
        ItemMeta meta = this.item.getItemMeta();
        if (meta.getItemFlags() != null) {
            ArrayList<ItemFlag> cloneitemflags = new ArrayList<ItemFlag>();
            for (ItemFlag itemflag : meta.getItemFlags()) {
                cloneitemflags.add(itemflag);
            }
            for (ItemFlag itemflag : cloneitemflags) {
                meta.removeItemFlags(new ItemFlag[]{itemflag});
            }
        }
        for (ItemFlag itemflag : itemflags) {
            meta.addItemFlags(new ItemFlag[]{itemflag});
        }
        this.item.setItemMeta(meta);
        return this;
    }

    public SkullMeta getSkullMeta() {
        if (this.item.getType().equals(Material.SKULL_ITEM)) {
            return (SkullMeta)this.item.getItemMeta();
        }
        return null;
    }

    public ItemCreator setSkullMeta(SkullMeta skullmeta) {
        if (this.item.getType().equals(Material.SKULL_ITEM)) {
            this.item.setItemMeta(skullmeta);
        }
        return this;
    }

    public String getOwner() {
        if (this.item.getType().equals(Material.SKULL_ITEM)) {
            return ((SkullMeta)this.item.getItemMeta()).getOwner();
        }
        return null;
    }

    public ItemCreator setOwner(String owner) {
        if (this.item.getType().equals(Material.SKULL_ITEM)) {
            SkullMeta meta = (SkullMeta)this.item.getItemMeta();
            meta.setOwner(owner);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public BannerMeta getBannerMeta() {
        if (this.item.getType().equals(Material.BANNER)) {
            return (BannerMeta)this.item.getItemMeta();
        }
        return null;
    }

    public ItemCreator setBannerMeta(BannerMeta bannermeta) {
        if (this.item.getType().equals(Material.BANNER)) {
            this.item.setItemMeta(bannermeta);
        }
        return this;
    }

    public DyeColor getBasecolor() {
        if (this.item.getType().equals(Material.BANNER)) {
            return ((BannerMeta)this.item.getItemMeta()).getBaseColor();
        }
        return null;
    }

    public ItemCreator setBasecolor(DyeColor basecolor) {
        if (this.item.getType().equals(Material.BANNER)) {
            BannerMeta meta = (BannerMeta)this.item.getItemMeta();
            meta.setBaseColor(basecolor);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ArrayList<Pattern> getPatterns() {
        if (this.item.getType().equals(Material.BANNER)) {
            return (ArrayList)((BannerMeta)this.item.getItemMeta()).getPatterns();
        }
        return null;
    }

    public ItemCreator setPatterns(ArrayList<Pattern> petterns) {
        if (this.item.getType().equals(Material.BANNER)) {
            BannerMeta meta = (BannerMeta)this.item.getItemMeta();
            meta.setPatterns(petterns);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator clearPatterns() {
        if (this.item.getType().equals(Material.BANNER)) {
            BannerMeta meta = (BannerMeta)this.item.getItemMeta();
            meta.setPatterns(new ArrayList());
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator addPattern(Pattern pattern) {
        if (this.item.getType().equals(Material.BANNER)) {
            BannerMeta meta = (BannerMeta)this.item.getItemMeta();
            meta.addPattern(pattern);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator removePattern(Pattern pattern) {
        BannerMeta meta;
        ArrayList patterns;
        if (this.item.getType().equals(Material.BANNER) && (patterns = (ArrayList)(meta = (BannerMeta)this.item.getItemMeta()).getPatterns()) != null && patterns.contains(pattern)) {
            patterns.remove(pattern);
            meta.setPatterns(patterns);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public Pattern[] getTableauPatterns() {
        if (this.item.getType().equals(Material.BANNER)) {
            BannerMeta meta = (BannerMeta)this.item.getItemMeta();
            Pattern[] tableaupatterns = new Pattern[]{};
            if (meta.getPatterns() != null) {
                Integer i = 0;
                Iterator iterator = meta.getPatterns().iterator();
                while (iterator.hasNext()) {
                    Pattern pattern;
                    tableaupatterns[i.intValue()] = pattern = (Pattern)iterator.next();
                    Integer integer1 = i;
                    Integer n = i = Integer.valueOf(i + 1);
                }
            }
            return tableaupatterns;
        }
        return null;
    }

    public ItemCreator setTableauPatterns(Pattern[] patterns) {
        if (this.item.getType().equals(Material.BANNER)) {
            BannerMeta meta = (BannerMeta)this.item.getItemMeta();
            if (meta.getPatterns() != null) {
                meta.setPatterns(new ArrayList());
            }
            for (Pattern pattern : patterns) {
                meta.addPattern(pattern);
            }
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public EnchantmentStorageMeta getEnchantmentStorageMeta() {
        if (this.item.getType().equals(Material.ENCHANTED_BOOK)) {
            return (EnchantmentStorageMeta)this.item.getItemMeta();
        }
        return null;
    }

    public ItemCreator setEnchantmentStorageMeta(EnchantmentStorageMeta enchantmentstoragemeta) {
        if (this.item.getType().equals(Material.ENCHANTED_BOOK)) {
            this.item.setItemMeta(enchantmentstoragemeta);
        }
        return this;
    }

    public HashMap<Enchantment, Integer> getStoredEnchantments() {
        if (this.item.getType().equals(Material.ENCHANTED_BOOK)) {
            return (HashMap)((EnchantmentStorageMeta)this.item.getItemMeta()).getEnchants();
        }
        return null;
    }

    public ItemCreator setStoredEnchantments(HashMap<Enchantment, Integer> storedenchantments) {
        if (this.item.getType().equals(Material.ENCHANTED_BOOK)) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta)this.item.getItemMeta();
            if (meta.getStoredEnchants() != null) {
                ArrayList clonestoredenchantments = new ArrayList(meta.getStoredEnchants().keySet());
                for (Object storedenchantment : clonestoredenchantments) {
                    meta.removeStoredEnchant((Enchantment) storedenchantment);
                }
            }
            for (Map.Entry<Enchantment, Integer> e : storedenchantments.entrySet()) {
                meta.addEnchant(e.getKey(), e.getValue(), true);
            }
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator clearStoredEnchantments() {
        EnchantmentStorageMeta meta;
        if (this.item.getType().equals(Material.ENCHANTED_BOOK) && (meta = (EnchantmentStorageMeta)this.item.getItemMeta()).getStoredEnchants() != null) {
            ArrayList clonestoredenchantments = new ArrayList(meta.getStoredEnchants().keySet());
            for (Object storedenchantment : clonestoredenchantments) {
                meta.removeStoredEnchant((Enchantment) storedenchantment);
            }
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator addStoredEnchantment(Enchantment storedenchantment, Integer lvl) {
        if (this.item.getType().equals(Material.ENCHANTED_BOOK)) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta)this.item.getItemMeta();
            meta.addStoredEnchant(storedenchantment, lvl, true);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator removeStoredEnchantment(Enchantment enchantment) {
        EnchantmentStorageMeta meta;
        if (this.item.getType().equals(Material.ENCHANTED_BOOK) && (meta = (EnchantmentStorageMeta)this.item.getItemMeta()).getStoredEnchants() != null && meta.getStoredEnchants().containsKey(enchantment)) {
            meta.removeEnchant(enchantment);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public Enchantment[] getTableauStoredEnchantments() {
        if (this.item.getType().equals(Material.ENCHANTED_BOOK)) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta)this.item.getItemMeta();
            Enchantment[] storedenchantments = new Enchantment[]{};
            if (meta.getStoredEnchants() != null) {
                Integer i = 0;
                Iterator iterator = meta.getStoredEnchants().keySet().iterator();
                while (iterator.hasNext()) {
                    Enchantment storedenchantment;
                    storedenchantments[i.intValue()] = storedenchantment = (Enchantment)iterator.next();
                    Integer integer1 = i;
                    Integer n = i = Integer.valueOf(i + 1);
                }
            }
            return storedenchantments;
        }
        return null;
    }

    public Integer[] getTableauStoredEnchantmentslvl() {
        if (this.item.getType().equals(Material.ENCHANTED_BOOK)) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta)this.item.getItemMeta();
            Integer[] storedenchantmentslvl = new Integer[]{};
            if (meta.getStoredEnchants() != null) {
                Integer i = 0;
                Iterator iterator = meta.getStoredEnchants().values().iterator();
                while (iterator.hasNext()) {
                    Integer storedenchantmentlvl;
                    storedenchantmentslvl[i.intValue()] = storedenchantmentlvl = (Integer)iterator.next();
                    Integer integer1 = i;
                    Integer n = i = Integer.valueOf(i + 1);
                }
            }
            return storedenchantmentslvl;
        }
        return null;
    }

    public ItemCreator setTableauStoredEnchantments(Enchantment[] storedenchantments, Integer[] storedenchantmentslvl) {
        if (this.item.getType().equals(Material.ENCHANTED_BOOK)) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta)this.item.getItemMeta();
            if (meta.getStoredEnchants() != null) {
                ArrayList clonestoredenchantments = new ArrayList(meta.getStoredEnchants().keySet());
                for (Object storedenchantment : clonestoredenchantments) {
                    meta.removeStoredEnchant((Enchantment) storedenchantment);
                }
            }
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator addallItemsflags() {
        ItemMeta meta = this.item.getItemMeta();
        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_PLACED_ON});
        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemCreator addBannerPreset(Integer ID, DyeColor patterncolor) {
        switch (ID) {
            case 1: {
                this.addBannerPreset(BannerPreset.barre, patterncolor);
                break;
            }
            case 2: {
                this.addBannerPreset(BannerPreset.precedent, patterncolor);
                break;
            }
            case 3: {
                this.addBannerPreset(BannerPreset.suivant, patterncolor);
                break;
            }
            case 4: {
                this.addBannerPreset(BannerPreset.coeur, patterncolor);
                break;
            }
            case 5: {
                this.addBannerPreset(BannerPreset.cercleEtoile, patterncolor);
                break;
            }
            case 6: {
                this.addBannerPreset(BannerPreset.croix, patterncolor);
                break;
            }
            case 7: {
                this.addBannerPreset(BannerPreset.yinYang, patterncolor);
                break;
            }
            case 8: {
                this.addBannerPreset(BannerPreset.losange, patterncolor);
                break;
            }
            case 9: {
                this.addBannerPreset(BannerPreset.moin, patterncolor);
                break;
            }
            case 10: {
                this.addBannerPreset(BannerPreset.plus, patterncolor);
            }
        }
        return this;
    }

    public ItemCreator addBannerPreset(BannerPreset type, DyeColor patterncolor) {
        if (type == null) {
            return this;
        }
        if (this.item.getType().equals(Material.BANNER)) {
            BannerMeta meta = (BannerMeta)this.item.getItemMeta();
            DyeColor dyeColor = meta.getBaseColor();
        }
        return this;
    }

    private void addasyncronePattern(Pattern pattern, Boolean calcul) {
        if (calcul.booleanValue()) {
            this.patterns.add(pattern);
            BannerMeta meta = (BannerMeta)this.item.getItemMeta();
            for (Pattern currentpattern : this.patterns) {
                meta.addPattern(currentpattern);
            }
            this.patterns.clear();
            this.item.setItemMeta(meta);
        } else {
            if (this.patterns == null) {
                this.patterns = new ArrayList();
            }
            this.patterns.add(pattern);
        }
    }

    public Player getPossesseur() {
        return this.possesseur;
    }

    public ItemCreator setPossesseur(Player possesseur) {
        this.possesseur = possesseur;
        return this;
    }

    public String getCreator_name() {
        return this.creator_name;
    }

    public ItemCreator setCreator_name(String creator_name) {
        this.creator_name = creator_name;
        return this;
    }

    public ArrayList<String> getTag() {
        return this.tag;
    }

    public ItemCreator setTag(ArrayList<String> tag) {
        this.tag = tag;
        return this;
    }

    public ItemCreator clearTag() {
        if (this.tag != null) {
            this.tag.clear();
        }
        return this;
    }

    public ItemCreator addTag(String tag) {
        if (this.tag == null) {
            this.tag = new ArrayList();
        }
        this.tag.add(tag);
        return this;
    }

    public ItemCreator removeTag(String tag) {
        if (this.tag != null && this.tag.contains(tag)) {
            this.tag.remove(tag);
        }
        return this;
    }

    public String[] getTableauTag() {
        String[] taglist = new String[]{};
        Integer i = 0;
        Iterator<String> iterator = this.tag.iterator();
        while (iterator.hasNext()) {
            String currenttag;
            taglist[i.intValue()] = currenttag = iterator.next();
            Integer integer1 = i;
            Integer n = i = Integer.valueOf(i + 1);
        }
        return taglist;
    }

    public ItemCreator setTableaTag(String[] tag) {
        if (this.tag == null) {
            this.tag = new ArrayList();
        } else {
            this.tag.clear();
        }
        for (String currenttag : tag) {
            this.tag.add(currenttag);
        }
        return this;
    }

    public Boolean comparate(ItemCreator item, ComparatorType type) {
        switch (type) {
            case All: {
                return this.comparate(item, ComparatorType.Material) != false && this.comparate(item, ComparatorType.Amount) != false && this.comparate(item, ComparatorType.Durability) != false && this.comparate(item, ComparatorType.Name) != false && this.comparate(item, ComparatorType.Lores) != false && this.comparate(item, ComparatorType.Enchantements) != false && this.comparate(item, ComparatorType.ItemsFlags) != false && this.comparate(item, ComparatorType.Owner) != false && this.comparate(item, ComparatorType.BaseColor) != false && this.comparate(item, ComparatorType.Patterns) != false && this.comparate(item, ComparatorType.StoredEnchantements) != false && this.comparate(item, ComparatorType.Creator_Name) != false && this.comparate(item, ComparatorType.Possesseur) != false && this.comparate(item, ComparatorType.TAG) != false;
            }
            case Similar: {
                return this.comparate(item, ComparatorType.Material) != false && this.comparate(item, ComparatorType.Durability) != false && this.comparate(item, ComparatorType.Name) != false && this.comparate(item, ComparatorType.Lores) != false && this.comparate(item, ComparatorType.Enchantements) != false && this.comparate(item, ComparatorType.ItemsFlags) != false && this.comparate(item, ComparatorType.Owner) != false && this.comparate(item, ComparatorType.BaseColor) != false && this.comparate(item, ComparatorType.Patterns) != false && this.comparate(item, ComparatorType.StoredEnchantements) != false;
            }
            case ItemStack: {
                return this.comparate(item, ComparatorType.Material) != false && this.comparate(item, ComparatorType.Amount) != false && this.comparate(item, ComparatorType.Durability) != false && this.comparate(item, ComparatorType.Name) != false && this.comparate(item, ComparatorType.Lores) != false && this.comparate(item, ComparatorType.Enchantements) != false && this.comparate(item, ComparatorType.ItemsFlags) != false && this.comparate(item, ComparatorType.Owner) != false && this.comparate(item, ComparatorType.BaseColor) != false && this.comparate(item, ComparatorType.Patterns) != false && this.comparate(item, ComparatorType.StoredEnchantements) != false;
            }
            case Amount: {
                return this.getAmount() == item.getAmount();
            }
            case Durability: {
                return this.getDurability() == item.getDurability();
            }
            case Name: {
                return this.getName() == item.getName();
            }
        }
        return false;
    }

    public int getSlot() {
        return this.slot;
    }

    public ItemCreator setSlot(int slot) {
        this.slot = slot;
        return this;
    }

    private class comparaison<type1, type2> {
        private comparaison() {
        }

        public Boolean islistequal(List<type1> list1, List<type1> list2) {
            if (list1 == null && list2 == null) {
                return true;
            }
            if (list1 == null || list2 == null) {
                return false;
            }
            if (list1.size() == list2.size()) {
                return true;
            }
            return false;
        }

        public Boolean ismapequal(Map<type1, type2> map1, Map<type1, type2> map2) {
            if (map1 == null && map2 == null) {
                return true;
            }
            if (map1 == null || map2 == null) {
                return false;
            }
            if (map1.size() == map2.size()) {
                for (Map.Entry<type1, type2> e : map1.entrySet()) {
                    if (map2.get(e.getKey()) == null) {
                        return false;
                    }
                    if (map2.get(e.getKey()) == e.getValue()) continue;
                    return false;
                }
                return true;
            }
            return false;
        }
    }

    public static enum ComparatorType {
        All,
        ItemStack,
        Similar,
        Material,
        Amount,
        Durability,
        Name,
        Lores,
        Enchantements,
        ItemsFlags,
        Owner,
        BaseColor,
        Patterns,
        StoredEnchantements,
        Possesseur,
        Creator_Name,
        TAG;

    }

    public static enum BannerPreset {
        barre,
        precedent,
        suivant,
        coeur,
        cercleEtoile,
        croix,
        yinYang,
        losange,
        moin,
        plus;

    }
}
