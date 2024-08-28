package me.itzkiba.pluginMST.listeners.abilities.ranged;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.listeners.abilities.Abilities;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Classes;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import me.itzkiba.pluginMST.listeners.playerdamage.AttackCooldown;
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
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class ArrowStorm2 implements Listener {

    private long cooldown = 4000;
    private HashMap<UUID, Long> cooldownMap = new HashMap<>();
    private final float arrowBloom = 15F;

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

        if (id != 2004)
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
        double multiplier = 0.1 + ((level) * 0.003);
        double damageOriginal = (int)(Stats.getEntityRangedDamageStat(player));

        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMST.getPlugin(), () -> {

            player.sendTitle("", ChatColor.GREEN + "Bolt Storm ready", 2, 20, 2);
            player.playSound(player, Sound.UI_BUTTON_CLICK, 0.8F, 1.5F);

        }, cooldown / 50);

        new BukkitRunnable()
        {
            int i = 0;
            public void run()
            {
                i++;

                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1F, 1.25F + random.nextFloat(0.1F));

                Arrow arrow = player.getWorld().spawnArrow(player.getEyeLocation(), player.getEyeLocation().getDirection(), 5F, arrowBloom);
                arrow.setShooter(player);
                arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                arrow.setKnockbackStrength(0);
                arrow.setMetadata("destroyArrow", new FixedMetadataValue(PluginMST.getPlugin(), "destroyArrow"));
                arrow.setMetadata("barrageArrow2_1", new FixedMetadataValue(PluginMST.getPlugin(), "barrageArrow2_1"));
                Stats.setEntityRangedDamageStat(arrow, (int)(damageOriginal * multiplier));

                Arrow arrow3 = player.getWorld().spawnArrow(player.getEyeLocation(), player.getEyeLocation().getDirection(), 5F, arrowBloom * 1.3F);
                arrow3.setShooter(player);
                arrow3.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                arrow3.setKnockbackStrength(0);
                arrow3.setMetadata("destroyArrow", new FixedMetadataValue(PluginMST.getPlugin(), "destroyArrow"));
                arrow3.setMetadata("barrageArrow2_1", new FixedMetadataValue(PluginMST.getPlugin(), "barrageArrow2_1"));
                Stats.setEntityRangedDamageStat(arrow3, (int)(damageOriginal * multiplier));

                Arrow arrow2 = player.getWorld().spawnArrow(player.getEyeLocation(), player.getEyeLocation().getDirection(), 5F, arrowBloom * 0.5F);
                arrow2.setShooter(player);
                arrow2.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                arrow2.setKnockbackStrength(0);
                arrow2.setMetadata("destroyArrow", new FixedMetadataValue(PluginMST.getPlugin(), "destroyArrow"));
                arrow2.setMetadata("barrageArrow2_1", new FixedMetadataValue(PluginMST.getPlugin(), "barrageArrow2_1"));
                Stats.setEntityRangedDamageStat(arrow2, (int)(damageOriginal * multiplier));

                if (i == 20)
                {
                    this.cancel();
                }
            }
        }.runTaskTimer(PluginMST.getPlugin(), 0, 1);
    }

    @EventHandler
    public void barrageArrowParticles2(ServerLoadEvent e)
    {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginMST.getPlugin(), () -> {
            for (World world : Bukkit.getServer().getWorlds())
            {
                for (Arrow arrow : world.getEntitiesByClass(Arrow.class))
                {
                    if (arrow.hasMetadata("barrageArrow2"))
                    {
                        arrow.getWorld().spawnParticle(Particle.CRIT,  arrow.getLocation(), 1, 0, 0, 0, 0);
                    }
                    if (arrow.hasMetadata("barrageArrow2_1"))
                    {
                        arrow.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME,  arrow.getLocation(), 1, 0, 0, 0, 0);
                    }
                }
            }
        }, 1, 2);
    }
}
