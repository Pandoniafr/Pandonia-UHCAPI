package fr.pandonia.uhcapi.common.rules.items;

import fr.pandonia.uhcapi.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum GeneralRules {
    DIAMOND_HELMET("Casque en diamant", Material.DIAMOND_HELMET, 0, true),
    DIAMOND_CHESTPLATE("Plastron en diamant", Material.DIAMOND_CHESTPLATE, 0, true),
    DIAMOND_LEGGINGS("Jambières en diamant", Material.DIAMOND_LEGGINGS, 0, false),
    DIAMOND_BOOTS("Bottes en diamant", Material.DIAMOND_BOOTS, 0, true),
    DIAMOND_SWORD("Épée en diamant", Material.DIAMOND_SWORD, 0, true),
    STRIPMINING("Strip Mining", Material.IRON_PICKAXE, 0, true),
    IPVP("iPvP", Material.IRON_SWORD, 0, true),
    CROSSTEAM("Cross Team", Material.GOLD_SWORD, 0, true),
    TOWER("Towers", Material.NETHER_BRICK, 0, true),
    DIGDOWN("Dig Down", Material.WOOD_SPADE, 0, true),
    ROLLERCOASTER("Roller Coaster", Material.LADDER, 0, true),
    HEALTH("Vie des joueurs", Material.RED_ROSE, 0, false),
    MUMBLE("Mumble obligatoire", Material.IRON_HELMET, 0, true),
    PRIVATEMSG("Messages privés", Material.SIGN, 0, true);

    private final String name;
    private final Material material;
    private final int data;
    private boolean enabled;

    private GeneralRules(String name, Material material, int data, boolean enabled) {
        this.name = name;
        this.material = material;
        this.data = data;
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void toggleEnabled() {
        this.enabled = !this.enabled;
    }

    public ItemStack getItem() {
        return new ItemCreator(this.material).setDurability(this.data).setName("§8┃ §c" + this.name + " §8(" + (this.isEnabled() ? "§aActivé§8)" : "§cDésactivé§8)")).getItem();
    }

    public String getName() {
        return this.name;
    }

    public Material getMaterial() {
        return this.material;
    }

    public int getData() {
        return this.data;
    }
}
