package me.itzkiba.pluginMST.listeners.playermagic;

import me.itzkiba.pluginMST.helperfunctions.ParticleRay;
import me.itzkiba.pluginMST.helperfunctions.VectorUtility;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import me.itzkiba.pluginMST.listeners.playerdamage.AttackCooldown;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class MagicBeam implements Listener {

    private HashMap<UUID, Long> cooldown = new HashMap<>();

    private long cdTime = 450;

    private int beamLength = 20;

    @EventHandler
    public void onMageClick(PlayerInteractEvent e)
    {
        if (e.getAction().isRightClick() || e.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            return;
        }

        Player p = e.getPlayer();

        if (Stats.getMagicDamageStat(p.getInventory().getItemInMainHand()) == 0)
        {
            return;
        }

        long attackBonus = Stats.getEntityAttackSpeedStat(p) * 2L;
        if (attackBonus > 200)
        {
            attackBonus = 200;
        }

        if (!cooldown.containsKey(p.getUniqueId())
        || System.currentTimeMillis() - cooldown.get(p.getUniqueId()) > cdTime - attackBonus)
        {
            cooldown.put(p.getUniqueId(), System.currentTimeMillis());
            createBeam(p);

        }

    }

    private void createBeam(Player p)
    {
        Vector direction = p.getEyeLocation().getDirection().normalize();
        Location startPosition = p.getEyeLocation().add(direction);

        RayTraceResult rayTrace = p.getWorld()
                .rayTrace(startPosition, direction, beamLength, FluidCollisionMode.NEVER, true, 0.5,
                        test -> !p.getUniqueId().equals(test.getUniqueId()) && !(test instanceof ArmorStand) && test instanceof LivingEntity);
        double rayTraceLength;

        if (rayTrace == null)
        {
            rayTraceLength = beamLength;
        }
        else
        {
            if (rayTrace.getHitBlock() != null)
            {
                rayTraceLength = rayTrace.getHitPosition().toLocation(p.getWorld()).distance(startPosition);
            }
            else if (rayTrace.getHitEntity() instanceof LivingEntity)
            {
                LivingEntity victim = (LivingEntity) rayTrace.getHitEntity();

                double damage = Stats.getEntityMagicDamageStat(p);

                Stats.setEntityManaStat(p, Math.min(Stats.getEntityManaStat(p) + Stats.getEntityMaxManaStat(p) * 0.05, Stats.getEntityMaxManaStat(p)));

                AttackCooldown.setIgnoreCooldown(p, true);
                victim.damage(damage, p);
                AttackCooldown.setIgnoreCooldown(p, false);

                p.getWorld().playSound(p, Sound.ITEM_FLINTANDSTEEL_USE, 0.9f, 0.5f);
                rayTraceLength = rayTrace.getHitPosition().toLocation(p.getWorld()).distance(startPosition);
            }
            else
            {
                rayTraceLength = beamLength;
            }
        }

        startPosition.add(0, -0.25, 0);
        ParticleRay.createLine(startPosition, direction, rayTraceLength, 3, Particle.INSTANT_EFFECT, p.getWorld());

    }
}
