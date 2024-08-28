package me.itzkiba.pluginMST.listeners.abilities.sorcerer;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.helperfunctions.ParticleRay;
import me.itzkiba.pluginMST.listeners.abilities.Abilities;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Classes;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import org.bukkit.*;
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
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class Remedy implements Listener {

    private long cooldown = 150;
    private HashMap<UUID, Long> cooldownMap = new HashMap<>();

    private long manaCost = 50;

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

        if (id != 3000)
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
        double multiplier = (0.05 + (level * 0.0005));
        double healMax = player.getMaxHealth() * multiplier;

        Location origLocation = player.getLocation().add(0, 0.1, 0);
        PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION,  20, 0, false, true, true);

        new BukkitRunnable()
        {
            int i = 0;
            public void run()
            {

                if (i == 0) {

                    Collection<Player> nearbyPlayers = player.getLocation().getNearbyPlayers(8);
                    for (Player p : nearbyPlayers)
                    {
                        double healAmount = Math.min(healMax, p.getMaxHealth() - p.getHealth());

                        p.setHealth(p.getHealth() + healAmount);
                        p.addPotionEffect(regen);
                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 0.8F, 1F + random.nextFloat(0.3F));
                    }

                }

                if (i < 9) {
                    ParticleRay.createCircle(origLocation, i / 1, 32, Particle.GLOW);
                    ParticleRay.createCircle(origLocation, i / 1, 8, Particle.FIREWORK);
                }

                i++;

                if (i == 9)
                {
                    ParticleRay.createCircle(origLocation, i / 1, 32, Particle.HEART);
                    this.cancel();
                }
            }
        }.runTaskTimer(PluginMST.getPlugin(), 0, 1);
    }

}
