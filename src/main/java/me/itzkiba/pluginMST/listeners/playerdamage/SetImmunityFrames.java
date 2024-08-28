package me.itzkiba.pluginMST.listeners.playerdamage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class SetImmunityFrames implements Listener {

    @EventHandler
    public void onDamageSetIFrames(EntityDamageEvent e)
    {
        if (!(e.getEntity() instanceof LivingEntity))
            return;
        if (e.getEntity() instanceof Player)
        {
            ((Player)e.getEntity()).setMaximumNoDamageTicks(5);
        }
        else
        {
            ((LivingEntity)e.getEntity()).setMaximumNoDamageTicks(0);
        }
    }
}
