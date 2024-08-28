package me.itzkiba.pluginMST.listeners.persistentdatakeys;

import me.itzkiba.pluginMST.PluginMST;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class Classes {

    static NamespacedKey CLASS = new NamespacedKey(PluginMST.getPlugin(), "class");

    // CLASS=0 -> no class
    // CLASS=1 -> warrior
    // CLASS=2 -> archer
    // CLASS=3 -> sorcerer

    public static void setPlayerClass(Player p, int c)
    {
        p.getPersistentDataContainer().set(CLASS, PersistentDataType.INTEGER, c);
    }
    public static int getPlayerClass(Player p)
    {
        if (!p.getPersistentDataContainer().has(CLASS))
        {
            return 0;
        }
        return p.getPersistentDataContainer().get(CLASS, PersistentDataType.INTEGER);
    }

}
