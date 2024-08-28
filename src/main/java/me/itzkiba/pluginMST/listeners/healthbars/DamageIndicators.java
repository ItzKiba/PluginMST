package me.itzkiba.pluginMST.listeners.healthbars;

import me.itzkiba.pluginMST.PluginMST;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageIndicators implements Listener {

    public static void spawnIndicator(LivingEntity entity, double damage, boolean isCritical)
    {
        if (entity instanceof ArmorStand)
        {
            return;
        }
        damage *= 5;
        int damageCalc = Math.max((int) damage, 1);

        Location l = entity.getLocation().add(2*(Math.random() - 0.5), 0.25*Math.random() + 0.85 - 5, 2*(Math.random() - 0.5));
        ArmorStand indicator = (ArmorStand) l.getWorld().spawn(l, ArmorStand.class);

        indicator.setInvisible(true);
        indicator.setCustomNameVisible(true);
        indicator.setMarker(true);
        indicator.setInvulnerable(true);
        indicator.setSmall(true);
        indicator.setBasePlate(false);
        indicator.teleport(l.add(0, 5, 0));

        if (!isCritical)
            indicator.setCustomName(ChatColor.GRAY + "" + damageCalc);
        else
            indicator.setCustomName(ChatColor.YELLOW + "" + ChatColor.BOLD + damageCalc);

        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMST.getPlugin(), () -> {
            indicator.remove();
        }, 20);
    }
}
