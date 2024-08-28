package me.itzkiba.pluginMST.listeners.abilities.ranged;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.helperfunctions.ParticleRay;
import me.itzkiba.pluginMST.listeners.abilities.Abilities;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Classes;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import org.bukkit.*;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class GrapplingShot implements Listener {

    static NamespacedKey IS_GRAPPLING = new NamespacedKey(PluginMST.getPlugin(), "isGrappling");
    private long cooldown = 8500;
    private HashMap<UUID, Long> cooldownMap = new HashMap<>();
    private final float arrowBloom = 5F;

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

        if (id != 2001)
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

        long trueCooldown = cooldown - level * 50L;

        if (cooldownMap.containsKey(player.getUniqueId()) && System.currentTimeMillis() - cooldownMap.get(player.getUniqueId()) <= trueCooldown) {
            return;
        }

        cooldownMap.put(player.getUniqueId(), System.currentTimeMillis());
        Random random = new Random();
        double multiplier = 0.25 + ((level) * 0.004);
        double damageOriginal = (int)(Stats.getEntityRangedDamageStat(player));

        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMST.getPlugin(), () -> {

            player.sendTitle("", ChatColor.GREEN + "Grappling Shot ready", 2, 20, 2);
            player.playSound(player, Sound.UI_BUTTON_CLICK, 0.8F, 1.5F);

        }, trueCooldown / 50);

        Arrow arrow = player.getWorld().spawnArrow(player.getEyeLocation(), player.getEyeLocation().getDirection(), 5F, 0);

        new BukkitRunnable()
        {
            int i = 0;
            public void run()
            {

                if (i == 0) {
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1F, 1F + random.nextFloat(0.1F));

                    arrow.setShooter(player);
                    arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                    arrow.setKnockbackStrength(0);
                    arrow.setMetadata("destroyArrow", new FixedMetadataValue(PluginMST.getPlugin(), "destroyArrow"));
                    arrow.setMetadata("grappleArrow", new FixedMetadataValue(PluginMST.getPlugin(), "grappleArrow"));
                    Stats.setEntityRangedDamageStat(arrow, 0);
                }
                i++;

                if (i == 35)
                {
                    arrow.remove();
                    this.cancel();
                }
            }
        }.runTaskTimer(PluginMST.getPlugin(), 0, 1);
    }

    public static void setGrappleState(Player player, boolean b)
    {
        if (b) {
            player.getPersistentDataContainer().set(IS_GRAPPLING, PersistentDataType.INTEGER, 1);
            return;
        }
        player.getPersistentDataContainer().set(IS_GRAPPLING, PersistentDataType.INTEGER, 0);
    }

    public static boolean getGrappleState(Player player)
    {
        return player.getPersistentDataContainer().has(IS_GRAPPLING) && player.getPersistentDataContainer().get(IS_GRAPPLING, PersistentDataType.INTEGER) != 0;
    }

    @EventHandler
    public void grappleArrowParticles(ServerLoadEvent e)
    {
        Particle.DustTransition dust = new Particle.DustTransition(Color.fromRGB(200, 200, 200), Color.fromRGB(255, 255, 255), 2F);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginMST.getPlugin(), () -> {
            for (World world : Bukkit.getServer().getWorlds())
            {
                for (Arrow arrow : world.getEntitiesByClass(Arrow.class))
                {
                    if (arrow.hasMetadata("grappleArrow"))
                    {
                        arrow.getWorld().spawnParticle(Particle.DUST, arrow.getLocation(), 2, 0, 0, 0, 0, dust, true);
                    }
                }
            }
        }, 1, 1);
    }

    @EventHandler
    public void ActivateGrappleArrows(ProjectileHitEvent e)
    {
        if (!(e.getEntity() instanceof Arrow))
        {
            return;
        }

        Arrow arrow = (Arrow)e.getEntity();

        if (!arrow.hasMetadata("grappleArrow"))
        {
            return;
        }

        if (!(arrow.getShooter() instanceof Player))
        {
            return;
        }

        Player player = (Player) arrow.getShooter();

        player.setVelocity(new Vector(0, 1, 0));
        Vector dir = arrow.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(new Vector(1, 0.6, 1));
        Vector particleDir = arrow.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
        ParticleRay.createLine(player.getEyeLocation(), particleDir, arrow.getLocation().distance(player.getLocation()), 5, Particle.CRIT, player.getWorld());

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FISHING_BOBBER_THROW, 1F, 0.5F);

        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMST.getPlugin(), () -> {
            player.setVelocity(dir.multiply(3));
            setGrappleState(player, true);
        }, 9);

        arrow.remove();
    }

    @EventHandler
    public void cancelFallDamage(EntityDamageEvent e)
    {
        if (!(e.getEntity() instanceof Player))
        {
            return;
        }

        Player p = (Player)e.getEntity();

        if (!e.getCause().equals(EntityDamageEvent.DamageCause.FALL))
        {
            return;
        }

        if (getGrappleState(p)) {
            e.setCancelled(true);
        }
        setGrappleState(p, false);
    }
}
