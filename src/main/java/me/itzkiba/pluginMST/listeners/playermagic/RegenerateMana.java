package me.itzkiba.pluginMST.listeners.playermagic;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;


public class RegenerateMana implements Listener {

    @EventHandler
    public void RegenerateManaLoop(ServerLoadEvent e) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginMST.getPlugin(), () -> {

            for (World world : Bukkit.getServer().getWorlds()) {
                for (Player player : world.getPlayers()) {

                    if (Stats.getEntityManaStat(player) < Stats.getEntityMaxManaStat(player))
                    {
                        double manaIncrease = (Stats.getEntityMaxManaStat(player) * 0.01) + 1;
                        Stats.setEntityManaStat(player, Stats.getEntityManaStat(player) + manaIncrease);
                    }

                    if (Stats.getEntityManaStat(player) > Stats.getEntityMaxManaStat(player))
                    {
                        Stats.setEntityManaStat(player, Stats.getEntityMaxManaStat(player));
                        return;
                    }

                    if (Stats.getEntityManaStat(player) < 0) {
                        Stats.setEntityManaStat(player, 0);
                    }

                    if (player.getHealth() < player.getMaxHealth()) {
                        double regenValue = player.getMaxHealth() * 0.005;
                        player.heal(regenValue);
                    }

                }
            }
        }, 0, 10);
    }
}
