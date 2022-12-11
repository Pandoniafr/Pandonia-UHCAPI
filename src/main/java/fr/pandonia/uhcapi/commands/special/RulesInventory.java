package fr.pandonia.uhcapi.commands.special;

import fr.pandonia.uhcapi.common.rules.items.DropItemRate;
import fr.pandonia.uhcapi.common.rules.items.GeneralRules;
import fr.pandonia.uhcapi.common.rules.items.UseItems;
import fr.pandonia.uhcapi.config.GameConfig;
import fr.pandonia.uhcapi.config.common.potion.PotionManagerGUI;
import fr.pandonia.uhcapi.config.scenario.Scenario;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.utils.Chrono;
import fr.pandonia.uhcapi.utils.CustomInventory;
import fr.pandonia.uhcapi.utils.ItemCreator;
import java.util.function.Supplier;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RulesInventory
        implements CustomInventory {
    private final GameManager gameManager;
    private final GameConfig gameConfig;

    public RulesInventory(GameManager gameManager) {
        this.gameManager = gameManager;
        this.gameConfig = gameManager.getGameConfig();
    }

    @Override
    public String getName() {
        return "Règles";
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack[] slots = new ItemStack[this.getSlots()];
        int result = Chrono.getCycleDurationTime(this.gameConfig.getDayNightDuration());
        ItemCreator infoItem = new ItemCreator(Material.ITEM_FRAME).setName("§6Informations").addLore("§fSlots: §6" + this.gameConfig.getGameSlot()).addLore("§fJoueur(s) par équipe: §6" + this.gameConfig.getPlayerPerTeam()).addLore("§fLimite de diamants: §6" + this.gameConfig.getDiamondMax()).addLore("§fLimite d'ors: §6" + this.gameConfig.getGoldMax()).addLore("§fCycle jour/nuit: §6" + result + "min").addLore("§fNether: " + (this.gameConfig.isNether() ? "§aActivé" : "§cDésactivé")).addLore("§fSpectateurs: " + (this.gameConfig.isSpectators() ? "§aActivé" : "§cDésactivé"));
        if (this.gameConfig.getPlayerPerTeam() > 1) {
            infoItem.addLore("§6Friendly Fire: " + (this.gameConfig.isFriendlyfire() ? "§aActivé" : "§cDésactivé"));
        }
        slots[4] = infoItem.getItem();
        slots[10] = new ItemCreator(Material.WATCH).setName("§6Temps").addLore("§fPvP: §a" + Chrono.timeToDigitalString(this.gameConfig.getPvpTime())).addLore("§fBordure: §a" + Chrono.timeToDigitalString(this.gameConfig.getBorderTime())).getItem();
        ItemCreator potion = new ItemCreator(Material.POTION).setName("§6Potions activées");
        for (PotionManagerGUI.Potions potions : PotionManagerGUI.Potions.values()) {
            potion.addLore("§f" + potions.getName() + ": " + (potions.isEnabled() ? "§aActivée" : "§cDésactivée"));
        }
        slots[16] = potion.getItem();
        ItemCreator useItemItem = new ItemCreator(Material.IRON_SWORD).setName("§6Objets");
        for (UseItems useItems : UseItems.values()) {
            useItemItem.addLore("§f" + useItems.getName() + ": " + (useItems.isEnabled() ? "§aActivée" : "§cDésactivée"));
        }
        slots[20] = useItemItem.getItem();
        slots[22] = new ItemCreator(Material.PRISMARINE_SHARD).setName("§6Mode de jeu: §f" + this.gameManager.getModuleManager().getCurrentModule().getName()).getItem();
        ItemCreator dropItemRateItem = new ItemCreator(Material.FLINT).setName("§6Taux de drop");
        for (DropItemRate dropItemRate : DropItemRate.values()) {
            dropItemRateItem.addLore("§f" + dropItemRate.getName() + ": §a+" + dropItemRate.getAmount() + "%");
        }
        slots[24] = dropItemRateItem.getItem();
        slots[28] = new ItemCreator(Material.STAINED_GLASS).setName("§6Bordure").setDurability(9).addLore("§fTaille initiale: §b" + this.gameConfig.getBorderStartSize() + " / -" + this.gameConfig.getBorderStartSize()).addLore("§fTaille finale: §b" + this.gameConfig.getBorderEndSize() + " / -" + this.gameConfig.getBorderEndSize()).addLore("§fBloc(s) par seconde: §b" + this.gameConfig.getBorderBlocksPerSecond() + " bloc" + (this.gameConfig.getBorderBlocksPerSecond() > 1 ? "s" : "")).getItem();
        ItemCreator ruleItem = new ItemCreator(Material.PAPER).setName("§6Règles");
        for (GeneralRules rules : GeneralRules.values()) {
            ruleItem.addLore("§f" + rules.getName() + ": " + (rules.isEnabled() ? "§aActivée" : "§cDésactivée"));
        }
        slots[34] = ruleItem.getItem();
        ItemCreator scenarioItem = new ItemCreator(Material.BOOK).setName("§6Scénarios");
        for (Scenario scenarios : Scenario.values()) {
            if (!scenarios.isEnabled()) continue;
            scenarioItem.addLore("§f" + scenarios.getName());
        }
        slots[40] = scenarioItem.getItem();
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
    }

    @Override
    public int getRows() {
        return 5;
    }
}
