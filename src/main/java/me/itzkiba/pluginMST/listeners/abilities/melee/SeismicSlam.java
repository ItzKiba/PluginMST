package me.itzkiba.pluginMST.listeners.abilities.melee;

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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class SeismicSlam implements Listener {

    static NamespacedKey IS_LEAPING = new NamespacedKey(PluginMST.getPlugin(), "isLeaping");

    private long cooldown = 5000;
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

        if (id != 1003)
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
        double multiplier = 1.5 + ((level) * 0.05);

        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMST.getPlugin(), () -> {

            player.sendTitle("", ChatColor.GREEN + "Seismic Slam ready", 2, 20, 2);
            player.playSound(player, Sound.UI_BUTTON_CLICK, 0.8F, 1.5F);

        }, cooldown / 50);

        new BukkitRunnable()
        {
            int i = 0;
            public void run()
            {
                if (i == 0)
                {
                    setLeapingState(player, true);
                    double ls = 2;
                    player.setVelocity(player.getEyeLocation().getDirection().multiply(ls).multiply(new Vector(1, 0.15, 1)).add(new Vector(0, 0.8, 0)));
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1F, 0.6F + random.nextFloat(0.4F));
                }

                i++;

                player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().add(0, 1, 0), 2, 0, 0, 0, 0.1);
                player.getWorld().spawnParticle(Particle.SMOKE, player.getLocation().add(0, 1, 0), 2, 0, 0, 0, 0.1);

                if (player.isOnGround() && i > 10)
                {
                    Location hitLoc = player.getLocation();
                    Collection<LivingEntity> nearbyEntities = hitLoc.getNearbyLivingEntities(5);
                    for (LivingEntity e : nearbyEntities)
                    {
                        if (e instanceof Player)
                        {
                            continue;
                        }
                        AttackCooldown.setIgnoreCooldown(player, true);
                        e.damage(Stats.getEntityDamageStat(player) * multiplier, player);
                        e.setVelocity(new Vector(0, 1, 0).multiply(0.6));
                        AttackCooldown.setIgnoreCooldown(player, false);
                    }

                    player.getWorld().spawnParticle(Particle.EXPLOSION, player.getLocation(), 10, 1, 0, 1);
                    player.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, player.getLocation(), 20, 1, 0, 1, 0.1);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.7F, 0.5F + random.nextFloat(0.1F));
                    ParticleRay.createCircle(player.getLocation(), 5, 50, Particle.LARGE_SMOKE);
                    player.setVelocity(new Vector(0, 1, 0).multiply(0.4));
                    setLeapingState(player, false);
                    this.cancel();
                }

                if (i == 79)
                {
                    this.cancel();
                    setLeapingState(player, false);
                }
            }
        }.runTaskTimer(PluginMST.getPlugin(), 0, 1);
    }

    public static void setLeapingState(Player player, boolean b)
    {
        if (b) {
            player.getPersistentDataContainer().set(IS_LEAPING, PersistentDataType.INTEGER, 1);
            return;
        }
        player.getPersistentDataContainer().set(IS_LEAPING, PersistentDataType.INTEGER, 0);
    }

    public static boolean getLeapingState(Player player)
    {
        return player.getPersistentDataContainer().has(IS_LEAPING) && player.getPersistentDataContainer().get(IS_LEAPING, PersistentDataType.INTEGER) != 0;
    }

    @EventHandler
    public void cancelFallDamage(EntityDamageEvent e)
    {
        if (!(e.getEntity() instanceof Player))
        {
            return;
        }

        if (!e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
        {
            return;
        }

        if (getLeapingState((Player)e.getEntity()))
        {
            e.setCancelled(true);
        }
    }
}
