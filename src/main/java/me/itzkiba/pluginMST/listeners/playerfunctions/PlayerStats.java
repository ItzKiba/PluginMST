package me.itzkiba.pluginMST.listeners.playerfunctions;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.listeners.abilities.melee.HeavyGuard;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Classes;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerStats implements Listener {

    @EventHandler
    public void setPlayerStats(ServerLoadEvent e) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginMST.getPlugin(), () -> {

            for (World world : Bukkit.getServer().getWorlds()) {
                for (Player player : world.getPlayers()) {

                    setStats(player);

                }
            }

        }, 1, 2);
    }

    @EventHandler
    public void setEntityStats(EntitySpawnEvent e)
    {
        if (!(e.getEntity() instanceof LivingEntity))
        {
            return;
        }
        LivingEntity entity = (LivingEntity) e.getEntity();
        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMST.getPlugin(), () -> {
            setStats(entity);
        }, 5);
    }

    public static void setStats(LivingEntity player)
    {

        // WHEN ADDING STATS, REMEMBER TO:
        // 1. ADD THE ITEM STAT TO INT
        // 2. ADD THE ITEM STAT TO CANCEL
        // 3. CHECK FOR ARMOR TOO

        int itemDamage = Stats.getDamageStat(player.getEquipment().getItemInMainHand());
        int itemRangedDamage = Stats.getRangedDamageStat(player.getEquipment().getItemInMainHand());
        int itemMagicDamage = Stats.getMagicDamageStat(player.getEquipment().getItemInMainHand());
        int itemMaxMana = Stats.getMaxManaStat(player.getEquipment().getItemInMainHand());
        int itemCrit = Stats.getCritStat(player.getEquipment().getItemInMainHand());
        int itemAttackSpeed = Stats.getAttackSpeedStat(player.getEquipment().getItemInMainHand());
        int itemSpeed = Stats.getSpeedStat(player.getEquipment().getItemInMainHand());
        int itemPower = Stats.getPowerStat(player.getEquipment().getItemInMainHand());

        if (player instanceof Player && Levels.getPlayerLevel((Player)player) < Stats.getItemLevel(player.getEquipment().getItemInMainHand()))
        {
            itemDamage = 0;
            itemRangedDamage = 0;
            itemMagicDamage = 0;
            itemMaxMana = 0;
            itemCrit = 0;
            itemAttackSpeed = 0;
            itemSpeed = 0;
            itemPower = 0;
        }

        // ARMOR
        int itemDefense = 0;
        int itemHealth = 0;

        if (player instanceof Player)
        {
            itemCrit += 50;

            // set attack speed to a high number so there's no attack cooldown
            Player p = (Player) player;
        }
        for (ItemStack i : player.getEquipment().getArmorContents())
        {
            if (player instanceof Player)
            {
                Player p = (Player) player;
                if (Levels.getPlayerLevel(p) < Stats.getItemLevel(i))
                {
                    continue;
                }
            }
            itemDefense += Stats.getDefenseStat(i);
            itemHealth += Stats.getHealthStat(i);
            itemCrit += Stats.getCritStat(i);
            itemMaxMana += Stats.getMaxManaStat(i);
            itemSpeed += Stats.getSpeedStat(i);
            itemPower += Stats.getPowerStat(i);
        }

        int baseDamageValue = 5;
        if (player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE) != null)
        {
            baseDamageValue += (int)player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue();
        }

        double mageMultiplier = 1;

        if (player instanceof Player)
        {
            baseDamageValue = 5;
            if (Classes.getPlayerClass((Player) player) == 3)
            {
                itemMaxMana += 100;
                mageMultiplier = 1 + ((Stats.getEntityMaxManaStat((Player) player) / 5F) * 0.01);
            }
        }

        double powerMultiplier = 1;
        powerMultiplier += itemPower / 100.0;

        double multiplier = 1;
        if (!(player instanceof Player) && !(player instanceof ArmorStand))
        {
            multiplier = Stats.getLevelMultiplier(Stats.getEntityLevel(player));
            Stats.setEntityDefenseStat(player, (int)(itemDefense * multiplier));
        }

        Stats.setEntityDamageStat(player, (int)((itemDamage + baseDamageValue) * multiplier * powerMultiplier));
        Stats.setEntityRangedDamageStat(player, (int)(itemRangedDamage * multiplier * powerMultiplier));
        Stats.setEntityHealthStat(player, (int)(itemHealth * multiplier));
        Stats.setEntityCritStat(player, itemCrit);
        Stats.setEntityMagicDamageStat(player, (int)(itemMagicDamage * mageMultiplier * powerMultiplier));
        Stats.setEntityMaxManaStat(player, (int)itemMaxMana);
        Stats.setEntityAttackSpeedStat(player, Math.min((int)itemAttackSpeed, 100));
        Stats.setEntitySpeedStat(player, (int)itemSpeed);
        Stats.setEntityPowerStat(player, (int)itemPower);

        if (player instanceof Player &&
        !HeavyGuard.getHeavyGuardState((Player)player))
        {
            Stats.setEntityDefenseStat(player, (int)(itemDefense * multiplier));
        }

        if (player instanceof Player)
        {
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue((int)(100 + itemHealth) / 5.0);
            player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1 + (itemSpeed * 0.001));
            Player p = (Player) player;
            p.setHealthScale(Math.min(player.getMaxHealth(), 40));

            if (player.getHealth() - player.getAbsorptionAmount() > player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue())
            {
                p.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            }
        }

    }
}
