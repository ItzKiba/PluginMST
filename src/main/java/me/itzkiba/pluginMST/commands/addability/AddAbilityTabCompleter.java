package me.itzkiba.pluginMST.commands.addability;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AddAbilityTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1)
        {
            List<String> a = new ArrayList<>();
            a.add("multistrike");
            a.add("heavyguard");
            a.add("execution");
            a.add("seismicslam");
            a.add("arrowstorm");
            a.add("arrowstorm2");
            a.add("grapplingshot");
            a.add("sculkblast");
            a.add("evasion");
            a.add("remedy");
            a.add("manabolt");
            a.add("warp");
            a.add("soulsurge");
            a.add("inferno");
            a.add("overcharge");
            a.add("lavafissure");
            return a;
        }

        return null;
    }
}
