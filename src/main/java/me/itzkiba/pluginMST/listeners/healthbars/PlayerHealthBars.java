package me.itzkiba.pluginMST.listeners.healthbars;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.potion.PotionEffectType;

public class PlayerHealthBars implements Listener
{
    @EventHandler
    public void HealthBarsLoop(ServerLoadEvent e)
    {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginMST.getPlugin(),() -> {

            for (World world : Bukkit.getServer().getWorlds())
            {
                for (Player player : world.getPlayers())
                {
                    if (true || (player.getGameMode() == GameMode.ADVENTURE || player.getGameMode() == GameMode.SURVIVAL)) {
                        int currentHP = (int)(Math.ceil(player.getHealth() * 5) + Math.ceil(player.getAbsorptionAmount() * 5));
                        int maxHP = (int) player.getMaxHealth() * 5;
                        int currentDefense = Stats.getEntityDefenseStat(player);
                        int maxMana = Stats.getEntityMaxManaStat(player);
                        int currentMana = (int)Stats.getEntityManaStat(player);

                        ChatColor color = ChatColor.RED;
                        if (player.hasPotionEffect(PotionEffectType.ABSORPTION) && player.getAbsorptionAmount() > 0) {
                            color = ChatColor.YELLOW;
                        }
                        if (player.hasPotionEffect(PotionEffectType.POISON)) {
                            color = ChatColor.DARK_GREEN;
                        }
                        if (player.hasPotionEffect(PotionEffectType.WITHER)) {
                            color = ChatColor.DARK_GRAY;
                        }

                        Component text;

                        if (maxMana > 0) {
                            text = Component.text(
                                    color + "\u2764 " + currentHP + " | " + maxHP + "            " + ChatColor.WHITE + "\u26E8 " + currentDefense
                                            + "            " + ChatColor.AQUA + "\u2735 " + currentMana + " | " + maxMana + "      "
                            );
                        }
                        else
                            text = Component.text(
                                    color + "   \u2764 " + currentHP + " / " + maxHP + "                      " + ChatColor.WHITE + "\u26E8 " + currentDefense + "    "
                            );


                        player.sendActionBar(text);
                    }
                }
            }

        }, 1, 1);
    }

}
