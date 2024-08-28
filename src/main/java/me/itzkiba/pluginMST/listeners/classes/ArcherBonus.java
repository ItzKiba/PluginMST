package me.itzkiba.pluginMST.listeners.classes;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.listeners.abilities.Abilities;
import me.itzkiba.pluginMST.listeners.healthbars.DamageIndicators;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Classes;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import me.itzkiba.pluginMST.listeners.playerdamage.AttackCooldown;
import me.itzkiba.pluginMST.listeners.playerdamage.DamageReduction;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class ArcherBonus implements Listener {

    private long cooldown = 230;
    private HashMap<UUID, Long> cooldownMap = new HashMap<>();

    @EventHandler
    public void shootArrow(PlayerInteractEvent e)
    {
        if (!(e.getAction() == Action.LEFT_CLICK_AIR))
        {
            return;
        }

        Player player = e.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        int level = Stats.getItemLevel(item);
        int id = Abilities.getItemAbilityID(item);

        if (player.getInventory().getItemInMainHand().getType() != Material.BOW)
        {
            return;
        }

        if (Classes.getPlayerClass(player) != 2)
        {
            return;
        }

        if (Levels.getPlayerLevel(player) < level)
        {
            return;
        }

        if (player.isSneaking())
        {
            return;
        }

        long attackBonus = Stats.getEntityAttackSpeedStat(player) * 1L;
        if (attackBonus > cooldown)
        {
            attackBonus = cooldown - 1;
        }

        if (cooldownMap.containsKey(player.getUniqueId()) && System.currentTimeMillis() - cooldownMap.get(player.getUniqueId()) <= cooldown - attackBonus) {
            return;
        }
        cooldownMap.put(player.getUniqueId(), System.currentTimeMillis());

        if (!player.getInventory().contains(Material.ARROW) && player.getGameMode() != GameMode.CREATIVE)
        {
            return;
        }

        if (!player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.INFINITY) && player.getGameMode() != GameMode.CREATIVE)
        {
            player.getInventory().removeItem(new ItemStack(Material.ARROW, 1));
        }



        Random random = new Random();
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1F, 1F + random.nextFloat(0.1F));

        Arrow arrow = player.launchProjectile(Arrow.class, player.getEyeLocation().getDirection().multiply(2.5));
        arrow.setShooter(player);
        Stats.setEntityRangedDamageStat(arrow, Stats.getEntityRangedDamageStat(player));
        arrow.setMetadata("destroyArrow", new FixedMetadataValue(PluginMST.getPlugin(), "destroyArrow"));

    }

    @EventHandler
    public void DestroyArrows(ProjectileHitEvent e)
    {
        if (!(e.getEntity() instanceof Arrow))
        {
            return;
        }

        Arrow arrow = (Arrow)e.getEntity();

        if (e.getHitEntity() instanceof Enderman)
        {
            if (arrow.getShooter() instanceof Player)
            {
                Enderman eman = (Enderman) e.getHitEntity();
                Player player = (Player) arrow.getShooter();

                e.setCancelled(true);
                AttackCooldown.setIgnoreCooldown(player, true);

                if (!arrow.isCritical()) {
                    eman.damage(Stats.getEntityRangedDamageStat(arrow), player);
                }
                else
                {
                    double multiplier = Stats.getEntityCritStat(player) / 100.0 + 1;
                    eman.damage(Stats.getEntityRangedDamageStat(arrow) * multiplier, player);
                }
                AttackCooldown.setIgnoreCooldown(player, false);
                arrow.remove();
            }

        }

        if (!arrow.hasMetadata("destroyArrow"))
        {
            return;
        }

        arrow.remove();
    }

}
