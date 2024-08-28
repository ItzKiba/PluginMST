package me.itzkiba.pluginMST.commands.createcustomitem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CreateCustomItemTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1)
        {
            List<String> weaponTypes = new ArrayList<>();
            weaponTypes.add("sword");
            weaponTypes.add("axe");
            weaponTypes.add("bow");
            weaponTypes.add("magic");
            weaponTypes.add("armor");
            return weaponTypes;
        }
        return null;
    }
}
