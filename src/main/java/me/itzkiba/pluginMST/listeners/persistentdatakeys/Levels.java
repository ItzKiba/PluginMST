package me.itzkiba.pluginMST.listeners.persistentdatakeys;

import me.itzkiba.pluginMST.PluginMST;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Levels {

    // for player
    static NamespacedKey LEVEL = new NamespacedKey(PluginMST.getPlugin(), "level");
    static NamespacedKey TOTAL_XP = new NamespacedKey(PluginMST.getPlugin(), "totalXP");

    public static void setPlayerLevel(Player player, int value)
    {
        player.getPersistentDataContainer().set(LEVEL, PersistentDataType.INTEGER, value);
    }

    public static int getPlayerLevel(Player player)
    {
        if (!player.getPersistentDataContainer().has(LEVEL, PersistentDataType.INTEGER))
        {
            return 0;
        }
        return player.getPersistentDataContainer().get(LEVEL, PersistentDataType.INTEGER);
    }

    public static void setPlayerXP(Player player, int value)
    {
        player.getPersistentDataContainer().set(TOTAL_XP, PersistentDataType.INTEGER, value);
    }

    public static int getPlayerXP(Player player)
    {
        if (!player.getPersistentDataContainer().has(TOTAL_XP, PersistentDataType.INTEGER))
        {
            return 0;
        }
        return player.getPersistentDataContainer().get(TOTAL_XP, PersistentDataType.INTEGER);
    }

    private static final double a = 0.1;
    private static final double b = 0.4;

    public static int calculatePlayerLevel(int xp)
    {
        return (int)(Math.pow(a * xp, b));
    }

    public static int getXPRequiredForLevel(int level)
    {
        return (int)(Math.pow(level, (1 / b)) * (1 / a));
    }

    public static void increasePlayerXP(Player player, LivingEntity victim)
    {
        int amount = (int)(Stats.getEntityLevel(victim) * 2.5);
        if (Stats.getEntityLevel(victim) > 100)
        {
            amount *= 2;
        }

        if (amount == 0)
        {
            return;
        }

        if (victim.getType() == EntityType.WITHER)
        {
            amount = 10000;
        }

        if (victim.getType() == EntityType.ENDER_DRAGON)
        {
            amount = 25000;
        }

        if (victim.getType() == EntityType.WARDEN)
        {
            amount = 4000;
        }

        int levelBefore = calculatePlayerLevel(getPlayerXP(player));
        setPlayerXP(player, getPlayerXP(player) + amount);
        int levelAfter = calculatePlayerLevel(getPlayerXP(player));
        setPlayerLevel(player, levelAfter);

        double currentXP = getPlayerXP(player);
        String percentDifference = String.format("%.1f", Math.min((currentXP - Levels.getXPRequiredForLevel(levelBefore)) / (Levels.getXPRequiredForLevel(levelBefore + 1) - Levels.getXPRequiredForLevel(levelBefore)), 1) * 100);

        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
        player.sendMessage(ChatColor.GREEN + "+" + amount + " XP " + ChatColor.DARK_GRAY + "(" + percentDifference + "%)");

        if (levelBefore < levelAfter) {

            PotionEffect resist = new PotionEffect(PotionEffectType.RESISTANCE, 100, 5);
            PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, 100, 5);
            PotionEffect sat = new PotionEffect(PotionEffectType.SATURATION, 100, 5);
            player.addPotionEffect(resist);
            player.addPotionEffect(regen);
            player.addPotionEffect(sat);

            player.playSound(player, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 0.8f);


            player.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "-----------------------------------"); //27 dashes
            player.sendMessage("");
            player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "               " + "\u2605 LEVEL UP! \u2605" + "             ");
            player.sendMessage(ChatColor.GRAY + "                You are now " + ChatColor.WHITE + "Level " + calculatePlayerLevel(getPlayerXP(player)));
            if (levelAfter == 5)
            {
                player.sendMessage("");
                player.sendMessage(ChatColor.GREEN + "           Use /class to select a class. " + "                ");
            }

            player.sendMessage("");
            player.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "-----------------------------------");

            for (Player p : Bukkit.getServer().getOnlinePlayers())
            {
                TextComponent text = Component.text(player.getName(), TextColor.color(250, 250, 210))
                        .append(Component.text(" is now ", TextColor.color(250, 250, 130)))
                        .append(Component.text("Level " + levelAfter, TextColor.color(255, 210, 84)))
                        .append(Component.text("!", TextColor.color(250, 250, 130)));
                p.sendMessage(text);
            }

            for (int i = 0; i < 3; i++)
            {
                Random random = new Random();
                Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(),
                        EntityType.FIREWORK_ROCKET);

                FireworkMeta fwm = fw.getFireworkMeta();
                Random r = new Random();
                int rt = r.nextInt(5) + 1;
                FireworkEffect.Type type = FireworkEffect.Type.BALL;
                if (rt == 2) type = FireworkEffect.Type.BALL_LARGE;
                if (rt == 3) type = FireworkEffect.Type.BURST;
                if (rt == 4) type = FireworkEffect.Type.CREEPER;
                if (rt == 5) type = FireworkEffect.Type.STAR;

                Color c1 = Color.fromRGB(r.nextInt(100) + 150, r.nextInt(100) + 150, r.nextInt(100) + 150);
                Color c2 = Color.fromRGB(r.nextInt(100) + 150, r.nextInt(100) + 150, r.nextInt(100) + 150);

                FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();

                fwm.addEffect(effect);

                int rp = r.nextInt(2) + 1;
                fwm.setPower(rp);

                fw.setFireworkMeta(fwm);
            }
        }
    }

    @EventHandler
    public void PreventFireworkDamage(EntityDamageByEntityEvent e)
    {
        if (e.getDamager() instanceof Firework)
        {
            e.setCancelled(true);
        }
    }
}
