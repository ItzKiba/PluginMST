package me.itzkiba.pluginMST.listeners.menus;

import me.itzkiba.pluginMST.listeners.persistentdatakeys.Classes;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import me.itzkiba.pluginMST.listeners.playerdamage.DamageReduction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ClassMenu implements Listener {

    public static void open(Player viewer)
    {

        Inventory inventory = Bukkit.createInventory(viewer, 45, ChatColor.BOLD + "Select Class");

        setItems(inventory, viewer);

        viewer.openInventory(inventory);

    }

    @EventHandler
    public void statsInventoryFunctions(InventoryClickEvent e)
    {
        Player p = (Player) e.getWhoClicked();

        if (!(e.getView().getTitle().equals(ChatColor.BOLD + "Select Class")))
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
            p.closeInventory();
        }

        if (e.getCurrentItem().getType() == Material.DIAMOND_SWORD)
        {
            Classes.setPlayerClass(p, 1);
        }

        if (e.getCurrentItem().getType() == Material.BOW)
        {
            Classes.setPlayerClass(p, 2);
        }

        if (e.getCurrentItem().getType() == Material.BLAZE_ROD)
        {
            Classes.setPlayerClass(p, 3);
        }

        if (e.getCurrentItem().getType() == Material.DIAMOND_SWORD ||
                e.getCurrentItem().getType() == Material.BOW ||
                e.getCurrentItem().getType() == Material.BLAZE_ROD)
        {
            p.playSound(p, Sound.UI_BUTTON_CLICK, 1F, 0.5F);
            setItems(e.getInventory(), p);
        }



        e.setCancelled(true);
    }

    private static void setItems(Inventory inv, Player viewer)
    {
        ItemMeta meta;
        ArrayList<String> lore;

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        meta = close.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Close Menu");
        close.setItemMeta(meta);

        ItemStack warrior = new ItemStack(Material.DIAMOND_SWORD, 1);
        meta = warrior.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "\uD83D\uDDE1 " + "Warrior Class");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Warriors excel at close-quarters combat and dealing");
        lore.add(ChatColor.GRAY + "a respectable amount of damage while maintaining");
        lore.add(ChatColor.GRAY + "high survivability in the form of defense.");
        lore.add("");
        lore.add(ChatColor.YELLOW + "\u272A " + ChatColor.GOLD + "Warrior Bonus");
        lore.add(ChatColor.GRAY + "Each consecutive attack gains " + ChatColor.RED + "+2%" + ChatColor.GRAY + " additional");
        lore.add(ChatColor.GRAY + "damage. This bonus has no upper limit, but resets after");
        lore.add(ChatColor.GRAY + "not dealing damage for 4 seconds.");
        lore.add("");

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        if (Classes.getPlayerClass(viewer) == 1)
        {
            meta.addEnchant(Enchantment.SHARPNESS, 1, true);
            lore.add(ChatColor.GREEN + "" + ChatColor.BOLD + "SELECTED");
        }
        meta.setLore(lore);
        warrior.setItemMeta(meta);

        ItemStack archer = new ItemStack(Material.BOW, 1);
        meta = archer.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "\u27BD " + "Archer Class");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Archers are masterful at dealing heavy long-range");
        lore.add(ChatColor.GRAY + "burst damage by using bows along with having a");
        lore.add(ChatColor.GRAY + "wide assortment of mobility options.");
        lore.add("");
        lore.add(ChatColor.YELLOW + "\u272A " + ChatColor.GOLD + "Archer Bonus");
        lore.add(ChatColor.GRAY + "Left click while holding a bow to instantly fire an arrow.");
        lore.add(ChatColor.GRAY + "The rate of fire is affected by " + ChatColor.DARK_PURPLE + "\u2694 " + "Attack Speed" + ChatColor.GRAY + ".");
        lore.add("");

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        if (Classes.getPlayerClass(viewer) == 2)
        {
            meta.addEnchant(Enchantment.SHARPNESS, 1, true);
            lore.add(ChatColor.GREEN + "" + ChatColor.BOLD + "SELECTED");
        }
        meta.setLore(lore);
        archer.setItemMeta(meta);

        ItemStack sorcerer = new ItemStack(Material.BLAZE_ROD, 1);
        meta = sorcerer.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "\u2726 Sorcerer Class");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Sorcerers are capable of dealing large");
        lore.add(ChatColor.GRAY + "area of effect damage. They are empowered by");
        lore.add(ChatColor.GRAY + "the amount of Mana that they have.");
        lore.add("");
        lore.add(ChatColor.YELLOW + "\u272A " + ChatColor.GOLD + "Sorcerer Bonus");
        lore.add(ChatColor.GRAY + "Gain " + ChatColor.DARK_AQUA + "100 \u2735 Mana" + ChatColor.GRAY + " by default.");
        lore.add(ChatColor.GRAY + "Mana regenerates over time or by attacking enemies.");
        lore.add("");
        lore.add(ChatColor.AQUA + "\u2726 " + "Magic Damage " + ChatColor.GRAY + "increased by 1%");
        lore.add(ChatColor.GRAY + "for every " + ChatColor.DARK_AQUA + "5 \u2735 Mana " + ChatColor.GRAY + "you currently have.");
        lore.add("");

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        if (Classes.getPlayerClass(viewer) == 3)
        {
            meta.addEnchant(Enchantment.SHARPNESS, 1, true);
            lore.add(ChatColor.GREEN + "" + ChatColor.BOLD + "SELECTED");
        }
        meta.setLore(lore);
        sorcerer.setItemMeta(meta);

        ItemStack border = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);

        for (int i = 0; i < 5; i++)
        {

            for (int j = 0; j < 9; j++)
            {

                if (i == 0 || i == 4)
                {
                    inv.setItem(i*9 + j, border);
                    continue;
                }

                if (j == 0 || j == 8)
                {
                    inv.setItem(i*9 + j, border);
                }
            }
        }

        inv.setItem(40, close);
        inv.setItem(20, warrior);
        inv.setItem(22, archer);
        inv.setItem(24, sorcerer);
    }

}
