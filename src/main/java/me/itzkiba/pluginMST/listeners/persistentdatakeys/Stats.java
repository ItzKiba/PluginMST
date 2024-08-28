package me.itzkiba.pluginMST.listeners.persistentdatakeys;

import me.itzkiba.pluginMST.PluginMST;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.UUID;

public class Stats {

    // Entity Namespaces
    // Stats Namespaces

    // Items Namespaces

    public static NamespacedKey getArmorNamespace() {
        return new NamespacedKey(PluginMST.getPlugin(), "armor_namespace");
    }
    public static NamespacedKey getArmorToughNamespace() {
        return new NamespacedKey(PluginMST.getPlugin(), "armor_tough_namespace");
    }


    static NamespacedKey ITEM_LEVEL = new NamespacedKey(PluginMST.getPlugin(), "itemLevel");

    public static void setItemLevel(ItemStack item, int value)
    {
        if (item == null)
            return;

        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(ITEM_LEVEL, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getItemLevel(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(ITEM_LEVEL, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(ITEM_LEVEL, PersistentDataType.INTEGER);
    }










    static NamespacedKey ITEMNAME = new NamespacedKey(PluginMST.getPlugin(), "itemName");

    public static void setItemName(ItemStack item, String value)
    {
        if (item == null)
            return;

        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(ITEMNAME, PersistentDataType.STRING, value);
        item.setItemMeta(meta);
    }

    public static String getItemName(ItemStack item)
    {
        if (item == null)
            return "";
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return "";
        }
        if (!meta.getPersistentDataContainer().has(ITEMNAME, PersistentDataType.STRING))
            return "";
        return meta.getPersistentDataContainer().get(ITEMNAME, PersistentDataType.STRING);
    }









    static NamespacedKey RARITY = new NamespacedKey(PluginMST.getPlugin(), "rarity");

    public static void setRarity(ItemStack item, int value)
    {
        if (item == null)
            return;

        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(RARITY, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getRarity(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(RARITY, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(RARITY, PersistentDataType.INTEGER);
    }










    static NamespacedKey CUSTOM = new NamespacedKey(PluginMST.getPlugin(), "custom");

    public static boolean isCustom(ItemStack item)
    {
        ItemMeta meta = item.getItemMeta();
        if (meta == null)
        {
            return false;
        }
        if (!meta.getPersistentDataContainer().has(CUSTOM, PersistentDataType.INTEGER))
        {
            return false;
        }

        if (meta.getPersistentDataContainer().get(CUSTOM, PersistentDataType.INTEGER) == 0)
        {
            return false;
        }

        return true;

    }

    public static void setCustom(ItemStack item, boolean value)
    {
        if (item == null)
            return;
        int keyValue = 0;
        if (value) {
            keyValue = 1;
        }
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(CUSTOM, PersistentDataType.INTEGER, keyValue);
        item.setItemMeta(meta);
    }





    static NamespacedKey POWER = new NamespacedKey(PluginMST.getPlugin(), "powerStat");
    static NamespacedKey POWER_BASE = new NamespacedKey(PluginMST.getPlugin(), "powerStatBase");

    public static void setPowerStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(POWER, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getPowerStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(POWER, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(POWER, PersistentDataType.INTEGER);
    }

    public static void setBasePowerStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(POWER_BASE, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getBasePowerStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(POWER_BASE, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(POWER_BASE, PersistentDataType.INTEGER);
    }

    public static void setEntityPowerStat(Entity player, int value)
    {
        player.getPersistentDataContainer().set(POWER, PersistentDataType.INTEGER, value);
    }

    public static int getEntityPowerStat(Entity player)
    {
        if (!player.getPersistentDataContainer().has(POWER, PersistentDataType.INTEGER))
        {
            return 0;
        }
        return player.getPersistentDataContainer().get(POWER, PersistentDataType.INTEGER);
    }











    static NamespacedKey DAMAGE = new NamespacedKey(PluginMST.getPlugin(), "damageStat");
    static NamespacedKey DAMAGE_BASE = new NamespacedKey(PluginMST.getPlugin(), "damageStatBase");

    public static void setDamageStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(DAMAGE, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getDamageStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(DAMAGE, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(DAMAGE, PersistentDataType.INTEGER);
    }

    public static void setBaseDamageStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(DAMAGE_BASE, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getBaseDamageStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(DAMAGE_BASE, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(DAMAGE_BASE, PersistentDataType.INTEGER);
    }

    public static void setEntityDamageStat(Entity player, int value)
    {
        player.getPersistentDataContainer().set(DAMAGE, PersistentDataType.INTEGER, value);
    }

    public static int getEntityDamageStat(Entity player)
    {
        if (!player.getPersistentDataContainer().has(DAMAGE, PersistentDataType.INTEGER))
        {
            return 0;
        }
        return player.getPersistentDataContainer().get(DAMAGE, PersistentDataType.INTEGER);
    }














    static NamespacedKey HEALTH = new NamespacedKey(PluginMST.getPlugin(), "healthStat");
    static NamespacedKey HEALTH_BASE = new NamespacedKey(PluginMST.getPlugin(), "healthStatBase");

    public static void setHealthStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(HEALTH, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getHealthStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(HEALTH, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(HEALTH, PersistentDataType.INTEGER);
    }

    public static void setBaseHealthStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(HEALTH_BASE, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getBaseHealthStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(HEALTH_BASE, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(HEALTH_BASE, PersistentDataType.INTEGER);
    }

    public static void setEntityHealthStat(Entity player, int value)
    {
        player.getPersistentDataContainer().set(HEALTH, PersistentDataType.INTEGER, value);
    }

    public static int getEntityHealthStat(Entity player)
    {
        if (!player.getPersistentDataContainer().has(HEALTH, PersistentDataType.INTEGER))
        {
            return 0;
        }
        return player.getPersistentDataContainer().get(HEALTH, PersistentDataType.INTEGER);
    }












    static NamespacedKey DEFENSE = new NamespacedKey(PluginMST.getPlugin(), "defenseStat");
    static NamespacedKey DEFENSE_BASE = new NamespacedKey(PluginMST.getPlugin(), "defenseStatBase");

    public static void setDefenseStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(DEFENSE, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getDefenseStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(DEFENSE, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(DEFENSE, PersistentDataType.INTEGER);
    }

    public static void setBaseDefenseStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(DEFENSE_BASE, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getBaseDefenseStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(DEFENSE_BASE, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(DEFENSE_BASE, PersistentDataType.INTEGER);
    }

    public static void setEntityDefenseStat(Entity player, int value)
    {
        player.getPersistentDataContainer().set(DEFENSE, PersistentDataType.INTEGER, value);
    }

    public static int getEntityDefenseStat(Entity player)
    {
        if (!player.getPersistentDataContainer().has(DEFENSE, PersistentDataType.INTEGER))
        {
            return 0;
        }
        return player.getPersistentDataContainer().get(DEFENSE, PersistentDataType.INTEGER);
    }











    static NamespacedKey RANGED_DAMAGE = new NamespacedKey(PluginMST.getPlugin(), "rangedDamageStat");
    static NamespacedKey RANGED_DAMAGE_BASE = new NamespacedKey(PluginMST.getPlugin(), "rangedDamageStatBase");

    public static void setRangedDamageStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(RANGED_DAMAGE, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getRangedDamageStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(RANGED_DAMAGE, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(RANGED_DAMAGE, PersistentDataType.INTEGER);
    }

    public static void setBaseRangedDamageStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(RANGED_DAMAGE_BASE, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getBaseRangedDamageStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(RANGED_DAMAGE_BASE, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(RANGED_DAMAGE_BASE, PersistentDataType.INTEGER);
    }

    public static void setEntityRangedDamageStat(Entity player, int value)
    {
        player.getPersistentDataContainer().set(RANGED_DAMAGE, PersistentDataType.INTEGER, value);
    }

    public static int getEntityRangedDamageStat(Entity player)
    {
        if (!player.getPersistentDataContainer().has(RANGED_DAMAGE, PersistentDataType.INTEGER))
        {
            return 0;
        }
        return player.getPersistentDataContainer().get(RANGED_DAMAGE, PersistentDataType.INTEGER);
    }












    static NamespacedKey CRIT = new NamespacedKey(PluginMST.getPlugin(), "critStat");
    static NamespacedKey CRIT_BASE = new NamespacedKey(PluginMST.getPlugin(), "critStatBase");

    public static void setCritStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(CRIT, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getCritStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(CRIT, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(CRIT, PersistentDataType.INTEGER);
    }

    public static void setBaseCritStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(CRIT_BASE, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getBaseCritStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(CRIT_BASE, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(CRIT_BASE, PersistentDataType.INTEGER);
    }

    public static void setEntityCritStat(Entity player, int value)
    {
        player.getPersistentDataContainer().set(CRIT, PersistentDataType.INTEGER, value);
    }

    public static int getEntityCritStat(Entity player)
    {
        if (!player.getPersistentDataContainer().has(CRIT, PersistentDataType.INTEGER))
        {
            return 0;
        }
        return player.getPersistentDataContainer().get(CRIT, PersistentDataType.INTEGER);
    }














    static NamespacedKey MAGIC_DAMAGE = new NamespacedKey(PluginMST.getPlugin(), "magicDamageStat");
    static NamespacedKey MAGIC_DAMAGE_BASE = new NamespacedKey(PluginMST.getPlugin(), "magicDamageStatBase");

    public static void setMagicDamageStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(MAGIC_DAMAGE, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getMagicDamageStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(MAGIC_DAMAGE, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(MAGIC_DAMAGE, PersistentDataType.INTEGER);
    }

    public static void setBaseMagicDamageStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(MAGIC_DAMAGE_BASE, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getBaseMagicDamageStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(MAGIC_DAMAGE_BASE, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(MAGIC_DAMAGE_BASE, PersistentDataType.INTEGER);
    }

    public static void setEntityMagicDamageStat(Entity player, int value)
    {
        player.getPersistentDataContainer().set(MAGIC_DAMAGE, PersistentDataType.INTEGER, value);
    }

    public static int getEntityMagicDamageStat(Entity player)
    {
        if (!player.getPersistentDataContainer().has(MAGIC_DAMAGE, PersistentDataType.INTEGER))
        {
            return 0;
        }
        return player.getPersistentDataContainer().get(MAGIC_DAMAGE, PersistentDataType.INTEGER);
    }











    static NamespacedKey MANA = new NamespacedKey(PluginMST.getPlugin(), "manaStat");

    static NamespacedKey MAX_MANA = new NamespacedKey(PluginMST.getPlugin(), "maxManaStat");
    static NamespacedKey MAX_MANA_BASE = new NamespacedKey(PluginMST.getPlugin(), "maxManaStatBase");

    public static void setManaStat(ItemStack item, double value)
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
        meta.getPersistentDataContainer().set(MANA, PersistentDataType.DOUBLE, value);
        item.setItemMeta(meta);
    }

    public static double getManaStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(MANA, PersistentDataType.DOUBLE))
            return 0;
        return meta.getPersistentDataContainer().get(MANA, PersistentDataType.DOUBLE);
    }

    public static void setMaxManaStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(MAX_MANA, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getMaxManaStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(MAX_MANA, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(MAX_MANA, PersistentDataType.INTEGER);
    }

    public static void setEntityManaStat(Entity player, double value)
    {
        player.getPersistentDataContainer().set(MANA, PersistentDataType.DOUBLE, value);
    }

    public static double getEntityManaStat(Entity player)
    {
        if (!player.getPersistentDataContainer().has(MANA, PersistentDataType.DOUBLE))
        {
            return 0;
        }
        return player.getPersistentDataContainer().get(MANA, PersistentDataType.DOUBLE);
    }

    public static void setBaseMaxManaStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(MAX_MANA_BASE, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getBaseMaxManaStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(MAX_MANA_BASE, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(MAX_MANA_BASE, PersistentDataType.INTEGER);
    }

    public static void setEntityMaxManaStat(Entity player, int value)
    {
        player.getPersistentDataContainer().set(MAX_MANA, PersistentDataType.INTEGER, value);
    }

    public static int getEntityMaxManaStat(Entity player)
    {
        if (!player.getPersistentDataContainer().has(MAX_MANA, PersistentDataType.INTEGER))
        {
            return 0;
        }
        return player.getPersistentDataContainer().get(MAX_MANA, PersistentDataType.INTEGER);
    }

















    static NamespacedKey ATTACK_SPEED = new NamespacedKey(PluginMST.getPlugin(), "attackSpeedStat");
    static NamespacedKey ATTACK_SPEED_BASE = new NamespacedKey(PluginMST.getPlugin(), "attackSpeedStatBase");

    public static void setAttackSpeedStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(ATTACK_SPEED, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getAttackSpeedStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(ATTACK_SPEED, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(ATTACK_SPEED, PersistentDataType.INTEGER);
    }

    public static void setBaseAttackSpeedStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(ATTACK_SPEED_BASE, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getBaseAttackSpeedStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(ATTACK_SPEED_BASE, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(ATTACK_SPEED_BASE, PersistentDataType.INTEGER);
    }

    public static void setEntityAttackSpeedStat(Entity player, int value)
    {
        player.getPersistentDataContainer().set(ATTACK_SPEED, PersistentDataType.INTEGER, value);
    }

    public static int getEntityAttackSpeedStat(Entity player)
    {
        if (!player.getPersistentDataContainer().has(ATTACK_SPEED, PersistentDataType.INTEGER))
        {
            return 0;
        }
        return player.getPersistentDataContainer().get(ATTACK_SPEED, PersistentDataType.INTEGER);
    }














    static NamespacedKey SPEED = new NamespacedKey(PluginMST.getPlugin(), "speedStat");
    static NamespacedKey SPEED_BASE = new NamespacedKey(PluginMST.getPlugin(), "speedStatBase");

    public static void setSpeedStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(SPEED, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getSpeedStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(SPEED, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(SPEED, PersistentDataType.INTEGER);
    }

    public static void setBaseSpeedStat(ItemStack item, int value)
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
        meta.getPersistentDataContainer().set(SPEED_BASE, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getBaseSpeedStat(ItemStack item)
    {
        if (item == null)
            return 0;
        ItemMeta meta = item.getItemMeta();
        if (item.getItemMeta() == null)
        {
            return 0;
        }
        if (!meta.getPersistentDataContainer().has(SPEED_BASE, PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(SPEED_BASE, PersistentDataType.INTEGER);
    }

    public static void setEntitySpeedStat(Entity player, int value)
    {
        player.getPersistentDataContainer().set(SPEED, PersistentDataType.INTEGER, value);
    }

    public static int getEntitySpeedStat(Entity player)
    {
        if (!player.getPersistentDataContainer().has(SPEED, PersistentDataType.INTEGER))
        {
            return 0;
        }
        return player.getPersistentDataContainer().get(SPEED, PersistentDataType.INTEGER);
    }


    // PLAYERS

















    //////////////////////////////////////////////////
    // ENTITY STATS
    //////////////////////////////////////////////////

    public static double getLevelMultiplier(int level)
    {
        return (Math.pow(1.05, level-1) * 0.9) + 0.1;
    }












    static NamespacedKey ENTITY_LEVEL = new NamespacedKey(PluginMST.getPlugin(), "entityLevel");
    static NamespacedKey ENTITY_ORIGINAL_NAME = new NamespacedKey(PluginMST.getPlugin(), "entityOriginalName");

    public static void setEntityLevel(Entity player, int value)
    {
        player.getPersistentDataContainer().set(ENTITY_LEVEL, PersistentDataType.INTEGER, value);
    }
    public static int getEntityLevel(Entity player)
    {
        if (!player.getPersistentDataContainer().has(ENTITY_LEVEL, PersistentDataType.INTEGER))
        {
            return 0;
        }
        return player.getPersistentDataContainer().get(ENTITY_LEVEL, PersistentDataType.INTEGER);
    }

    public static void setEntityOriginalName(Entity player, String value)
    {
        player.getPersistentDataContainer().set(ENTITY_ORIGINAL_NAME, PersistentDataType.STRING, value);
    }
    public static String getEntityOriginalName(Entity player)
    {
        if (!player.getPersistentDataContainer().has(ENTITY_ORIGINAL_NAME, PersistentDataType.STRING))
        {
            return "UNKNOWN";
        }
        return player.getPersistentDataContainer().get(ENTITY_ORIGINAL_NAME, PersistentDataType.STRING);
    }



}
