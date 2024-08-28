package me.itzkiba.pluginMST.listeners.abilities;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class Abilities {

    static NamespacedKey ID = new NamespacedKey(PluginMST.getPlugin(), "ID");

    public static void setItemAbilityID(ItemStack item, int value)
    {
        if (item == null)
        {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return;
        }
        meta.getPersistentDataContainer().set(ID, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getItemAbilityID(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(ID, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(ID, PersistentDataType.INTEGER);
    }


    // DESCRIPTIONS
    public static ArrayList<String> getAbilityDescription(ItemStack item)
    {
        ArrayList<String> desc = new ArrayList<>();
        int id = getItemAbilityID(item);
        int level = Stats.getItemLevel(item);

        // MULTISTRIKE
        if (id == 1000)
        {
            desc.add(ChatColor.YELLOW + "\u272A " + ChatColor.GOLD + "Multistrike " + ChatColor.DARK_GRAY + "(Right Click)");
            desc.add(ChatColor.GRAY + "Unleash a flurry of melee strikes. Each strike");
            desc.add(ChatColor.GRAY + "deals " + ChatColor.RED + (20 + (level) * 1) + "% " + ChatColor.GRAY + "of your Melee Damage.");
            desc.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "(Hold Sneak to cancel the small forward boost)");
            desc.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + "5s");
        }

        // HEAVY GUARD
        if (id == 1001)
        {
            desc.add(ChatColor.YELLOW + "\u272A " + ChatColor.GOLD + "Heavy Guard " + ChatColor.DARK_GRAY + "(Right Click)");
            desc.add(ChatColor.GRAY + "Enter a defensive stance, gaining");
            desc.add(ChatColor.GRAY + "" + ChatColor.WHITE + "+" + (40 + (20*level)) + " \u26E8 Defense " + ChatColor.GRAY + "and knockback");
            desc.add(ChatColor.GRAY + "immunity for 5 seconds.");
            desc.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + "8s");
        }

        // EXECUTION
        if (id == 1002)
        {
            desc.add(ChatColor.YELLOW + "\u272A " + ChatColor.GOLD + "Execution " + ChatColor.DARK_GRAY + "(Right Click)");
            desc.add(ChatColor.GRAY + "Attack your enemies with a powerful blow,");
            desc.add(ChatColor.GRAY + "dealing " + ChatColor.RED + String.format("%.1f", (2 + (level * 0.4))) + "x " + ChatColor.GRAY + "your Crit Strength as damage.");
            desc.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + "7s");
        }

        // SEISMIC SLAM
        if (id == 1003)
        {
            desc.add(ChatColor.YELLOW + "\u272A " + ChatColor.GOLD + "Seismic Slam " + ChatColor.DARK_GRAY + "(Right Click)");
            desc.add(ChatColor.GRAY + "Leap into the air and slam down,");
            desc.add(ChatColor.GRAY + "dealing " + ChatColor.RED + (150 + (level) * 5) + "% " + ChatColor.GRAY + "of your Melee Damage.");
            desc.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + "5s");
        }

        // FISSURE
        if (id == 1004)
        {
            desc.add(ChatColor.YELLOW + "\u272A " + ChatColor.GOLD + "Lava Fissure " + ChatColor.DARK_GRAY + "(Right Click)");
            desc.add(ChatColor.GRAY + "Strike your weapon into the ground, creating a");
            desc.add(ChatColor.GRAY + "lava fissure that deals " + ChatColor.RED + (220 + (level) * 8) + "% " + ChatColor.GRAY + "of your Melee Damage");
            desc.add(ChatColor.GRAY + "and greatly hinders enemies for " + ChatColor.GREEN + String.format("%.1f", (1 + (level) * 0.1)) + ChatColor.GRAY + " seconds.");
            desc.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + "6s");
        }




        // ARROW STORM
        if (id == 2000)
        {
            desc.add(ChatColor.YELLOW + "\u272A " + ChatColor.GOLD + "Arrow Storm " + ChatColor.DARK_GRAY + "(Shift + Left Click)");
            desc.add(ChatColor.GRAY + "Fire a barrage of deadly arrows. Each arrow");
            desc.add(ChatColor.GRAY + "deals " + ChatColor.RED + String.format("%.1f", (25 + (level) * 0.4)) + "% " + ChatColor.GRAY + "of your Ranged Damage.");
            desc.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + "4s");
        }

        // GRAPPLING SHOT
        if (id == 2001)
        {
            desc.add(ChatColor.YELLOW + "\u272A " + ChatColor.GOLD + "Grappling Shot " + ChatColor.DARK_GRAY + "(Shift + Left Click)");
            desc.add(ChatColor.GRAY + "Fire a roped arrow. Upon landing,");
            desc.add(ChatColor.GRAY + "you are pulled towards the arrow.");
            desc.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + "" + String.format("%.1f", (8.5 - level * 0.05)) + "s");
        }

        // SKULK BLAST
        if (id == 2002)
        {
            desc.add(ChatColor.YELLOW + "\u272A " + ChatColor.GOLD + "Sculk Blast " + ChatColor.DARK_GRAY + "(Shift + Left Click)");
            desc.add(ChatColor.GRAY + "Fire three sculk arrows that explode on impact.");
            desc.add(ChatColor.GRAY + "Each arrow deals " + ChatColor.RED + String.format("%.2f", (3 + (level * 0.1))) + "x " + ChatColor.GRAY + "of your Crit Strength as damage.");
            desc.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + "6s");
        }

        // EVASION
        if (id == 2003)
        {
            desc.add(ChatColor.YELLOW + "\u272A " + ChatColor.GOLD + "Evasion " + ChatColor.DARK_GRAY + "(Shift + Left Click)");
            desc.add(ChatColor.GRAY + "Launch all nearby enemies away and grant");
            desc.add(ChatColor.GRAY + "Speed II to yourself and nearby allies for " + ChatColor.GREEN + String.format("%.1f", (4 + (level * 0.1))) + "s" + ChatColor.GRAY + ".");
            desc.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + "6s");
        }

        // ARROW STORM 2
        if (id == 2004)
        {
            desc.add(ChatColor.YELLOW + "\u272A " + ChatColor.GOLD + "Bolt Storm " + ChatColor.DARK_GRAY + "(Shift + Left Click)");
            desc.add(ChatColor.GRAY + "Fire a barrage of high-velocity bolts. Each bolt");
            desc.add(ChatColor.GRAY + "deals " + ChatColor.RED + String.format("%.1f", (10 + (level) * 0.3)) + "% " + ChatColor.GRAY + "of your Ranged Damage.");
            desc.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + "4s");
        }

        // OVERCHARGE
        if (id == 2005)
        {
            desc.add(ChatColor.YELLOW + "\u272A " + ChatColor.GOLD + "Overcharge " + ChatColor.DARK_GRAY + "(Shift + Left Click)");
            desc.add(ChatColor.GRAY + "Fire a long-range critical hitscan beam that");
            desc.add(ChatColor.GRAY + "deals " + ChatColor.RED + "90% " + ChatColor.GRAY + "of your Ranged Damage. Damage is");
            desc.add(ChatColor.GRAY + "increased by " + ChatColor.RED + String.format("%.1f", (0 + (level) * 0.1)) + "% " + ChatColor.GRAY + "for each block traveled.");
            desc.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "(Damage is boosted by Crit Strength.)");
            desc.add(ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + "3s");
        }




        // REMEDY (HEAL)
        if (id == 3000)
        {
            desc.add(ChatColor.YELLOW + "\u272A " + ChatColor.GOLD + "Remedy " + ChatColor.DARK_GRAY + "(Right Click)");
            desc.add(ChatColor.GRAY + "Heal yourself and nearby allies for");
            desc.add(ChatColor.GRAY + "up to " + ChatColor.GREEN + String.format("%.2f", (5 + (level * 0.05))) + "%" + ChatColor.GRAY + " of your maximum health.");
            desc.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + "\u2735 50");
        }

        // MANA BOLT
        if (id == 3001)
        {
            desc.add(ChatColor.YELLOW + "\u272A " + ChatColor.GOLD + "Mana Bolt " + ChatColor.DARK_GRAY + "(Right Click)");
            desc.add(ChatColor.GRAY + "Fire an exploding mana bolt,");
            desc.add(ChatColor.GRAY + "dealing " + ChatColor.RED + (150 + (level * 3)) + "%" + ChatColor.GRAY + " of your Magic Damage.");
            desc.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + "\u2735 25");
        }

        // WARP
        if (id == 3002)
        {
            desc.add(ChatColor.YELLOW + "\u272A " + ChatColor.GOLD + "Warp " + ChatColor.DARK_GRAY + "(Right Click)");
            desc.add(ChatColor.GRAY + "Teleport forward a short distance and");
            desc.add(ChatColor.GRAY + "deal " + ChatColor.RED + (50 + (level * 3)) + "%" + ChatColor.GRAY + " of your Mana as damage to");
            desc.add(ChatColor.GRAY + "nearby enemies upon landing.");
            desc.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + "\u2735 40");
        }

        // SOUL SURGE
        if (id == 3003)
        {
            desc.add(ChatColor.YELLOW + "\u272A " + ChatColor.GOLD + "Soul Surge " + ChatColor.DARK_GRAY + "(Right Click)");
            desc.add(ChatColor.GRAY + "Unleash a thundering of soul fire beam,");
            desc.add(ChatColor.GRAY + "dealing " + ChatColor.RED + (250 + (level * 3)) + "%" + ChatColor.GRAY + " of your Magic Damage.");
            desc.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + "\u2735 60");
        }

        // INFERNO
        if (id == 3004)
        {
            desc.add(ChatColor.YELLOW + "\u272A " + ChatColor.GOLD + "Inferno " + ChatColor.DARK_GRAY + "(Right Click)");
            desc.add(ChatColor.GRAY + "Cast a short-range ray of flames,");
            desc.add(ChatColor.GRAY + "dealing " + ChatColor.RED + (125 + (level)) + "%" + ChatColor.GRAY + " of your Magic Damage and");
            desc.add(ChatColor.GRAY + "lighting enemies on fire for " + ChatColor.GREEN + String.format("%.1f", (1 + (level) * 0.1)) + ChatColor.GRAY + " seconds.");
            desc.add(ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + "\u2735 35");
        }

        return desc;
    }

}
