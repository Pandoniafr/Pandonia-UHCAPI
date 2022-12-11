package fr.pandonia.uhcapi.config.teamValue;

import fr.pandonia.uhcapi.API;
import fr.pandonia.uhcapi.common.player.PlayerUtils;
import fr.pandonia.uhcapi.config.ConfigMainGUI;
import fr.pandonia.uhcapi.config.GameConfig;
import fr.pandonia.uhcapi.config.value.CommonItems;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.utils.CommonString;
import fr.pandonia.uhcapi.utils.CustomInventory;
import fr.pandonia.uhcapi.utils.ItemCreator;
import java.util.function.Supplier;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TeamManagerGUI
        implements CustomInventory {
    private final API api;
    private final GameManager gameManager;

    public TeamManagerGUI(API api) {
        this.api = api;
        this.gameManager = api.getGameManager();
    }

    @Override
    public String getName() {
        return "Gestion des équipes";
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack[] slots = new ItemStack[this.getSlots()];
        slots[3] = new ItemCreator(Material.DIAMOND).setName("§eAffichage des équipes").addLore("§fPermet de modifier l'affichage des").addLore("§féquipes dans dans le menu correspondant").addLore("§fen fonction du nombre de slots").addLore("§fou en affichant toute les équipes disponibles.").addLore("").addLore("§f» §eÉtat: §f" + (this.gameManager.getGameConfig().isShowAllTeams() ? "Toutes les équipes" : "Nombre de slots")).addLore("").addLore(CommonString.CLICK_HERE_TO_MODIFY.getMessage()).addLore("").getItem();
        slots[4] = new ItemCreator(Material.BLAZE_POWDER).setName("§6Friendly Fire").addLore("§fCliquez ici pour autoriser").addLore("§fou non le friendly fire.").addLore("").addLore("§f» §eConfiguration: §f" + (this.gameManager.getGameConfig().isFriendlyfire() ? "§aOui" : "§cNon")).addLore("").addLore(CommonString.CLICK_HERE_TO_MODIFY.getMessage()).addLore("").getItem();
        slots[5] = new ItemCreator(Material.BANNER).setDurability(15).setName("§eÉquipes").setAmount(this.gameManager.getGameConfig().getPlayerPerTeam()).addLore("§fCliquez ici pour modifier").addLore("§fle nombre de joueurs").addLore("§fprésent dans chaque équipe.").addLore("").addLore("§f» §eConfiguration: §f" + this.gameManager.getGameConfig().getPlayerPerTeam() + "vs" + this.gameManager.getGameConfig().getPlayerPerTeam()).addLore("").addLore(CommonString.CLICK_HERE_TO_MODIFY.getMessage()).addLore("").getItem();
        slots[13] = CommonItems.GUI_BACK_ITEM.getItem();
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
        switch (clickedItem.getType()) {
            case DIAMOND: {
                this.gameManager.getGameConfig().setShowAllTeams(!this.gameManager.getGameConfig().isShowAllTeams());
                this.gameManager.getTeamManager().resetTeams();
                break;
            }
            case BANNER: {
                this.changePlayerPerTeam(clickType);
                break;
            }
            case BLAZE_POWDER: {
                this.gameManager.getGameConfig().setFriendlyfire(!this.gameManager.getGameConfig().isFriendlyfire());
                break;
            }
            case ARROW: {
                this.api.openInventory(player, ConfigMainGUI.class);
                return;
            }
        }
        this.api.openInventory(player, this.getClass());
    }

    private void changePlayerPerTeam(ClickType clickType) {
        this.gameManager.getTeamManager().resetTeams();
        GameConfig gameConfig = this.gameManager.getGameConfig();
        gameConfig.setPlayerPerTeam(clickType.isRightClick() ? gameConfig.getPlayerPerTeam() - 1 : gameConfig.getPlayerPerTeam() + 1);
        Bukkit.getOnlinePlayers().forEach(players -> PlayerUtils.giveDefaultItems(players));
    }

    @Override
    public int getRows() {
        return 2;
    }
}
