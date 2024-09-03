package me.itzkiba.pluginMST.listeners.menus;

import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import me.itzkiba.pluginMST.listeners.playerdamage.DamageReduction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class UpgradeMenu implements Listener {

    public static void open(Player viewer)
    {

        Inventory inventory = Bukkit.createInventory(viewer, 54, ChatColor.BOLD + "Player Upgrades");
        viewer.sendMessage(ChatColor.GREEN + "Opened upgrades menu.");

        ItemMeta meta;
        ArrayList<String> lore;

        ItemStack border = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        meta = close.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Close Menu");
        close.setItemMeta(meta);

        ItemStack meleeDamage = new ItemStack(Material.DIAMOND_SWORD, 1);
        meta = meleeDamage.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "\uD83D\uDDE1 " + "Melee Damage: " + ChatColor.WHITE + "+" + Stats.getEntityDamageStat(viewer));
        lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Damage dealt by using Swords,");
        lore.add(ChatColor.GRAY + "Axes, and other melee weapons.");
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Gain boosts to melee damage through equipment");
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "bonuses or by selecting the Warrior class.");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meleeDamage.setItemMeta(meta);

        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (i == 0 || i == 5)
                {
                    inventory.setItem(i*9 + j, border);
                    continue;
                }

                if (j == 0 || j == 8)
                {
                    inventory.setItem(i*9 + j, border);
                }
            }
        }

        inventory.setItem(49, close);
        inventory.setItem(10, meleeDamage);
        viewer.openInventory(inventory);

    }

    @EventHandler
    public void statsInventoryFunctions(InventoryClickEvent e)
    {
        if (!(e.getView().getTitle().equals(ChatColor.BOLD + "Stats Viewer")))
        {
            return;
        }

        if (e.getClick() == ClickType.NUMBER_KEY) {
            e.setCancelled(true);
            return;
        }

        if (e.getCurrentItem() == null)
        {
            return;
        }

        if (e.getCurrentItem().getType() == Material.BARRIER)
        {
            e.getWhoClicked().closeInventory();
        }

        e.setCancelled(true);
    }

}
