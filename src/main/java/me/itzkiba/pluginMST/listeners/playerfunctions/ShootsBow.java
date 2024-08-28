package me.itzkiba.pluginMST.listeners.playerfunctions;

import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class ShootsBow implements Listener {

    @EventHandler
    public void firingArrowForPlayer(EntityShootBowEvent e)
    {
        if (!(e.getProjectile() instanceof Arrow)) {
            return;
        }

        double power = e.getForce();

        Arrow arrow = (Arrow) e.getProjectile();
        Stats.setEntityRangedDamageStat(arrow, (int)(Stats.getEntityRangedDamageStat(e.getEntity()) * power));

        if (e.getEntity() instanceof Player) {
            Stats.setEntityCritStat(arrow, Stats.getEntityCritStat(e.getEntity()));

        }
    }
}
