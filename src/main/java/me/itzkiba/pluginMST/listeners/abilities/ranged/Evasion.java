package me.itzkiba.pluginMST.listeners.abilities.ranged;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.helperfunctions.ParticleRay;
import me.itzkiba.pluginMST.listeners.abilities.Abilities;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Classes;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import me.itzkiba.pluginMST.listeners.playerdamage.AttackCooldown;
import org.bukkit.*;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class Evasion implements Listener {

    private long cooldown = 6000;
    private HashMap<UUID, Long> cooldownMap = new HashMap<>();

    @EventHandler
    public void Use(PlayerInteractEvent e)
    {
        if (!e.getAction().isLeftClick())
        {
            return;
        }

        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        int level = Stats.getItemLevel(item);
        int id = Abilities.getItemAbilityID(item);

        if (Levels.getPlayerLevel(player) < level)
        {
            return;
        }

        if (id != 2003)
        {
            return;
        }

        if (!player.isSneaking())
        {
            return;
        }

        if (Classes.getPlayerClass(player) != 2)
        {
            player.sendMessage(ChatColor.RED + "You must be an Archer to use this ability.");
            return;
        }

        if (cooldownMap.containsKey(player.getUniqueId()) && System.currentTimeMillis() - cooldownMap.get(player.getUniqueId()) <= cooldown) {
            return;
        }

        cooldownMap.put(player.getUniqueId(), System.currentTimeMillis());
        Random random = new Random();
        int duration = (int)((4 + (level * 0.1)) * 20);
        PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, duration, 1, false, true, true);
        PotionEffect slowfall = new PotionEffect(PotionEffectType.SLOW_FALLING, 60, 0, false, true, true);
        Location origLocation = player.getLocation();

        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMST.getPlugin(), () -> {

            player.sendTitle("", ChatColor.GREEN + "Evasion ready", 2, 20, 2);
            player.playSound(player, Sound.UI_BUTTON_CLICK, 0.8F, 1.5F);

        }, cooldown / 50);

        new BukkitRunnable()
        {
            int i = 0;
            public void run()
            {

                if (i == 0) {
                    player.setVelocity(player.getEyeLocation().getDirection().multiply(-1).setY(0.5).multiply(2.5));
                    player.addPotionEffect(slowfall);
                    Collection<LivingEntity> nearbyEntities = player.getLocation().getNearbyLivingEntities(8);
                    for (LivingEntity entity : nearbyEntities)
                    {
                        if (entity instanceof Player)
                        {
                            entity.addPotionEffect(speed);
                            continue;
                        }
                        Vector direction = entity.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
                        direction.setY(0.5);
                        entity.setVelocity(direction.multiply(2));
                    }
                }

                if (i < 8) {
                    ParticleRay.createCircle(origLocation, i, 24, Particle.CLOUD);
                    ParticleRay.createCircle(origLocation, i, 24, Particle.END_ROD);
                }

                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.8F, 1F + (i * 0.1F));

                i++;

                if (i == 10)
                {
                    this.cancel();
                }
            }
        }.runTaskTimer(PluginMST.getPlugin(), 0, 1);
    }
}
