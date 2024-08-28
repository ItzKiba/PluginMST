package me.itzkiba.pluginMST.commands.setmana;

import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SetPlayerManaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player))
        {
            System.out.println("You must be a player to execute this command.");
            return false;
        }

        Player player = (Player) sender;

        if (!player.isOp())
        {
            player.sendMessage(ChatColor.RED + "You must be a server operator to execute this command.");
            return true;
        }

        if (args.length != 1)
        {
            player.sendMessage(ChatColor.RED + "You must specify a value to set your Mana to.");
            return true;
        }

        int value;
        String stat = args[0];
        ItemStack item;
        try {
            value = Integer.parseInt(args[0]);
        } catch (NumberFormatException e)
        {
            player.sendMessage(Component.text(ChatColor.DARK_RED + "Invalid command usage.\n" + ChatColor.RED + "Specify only integers for VALUE."));
            return true;
        }

        Stats.setEntityManaStat(player, value);
        player.sendMessage(ChatColor.GREEN + "Your Mana has been set to " + ChatColor.AQUA + value);

        return true;
    }
}
