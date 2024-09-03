package me.itzkiba.pluginMST.listeners.healthbars;

import me.itzkiba.pluginMST.PluginMST;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
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

        Location l = entity.getLocation().add(2*(Math.random() - 0.5), 0.25*Math.random() + 0.85, 2*(Math.random() - 0.5));
        TextDisplay indicator = l.getWorld().spawn(l, TextDisplay.class, text -> {
            if (!isCritical)
                text.text(Component.text(damageCalc, TextColor.color(220, 220, 220)));
            else
                text.text(Component.text(damageCalc, TextColor.color(255, 220, 0)).decorate(TextDecoration.BOLD));
            text.setBillboard(Display.Billboard.CENTER);
        });


        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMST.getPlugin(), () -> {
            indicator.remove();
        }, 20);
    }
}
