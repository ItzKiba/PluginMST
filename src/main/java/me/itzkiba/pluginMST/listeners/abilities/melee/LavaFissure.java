package me.itzkiba.pluginMST.listeners.abilities.melee;

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
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.*;

public class LavaFissure implements Listener {

    private long cooldown = 6000;
    private HashMap<UUID, Long> cooldownMap = new HashMap<>();

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

        if (id != 1004)
        {
            return;
        }

        if (Classes.getPlayerClass(player) != 1)
        {
            player.sendMessage(ChatColor.RED + "You must be a Warrior to use this ability.");
            return;
        }

        if (cooldownMap.containsKey(player.getUniqueId()) && System.currentTimeMillis() - cooldownMap.get(player.getUniqueId()) <= cooldown) {
            return;
        }

        cooldownMap.put(player.getUniqueId(), System.currentTimeMillis());
        Random random = new Random();
        double multiplier = 2.2 + ((level) * 0.08);

        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMST.getPlugin(), () -> {

            player.sendTitle("", ChatColor.GREEN + "Lava Fissure ready", 2, 20, 2);
            player.playSound(player, Sound.UI_BUTTON_CLICK, 0.8F, 1.5F);

        }, cooldown / 50);




        ArrayList<LivingEntity> entitiesAlreadyHit = new ArrayList<LivingEntity>();
        Location origLocation = player.getLocation();
        Vector origFacing = player.getEyeLocation().getDirection().multiply(new Vector(1, 0, 1)).normalize();

        player.getWorld().playSound(origLocation, Sound.ENTITY_BLAZE_HURT, 0.9F, 0.6F);
        new BukkitRunnable()
        {
            int numberOfEntities = 0;
            int i = 0;
            Location currentLoc = origLocation;

            PotionEffect hinder = new PotionEffect(PotionEffectType.SLOWNESS, 20 + (2 * level), 3, false, false);

            public void run()
            {
                i++;

                currentLoc.add(origFacing);

                Collection<LivingEntity> nearbyEntities = currentLoc.getNearbyLivingEntities(2);
                ArrayList<LivingEntity> entitiesToHit = new ArrayList<LivingEntity>();
                for (LivingEntity entity : nearbyEntities) {
                    if (entity instanceof Player) {
                        continue;
                    }
                    if (entity instanceof ArmorStand) {
                        continue;
                    }
                    if (!entitiesAlreadyHit.contains(entity)) {
                        entitiesToHit.add(entity);
                        entitiesAlreadyHit.add(entity);
                        numberOfEntities++;
                    }
                }
                player.getWorld().playSound(currentLoc, Sound.BLOCK_FIRE_EXTINGUISH, 0.6F, 0.5F);

                if (currentLoc.getBlock().isSolid())
                {
                    this.cancel();
                }
                if (numberOfEntities > 0) {
                    for (LivingEntity entity : entitiesToHit) {
                        fissureDamageEntity(entity, player, Stats.getEntityDamageStat(player) * multiplier, level);
                        entity.setVelocity(new Vector(0, 0.75, 0));
                    }
                }

                currentLoc.getWorld().spawnParticle(Particle.EXPLOSION, currentLoc, 1, 0, 0, 0, 0.1);
                currentLoc.getWorld().spawnParticle(Particle.LARGE_SMOKE, currentLoc, 5, 0.5, 1, 0.5, 0.1);
                currentLoc.getWorld().spawnParticle(Particle.LAVA, currentLoc, 8, 0.5, 0.1, 0.5, 0.1);
                currentLoc.getWorld().spawnParticle(Particle.FLAME, currentLoc, 5, 0.5, 0.5, 0.5, 0.1);


                if (i == 16)
                {
                    this.cancel();
                }
            }
        }.runTaskTimer(PluginMST.getPlugin(), 0, 1);

    }

    private void fissureDamageEntity(LivingEntity entity, Player player, double damage, int level)
    {
        entity.setFireTicks(20 + (2 * level));

        AttackCooldown.setIgnoreCooldown(player, true);
        entity.damage(damage, player);
        AttackCooldown.setIgnoreCooldown(player, false);

        entity.getWorld().spawnParticle(Particle.EXPLOSION, entity.getLocation(), 2, 0.5, 0.5, 0.5);
        entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.7F, 0.9F);
    }
}
