package me.itzkiba.pluginMST.listeners.entitybars;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class ShowEntityHealthBar implements Listener {


//    @EventHandler
//    public void createBarOnEntitySpawn(EntitySpawnEvent e)
//    {
//        if ((e.getEntity() instanceof Player))
//        {
//            return;
//        }
//        if (!(e.getEntity() instanceof LivingEntity))
//        {
//            return;
//        }
//        if (e.getEntity() instanceof ArmorStand)
//        {
//            return;
//        }
//
//        LivingEntity entity = (LivingEntity) e.getEntity();
//        int moblevel = Stats.getEntityLevel(entity);
//
//        entity.setMaxHealth((int)(Stats.getEntityHealthStat(entity) / 5.0));
//        entity.setHealth(entity.getMaxHealth());
//
//        Stats.setEntityOriginalName(entity, entity.getName());
//
//        Bukkit.getScheduler().runTaskLaterAsynchronously(PluginMST.getPlugin(), () -> {
//            setHealthBar(entity, moblevel);
//        }, 1);
//    }

    @EventHandler
    public void updateBarOnEntityDamage(EntityDamageEvent e)
    {
        if ((e.getEntity() instanceof Player))
        {
            return;
        }
        if (!(e.getEntity() instanceof LivingEntity))
        {
            return;
        }

        LivingEntity entity = (LivingEntity) e.getEntity();
        int moblevel = Stats.getEntityLevel(entity);

        entity.setCustomNameVisible(true);

        Bukkit.getScheduler().runTaskLaterAsynchronously(PluginMST.getPlugin(), () -> {
            setHealthBar(entity, moblevel);
        }, 1);
    }

    @EventHandler
    public void updateBarOnEntityDamage(EntityRegainHealthEvent e)
    {
        if ((e.getEntity() instanceof Player))
        {
            return;
        }
        if (!(e.getEntity() instanceof LivingEntity))
        {
            return;
        }

        LivingEntity entity = (LivingEntity) e.getEntity();
        int moblevel = Stats.getEntityLevel(entity);

        entity.setCustomNameVisible(true);

        Bukkit.getScheduler().runTaskLaterAsynchronously(PluginMST.getPlugin(), () -> {
            setHealthBar(entity, moblevel);
        }, 1);
    }



    private static void setHealthBar(LivingEntity entity, int moblevel)
    {

        ChatColor color = ChatColor.GREEN;
        if (entity.getHealth() / entity.getMaxHealth() < 0.66)
            color = ChatColor.YELLOW;
        if (entity.getHealth() / entity.getMaxHealth() < 0.33)
            color = ChatColor.RED;
        entity.setCustomName(ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "Lv." + ChatColor.GRAY + moblevel + ChatColor.DARK_GRAY + "] " + ChatColor.RED + Stats.getEntityOriginalName(entity) + "" + ChatColor.WHITE + " - " + color + (int)Math.ceil((entity.getHealth()) * 5) + ChatColor.RED + "\u2764");
    }

    public static void spawnEvent(LivingEntity entity)
    {
        int moblevel = Stats.getEntityLevel(entity);

        entity.setMaxHealth((int)(Stats.getEntityHealthStat(entity) / 5.0));
        entity.setHealth(entity.getMaxHealth());

        Stats.setEntityOriginalName(entity, entity.getName());

        Bukkit.getScheduler().runTaskLaterAsynchronously(PluginMST.getPlugin(), () -> {
            setHealthBar(entity, moblevel);
        }, 2);
    }
}
