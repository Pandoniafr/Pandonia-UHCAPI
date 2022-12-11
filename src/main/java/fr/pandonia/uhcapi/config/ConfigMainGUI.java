package fr.pandonia.uhcapi.config;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.GamePlayer;
import fr.pandonia.uhcapi.config.borderValue.BorderManagerGUI;
import fr.pandonia.uhcapi.config.intValue.SlotsGUI;
import fr.pandonia.uhcapi.config.teamValue.TeamManagerGUI;
import fr.pandonia.uhcapi.config.value.OpenVar;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.game.GameState;
import fr.pandonia.uhcapi.module.ModuleType;
import fr.pandonia.uhcapi.utils.CommonString;
import fr.pandonia.uhcapi.utils.CustomInventory;
import fr.pandonia.uhcapi.utils.ItemCreator;
import fr.pandonia.uhcapi.utils.Title;
import fr.pandonia.uhcapi.world.BiomeChanger;
import java.util.function.Supplier;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ConfigMainGUI
        implements CustomInventory {
    private final API api;
    private final GameManager gameManager;
    private final GameConfig gameConfig;

    public ConfigMainGUI(API api) {
        this.api = api;
        this.gameManager = api.getGameManager();
        this.gameConfig = this.gameManager.getGameConfig();
    }

    @Override
    public String getName() {
        return "§f(§c!§f) §cConfiguration";
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        Integer[] glass;
        ItemStack[] slots = new ItemStack[this.getSlots()];
        Integer[] arrayOfInteger1 = glass = new Integer[]{0, 1, 7, 8, 9, 17, 36, 44, 45, 46, 52, 53};
        int i = arrayOfInteger1.length;
        for (int b = 0; b < i; b = (int)((byte)(b + 1))) {
            int j = arrayOfInteger1[b];
            slots[j] = new ItemCreator(Material.STAINED_GLASS_PANE).setDurability(14).setName("§f").getItem();
        }
        slots[2] = new ItemCreator(Material.RED_ROSE).setName("§8┃ §fPanel d'§cAdministration").addLore("").addLore(" §8» §fAccès §f: §c§lAdministration").addLore("").addLore("  §8┃ §fPermet d'acceder au").addLore("  §8┃ §fpanel d'§cadministration§f.").addLore("").addLore(CommonString.CLICK_HERE_TO_ACCESS.getMessage()).addLore("").getItem();
        slots[4] = new ItemCreator(Material.SAPLING).setName("§8┃ §fPré-charger").addLore("").addLore(" §8» §fAccès §f: §6§lHost").addLore("").addLore("  §8┃ §fPermet de pré-charger").addLore("  §8┃ §ftoute la §2map§f.").addLore("").addLore(CommonString.CLICK_HERE_TO_ACCESS.getMessage()).addLore("").getItem();
        GameConfig.WaitingTeleportationState waitingState = this.gameConfig.getTeleportationState();
        slots[6] = new ItemCreator(waitingState.equals((Object)GameConfig.WaitingTeleportationState.IN_ROOM) ? Material.EYE_OF_ENDER : Material.ENDER_PEARL).setName(waitingState.equals((Object)GameConfig.WaitingTeleportationState.IN_ROOM) ? "§8┃ §fTéléportation au §alobby" : "§8┃ §fTéléportation à la §asalle des règles").addLore("").addLore(" §8» §fAccès §f: §6§lHost").addLore("").addLore("  §8┃ §fPermet de teleporter les §ajoueurs").addLore("  §8┃ §f" + (waitingState.equals((Object)GameConfig.WaitingTeleportationState.IN_ROOM) ? "au point d'apparition" : "dans la salle des règles") + "§f.").addLore("").addLore(CommonString.CLICK_HERE_TO_ACCESS.getMessage()).addLore("").getItem();
        slots[10] = OpenVar.SLOTS.getItem();
        if (this.gameManager.getModuleManager().getCurrentModule().hasTeam()) {
            slots[25] = new ItemCreator(Material.BANNER).setName("§8┃ §fGestion des §céquipes").setDurability(15).addLore("").addLore(" §8» §fAccès §f: §6§lHost").addLore("").addLore("  §8┃ §fPermet de gérer les").addLore("  §8┃ §coptions §fdes équipes.").addLore("").addLore(CommonString.CLICK_HERE_TO_ACCESS.getMessage()).addLore("").getItem();
        }
        slots[11] = new ItemCreator(Material.BARRIER).setName("§8┃ §fStopper le serveur").setDurability(15).addLore("").addLore(" §8» §fAccès §f: §6§lHost").addLore("").addLore("  §8┃ §fPermet de stopper").addLore("  §8┃ §fle §cserveur").addLore("").addLore(CommonString.CLICK_HERE_TO_ACTIVATE.getMessage()).addLore("").getItem();
        slots[15] = new ItemCreator(Material.EYE_OF_ENDER).setName("§8┃ §fSpectateurs").addLore("").addLore(" §8» §fAccès §f: §6§lHost").addLore(" §8» §fStatut §f: " + (this.gameConfig.isSpectators() ? "§aActivé" : "§cDésactivé")).addLore("").addLore("  §8┃ §fPermet d'§aaccepter§f ou §cnon§f la présence").addLore("  §8┃ §fdes spectateurs dans la §cpartie§f.").addLore("").addLore(CommonString.CLICK_HERE_TO_MODIFY.getMessage()).addLore("").getItem();
        slots[16] = new ItemCreator(Material.NETHERRACK).setName("§8┃ §fNether").addLore("").addLore(" §8» §fAccès §f: §6§lHost").addLore(" §8» §fStatut §f: " + (this.gameConfig.isNether() ? "§aActivé" : "§cDésactivé")).addLore("").addLore("  §8┃ §fPermet d'§aaccepter§f ou §cnon§f").addLore("  §8┃ §fdes joueurs à aller dans le §cnether§f.").addLore("").addLore(CommonString.CLICK_HERE_TO_MODIFY.getMessage()).addLore("").getItem();
        slots[22] = new ItemCreator(Material.ITEM_FRAME).setName("§8┃ §fOptions de la §cpartie").addLore("").addLore(" §8» §fAccès §f: §6§lHost").addLore("").addLore("  §8┃ §fPermet d'accéder aux").addLore("  §8┃ §coptions§f/§crègles§f de la partie").addLore("").addLore(CommonString.CLICK_HERE_TO_MODIFY.getMessage()).addLore("").getItem();
        if (!this.gameManager.getModuleManager().getCurrentModule().equals((Object) ModuleType.UHC)) {
            slots[31] = new ItemCreator(Material.PRISMARINE_SHARD).setName("§8┃ §fMode de §cjeu").addLore("").addLore(" §8» §fAccès §f: §6§lHost").addLore(" §8» §fMode §f: §6§l" + this.gameManager.getModuleManager().getCurrentModule().getName()).addLore("").addLore("  §8┃ §fPermet de modifer les options").addLore("  §8┃ §cliées§f au mode de jeu §aactif§f.").addLore("").addLore(CommonString.CLICK_HERE_TO_ACCESS.getMessage()).addLore("").getItem();
        }
        slots[37] = new ItemCreator(Material.STAINED_GLASS).setDurability(9).setName("§8┃ §fGestion de la §cbordure").addLore("").addLore(" §8» §fAccès §f: §6§lHost").addLore("").addLore("  §8┃ §fPermet de modifer la §ataille").addLore("  §8┃ §fet la §bvitesse§f de la §cbordure§f.").addLore("").addLore(CommonString.CLICK_HERE_TO_ACCESS.getMessage()).addLore("").getItem();
        slots[43] = new ItemCreator(Material.BOOK).setName("§8┃ §fGestion des §cscénarios").addLore("").addLore(" §8» §fAccès §f: §6§lHost").addLore("").addLore("  §8┃ §fPermet d'§aajouter§f des scénarios").addLore("  §8┃ §fqui dynamiseront la §cpartie§f.").addLore("").addLore(CommonString.CLICK_HERE_TO_ACCESS.getMessage()).addLore("").getItem();
        slots[47] = new ItemCreator(Material.WATCH).setName("§8┃ §fAccessibilité de la §cpartie").addLore("").addLore(" §8» §fAccès §f: §6§lHost").addLore(" §8» §fStatut §f: " + this.gameConfig.getGameAccess().getMessage()).addLore("").addLore("  §8┃ §fPermet de §cmodifier§f l'accessibilité").addLore("  §8┃ §fà la partie pour les §cjoueurs§f.").addLore("").addLore(CommonString.CLICK_HERE_TO_MODIFY.getMessage()).addLore("").getItem();
        slots[51] = this.gameManager.getModuleManager().getCurrentModule().equals((Object)ModuleType.DEMONSLAYER) ? new ItemCreator(Material.PAPER).setName("§8┃ §fPré-Config §f(§c§lDEMONSLAYER§f)").addLore("").addLore(" §8» §fAccès §f: §6§lHost").addLore("").addLore("  §8┃ §fPermet d'accéder à").addLore("  §8┃ §fvos §cconfigurations§f.").addLore("").addLore(CommonString.CLICK_HERE_TO_ACCESS.getMessage()).addLore("").getItem() : (this.gameManager.getModuleManager().getCurrentModule().equals((Object)ModuleType.UHC) ? new ItemCreator(Material.PAPER).setName("§8┃ §fPré-Config §f(§c§lUHC§f)").addLore("").addLore(" §8» §fAccès §f: §6§lHost").addLore("").addLore("  §8┃ §fPermet d'accéder à").addLore("  §8┃ §fvos §cconfigurations§f.").addLore("").addLore(CommonString.CLICK_HERE_TO_ACCESS.getMessage()).addLore("").getItem() : (this.gameManager.getModuleManager().getCurrentModule().equals((Object)ModuleType.LG) ? new ItemCreator(Material.PAPER).setName("§8┃ §fPré-Config §f(§c§lLG§f)").addLore("").addLore(" §8» §fAccès §f: §6§lHost").addLore("").addLore("  §8┃ §fPermet d'accéder à").addLore("  §8┃ §fvos §cconfigurations§f.").addLore("").addLore(CommonString.CLICK_HERE_TO_ACCESS.getMessage()).addLore("").getItem() : (this.gameManager.getModuleManager().getCurrentModule().equals((Object)ModuleType.NARUTO) ? new ItemCreator(Material.PAPER).setName("§8┃ §fPré-Config §f(§c§lNARUTO§f)").addLore("").addLore(" §8» §fAccès §f: §6§lHost").addLore("").addLore("  §8┃ §fPermet d'accéder à").addLore("  §8┃ §fvos §cconfigurations§f.").addLore("").addLore(CommonString.CLICK_HERE_TO_ACCESS.getMessage()).addLore("").getItem() : new ItemCreator(Material.PAPER).setName("§8┃ §fPré-Config §f(§c§LUHC§f)").addLore("").addLore(" §8» §fAccès §f: §c§lIndisponible dans ce mode.").addLore("").getItem())));
        if (this.api.getGameManager().getGameState().equals((Object) GameState.WAITING)) {
            slots[49] = new ItemCreator(Material.INK_SACK).setDurability(10).setName("§8┃ §aLancer§f la §cpartie").addLore("").addLore(" §8» §fTout est §aprêt§f ?").addLore(" §8» §fAccès §f: §6§lHost").addLore("").addLore("  §8┃ §fPermet de lancer la §cpartie§f si").addLore("  §8┃ §fvous avez fini la config de la §cpartie§f.").addLore("").addLore(CommonString.CLICK_HERE_TO_ACTIVATE.getMessage()).addLore("").getItem();
        } else if (this.api.getGameManager().getGameState().equals((Object)GameState.STARTING)) {
            slots[49] = new ItemCreator(Material.INK_SACK).setDurability(8).setName("§8┃ §cArrêter§f le lancement§f.").addLore("").addLore(" §8» §fPas sûr ? §cArrête !").addLore(" §8» §fAccès §f: §6§lHost").addLore("").addLore("  §8┃ §fPermet d'arrêter la §cpartie§f si").addLore("  §8┃ §fvous avez mal fait la config de la §cpartie§f.").addLore("").addLore(CommonString.CLICK_HERE_TO_ACTIVATE.getMessage()).addLore("").getItem();
        }
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
        if (slot == 6) {
            if (this.gameManager.getGameState().equals((Object)GameState.WAITING)) {
                if (this.gameConfig.getTeleportationState().equals((Object)GameConfig.WaitingTeleportationState.IN_ROOM)) {
                    this.gameConfig.setTeleportationState(GameConfig.WaitingTeleportationState.IN_LOBBY);
                } else {
                    this.gameConfig.setTeleportationState(GameConfig.WaitingTeleportationState.IN_ROOM);
                }
            }
            return;
        }
        switch (clickedItem.getType()) {
            case SAPLING: {
                player.closeInventory();
                if (!this.gameManager.isPreloadFinished() && !this.gameManager.isPreload()) {
                    this.gameManager.setPreload(true);
                    BiomeChanger.addSapling();
                    player.sendMessage("§6§lDOMEI §8§l• §fVous venez de §alancer §fla prégénération de la map.");
                    break;
                }
                player.sendMessage("§6§lDOMEI §8§l• §fLe serveur est déjà §cchargé§f ou est §centrain§f...");
                break;
            }
            case RED_ROSE: {
                if (GamePlayer.getPlayer(player.getUniqueId()).isHeadStaff()) {
                    this.api.openInventory(player, AdminPanelGUI.class);
                    break;
                }
                player.sendMessage("§6§lDOMEI §8§l• §fVous n'êtes §cpas autorisé§f à faire ceci.");
                break;
            }
            case SKULL_ITEM: {
                if (this.gameManager.getModuleManager().getCurrentModule().isHasRole()) break;
                this.api.openInventory(player, SlotsGUI.class);
                break;
            }
            case INK_SACK: {
                if (clickedItem.getDurability() == 10) {
                    player.closeInventory();
                    this.api.getGameManager().startWithTimer();
                    break;
                }
                if (clickedItem.getDurability() != 8) break;
                this.gameManager.getStartGameCountDown().cancel();
                this.api.getGameManager().setGameState(GameState.WAITING);
                player.closeInventory();
                Bukkit.getOnlinePlayers().forEach(players -> {
                    Title.sendTitle(players, 10, 40, 10, "§cDomei§f-§cUHC", "§cLancement annulé... :c");
                    players.setLevel(0);
                    players.setExp(0.0f);
                });
                break;
            }
            case BANNER: {
                this.api.openInventory(player, TeamManagerGUI.class);
                break;
            }
            case EYE_OF_ENDER: {
                this.gameConfig.setSpectators(!this.gameConfig.isSpectators());
                this.api.openInventory(player, this.getClass());
                break;
            }
            case NETHERRACK: {
                this.gameConfig.setNether(!this.gameConfig.isNether());
                this.api.openInventory(player, this.getClass());
                break;
            }
            case STAINED_GLASS: {
                this.api.openInventory(player, BorderManagerGUI.class);
                break;
            }
            case ITEM_FRAME: {
                this.api.openInventory(player, ConfigOptionsGUI.class);
                break;
            }
            case BOOK: {
                this.api.getCommon().getScenariosGUI().openInventory(player, 1);
                break;
            }
            case BARRIER: {
                break;
            }
            case WATCH: {
                player.sendMessage("§6§lDOMEI §8§l• §fVous devez §cpré-charger§f la map avant d'ouvrir la §cpartie§f.");
                player.closeInventory();
            }
        }
    }

    @Override
    public int getRows() {
        return 6;
    }
}
