package me.itzkiba.pluginMST.listeners.abilities.ranged;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.helperfunctions.ParticleRay;
import me.itzkiba.pluginMST.listeners.abilities.Abilities;
import me.itzkiba.pluginMST.listeners.healthbars.DamageIndicators;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Classes;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import me.itzkiba.pluginMST.listeners.playerdamage.AttackCooldown;
import me.itzkiba.pluginMST.listeners.playerdamage.DamageReduction;
import org.bukkit.*;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class SculkBlast implements Listener {

    private long cooldown = 7000;
    private HashMap<UUID, Long> cooldownMap = new HashMap<>();
    private final float arrowBloom = 10F;

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

        if (id != 2002)
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
        double multiplier = 3 + ((level) * 0.2);
        double damageOriginal = (int)(Stats.getEntityCritStat(player));

        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMST.getPlugin(), () -> {

            player.sendTitle("", ChatColor.GREEN + "Sculk Blast ready", 2, 20, 2);
            player.playSound(player, Sound.UI_BUTTON_CLICK, 0.8F, 1.5F);

        }, cooldown / 50);

        player.setVelocity(player.getEyeLocation().getDirection().multiply(-1));

        new BukkitRunnable()
        {
            int i = 0;
            public void run()
            {

                if (i < 3) {
                    Arrow arrow = player.getWorld().spawnArrow(player.getEyeLocation(), player.getEyeLocation().getDirection().rotateAroundY(Math.toRadians(arrowBloom - i * arrowBloom)), 2.5F, 0);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WARDEN_SONIC_BOOM, 0.7F, 1F + random.nextFloat(0.1F));

                    arrow.setShooter(player);
                    arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                    arrow.setKnockbackStrength(0);
                    arrow.setMetadata("destroyArrow", new FixedMetadataValue(PluginMST.getPlugin(), "destroyArrow"));
                    arrow.setMetadata("sculkArrow", new FixedMetadataValue(PluginMST.getPlugin(), "sculkArrow"));
                    //arrow.setGravity(false);
                    Stats.setEntityRangedDamageStat(arrow, (int)(damageOriginal * multiplier));

                    Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMST.getPlugin(), () -> {
                        arrow.remove();
                    }, 50);
                }
                i++;

                if (i == 50)
                {
                    this.cancel();
                }
            }
        }.runTaskTimer(PluginMST.getPlugin(), 0, 1);
    }

    @EventHandler
    public void sculkArrowParticles(ServerLoadEvent e)
    {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginMST.getPlugin(), () -> {
            for (World world : Bukkit.getServer().getWorlds())
            {
                for (Arrow arrow : world.getEntitiesByClass(Arrow.class))
                {
                    if (arrow.hasMetadata("sculkArrow"))
                    {
                        arrow.getWorld().spawnParticle(Particle.EXPLOSION,  arrow.getLocation(), 1, 0, 0, 0, 0, null,true);
                        arrow.getWorld().spawnParticle(Particle.SONIC_BOOM,  arrow.getLocation(), 1, 0, 0, 0, 0, null,true);
                        arrow.getWorld().spawnParticle(Particle.SCULK_SOUL, arrow.getLocation(), 1, 0, 0, 0, 0.2, null, true);
                    }
                }
            }
        }, 1, 1);
    }

    @EventHandler
    public void ActivateSculkArrows(ProjectileHitEvent e)
    {
        if (!(e.getEntity() instanceof Arrow))
        {
            return;
        }

        Arrow arrow = (Arrow)e.getEntity();

        if (!arrow.hasMetadata("sculkArrow"))
        {
            return;
        }

        if (!(arrow.getShooter() instanceof Player))
        {
            return;
        }

        arrow.getWorld().spawnParticle(Particle.END_ROD, arrow.getLocation(), 40, 0, 0, 0, 0.5);

        Player player = (Player)arrow.getShooter();

        Collection<LivingEntity> nearbyEntities = arrow.getLocation().getNearbyLivingEntities(5);
        for (LivingEntity entity : nearbyEntities)
        {
            if (entity instanceof Player)
            {
                continue;
            }

            AttackCooldown.setIgnoreCooldown(player, true);
            entity.damage(Stats.getEntityRangedDamageStat(arrow), player);
            AttackCooldown.setIgnoreCooldown(player, false);
        }

        arrow.remove();
    }
}
