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
        int defense = Stats.getEntityDefenseStat(v);
        e.setDamage(
                Math.max((e.getDamage() - damageSubtractionFromDefense(defense) / 5.0) * (1 - damageReductionFromDefense(defense)), 1)
        );

    }

    // DAMAGE REDUCTION FORMULA
    public static double damageReductionFromDefense(int defense)
    {
        return defense / (defense + 400.0);
    }

    public static double damageSubtractionFromDefense(int defense) {
        return defense / 10.0;
    }

}
