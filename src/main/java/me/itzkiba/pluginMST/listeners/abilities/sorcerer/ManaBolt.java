package me.itzkiba.pluginMST.listeners.abilities.sorcerer;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.helperfunctions.ParticleRay;
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
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class ManaBolt implements Listener {

    private long cooldown = 50;
    private HashMap<UUID, Long> cooldownMap = new HashMap<>();

    private long manaCost = 25;

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

        if (id != 3001)
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
        double multiplier = (1.5 + (level * 0.03));
        double healMax = player.getMaxHealth() * multiplier;

        Location origLocation = player.getEyeLocation();
        origLocation.getWorld().playSound(origLocation, Sound.ENTITY_BLAZE_SHOOT, 0.5F, 1.2F + random.nextFloat(0.6F));

        Vector origFacing = player.getEyeLocation().getDirection().multiply(0.9);

        new BukkitRunnable()
        {
            int numberOfEntities = 0;
            int i = 0;
            Location currentLoc = origLocation;
            public void run()
            {
                i++;

                currentLoc.add(origFacing);

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

                Particle.DustTransition dust = new Particle.DustTransition(Color.fromRGB(0, 255, 255), Color.fromRGB(255, 255, 255), 2.5F);
                currentLoc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, currentLoc, 2, 0, 0, 0, dust);
                currentLoc.getWorld().spawnParticle(Particle.CRIT, currentLoc, 5, 0, 0, 0, 0.1);


                if (i == 40)
                {
                    this.cancel();
                }
            }
        }.runTaskTimer(PluginMST.getPlugin(), 0, 1);
    }

    private void manaBoltExplode(Location location, Player player, double damage)
    {
        Random random = new Random();
        location.getWorld().spawnParticle(Particle.END_ROD, location, 10, 0, 0, 0, 0.1);
        location.getWorld().spawnParticle(Particle.FLASH, location, 1, 0, 0, 0, 0.1);
        location.getWorld().playSound(location, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 0.7F, 1.2F + random.nextFloat(0.2F));

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
