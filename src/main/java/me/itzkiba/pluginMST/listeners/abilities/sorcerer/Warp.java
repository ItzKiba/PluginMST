package me.itzkiba.pluginMST.listeners.abilities.sorcerer;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.helperfunctions.ParticleRay;
import me.itzkiba.pluginMST.listeners.abilities.Abilities;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Classes;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import me.itzkiba.pluginMST.listeners.playerdamage.AttackCooldown;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class Warp implements Listener {

    private long cooldown = 25;
    private HashMap<UUID, Long> cooldownMap = new HashMap<>();

    private long manaCost = 40;

    @EventHandler
    public void Use(PlayerInteractEvent e)
    {
        if (!e.getAction().isRightClick())
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

        if (id != 3002)
        {
            return;
        }

        if (Classes.getPlayerClass(player) != 3)
        {
            player.sendMessage(ChatColor.RED + "You must be a Sorcerer to use this ability.");
            return;
        }

        if (cooldownMap.containsKey(player.getUniqueId()) && System.currentTimeMillis() - cooldownMap.get(player.getUniqueId()) <= cooldown) {
            return;
        }

        cooldownMap.put(player.getUniqueId(), System.currentTimeMillis());

        if (Stats.getEntityManaStat(player) < manaCost)
        {
            return;
        }
        Stats.setEntityManaStat(player, Stats.getEntityManaStat(player) - manaCost);

        Random random = new Random();
        double multiplier = (0.50 + (level * 0.03));
        double maxDistance = 8;
        Vector facing = player.getEyeLocation().getDirection().multiply(0.75).setY(0.25);

        Location origLocation = player.getLocation().add(0, 1, 0);
        Location tpLocation = player.getLocation();

        player.getWorld().playSound(origLocation, Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 0.7F, 1F + random.nextFloat(0.3F));

        for (double i = maxDistance; i > 0; i -= 0.25)
        {
            RayTraceResult teleportRayTrace = player.getWorld().rayTraceBlocks(player.getEyeLocation(), player.getEyeLocation().getDirection(), i, FluidCollisionMode.NEVER, true);
            if (teleportRayTrace == null)
            {
                tpLocation = player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(i)).toCenterLocation();
                player.teleport(tpLocation);
                Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMST.getPlugin(), () -> {
                    player.setVelocity(facing);
                }, 1);

                break;
            }
        }

        Location particleLocation = tpLocation;

        new BukkitRunnable()
        {
            int i = 0;
            public void run()
            {
                if (i < 8) {
                    ParticleRay.createCircle(particleLocation, i / 2.0, 32, Particle.CRIT);
                }

                i++;

                if (i == 8)
                {
                    this.cancel();
                }
            }
        }.runTaskTimer(PluginMST.getPlugin(), 0, 1);

        tpLocation.add(0, 1, 0);
        ParticleRay.particleRay2Points(origLocation, tpLocation, 50, Particle.WITCH, 0.1);
        ParticleRay.particleRay2Points(origLocation, tpLocation, 10, Particle.END_ROD, 0.025);


        double damage = Stats.getEntityMaxManaStat(player) * multiplier;

        Collection<LivingEntity> nearbyEntities = tpLocation.getNearbyLivingEntities(3.5);
        for (LivingEntity entity : nearbyEntities)
        {
            if (entity instanceof Player)
            {
                continue;
            }
            player.getWorld().playSound(origLocation, Sound.ITEM_FLINTANDSTEEL_USE, 0.9F, 0.5F + random.nextFloat(0.15F));
            AttackCooldown.setIgnoreCooldown(player, true);
            entity.damage(damage, player);
            AttackCooldown.setIgnoreCooldown(player, false);
        }

    }
}
