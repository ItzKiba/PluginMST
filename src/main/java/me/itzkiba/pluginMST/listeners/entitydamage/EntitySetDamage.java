package me.itzkiba.pluginMST.listeners.entitydamage;

import me.itzkiba.pluginMST.helperfunctions.EntityUtility;
import me.itzkiba.pluginMST.listeners.healthbars.DamageIndicators;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.projectiles.ProjectileSource;

import java.util.List;

public class EntitySetDamage implements Listener {

    @EventHandler
    public void setEntityDamage(EntityDamageByEntityEvent e)
    {
        if (e.getDamager() instanceof Player || e.getDamager() instanceof Arrow)
        {
            return;
        }

        if (!(e.getEntity() instanceof LivingEntity))
        {
            return;
        }
        LivingEntity victim = (LivingEntity) e.getEntity();
        if (e.getDamager() instanceof LivingEntity)
        {
            LivingEntity damager = (LivingEntity) e.getDamager();
            e.setDamage(Stats.getEntityDamageStat(damager) / 5.0);
            DamageIndicators.spawnIndicator(victim, e.getFinalDamage(), false);
            return;
        }

        Entity damager = e.getDamager();

        double damage = Math.max(Stats.getEntityDamageStat(damager), Stats.getEntityRangedDamageStat(damager));

        e.setDamage(damage / 5.0);

        //DamageIndicators.spawnIndicator(victim, e.getFinalDamage(), false);
    }

    @EventHandler
    public void setProjectileDamageAmount(EntitySpawnEvent e)
    {
        if (e.getEntity() instanceof Projectile)
        {
            //if (!(EntityUtility.getClosestLivingEntity(e.getEntity().getLocation(), 3) instanceof Arrow))
            //{
                LivingEntity shooter = EntityUtility.getClosestLivingEntity(e.getEntity().getLocation(), 3);
                if (shooter != null) {
                    int damage = (int)(Math.max(Stats.getEntityDamageStat(shooter), 1 * Stats.getLevelMultiplier(Stats.getEntityLevel(shooter))));
                    Stats.setEntityDamageStat(e.getEntity(), damage);
                }
            //}
        }
    }
}
