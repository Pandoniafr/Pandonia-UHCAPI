package fr.pandonia.uhcapi.config.scenario;

import fr.pandonia.uhcapi.config.scenario.special.BestPVE;
import fr.pandonia.uhcapi.config.scenario.special.BetaZombie;
import fr.pandonia.uhcapi.config.scenario.special.BloodDiamond;
import fr.pandonia.uhcapi.config.scenario.special.Bookception;
import fr.pandonia.uhcapi.config.scenario.special.BowSwap;
import fr.pandonia.uhcapi.config.scenario.special.CatEyes;
import fr.pandonia.uhcapi.config.scenario.special.CutClean;
import fr.pandonia.uhcapi.config.scenario.special.DiamondLess;
import fr.pandonia.uhcapi.config.scenario.special.EnchantedDeath;
import fr.pandonia.uhcapi.config.scenario.special.FastSmelting;
import fr.pandonia.uhcapi.config.scenario.special.FinalHeal;
import fr.pandonia.uhcapi.config.scenario.special.GoldenHead;
import fr.pandonia.uhcapi.config.scenario.special.GoneFishing;
import fr.pandonia.uhcapi.config.scenario.special.HasteyBabies;
import fr.pandonia.uhcapi.config.scenario.special.HasteyBoys;
import fr.pandonia.uhcapi.config.scenario.special.Horseless;
import fr.pandonia.uhcapi.config.scenario.special.KillSwitch;
import fr.pandonia.uhcapi.config.scenario.special.MasterApple;
import fr.pandonia.uhcapi.config.scenario.special.MasterLevel;
import fr.pandonia.uhcapi.config.scenario.special.Netheribus;
import fr.pandonia.uhcapi.config.scenario.special.NineSlots;
import fr.pandonia.uhcapi.config.scenario.special.NoCleanUp;
import fr.pandonia.uhcapi.config.scenario.special.NoEnchant;
import fr.pandonia.uhcapi.config.scenario.special.NoFall;
import fr.pandonia.uhcapi.config.scenario.special.NoFire;
import fr.pandonia.uhcapi.config.scenario.special.NoFood;
import fr.pandonia.uhcapi.config.scenario.special.OresMultiplicator;
import fr.pandonia.uhcapi.config.scenario.special.OverCooked;
import fr.pandonia.uhcapi.config.scenario.special.Paranoia;
import fr.pandonia.uhcapi.config.scenario.special.PoisonFood;
import fr.pandonia.uhcapi.config.scenario.special.RandomTeam;
import fr.pandonia.uhcapi.config.scenario.special.SafeMiner;
import fr.pandonia.uhcapi.config.scenario.special.Sangsue;
import fr.pandonia.uhcapi.config.scenario.special.SharedHealth;
import fr.pandonia.uhcapi.config.scenario.special.SkyHigh;
import fr.pandonia.uhcapi.config.scenario.special.StockUp;
import fr.pandonia.uhcapi.config.scenario.special.SuperHeroes;
import fr.pandonia.uhcapi.config.scenario.special.Timber;
import fr.pandonia.uhcapi.config.scenario.special.TimeBomb;
import fr.pandonia.uhcapi.config.scenario.special.ToxicFood;
import fr.pandonia.uhcapi.config.scenario.special.VeinMiner;
import fr.pandonia.uhcapi.config.scenario.special.WebCage;
import fr.pandonia.uhcapi.game.GameUtils;
import fr.pandonia.uhcapi.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public enum Scenario {
    CUTCLEAN(1, 0, "Cut Clean", new String[]{"", " §8┃ §fTous les minerais et nourritues", " §8┃ §fsont directements cuits !"}, Material.IRON_INGOT, 0, new CutClean(), false, false),
    NOFALL(1, 1, "No Fall", new String[]{"", " §8┃ §fLes dégâts de chutes", " §8┃ §fn'existent plus."}, Material.IRON_BOOTS, 0, new NoFall(), false, false),
    HASTEYBOYS(1, 2, "Hastey Boys", new String[]{"", " §8┃ §fTous les outils sont enchantés !"}, Material.GOLD_PICKAXE, 0, new HasteyBoys(), false, false),
    NINE_SLOTS(1, 3, "Nine Slots", new String[]{"", " §8┃ §fSeuls vos 9 premiers", " §8┃ §fslots sont utilisables !"}, Material.STAINED_GLASS_PANE, 14, new NineSlots(), false, false),
    CAT_EYES(1, 4, "Cat Eyes", new String[]{"", " §8┃ §fVoyez clair, même la nuit.."}, Material.SEA_LANTERN, 0, new CatEyes(), false, false),
    DIAMOND_LESS(1, 5, "Diamond Less", new String[]{"", " §8┃ §fPouf ! Plus de diamants.."}, Material.DIAMOND, 0, new DiamondLess(), false, false),
    NOFIRE(1, 6, "No Fire", new String[]{"", " §8┃ §fLes dégâts du feu sont annulés."}, Material.FLINT_AND_STEEL, 0, new NoFire(), false, false),
    HORSELESS(1, 7, "Horseless", new String[]{"", " §8┃ §fLes chevaux sont indomptables."}, Material.SADDLE, 0, new Horseless(), false, false),
    TIMBER(1, 8, "Timber", new String[]{"", " §8┃ §fCasser un arbre en un seul coup !"}, Material.LOG, 0, new Timber(), false, false),
    TIMEBOMB(1, 9, "Time Bomb", new String[]{"", " §8┃ §fA la mort d'un joueur, son stuff est", " §8┃ §fdéposé dans un coffre qui explosera", " §8┃ §fau bout de X secondes."}, Material.TNT, 0, new TimeBomb(), false, true, ScenarioValueType.TIME, 15, 5, 30),
    SUPERHEROES(1, 10, "Super Heroes", new String[]{"", " §8┃ §fParmis plusieurs effets, chaques joueurs", " §8┃ §fse voit en recevoir un !"}, Material.POTION, 0, new SuperHeroes(), false, false),
    ORESMULTIPLICATOR(1, 11, "Ores Multiplicator", new String[]{"", " §8┃ §fLes minerais sont multipliés par X."}, Material.IRON_INGOT, 0, new OresMultiplicator(), false, true, ScenarioValueType.MULTIPLICATOR, 2, 2, 10),
    SANGSUE(1, 12, "Sangsue", new String[]{"", " §8┃ §fLorsque vous tuez un joueur, vous", " §8┃ §frécupérez 5§c❤  §8┃ §fainsi que", " §8┃ §f64 blocs de bois et de pierre."}, Material.REDSTONE, 0, new Sangsue(), false, false),
    BOOKCEPTION(1, 13, "Bookception", new String[]{"", " §8┃ §fChaque joueur tué donnera un livre enchanté."}, Material.ENCHANTED_BOOK, 0, new Bookception(), false, false),
    BOWSWAP(1, 14, "Bow Swap", new String[]{"", " §8┃ §fVous avez X% de chances d'échanger votre place", " §8┃ §favec celle d'un joueur touché avec votre flèche."}, Material.BOW, 0, new BowSwap(), false, true, ScenarioValueType.PERCENT, 10, 5, 100),
    SKYHIGH(1, 15, "SkyHigh", new String[]{"", " §8┃ §fAprès l'activation du PvP, tous les", " §8┃ §fjoueurs situés sous la couche X,", " §8┃ §fperdront 1§c❤  §8┃ §ftoutes les 30 secondes."}, Material.VINE, 0, new SkyHigh(), false, true, ScenarioValueType.YCOORD, 150, 100, 256),
    FINALHEAL(1, 16, "Final Heal", new String[]{"", " §8┃ §fAprès X minutes, tous les joueurs seront soignés."}, Material.GOLDEN_APPLE, 0, new FinalHeal(), false, true, ScenarioValueType.TIMEMIN, 20, 2, 60),
    STOCKUP(1, 17, "Stock Up", new String[]{"", " §8┃ §fChaque joueur tué offre un slot de", " §8┃ §fcoeur supplémentaire à tous les joueurs vivants."}, Material.CHEST, 0, new StockUp(), false, false),
    GOLDENHEAD(1, 18, "Golden Head", new String[]{"", " §8┃ §fA sa mort, le joueur tué laissera derrière", " §8┃ §flui en supplément sa tête. En l'entourant d'or,", " §8┃ §fcelle-ci vous permettra de crafter une Golden Head", " §8┃ §fvous permettant de régénérer 4§c❤ §8┃ §f."}, Material.SKULL_ITEM, 3, new GoldenHead(), false, false),
    ENCHANTEDDEATH(1, 19, "Enchanted Death", new String[]{"", " §8┃ §fLe seul moyen d'obtenir une table", " §8┃ §fd'enchantement est de tuer un joueur."}, Material.ENCHANTMENT_TABLE, 0, new EnchantedDeath(), false, false),
    POISONFOOD(1, 20, "Poison Food", new String[]{"", " §8┃ §fVous avez X% de chances", " §8┃ §fd'être empoisonné en mangeant."}, Material.POISONOUS_POTATO, 0, new PoisonFood(), false, true, ScenarioValueType.PERCENT, 5, 1, 100),
    NETHERIBUS(1, 21, "Netheribus", new String[]{"", " §8┃ §fAu bout de X minutes vous devez vous rendre", " §8┃ §fdans le Nether sous peine de recevoir des dégâts."}, Material.NETHERRACK, 0, new Netheribus(), false, true, ScenarioValueType.TIMEMIN, 60, 1, 180),
    MASTERLEVEL(1, 22, "Master Level", new String[]{"", " §8┃ §fDébutez la partie avec X niveaux."}, Material.EXP_BOTTLE, 0, new MasterLevel(), false, true, ScenarioValueType.INT, 10000, 1, 10000),
    SHAREDHEALTH(1, 23, "Shared Health", new String[]{"", " §8┃ §fLa vie des joueurs de l'équipe est", " §8┃ §fpartagée pour n'en former qu'une seule."}, Material.RED_ROSE, 0, new SharedHealth(), true, false),
    GONEFISHING(1, 24, "Gone Fishing", new String[]{"", " §8┃ §fDébutez la partie avec une canne", " §8┃ §fà pêche vous permettant d'attraper", " §8┃ §fde nombreux objets rares."}, Material.FISHING_ROD, 0, new GoneFishing(), false, false),
    NOCLEANUP(1, 25, "NoCleanUp", new String[]{"", " §8┃ §fAprès avoir tué un joueur", " §8┃ §fvous serez soigné de X§c❤"}, Material.GHAST_TEAR, 0, new NoCleanUp(), false, true, ScenarioValueType.DAMAGE, 4, 1, 20),
    RANDOMTEAM(1, 26, "Random Team", new String[]{"", " §8┃ §fChaque joueur sera assigné", " §8┃ §fdans une équipe aléatoire."}, Material.BANNER, 0, new RandomTeam(), true, false),
    OVERCOOKED(1, 27, "OverCooked", new String[]{"", " §8┃ §fVotre four explose après sa première cuisson", " §8┃ §frendant cuits tout les éléments !"}, Material.FURNACE, 0, new OverCooked(), false, false),
    BESTPVE(1, 28, "BestPVE", new String[]{"", " §8┃ §fAu début de la partie vous êtes ajouté", " §8┃ §fsur une liste, tant que vous y restez", " §8┃ §fvous gagnez un coeur chaque X minute(s)", " §8┃ §fSi vous prenez des dégâts vous ne ferez plus", " §8┃ §fparti de la liste et vous devrez tuer", " §8┃ §fun joueur pour retourner sur cette dernière."}, Material.PAPER, 0, new BestPVE(), false, true, ScenarioValueType.TIMEMIN, 1, 1, 60),
    KILLSWITCH(1, 29, "Kill Switch", new String[]{"", " §8┃ §fVotre inventaire sera remplacé par", " §8┃ §fcelui du joueur que vous tuerez."}, Material.FEATHER, 0, new KillSwitch(), false, false),
    NOENCAHNT(1, 30, "NoEnchant", new String[]{"", " §8┃ §fLes enchantements sont désactivés."}, Material.ENCHANTMENT_TABLE, 0, new NoEnchant(), false, false),
    TOXICFOOD(1, 31, "Toxic Food", new String[]{"", " §8┃ §fManger un aliment vous infligera X§c❤  §8┃ §fde dégâts."}, Material.POISONOUS_POTATO, 0, new ToxicFood(), false, true, ScenarioValueType.DAMAGE, 1, 1, 20),
    NOFOOD(1, 32, "No Food", new String[]{"", " §8┃ §fVous ne perdez plus de points de nourriture."}, Material.COOKED_BEEF, 0, new NoFood(), false, false),
    WEBCAGE(1, 33, "WebCage", new String[]{"", " §8┃  §8┃ §fUne sphère de toiles d'araignée se", " §8┃ §fcréée autour du joueur à sa mort."}, Material.WEB, 0, new WebCage(), false, false),
    FASTSMELTING(1, 34, "Fast Smelting", new String[]{"", " §8┃ §fLes fours cuisent plus rapidement."}, Material.FURNACE, 0, new FastSmelting(), false, false),
    BLOODDIAMOND(1, 35, "Blood Diamond", new String[]{"", " §8┃ §fMiner un diamant vous retire X§c❤"}, Material.DIAMOND_ORE, 0, new BloodDiamond(), false, true, ScenarioValueType.DAMAGE, 1, 1, 20),
    MASTERAPPLE(2, 0, "Master Apple", new String[]{"", " §8┃ §fToutes les X minutes chaque joueur", " §8┃ §freçoit une pomme d'or cheat."}, Material.GOLDEN_APPLE, 1, new MasterApple(), false, true, ScenarioValueType.TIMEMIN, 1, 1, 60),
    VEINMINER(2, 1, "VeinMiner", new String[]{"", " §8┃ §fCassez les filons de minerais", " §8┃ §fen un seul coup !"}, Material.IRON_PICKAXE, 0, new VeinMiner(), false, false),
    BETAZOMBIE(2, 2, "Beta Zombie", new String[]{"", " §8┃ §fLes zombies lâchent des", " §8┃ §fplumes à leur mort."}, Material.SKULL_ITEM, 2, new BetaZombie(), false, false),
    PARANOIA(2, 3, "Paranoïa", new String[]{"", " §8┃ §fMiner du diamant ou de l'or,", " §8┃ §fmanger une pomme d'or ou crafter", " §8┃ §fune table d'enchantement", " §8┃ §frevèlera vos coordonées."}, Material.EMERALD, 0, new Paranoia(), false, false),
    HASTEYBABIES(2, 4, "Hastey Babies", new String[]{"", " §8┃ §fTous les outils sont enchantés !"}, Material.GOLD_PICKAXE, 0, new HasteyBabies(), false, false),
    SAFEMINER(2, 6, "Safe Miner", new String[]{"", " §8┃ §fLes dégâts de feu/lave sont désactivés.", " §8┃ §fLes dégâts de chutes / mobs", " §8┃ §fsont réduits de moitié."}, Material.LAVA_BUCKET, 0, new SafeMiner(), false, true, ScenarioValueType.YCOORD, 40, 0, 60);

    private int page;
    private int slot;
    private String name;
    private String[] lore;
    private Material material;
    private int data;
    private ScenarioManager scenarioManager;
    private boolean needTeams;
    private boolean enabled;
    private boolean configurable;
    private ScenarioValueType scenarioValueType;
    private int value;
    private int min;
    private int max;

    private Scenario(int page, int slot, String name, String[] lore, Material material, int data, ScenarioManager scenarioManager, boolean needTeams, boolean configurable) {
        this.page = page;
        this.slot = slot;
        this.name = name;
        this.lore = lore;
        this.material = material;
        this.data = data;
        this.scenarioManager = scenarioManager;
        this.needTeams = needTeams;
        this.configurable = configurable;
    }

    private Scenario(int page, int slot, String name, String[] lore, Material material, int data, ScenarioManager scenarioManager, boolean needTeams, boolean configurable, ScenarioValueType scenarioValueType, int value, int min, int max) {
        this.scenarioValueType = scenarioValueType;
        this.value = value;
        this.min = min;
        this.max = max;
        this.page = page;
        this.slot = slot;
        this.name = name;
        this.lore = lore;
        this.material = material;
        this.data = data;
        this.scenarioManager = scenarioManager;
        this.needTeams = needTeams;
        this.configurable = configurable;
    }

    public ItemStack getItem() {
        ItemCreator item = new ItemCreator(this.material).setDurability(this.data).setTableauLores(this.lore).setName("§8┃ §f" + this.name + " §f(" + (this.isEnabled() ? "§aActivé§f)" : "§cDésactivé§f)")).addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if (this.isEnabled()) {
            item.addEnchantment(Enchantment.DURABILITY, 1);
            item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        if (this.isConfigurable()) {
            item.addLore("");
            switch (this.scenarioValueType) {
                case TIME: {
                    item.addLore(" §8» §fConfiguration §f: §6§l" + this.getValue() + " seconde" + (this.getValue() > 1 ? "s" : ""));
                    break;
                }
                case TIMEMIN: {
                    item.addLore(" §8» §fConfiguration §f: §6§l" + this.getValue() + " minute" + (this.getValue() > 1 ? "s" : ""));
                    break;
                }
                case MULTIPLICATOR: {
                    item.addLore(" §8» §fConfiguration §f: §c§lx" + this.getValue());
                    break;
                }
                case DAMAGE: {
                    item.addLore(" §8» §fConfiguration §f: §c§l" + this.getValue() + "❤");
                    break;
                }
                case PERCENT: {
                    item.addLore(" §8» §fConfiguration §f: §b§l" + this.getValue() + "%");
                    break;
                }
                case YCOORD: {
                    item.addLore(" §8» §fConfiguration §f: §b§lY " + this.getValue());
                    break;
                }
                case INT: {
                    item.addLore(" §8» §fConfiguration §f: §e§l" + this.getValue());
                }
            }
        }
        if (this.getValue() > 1 && this.getValue() <= 64) {
            item.setAmount(this.getValue());
        } else if (this.getValue() > 64) {
            item.setAmount(64);
        } else {
            item.setAmount(1);
        }
        if (this.isNeedTeams()) {
            item.addLore("");
            item.addLore(" §8┃ §fLes §céquipes§f sont obligatoires");
            item.addLore(" §8┃ §fpour utiliser ce §cscénario§f.");
        }
        item.addLore("");
        item.addLore(" §8» §fCliquez pour " + (!this.isEnabled() ? "§aactiver" : "§cdésactivé") + "§f.");
        item.addLore("");
        return item.getItem();
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.enabled = isEnabled;
    }

    public void toggleEnabled() {
        if (this.isEnabled()) {
            this.enabled = false;
        } else if (this.isNeedTeams()) {
            if (!GameUtils.isSoloMode()) {
                this.enabled = true;
            }
        } else {
            this.enabled = true;
        }
    }

    public String getName() {
        return this.name;
    }

    public String[] getLore() {
        return this.lore;
    }

    public Material getMaterial() {
        return this.material;
    }

    public int getData() {
        return this.data;
    }

    public ScenarioManager getScenarioManager() {
        return this.scenarioManager;
    }

    public boolean isNeedTeams() {
        return this.needTeams;
    }

    public boolean isConfigurable() {
        return this.configurable;
    }

    public ScenarioValueType getScenarioValueType() {
        return this.scenarioValueType;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    public int getPage() {
        return this.page;
    }

    public int getSlot() {
        return this.slot;
    }
}
