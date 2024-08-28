package me.itzkiba.pluginMST.commands.createcustomitem;

import me.itzkiba.pluginMST.listeners.spawnitem.CreateRandomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CreateCustomItemCommand implements CommandExecutor {
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

        if (args.length != 3)
        {
            return false;
        }

        String weaponType = args[0];

        int weaponClass = 0;

        if (weaponType.equals("bow"))
        {
            weaponClass = 1;
        }
        if (weaponType.equals("magic"))
        {
            weaponClass = 2;
        }
        if (weaponType.equals("armor"))
        {
            weaponClass = 3;
        }

        int rarity;
        try {
            rarity = Integer.parseInt(args[1]);
        } catch (NumberFormatException e)
        {
            p.sendMessage(ChatColor.RED + "You must input an integer for the rarity.");
            return false;
        }

        int level;
        try {
            level = Integer.parseInt(args[2]);
        } catch (NumberFormatException e)
        {
            p.sendMessage(ChatColor.RED + "You must input an integer for the level.");
            return false;
        }

        Material m = CreateRandomItem.getMaterial(weaponType, level);
        ItemStack item = CreateRandomItem.createRandom(m, weaponClass, rarity, level);

        p.getInventory().addItem(item);


        return true;
    }
}
