package me.itzkiba.pluginMST.listeners.abilities.sorcerer;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.listeners.abilities.Abilities;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Classes;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import me.itzkiba.pluginMST.listeners.playerdamage.AttackCooldown;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class HyperBolt implements Listener {

    private long cooldown = 50;
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

        if (id != 3005)
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
        double multiplier = (0.8 + (level * 0.02));

        Location origLocation = player.getEyeLocation();
        Location origLocation2 = player.getEyeLocation();
        origLocation.getWorld().playSound(origLocation, Sound.ENTITY_WITHER_SHOOT, 0.4F, 1.2F + random.nextFloat(0.5F));

        float bloom = 0.2f;

        Vector origFacing1 = player.getEyeLocation().getDirection().multiply(1.25).add(new Vector(random.nextDouble() * bloom - 0.5*bloom, random.nextDouble() * bloom - 0.5*bloom, random.nextDouble() * bloom - 0.5*bloom));
        Vector origFacing2 = player.getEyeLocation().getDirection().multiply(1.25).add(new Vector(random.nextDouble() * bloom - 0.5*bloom, random.nextDouble() * bloom - 0.5*bloom, random.nextDouble() * bloom - 0.5*bloom));

        new BukkitRunnable()
        {
            int numberOfEntities = 0;
            int i = 0;
            Location currentLoc = origLocation;
            public void run()
            {
                i++;

                currentLoc.add(origFacing1);

                Collection<LivingEntity> nearbyEntities = currentLoc.getNearbyLivingEntities(1);
                for (LivingEntity entity : nearbyEntities) {
                    if (entity instanceof ArmorStand)
                    {
                        continue;
                    }
                    if (entity instanceof Player) {
                        continue;
                    }
                    numberOfEntities++;
                }

                if (currentLoc.getBlock().isSolid() || numberOfEntities > 0)
                {
                    manaBoltExplode(currentLoc, player, Stats.getEntityMagicDamageStat(player) * multiplier);
                    this.cancel();
                }

                Particle.DustTransition dust = new Particle.DustTransition(Color.fromRGB(255, 20, 255), Color.fromRGB(255, 255, 255), 1.F);
                currentLoc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, currentLoc, 2, 0, 0, 0, dust);
                currentLoc.getWorld().spawnParticle(Particle.WITCH, currentLoc, 5, 0, 0, 0, 0);


                if (i == 30)
                {
                    this.cancel();
                }
            }
        }.runTaskTimer(PluginMST.getPlugin(), 0, 1);

        new BukkitRunnable()
        {
            int numberOfEntities2 = 0;
            int i2 = 0;
            Location currentLoc2 = origLocation2;
            public void run()
            {
                i2++;

                currentLoc2.add(origFacing2);

                Collection<LivingEntity> nearbyEntities2 = currentLoc2.getNearbyLivingEntities(1);
                for (LivingEntity entity : nearbyEntities2) {
                    if (entity instanceof ArmorStand)
                    {
                        continue;
                    }
                    if (entity instanceof Player) {
                        continue;
                    }
                    numberOfEntities2++;
                }

                if (currentLoc2.getBlock().isSolid() || numberOfEntities2 > 0)
                {
                    manaBoltExplode(currentLoc2, player, Stats.getEntityMagicDamageStat(player) * multiplier);
                    this.cancel();
                }

                Particle.DustTransition dust = new Particle.DustTransition(Color.fromRGB(255, 100, 255), Color.fromRGB(255, 255, 255), 1.F);
                currentLoc2.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, currentLoc2, 2, 0, 0, 0, dust);
                currentLoc2.getWorld().spawnParticle(Particle.WITCH, currentLoc2, 5, 0, 0, 0, 0);


                if (i2 == 30)
                {
                    this.cancel();
                }
            }
        }.runTaskTimer(PluginMST.getPlugin(), 1, 1);
    }

    private void manaBoltExplode(Location location, Player player, double damage)
    {
        Random random = new Random();
        location.getWorld().spawnParticle(Particle.END_ROD, location, 10, 0, 0, 0, 0.1);
        location.getWorld().spawnParticle(Particle.FLASH, location, 1, 0, 0, 0, 0.1);
        location.getWorld().playSound(location, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 0.7F, 1.6F + random.nextFloat(0.2F));

        Collection<LivingEntity> nearbyEntities = location.getNearbyLivingEntities(2.5);
        for (LivingEntity entity : nearbyEntities)
        {
            if (entity instanceof Player)
            {
                continue;
            }

            AttackCooldown.setIgnoreCooldown(player, true);
            entity.damage(damage, player);
            AttackCooldown.setIgnoreCooldown(player, false);
        }
    }
}
