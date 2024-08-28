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

import java.util.*;

public class FireBeam implements Listener {

    private long cooldown = 50;
    private HashMap<UUID, Long> cooldownMap = new HashMap<>();

    private long manaCost = 10;

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

        if (id != 3004)
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
        double multiplier = (1.25 + (level * 0.01));

        Location origLocation = player.getEyeLocation();
        origLocation.getWorld().playSound(origLocation, Sound.BLOCK_FIRE_EXTINGUISH, 0.4F, 0.8F + random.nextFloat(0.2F));

        Vector origFacing = player.getEyeLocation().getDirection().multiply(0.9);
        ArrayList<LivingEntity> entitiesAlreadyHit = new ArrayList<LivingEntity>();

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



                if (currentLoc.getBlock().isSolid())
                {
                    this.cancel();
                }
                if (numberOfEntities > 0) {
                    for (LivingEntity entity : entitiesToHit) {
                        flameBoltDamageEntity(entity, player, Stats.getEntityMagicDamageStat(player) * multiplier, level);
                    }
                }

                Particle.DustTransition dust = new Particle.DustTransition(Color.fromRGB(255, 200, 0), Color.fromRGB(255, 100, 0), 0.7F);
                currentLoc.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, currentLoc, 5, 0.5, 0.5, 0.5, dust);
                currentLoc.getWorld().spawnParticle(Particle.FLAME, currentLoc, 5, 0.5, 0.5, 0.5, 0.05);


                if (i == 8)
                {
                    this.cancel();
                }
            }
        }.runTaskTimer(PluginMST.getPlugin(), 0, 1);
    }

    private void flameBoltDamageEntity(LivingEntity entity, Player player, double damage, int level)
    {
        entity.setFireTicks(20 + (2 * level));

        AttackCooldown.setIgnoreCooldown(player, true);
        entity.damage(damage, player);
        AttackCooldown.setIgnoreCooldown(player, false);
    }
}
