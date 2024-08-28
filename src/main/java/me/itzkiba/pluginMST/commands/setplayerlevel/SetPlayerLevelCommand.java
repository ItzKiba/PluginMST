package me.itzkiba.pluginMST.commands.setplayerlevel;

import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetPlayerLevelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player))
        {
            System.out.println("You must be a player to execute this command.");
            return false;
        }

        Player p = (Player) sender;

        if (!p.isOp())
        {
            p.sendMessage(ChatColor.RED + "You must be a server operator to execute this command.");
            return true;
        }

        if (args.length != 1)
        {
            p.sendMessage(ChatColor.RED + "Please input a level.");
            return false;
        }

        int level;
        try {
            level = Integer.parseInt(args[0]);
        } catch (NumberFormatException e)
        {
            p.sendMessage(ChatColor.RED + "You must input an integer for the level.");
            return false;
        }

        Levels.setPlayerLevel(p, level);
        Levels.setPlayerXP(p, Levels.getXPRequiredForLevel(level) + 1);
        p.sendMessage(ChatColor.GREEN + "Your level has been set to " + ChatColor.GOLD + level);
        p.sendMessage(ChatColor.GREEN + "Similarly, your total XP has been set to " + ChatColor.GOLD + Levels.getXPRequiredForLevel(level));
        return true;
    }
}
