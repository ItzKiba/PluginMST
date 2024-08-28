package me.itzkiba.pluginMST.listeners.itemconverts;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.helperfunctions.ItemUtility;
import me.itzkiba.pluginMST.listeners.abilities.Abilities;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class ConvertDefaultItems implements Listener
{

    @EventHandler
    public void updateDefaultItems(ServerLoadEvent e)
    {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginMST.getPlugin(),() -> {

            for (World world : Bukkit.getServer().getWorlds())
            {
                for (Player player : world.getPlayers())
                {
                    convertItems(player);
                }
            }

        }, 1, 2);
    }

//    @EventHandler
//    public void onMobSpawn(EntitySpawnEvent e)
//    {
//        if (!(e.getEntity() instanceof LivingEntity))
//        {
//            return;
//        }
//        LivingEntity p = (LivingEntity) e.getEntity();
//        convertItemsEntity(p);
//    }

    public static void spawnEvent(LivingEntity entity)
    {
        convertItemsEntity(entity);
    }

    private void convertItems(Player p)
    {
        for (ItemStack i : p.getInventory().getContents())
        {
            if (i == null)
                continue;

            if ((ItemUtility.getDefaultItemStats(i).containsKey("damage")
                || ItemUtility.getDefaultItemStats(i).containsKey("defense")
                || ItemUtility.getDefaultItemStats(i).containsKey("ranged_damage"))
                && !Stats.isCustom(i))
            {
                setItemValuesEntity(i);
                Stats.setCustom(i, true);
            }
              updateStats(i);
              setNewItemLore(i, p);
        }
    }

    private static void convertItemsEntity(LivingEntity p)
    {
        ItemStack i = p.getEquipment().getItemInMainHand();
        if (i.getType() == Material.AIR)
        {
            return;
        }
            if ((ItemUtility.getDefaultItemStats(i).containsKey("damage")
                    || ItemUtility.getDefaultItemStats(i).containsKey("defense")
                    || ItemUtility.getDefaultItemStats(i).containsKey("ranged_damage"))
                    && !Stats.isCustom(i))
            {
                p.getEquipment().setItemInMainHand(setItemValuesEntity(i));
                Stats.setCustom(i, true);
                p.getEquipment().setItemInMainHand(updateStatsEntity(i));
            }

    }

    private static ItemStack setItemValuesEntity(ItemStack item)
    {
        // BASE STATS FOR DEFAULT ITEMS
        if (ItemUtility.getDefaultItemStats(item).containsKey("damage")) {
            int damage = ItemUtility.getDefaultItemStats(item).get("damage");
            Stats.setBaseDamageStat(item, damage);
        }
        if (ItemUtility.getDefaultItemStats(item).containsKey("defense")) {
            int defense = ItemUtility.getDefaultItemStats(item).get("defense");
            Stats.setBaseDefenseStat(item, defense);
        }
        if (ItemUtility.getDefaultItemStats(item).containsKey("ranged_damage")) {
            int ranged_damage = ItemUtility.getDefaultItemStats(item).get("ranged_damage");
            Stats.setBaseRangedDamageStat(item, ranged_damage);
        }
        if (ItemUtility.getDefaultItemStats(item).containsKey("rarity")) {
            int rarity = ItemUtility.getDefaultItemStats(item).get("rarity");
            Stats.setRarity(item, rarity);
        }


        Stats.setItemName(item, capitalize(item.getType().toString().toLowerCase().replace('_', ' ')));

        ItemMeta meta = item.getItemMeta();
        //meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", 0, AttributeModifier.Operation.ADD_NUMBER));
        //meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorToughness", 0, AttributeModifier.Operation.ADD_NUMBER));
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(Stats.getArmorNamespace(), 0, AttributeModifier.Operation.ADD_NUMBER));
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(Stats.getArmorToughNamespace(), 0, AttributeModifier.Operation.ADD_NUMBER));
        item.setItemMeta(meta);
        return item;
    }

    public static void setNewItemLore(ItemStack item, Player p)
    {
        ArrayList<String> lore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();

        ChatColor rarityColor = ChatColor.WHITE;
        String rarityTitle = "Common";

        if (!Stats.isCustom(item))
            return;

        switch(Stats.getRarity(item))
        {
            case 1:
                rarityColor = ChatColor.GREEN;
                rarityTitle = "Uncommon";
                break;
            case 2:
                rarityColor = ChatColor.BLUE;
                rarityTitle = "Rare";
                break;
            case 3:
                rarityColor = ChatColor.DARK_PURPLE;
                rarityTitle = "Epic";
                break;
            case 4:
                rarityColor = ChatColor.GOLD;
                rarityTitle = "Legendary";
                break;
            case 5:
                rarityColor = ChatColor.DARK_RED;
                rarityTitle = "Mythical";
                break;
        }


        // STATS
        if (Stats.getDamageStat(item) != 0)
        {
            lore.add(
                    ChatColor.RED + "\uD83D\uDDE1 " + ChatColor.GRAY + "Melee Damage: " +
                            ChatColor.RED + (Stats.getDamageStat(item) > 0 ? "+" : "") +
                            Stats.getDamageStat(item)
            );
        }

        if (Stats.getRangedDamageStat(item) != 0)
        {
            lore.add(
                    ChatColor.GREEN + "\u27BD " + ChatColor.GRAY + "Ranged Damage: " +
                            ChatColor.GREEN + (Stats.getRangedDamageStat(item) > 0 ? "+" : "") +
                            Stats.getRangedDamageStat(item)
            );
        }

        if (Stats.getMagicDamageStat(item) != 0)
        {
            lore.add(
                    ChatColor.AQUA + "\u2726 " + ChatColor.GRAY + "Magic Damage: " +
                            ChatColor.AQUA + (Stats.getMagicDamageStat(item) > 0 ? "+" : "") +
                            Stats.getMagicDamageStat(item)
            );
        }

        if (Stats.getPowerStat(item) != 0)
        {
            lore.add(
                    ChatColor.GOLD + "\u2742 " + ChatColor.GRAY + "Power: " +
                            ChatColor.GOLD + (Stats.getPowerStat(item) > 0 ? "+" : "") +
                            Stats.getPowerStat(item) + "%"
            );
        }

        if (Stats.getHealthStat(item) != 0)
        {
            lore.add(
                    ChatColor.RED + "\u2764 " + ChatColor.GRAY + "Health: " +
                            ChatColor.RED + (Stats.getHealthStat(item) > 0 ? "+" : "") +
                            Stats.getHealthStat(item)
            );
        }

        if (Stats.getDefenseStat(item) != 0)
        {
            lore.add(
                    ChatColor.WHITE + "\u26E8 " + ChatColor.GRAY + "Defense: " +
                            ChatColor.WHITE + (Stats.getDefenseStat(item) > 0 ? "+" : "") +
                            Stats.getDefenseStat(item)
            );
        }

        if (Stats.getMaxManaStat(item) != 0)
        {
            lore.add(
                    ChatColor.DARK_AQUA + "\u2735 " + ChatColor.GRAY + "Mana: " +
                            ChatColor.DARK_AQUA + (Stats.getMaxManaStat(item) > 0 ? "+" : "") +
                            Stats.getMaxManaStat(item)
            );
        }

        if (Stats.getCritStat(item) != 0)
        {
            lore.add(
                    ChatColor.BLUE + "\u2620 " + ChatColor.GRAY + "Crit Strength: " +
                            ChatColor.BLUE + (Stats.getCritStat(item) > 0 ? "+" : "") +
                            Stats.getCritStat(item) + "%"
            );
        }

        if (Stats.getAttackSpeedStat(item) != 0)
        {
            lore.add(
                    ChatColor.DARK_PURPLE + "\u2694 " + ChatColor.GRAY + "Attack Speed: " +
                            ChatColor.DARK_PURPLE + (Stats.getAttackSpeedStat(item) > 0 ? "+" : "") +
                            Stats.getAttackSpeedStat(item) + "%"
            );
        }

        if (Stats.getSpeedStat(item) != 0)
        {
            lore.add(
                    ChatColor.WHITE + "\u2248 " + ChatColor.GRAY + "Speed: " +
                            ChatColor.WHITE + (Stats.getSpeedStat(item) > 0 ? "+" : "") +
                            Stats.getSpeedStat(item) + "%"
            );
        }

        if (meta.getEnchants().size() > 0)
        {
            lore.add("");
            lore.add(ChatColor.LIGHT_PURPLE + "Enchantments:");
            for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet())
            {
                String enchantName = entry.getKey().getKey().getKey();
                enchantName = enchantName.replace('_', ' ');
                enchantName = capitalize(enchantName);
                lore.add(ChatColor.GRAY + enchantName + " " + entry.getValue());
            }
        }

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        if (Abilities.getItemAbilityID(item) != 0)
        {
            lore.add("");
            lore.addAll((Abilities.getAbilityDescription(item)));
        }

        // LEVEL

        if (Stats.getItemLevel(item) != 0)
        {
            lore.add("");
            if (Levels.getPlayerLevel(p) < Stats.getItemLevel(item))
            {
                lore.add(
                        ChatColor.RED + "\u2716 " + "Level " + Stats.getItemLevel(item) + " Required"
                );
            }
            else
            {
                lore.add(
                        ChatColor.GREEN + "\u2714 " + "Level " + Stats.getItemLevel(item) + " Required"
                );
            }
        }
        lore.add("");
        lore.add(rarityColor + rarityTitle);
        lore.add("");
        meta.setLore(lore);

        meta.setDisplayName(rarityColor + Stats.getItemName(item));
        item.setItemMeta(meta);

    }




    private static String capitalize(String message) {

        // stores each characters to a char array
        char[] charArray = message.toCharArray();
        boolean foundSpace = true;

        for (int i = 0; i < charArray.length; i++) {

            // if the array element is a letter
            if (Character.isLetter(charArray[i])) {

                // check space is present before the letter
                if (foundSpace) {

                    // change the letter into uppercase
                    charArray[i] = Character.toUpperCase(charArray[i]);
                    foundSpace = false;
                }
            } else {
                // if the new character is not character
                foundSpace = true;
            }
        }

        // convert the char array to the string
        message = String.valueOf(charArray);
        return message;
    }

    private void updateStats(ItemStack item)
    {
        // ENCHANTMENTS
        addEnchantsToItem(item);
    }

    private static ItemStack updateStatsEntity(ItemStack item)
    {
        addEnchantsToItem(item);
        return item;
    }


    public static void addEnchantsToItem(ItemStack item)
    {
        // RARITY BONUS
        double rarityBonus = (Stats.getRarity(item) * 1) + 1;
        // ENCHANTMENTS
        int sharpnessLevel = item.getEnchantmentLevel(Enchantment.SHARPNESS);
        int powerLevel = item.getEnchantmentLevel(Enchantment.POWER);

        int protectionLevel = item.getEnchantmentLevel(Enchantment.PROTECTION);

        // MELEE
        if ((item.getType() != Material.BOW && item.getType() != Material.CROSSBOW) && Stats.getBaseDamageStat(item) != 0)
        {
            double bonus = 5 * sharpnessLevel * rarityBonus;
            Stats.setDamageStat(item, (int) Math.ceil(Stats.getBaseDamageStat(item) + bonus));
        }

        // BOWS
        if (item.getType() == Material.BOW || item.getType() == Material.CROSSBOW && Stats.getBaseRangedDamageStat(item) != 0)
        {
            double bonus = 5 * powerLevel * rarityBonus;
            Stats.setRangedDamageStat(item, (int) Math.ceil(Stats.getBaseRangedDamageStat(item) + bonus));
        }

        // ARMOR
        if (Stats.getBaseDefenseStat(item) != 0) {
            double bonus = 5 * protectionLevel * rarityBonus;
            Stats.setDefenseStat(item, (int) Math.ceil(Stats.getBaseDefenseStat(item) + bonus));
        }

        if (Stats.getBaseHealthStat(item) != 0)
        {
            Stats.setHealthStat(item, Stats.getBaseHealthStat(item));
        }

        if (Stats.getBaseCritStat(item) != 0)
        {
            Stats.setCritStat(item, Stats.getBaseCritStat(item));
        }

        // MAGIC
        if (Stats.getBaseMagicDamageStat(item) != 0)
        {
            double bonus = 1 + (0.1 * sharpnessLevel * rarityBonus);
            Stats.setMagicDamageStat(item, (int) Math.ceil(Stats.getBaseMagicDamageStat(item) * bonus));
        }
        if (Stats.getBaseMaxManaStat(item) != 0)
        {
            Stats.setMaxManaStat(item, Stats.getBaseMaxManaStat(item));
        }

        // ATTACK SPEED
        if (Stats.getBaseAttackSpeedStat(item) != 0)
        {
            Stats.setAttackSpeedStat(item, Stats.getBaseAttackSpeedStat(item));
        }

        // SPEED
        if (Stats.getBaseSpeedStat(item) != 0)
        {
            Stats.setSpeedStat(item, Stats.getBaseSpeedStat(item));
        }

        // POWER
        if (Stats.getBasePowerStat(item) != 0) {
            Stats.setPowerStat(item, Stats.getBasePowerStat(item));
        }
    }
}
