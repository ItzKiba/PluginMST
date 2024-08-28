package me.itzkiba.pluginMST.listeners.abilities.sorcerer;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.helperfunctions.ParticleRay;
import me.itzkiba.pluginMST.listeners.abilities.Abilities;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Classes;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import me.itzkiba.pluginMST.listeners.playerdamage.AttackCooldown;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.*;

public class SoulSurge implements Listener {

    private long cooldown = 25;
    private HashMap<UUID, Long> cooldownMap = new HashMap<>();

    private long manaCost = 60;

    @EventHandler
    public void Use(PlayerInteractEvent e) {
        if (!e.getAction().isRightClick()) {
            return;
        }

        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        int level = Stats.getItemLevel(item);
        int id = Abilities.getItemAbilityID(item);

        if (Levels.getPlayerLevel(player) < level) {
            return;
        }

        if (id != 3003) {
            return;
        }

        if (Classes.getPlayerClass(player) != 3) {
            player.sendMessage(ChatColor.RED + "You must be a Sorcerer to use this ability.");
            return;
        }

        if (cooldownMap.containsKey(player.getUniqueId()) && System.currentTimeMillis() - cooldownMap.get(player.getUniqueId()) <= cooldown) {
            return;
        }

        cooldownMap.put(player.getUniqueId(), System.currentTimeMillis());

        if (Stats.getEntityManaStat(player) < manaCost) {
            return;
        }
        Stats.setEntityManaStat(player, Stats.getEntityManaStat(player) - manaCost);

        Random random = new Random();
        double multiplier = (2.50 + (level * 0.03));
        double damage = Stats.getEntityMagicDamageStat(player) * multiplier;
        double spread = 4;
        double dist = 3;

        Location l1 = player.getEyeLocation().add(0, -0.5, 0);
        Location l2 = player.getEyeLocation().add(player.getEyeLocation().getDirection()
                .multiply(dist + random.nextDouble(3))
                .add(new Vector(random.nextDouble(spread) - (spread * 0.5), random.nextDouble(spread) - (spread * 0.5), random.nextDouble(spread) - (spread * 0.5))));
        Location l3 = player.getEyeLocation().add(player.getEyeLocation().getDirection()
                .multiply(dist * 2 + random.nextDouble(3))
                .add(new Vector(random.nextDouble(spread) - (spread * 0.5), random.nextDouble(spread) - (spread * 0.5), random.nextDouble(spread) - (spread * 0.5))));
        Location l4 = player.getEyeLocation().add(player.getEyeLocation().getDirection()
                .multiply(dist * 3 + random.nextDouble(3))
                .add(new Vector(random.nextDouble(spread) - (spread * 0.5), random.nextDouble(spread) - (spread * 0.5), random.nextDouble(spread) - (spread * 0.5))));
        Location l5 = player.getEyeLocation().add(player.getEyeLocation().getDirection()
                .multiply(dist * 4 + random.nextDouble(3))
                .add(new Vector(random.nextDouble(spread) - (spread * 0.5), random.nextDouble(spread) - (spread * 0.5), random.nextDouble(spread) - (spread * 0.5))));
        Location l6 = player.getEyeLocation().add(player.getEyeLocation().getDirection()
                .multiply(dist * 5 + random.nextDouble(3))
                .add(new Vector(random.nextDouble(spread) - (spread * 0.5), random.nextDouble(spread) - (spread * 0.5), random.nextDouble(spread) - (spread * 0.5))));

        player.getWorld().spawnParticle(Particle.END_ROD, l6, 5, 0, 0, 0, 0.07);
        player.getWorld().playSound(l1, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.5F, 1.6F + random.nextFloat(0.4F));

        ArrayList<LivingEntity> alreadyHit = new ArrayList<LivingEntity>();

        ParticleRay.particleRay2Points(l1, l2, 20, Particle.SOUL_FIRE_FLAME, 0);
        alreadyHit = doDamage(l1, damage, player, alreadyHit);
        ParticleRay.particleRay2Points(l2, l3, 20, Particle.SOUL_FIRE_FLAME, 0);
        alreadyHit = doDamage(l2, damage, player, alreadyHit);
        ParticleRay.particleRay2Points(l3, l4, 20, Particle.SOUL_FIRE_FLAME, 0);
        alreadyHit = doDamage(l3, damage, player, alreadyHit);
        ParticleRay.particleRay2Points(l4, l5, 20, Particle.SOUL_FIRE_FLAME, 0);
        alreadyHit = doDamage(l4, damage, player, alreadyHit);
        ParticleRay.particleRay2Points(l5, l6, 20, Particle.SOUL_FIRE_FLAME, 0);
        alreadyHit = doDamage(l5, damage, player, alreadyHit);

    }

    private ArrayList<LivingEntity> doDamage(Location l, double damage, Player player, ArrayList<LivingEntity> alreadyHit)
    {
        Random random = new Random();
        Collection<LivingEntity> nearbyEntities = l.getNearbyLivingEntities(3.5);
        for (LivingEntity entity : nearbyEntities)
        {
            if (entity instanceof Player)
            {
                continue;
            }
            if (alreadyHit.contains(entity))
            {
                continue;
            }
            alreadyHit.add(entity);
            l.getWorld().playSound(l, Sound.ITEM_FLINTANDSTEEL_USE, 0.9F, 0.5F + random.nextFloat(0.15F));
            AttackCooldown.setIgnoreCooldown(player, true);
            entity.damage(damage, player);
            AttackCooldown.setIgnoreCooldown(player, false);
        }
        return alreadyHit;
    }
}
