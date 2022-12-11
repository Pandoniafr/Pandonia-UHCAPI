package fr.pandonia.uhcapi.commands;

import fr.pandonia.uhcapi.config.value.CommonItems;
import fr.pandonia.uhcapi.game.GameManager;
import fr.pandonia.uhcapi.utils.CustomInventory;
import fr.pandonia.uhcapi.utils.InventoryAPI;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InvCommand
        implements CommandExecutor,
        CustomInventory {
    private final GameManager gameManager;

    public InvCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] arguments) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            this.gameManager.getApi().openInventory(player, this.getClass());
        }
        return false;
    }

    @Override
    public String getName() {
        return "Inventaire par défaut";
    }

    @Override
    public Supplier<ItemStack[]> getContents(Player player) {
        ItemStack[] slots = new ItemStack[this.getSlots()];
        for (int slot = 0; slot < 36; ++slot) {
            ItemStack item = InventoryAPI.items[slot];
            if (item == null) continue;
            slots[slot] = item;
        }
        slots[36] = InventoryAPI.items[36];
        slots[37] = InventoryAPI.items[37];
        slots[38] = InventoryAPI.items[38];
        slots[39] = InventoryAPI.items[39];
        slots[53] = CommonItems.GUI_BACK_ITEM.getItem();
        return () -> slots;
    }

    @Override
    public void onClick(Player player, Inventory inventory, ItemStack clickedItem, int slot, ClickType clickType) {
        switch (slot) {
            case 53: {
                player.closeInventory();
            }
        }
    }

    @Override
    public int getRows() {
        return 6;
    }
}
