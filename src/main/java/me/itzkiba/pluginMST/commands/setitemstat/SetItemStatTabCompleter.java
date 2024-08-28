package me.itzkiba.pluginMST.commands.setitemstat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SetItemStatTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1)
        {
            List<String> stats = new ArrayList<>();
            stats.add("damage");
            stats.add("rangeddamage");
            stats.add("defense");
            stats.add("health");
            stats.add("crit");
            stats.add("magicdamage");
            stats.add("rarity");
            stats.add("level");
            stats.add("mana");
            stats.add("attackspeed");
            stats.add("speed");
            stats.add("power");
            return stats;
        }


        return null;
    }
}
