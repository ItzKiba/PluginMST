package me.itzkiba.pluginMST.listeners.entitybars;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.helperfunctions.RandomNumber;
import me.itzkiba.pluginMST.listeners.itemconverts.ConvertDefaultItems;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.awt.*;
import java.util.Random;

public class SetEntityLevel implements Listener {

    static NamespacedKey DETECTED = new NamespacedKey(PluginMST.getPlugin(), "detected");

    @EventHandler
    public void setLevelOnEntitySpawn(EntitySpawnEvent e)
    {
        if (!(e.getEntity() instanceof LivingEntity))
        {
            return;
        }

        LivingEntity entity = (LivingEntity) e.getEntity();

        if (entity instanceof ArmorStand)
        {
            return;
        }

        if (!getDetected(entity))
        {
            setDetected(entity, true);
            updateEntity(entity);
            ShowEntityHealthBar.spawnEvent(entity);
            ConvertDefaultItems.spawnEvent(entity);
        }

    }

    @EventHandler
    public void updateEntitiesUnknown(ServerLoadEvent e)
    {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginMST.getPlugin(), () -> {

            for (World world : Bukkit.getServer().getWorlds()) {
                for (LivingEntity entity : world.getLivingEntities()) {

                    if (entity instanceof Player || entity instanceof ArmorStand || entity instanceof Projectile)
                    {
                        continue;
                    }
                    if (!getDetected(entity))
                    {
                        setDetected(entity, true);
                        updateEntity(entity);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMST.getPlugin(), ()-> {
                            ShowEntityHealthBar.spawnEvent(entity);
                            ConvertDefaultItems.spawnEvent(entity);
                        }, 2);

                    }

                }
            }

        }, 1, 10);
    }


    private void updateEntity(LivingEntity entity)
    {
        if (entity instanceof ArmorStand)
        {
            return;
        }

        World.Environment dimension = entity.getWorld().getEnvironment();
        int randomRange;
        int moblevel = 0;

        switch(dimension)
        {
            case NETHER:
                randomRange = RandomNumber.generateInt(9500, 9930);
                break;
            case THE_END:
                randomRange = RandomNumber.generateInt(9930, 9950);
                break;
            default:
                randomRange = RandomNumber.generateInt(1, 9500);
                break;
        }

        RandomNumber.generateInt(1, 10000);

        // Level 1-10
        if (randomRange < 7500) {
            moblevel = RandomNumber.generateInt(1, 10);
        }

        // Level 11-20
        if (randomRange >= 7500 && randomRange < 9000) {
            moblevel = RandomNumber.generateInt(11, 20);
        }

        // Level 21-30
        if (randomRange >= 9000 && randomRange < 9500) {
            moblevel = RandomNumber.generateInt(21, 30);
        }

        // Level 31-40
        if (randomRange >= 9500 && randomRange < 9750) {
            moblevel = RandomNumber.generateInt(31, 40);
        }

        // Level 41-50
        if (randomRange >= 9750 && randomRange < 9875) {
            moblevel = RandomNumber.generateInt(41, 50);
        }

        // Level 51-60
        if (randomRange >= 9875 && randomRange < 9930) {
            moblevel = RandomNumber.generateInt(51, 60);
        }

        // Level 61-70
        if (randomRange >= 9930 && randomRange < 10000) {
            moblevel = RandomNumber.generateInt(61, 70);
        }

        int distanceDivisor = 500;
        int startingDistance = 1000;
        if (dimension == World.Environment.THE_END)
        {
            double distance = entity.getLocation().distance(new Location(entity.getWorld(), 0, entity.getLocation().getY(), 0)); // distance from world origin

            if (distance > startingDistance) {
                distance -= startingDistance;
                int levelBonus = 0;
                while (distance > distanceDivisor) {
                    levelBonus += 4;
                    distance -= distanceDivisor;
                }

                moblevel = RandomNumber.generateInt(71 + levelBonus, 80 + levelBonus);
            }
        }

        if (entity.getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER)
        {
            moblevel = 0;
        }

        if (entity.getType() == EntityType.WITHER)
        {
            moblevel = 75;
        }

        if (entity.getType() == EntityType.ENDER_DRAGON)
        {
            moblevel = 100;
        }

        if (entity.getType() == EntityType.WARDEN)
        {
            moblevel = 60;
        }

        int mobTestLevel = overrideLevel(entity);
        if (mobTestLevel != -1) {
            moblevel = mobTestLevel;
        }

        Stats.setEntityLevel(entity, moblevel);

        double multiplier = Stats.getLevelMultiplier(moblevel);
        if (moblevel == 0)
        {
            multiplier = 1;
        }

        double healthMultiplier = multiplier;
        double hpMultValue = (moblevel / 20.0) + 1;

        healthMultiplier *= hpMultValue;

        Stats.setEntityLevel(entity, moblevel);
        Stats.setEntityHealthStat(entity, (int)(entity.getMaxHealth() * healthMultiplier * 5));
        Stats.setEntityRangedDamageStat(entity, (int) (Stats.getEntityRangedDamageStat(entity) * multiplier * 5));

        double finalHealthMultiplier = healthMultiplier;
        Bukkit.getScheduler().runTaskLaterAsynchronously(PluginMST.getPlugin(), () -> {
            entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue((int)(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * finalHealthMultiplier));
            entity.setMaxHealth((int)(Stats.getEntityHealthStat(entity) / 5.0));
        }, 1);

//        //Stats.setEntityDamageStat(entity, (int)(entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() * multiplier * 5));
//        entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue((int)(entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() * multiplier));

        entity.setCustomNameVisible(true);

//        if (!(entity instanceof Villager) && !(entity instanceof Wolf) && !(entity instanceof Cat)) {
//            entity.setPersistent(false);
//        }
//        else
//        {
//            entity.setPersistent(true);
//        }
    }

    private boolean getDetected(LivingEntity e)
    {
        if (!e.getPersistentDataContainer().has(DETECTED, PersistentDataType.INTEGER))
        {
            return false;
        }

        if (e.getPersistentDataContainer().get(DETECTED, PersistentDataType.INTEGER) == 0)
        {
            return false;
        }

        return true;

    }

    private void setDetected(LivingEntity e, boolean value)
    {
        int keyValue = 0;
        if (value) {
            keyValue = 1;
        }
        e.getPersistentDataContainer().set(DETECTED, PersistentDataType.INTEGER, keyValue);
    }

    private int overrideLevel(LivingEntity e) {
        String name = e.getCustomName();
        if (name == null) {
            return -1;
        }
        if (!name.contains("<lvl")) {
            return -1;
        }
        int firstIndex = name.indexOf("<lvl");
        int lastIndex = name.indexOf('>');
        String levelString = name.substring(firstIndex + 4, lastIndex);
        String newNameString = name.substring(0, firstIndex) + name.substring(lastIndex + 1);

        int newLevel = Integer.parseInt(levelString);
        e.setCustomName(newNameString);
        Stats.setEntityOriginalName(e, newNameString);
        return newLevel;
    }

}
