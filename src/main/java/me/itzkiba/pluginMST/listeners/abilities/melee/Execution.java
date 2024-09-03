package me.itzkiba.pluginMST.listeners.abilities.melee;

import me.itzkiba.pluginMST.PluginMST;
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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class Execution implements Listener {

    private long cooldown = 7000;
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

        if (id != 1002)
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
        double multiplier = 2 + ((level) * 0.5);

        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMST.getPlugin(), () -> {

            player.sendTitle("", ChatColor.GREEN + "Execution ready", 2, 20, 2);
            player.playSound(player, Sound.UI_BUTTON_CLICK, 0.8F, 1.5F);

        }, cooldown / 50);

        Location hitLoc = player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(1.5));

        Collection<LivingEntity> nearbyEntities = hitLoc.getNearbyLivingEntities(1.6);
        for (LivingEntity entity : nearbyEntities)
        {
            if (entity instanceof Player)
            {
                continue;
            }

            AttackCooldown.setIgnoreCooldown(player, true);
            entity.damage(Stats.getEntityCritStat(player) * multiplier, player);
            AttackCooldown.setIgnoreCooldown(player, false);
        }

        Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1F);

        player.getWorld().spawnParticle(Particle.EXPLOSION, hitLoc, 1, 0, 0, 0);
        player.getWorld().spawnParticle(Particle.DUST, hitLoc, 60, 0.4, 0.4, 0.4, dust);
        player.getWorld().spawnParticle(Particle.CRIT, hitLoc, 40, 0, 0, 0, 1);
        player.getWorld().playSound(hitLoc, Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 0.6F, 0.5F + random.nextFloat(0.2F));
    }
}
