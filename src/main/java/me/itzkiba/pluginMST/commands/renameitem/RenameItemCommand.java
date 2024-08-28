package me.itzkiba.pluginMST.commands.renameitem;

import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class RenameItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player))
        {
            System.out.println("You must be a player to execute this command.");
            return false;
        }

        Player player = (Player) sender;

        if (Levels.getPlayerLevel(player) < 100)
        {
            player.sendMessage(ChatColor.RED + "You must be Level 100 to use this command.");
            return true;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) {
            player.sendMessage(Component.text(ChatColor.DARK_RED + "Invalid command usage.\n" + ChatColor.RED + "You must hold an item while executing this command."));
            return true;
        }

        if (args.length < 1)
        {
            player.sendMessage(ChatColor.RED + "Please specify a name for the item.");
            return false;
        }

        StringBuilder name = new StringBuilder(new String());
        for (int i = 0; i < args.length; i++)
        {
            name.append(args[i]);
            if (i < args.length - 1)
            {
                name.append(" ");
            }
        }

        Stats.setItemName(item, name.toString());
        return true;
    }
}
