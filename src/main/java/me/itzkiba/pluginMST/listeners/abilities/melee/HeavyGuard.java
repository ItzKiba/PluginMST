package me.itzkiba.pluginMST.listeners.abilities.melee;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.listeners.abilities.Abilities;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Classes;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class HeavyGuard implements Listener {

    static NamespacedKey IS_GUARDING = new NamespacedKey(PluginMST.getPlugin(), "isGuarding");

    private long cooldown = 13000;
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

        if (id != 1001)
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
        int defenseValue = Stats.getEntityDefenseStat(player) + (40 + (level * 20));

        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMST.getPlugin(), () -> {

            player.sendTitle("", ChatColor.GREEN + "Heavy Guard ready", 2, 20, 2);
            player.playSound(player, Sound.UI_BUTTON_CLICK, 0.8F, 1.5F);

        }, cooldown / 50);

        new BukkitRunnable()
        {
            int i = 0;
            public void run()
            {
                if (i == 0)
                {
                    player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(5);
                    setHeavyGuardState(player, true);
                    PotionEffect effect = new PotionEffect(PotionEffectType.SLOWNESS, 100, 3, true, false, true);
                    player.addPotionEffect(effect);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1F, 0.8F + random.nextFloat(0.4F));
                    player.getWorld().spawnParticle(Particle.CRIT,  player.getLocation().add(0, 1, 0), 60, 0.1, 0.5, 0.1, 1);
                }
                i++;
                Stats.setEntityDefenseStat(player, defenseValue);

                player.getWorld().spawnParticle(Particle.SMOKE,  player.getLocation().add(0, 1, 0), 1, 0.2, 0.2, 0.2, 0.05);

                if (i == 100)
                {
                    player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1F, 1.6F + random.nextFloat(0.4F));
                    setHeavyGuardState(player, false);
                    this.cancel();
                }
            }
        }.runTaskTimer(PluginMST.getPlugin(), 0, 1);
    }


    public static void setHeavyGuardState(Player player, boolean b)
    {
        if (b) {
            player.getPersistentDataContainer().set(IS_GUARDING, PersistentDataType.INTEGER, 1);
            return;
        }
        player.getPersistentDataContainer().set(IS_GUARDING, PersistentDataType.INTEGER, 0);
    }

    public static boolean getHeavyGuardState(Player player)
    {
        return player.getPersistentDataContainer().has(IS_GUARDING) && player.getPersistentDataContainer().get(IS_GUARDING, PersistentDataType.INTEGER) != 0;
    }
}
