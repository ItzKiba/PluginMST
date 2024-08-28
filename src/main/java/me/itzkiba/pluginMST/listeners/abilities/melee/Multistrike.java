package me.itzkiba.pluginMST.listeners.abilities.melee;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.listeners.abilities.Abilities;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Classes;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Multistrike implements Listener {

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

        if (id != 1000)
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
        double multiplier = 0.20 + ((level) * 0.01);
        double damageOriginal = (int)(Stats.getEntityDamageStat(player));

        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMST.getPlugin(), () -> {

            player.sendTitle("", ChatColor.GREEN + "Multistrike ready", 2, 20, 2);
            player.playSound(player, Sound.UI_BUTTON_CLICK, 0.8F, 1.5F);

        }, cooldown / 50);

        new BukkitRunnable()
        {
            int i = 0;
            public void run()
            {
                i++;
                Location hitLoc = player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(2.5));

                if (!player.isSneaking() && player.isOnGround())
                {
                    player.setVelocity(player.getVelocity().add(player.getEyeLocation().getDirection().multiply(0.25)));
                }


                Stats.setEntityDamageStat(player, (int)(damageOriginal * multiplier));
                Stats.setEntityAttackSpeedStat(player, 500);

                Collection<LivingEntity> nearbyEntities = hitLoc.getNearbyLivingEntities(2);
                for (LivingEntity e : nearbyEntities)
                {
                    if (e instanceof Player)
                    {
                        continue;
                    }
                    e.damage(1, player);
                }

                Stats.setEntityDamageStat(player, (int)(damageOriginal));

                player.getWorld().spawnParticle(Particle.SWEEP_ATTACK, hitLoc, 1, 0.1, 0.1, 0.1);
                player.getWorld().spawnParticle(Particle.CRIT, hitLoc, 10, 0.3, 0.3, 0.3);
                player.getWorld().playSound(hitLoc, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 0.6F, 0.9F + random.nextFloat(0.2F));

                if (i == 10)
                {
                    this.cancel();
                }
            }
        }.runTaskTimer(PluginMST.getPlugin(), 0, 2);
    }



}
