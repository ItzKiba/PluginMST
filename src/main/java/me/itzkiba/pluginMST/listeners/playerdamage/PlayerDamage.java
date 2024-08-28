package me.itzkiba.pluginMST.listeners.playerdamage;

import me.itzkiba.pluginMST.listeners.healthbars.DamageIndicators;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerDamage implements Listener {

    @EventHandler
    public void setAttackSpeedOnPlayerJoin(PlayerJoinEvent e)
    {
        e.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(25);
    }

    // BOW
    @EventHandler
    public void onBowDamageDealt(EntityDamageByEntityEvent e)
    {
        if (!(e.getDamager() instanceof Arrow)) {
            return;
        }

        if (!(e.getEntity() instanceof LivingEntity))
        {
            return;
        }

        LivingEntity v = (LivingEntity) e.getEntity();
        Arrow arrow = (Arrow) e.getDamager();

        double damage = (Stats.getEntityRangedDamageStat(arrow) / 5.0);

        boolean isCritical = false;
        if (e.isCritical())
        {
            isCritical = true;
            damage *= 1 + (Stats.getEntityCritStat(arrow) / 100.0);
        }
        e.setDamage(damage);
        DamageIndicators.spawnIndicator(v, e.getFinalDamage(), isCritical);
    }
}
