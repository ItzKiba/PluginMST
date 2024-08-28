package me.itzkiba.pluginMST.commands.selectclass;

import me.itzkiba.pluginMST.listeners.menus.ClassMenu;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SelectClassCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player))
        {
            System.out.println("You must be a player to execute this command.");
            return false;
        }

        Player player = (Player) sender;

        if (Levels.getPlayerLevel(player) < 5)
        {
            player.sendMessage(ChatColor.RED + "You must be Level 5 to select a class.");
            return true;
        }

        player.sendMessage(ChatColor.GREEN + "Opened class menu.");
        ClassMenu.open(player);
        return true;
    }
}
