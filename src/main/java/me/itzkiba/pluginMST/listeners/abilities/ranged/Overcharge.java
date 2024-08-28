package me.itzkiba.pluginMST.listeners.abilities.ranged;

import com.destroystokyo.paper.ParticleBuilder;
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
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

public class Overcharge implements Listener {

    private long cooldown = 3000;
    private HashMap<UUID, Long> cooldownMap = new HashMap<>();
    //private final float arrowBloom = 10F;

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

        if (id != 2005)
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
        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMST.getPlugin(), () -> {

            player.sendTitle("", ChatColor.GREEN + "Overcharge ready", 2, 20, 2);
            player.playSound(player, Sound.UI_BUTTON_CLICK, 0.8F, 1.5F);

        }, cooldown / 50);
        player.getWorld().playSound(player, Sound.ITEM_FLINTANDSTEEL_USE, 0.9f, 0.5f);
        int beamLength = 128;

        Vector direction = player.getEyeLocation().getDirection().normalize();
        Location startPosition = player.getEyeLocation().add(direction);

        RayTraceResult rayTrace = player.getWorld()
                .rayTrace(startPosition, direction, beamLength, FluidCollisionMode.NEVER, true, 0.5,
                        test -> !player.getUniqueId().equals(test.getUniqueId()) && !(test instanceof ArmorStand) && test instanceof LivingEntity);
        double rayTraceLength;
        Location endPosition = player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(beamLength));
        if (rayTrace == null)
        {
            rayTraceLength = beamLength;
        }
        else
        {
            endPosition = rayTrace.getHitPosition().toLocation(player.getWorld());
            if (rayTrace.getHitBlock() != null)
            {
                rayTraceLength = rayTrace.getHitPosition().toLocation(player.getWorld()).distance(startPosition);
            }
            else if (rayTrace.getHitEntity() instanceof LivingEntity)
            {
                LivingEntity victim = (LivingEntity) rayTrace.getHitEntity();

                double multiplier = (0.9 + (level * (startPosition.distance(endPosition)) * 0.001)) * (1 + (Stats.getEntityCritStat(player) / 100.0));
                double damage = (Stats.getEntityRangedDamageStat(player)) * multiplier;


                AttackCooldown.setIgnoreCooldown(player, true);
                victim.damage(damage, player);
                AttackCooldown.setIgnoreCooldown(player, false);

                player.getWorld().playSound(player, Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 0.6f, 1.8f);
                rayTraceLength = rayTrace.getHitPosition().toLocation(player.getWorld()).distance(startPosition);
            }
            else
            {
                rayTraceLength = beamLength;
            }
        }
        int numParticles = (int)(startPosition.distance(endPosition)) * 3;
        startPosition.add(0, -0.25, 0);
        Particle.DustOptions options = new Particle.DustOptions(Color.RED, 1);
        Particle.DustOptions options2 = new Particle.DustOptions(Color.ORANGE, 0.75F);
        ParticleRay.particleRay2PointsDust(startPosition, endPosition, numParticles, options, true);
        ParticleRay.particleRay2PointsDust(startPosition, endPosition, numParticles, options2, true);
        ParticleRay.particleRay2Points(startPosition, endPosition, numParticles, Particle.DRIPPING_LAVA,  0, true);
    }
}
