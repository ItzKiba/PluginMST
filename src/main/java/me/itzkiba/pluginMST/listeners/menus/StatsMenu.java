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

public class StatsMenu implements Listener {

    public static void open(Player viewer)
    {

        Inventory inventory = Bukkit.createInventory(viewer, 54, ChatColor.BOLD + "Stats Viewer");
        viewer.sendMessage(ChatColor.GREEN + "Opened stats menu.");

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

        ItemStack rangedDamage = new ItemStack(Material.BOW, 1);
        meta = rangedDamage.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "\u27BD " + "Ranged Damage: " + ChatColor.WHITE + "+" + Stats.getEntityRangedDamageStat(viewer));
        lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Damage dealt by using Bows,");
        lore.add(ChatColor.GRAY + "Crossbows, and other ranged weapons.");
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Gain boosts to ranged damage through equipment");
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "bonuses or by selecting the Archer class.");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        rangedDamage.setItemMeta(meta);

        ItemStack magicDamage = new ItemStack(Material.BLAZE_ROD, 1);
        meta = magicDamage.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "\u2726 " + "Magic Damage: " + ChatColor.WHITE + "+" + Stats.getEntityMagicDamageStat(viewer));
        lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Damage dealt by using Wands,");
        lore.add(ChatColor.GRAY + "Staffs, and other magic weapons.");
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Gain boosts to magic damage through equipment");
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "bonuses or by selecting the Sorcerer class.");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        magicDamage.setItemMeta(meta);





        ItemStack defense = new ItemStack(Material.IRON_CHESTPLATE, 1);
        meta = defense.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "\u26E8 " + "Defense: " + ChatColor.WHITE + "+" + Stats.getEntityDefenseStat(viewer));
        lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Reduces damage taken from all sources.");
        lore.add(ChatColor.GRAY + "Damage Reduction: " + ChatColor.GREEN + String.format("%.2f", DamageReduction.damageReductionFromDefense(Stats.getEntityDefenseStat(viewer))*100) + "%");
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Gain boosts to defense through wearing armor.");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        defense.setItemMeta(meta);


        ItemStack health = new ItemStack(Material.GOLDEN_APPLE, 1);
        meta = health.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "\u2764 " + "Health: " + ChatColor.WHITE + "+" + Stats.getEntityHealthStat(viewer));
        lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Increases your maximum health.");
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Gain boosts to health through wearing armor.");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        health.setItemMeta(meta);





        ItemStack crit = new ItemStack(Material.WITHER_SKELETON_SKULL, 1);
        meta = crit.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + "\u2620 " + "Crit Strength: " + ChatColor.WHITE + "+" + Stats.getEntityCritStat(viewer) + "%");
        lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Increases damage done by critical hits from");
        lore.add(ChatColor.GRAY + "melee weapons or fully-charged bow shots.");
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Gain boosts to crit strength through equipment bonuses.");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        crit.setItemMeta(meta);




        ItemStack attackspeed = new ItemStack(Material.AMETHYST_SHARD, 1);
        meta = attackspeed.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "\u2694 " + "Attack Speed: " + ChatColor.WHITE + "+" + Stats.getEntityAttackSpeedStat(viewer) + "%");
        lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Increases attack rate for melee and magic weapons.");
        lore.add(ChatColor.GRAY + "Attack speed is capped at +100% bonus.");
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Gain boosts to attack speed through equipment bonuses.");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        attackspeed.setItemMeta(meta);




        ItemStack mana = new ItemStack(Material.HEART_OF_THE_SEA, 1);
        meta = mana.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_AQUA + "\u2735 " + "Mana: " + ChatColor.WHITE + "+" + Stats.getEntityMaxManaStat(viewer));
        lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Increases your maximum mana. Mana allows you");
        lore.add(ChatColor.GRAY + "to use abilities applied to Magic items.");
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Gain boosts to mana through equipment bonuses.");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        mana.setItemMeta(meta);




        ItemStack speed = new ItemStack(Material.FEATHER, 1);
        meta = speed.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "\u2248 " + "Speed: " + ChatColor.WHITE + "+" + Stats.getEntitySpeedStat(viewer) + "%");
        lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Increases bonus movement speed.");
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Gain boosts to speed through equipment bonuses.");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        speed.setItemMeta(meta);





        ItemStack xp = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
        meta = xp.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "\u2605 " + ChatColor.GOLD + "Level " + Levels.getPlayerLevel(viewer) + ChatColor.AQUA + " \u2605");
        lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Defeat enemies to gain experience and level up.");
        lore.add(ChatColor.GRAY + "Leveling up may allow you to use certain items.");
        lore.add("");

        double currentXP = Levels.getPlayerXP(viewer);
        int currentLevel = Levels.getPlayerLevel(viewer);
        double requiredXPForNext = Levels.getXPRequiredForLevel(currentLevel + 1);

        double percentDifference = Math.min((currentXP - Levels.getXPRequiredForLevel(currentLevel)) / (Levels.getXPRequiredForLevel(currentLevel + 1) - Levels.getXPRequiredForLevel(currentLevel)), 1) * 100;
        String percentDifferenceStr = String.format("%.1f", Math.min((currentXP - Levels.getXPRequiredForLevel(currentLevel)) / (Levels.getXPRequiredForLevel(currentLevel + 1) - Levels.getXPRequiredForLevel(currentLevel)), 1) * 100);

        int filledChunks = 0;
        int emptyChunks = 20;

        for (int i = 0; i < 100; i+=5)
        {
            if (i < percentDifference)
            {
                filledChunks++;
                emptyChunks--;
            }
        }

        String bar = ChatColor.GOLD + "Level " + currentLevel + " ";
        for (int i = 0; i < filledChunks; i++)
        {
            bar = bar += ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.STRIKETHROUGH + "=";
        }
        for (int i = 0; i < emptyChunks; i++)
        {
            bar = bar += ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "" + ChatColor.STRIKETHROUGH + "=";
        }

        bar = bar += ChatColor.GRAY + " Level " + (currentLevel + 1) + " ";

        lore.add(bar);
        lore.add("");
        lore.add(ChatColor.GRAY + "You need " + ChatColor.YELLOW + (int)(requiredXPForNext - currentXP) + " XP " + ChatColor.GRAY + "to level up.");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        xp.setItemMeta(meta);


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
        inventory.setItem(11, rangedDamage);
        inventory.setItem(12, magicDamage);
        inventory.setItem(19, crit);
        inventory.setItem(20, attackspeed);
        inventory.setItem(21, mana);
        inventory.setItem(22, speed);
        inventory.setItem(28, defense);
        inventory.setItem(29, health);
        inventory.setItem(37, xp);
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
