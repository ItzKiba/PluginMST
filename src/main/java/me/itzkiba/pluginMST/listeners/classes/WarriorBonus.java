package me.itzkiba.pluginMST.listeners.classes;

import me.itzkiba.pluginMST.PluginMST;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;

public class WarriorBonus implements Listener {

    static NamespacedKey STACKS = new NamespacedKey(PluginMST.getPlugin(), "stacks");

    public static void setPlayerStacks(Player p, int v)
    {
        p.getPersistentDataContainer().set(STACKS, PersistentDataType.INTEGER, v);
    }

    public static int getPlayerStacks(Player p)
    {
        if (!p.getPersistentDataContainer().has(STACKS))
        {
            return 0;
        }
        return p.getPersistentDataContainer().get(STACKS, PersistentDataType.INTEGER);
    }


}
