package me.itzkiba.pluginMST.listeners.playerdamage;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.listeners.classes.WarriorBonus;
import me.itzkiba.pluginMST.listeners.healthbars.DamageIndicators;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Classes;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class AttackCooldown implements Listener {
    private HashMap<HashMap<UUID, UUID>, Long> cooldown = new HashMap<>();
    // <Entity, Player who harmed>, CD left

    static NamespacedKey BYPASS_CD = new NamespacedKey(PluginMST.getPlugin(), "bypassCD");

    // TEMPORARY
    private long cdTime = 400;
    // TEMPORARY

    @EventHandler
    public void onEntityDamageSetCooldown(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) {
            return;
        }

        if (!(e.getEntity() instanceof LivingEntity)) {
            return;
        }

        if (e.getEntity() instanceof ArmorStand)
        {
            return;
        }

        Player p = (Player) e.getDamager();
        LivingEntity v = (LivingEntity) e.getEntity();

        boolean isCritical = e.isCritical();

        if (ignoreCooldown(p))
        {
            e.setDamage(e.getDamage() / 5.0);
            DamageIndicators.spawnIndicator(v, e.getFinalDamage(), isCritical);
            return;
        }

        HashMap<UUID, UUID> check = new HashMap<>();
        check.put(v.getUniqueId(), p.getUniqueId());

        long attackBonus = Stats.getEntityAttackSpeedStat(p) * 2L;
        if (attackBonus > cdTime)
        {
            attackBonus = cdTime - 1;
        }

        if ((!cooldown.containsKey(check) || System.currentTimeMillis() - cooldown.get(check) > cdTime - attackBonus) || ignoreCooldown(p))
        {
            cooldown.put(check, System.currentTimeMillis());

            if (!(e.getDamager() instanceof Player)) {
                return;
            }

            if (Stats.getDamageStat(p.getInventory().getItemInMainHand()) > 0)
            {
                e.setDamage(Stats.getDamageStat(p.getInventory().getItemInMainHand()) / 5.0);
            }

            double damage = (Stats.getEntityDamageStat(p) / 5.0);
            if (Stats.getMagicDamageStat(p.getInventory().getItemInMainHand()) > 0)
            {
                damage = Stats.getEntityMagicDamageStat(p) / 5.0;
                isCritical = false;
            }

            if (e.isCritical() && Stats.getEntityMagicDamageStat(p) == 0)
            {
                damage *= 1 + (Stats.getEntityCritStat(p) / 100.0);
                isCritical = true;
            }

            double damageBonus = 1;

            if (Classes.getPlayerClass(p) == 1)
            {
                WarriorBonus.setPlayerStacks(p, WarriorBonus.getPlayerStacks(p) + 1);
                int currentStacks = WarriorBonus.getPlayerStacks(p);
                damageBonus += 0.02 * currentStacks;

                // debug
                //p.sendMessage(ChatColor.LIGHT_PURPLE + "" + currentStacks);

                Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMST.getPlugin(), () -> {
                    if (currentStacks == WarriorBonus.getPlayerStacks(p))
                    {
                        WarriorBonus.setPlayerStacks(p, 0);
                        p.playSound(p, Sound.ENTITY_ARROW_SHOOT, 0.5F, 2F);
                    }
                }, 80);

            }

            // Mace
            if (e.getDamager() instanceof Player) {
                Player player = (Player)e.getDamager();
                ItemStack item = player.getInventory().getItemInMainHand();
                if (item.getType() == Material.MACE) {
                    double velocity = player.getVelocity().getY() * -1;
                    int breachLvl = item.getEnchantmentLevel(Enchantment.BREACH);
                    int densityLvl = item.getEnchantmentLevel(Enchantment.DENSITY);
                    if (velocity > 0.7) {
                        damageBonus *= (velocity * 1.5);
                        damageBonus *= 1 + breachLvl * 0.1;
                        damageBonus *= 1 + densityLvl * 0.1;
                    }
                }
            }

            e.setDamage(damage * damageBonus);

            DamageIndicators.spawnIndicator(v, e.getFinalDamage(), isCritical);
        }
        else
        {
            e.setCancelled(true);
        }
    }

    public static void setIgnoreCooldown(Player player, boolean b)
    {
        if (b) {
            player.getPersistentDataContainer().set(BYPASS_CD, PersistentDataType.INTEGER, 1);
            return;
        }
        player.getPersistentDataContainer().set(BYPASS_CD, PersistentDataType.INTEGER, 0);
    }

    public static boolean ignoreCooldown(Player player)
    {
        return player.getPersistentDataContainer().has(BYPASS_CD) && player.getPersistentDataContainer().get(BYPASS_CD, PersistentDataType.INTEGER) != 0;
    }
}

