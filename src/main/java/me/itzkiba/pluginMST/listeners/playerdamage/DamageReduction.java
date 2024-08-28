package me.itzkiba.pluginMST.listeners.playerdamage;

import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageReduction implements Listener {


    // DAMAGE REDUCTION FOR DEFENSE
    @EventHandler
    public void onDamageTaken(EntityDamageByEntityEvent e)
    {
        if (!(e.getEntity() instanceof LivingEntity))
        {
            return;
        }

        LivingEntity v = (LivingEntity) e.getEntity();
        e.setDamage(e.getDamage() * (1 - damageReductionFromDefense(Stats.getEntityDefenseStat(v))));

    }

    // DAMAGE REDUCTION FORMULA
    public static double damageReductionFromDefense(int defense)
    {
        return (defense) / (defense + 100.0);
    }

}
