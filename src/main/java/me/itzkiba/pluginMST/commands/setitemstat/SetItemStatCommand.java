package me.itzkiba.pluginMST.commands.setitemstat;

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

public class SetItemStatCommand implements CommandExecutor {

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

        if (args.length != 2)
        {
            player.sendMessage(Component.text(ChatColor.DARK_RED + "Invalid command usage.\n" + ChatColor.RED + "Specify a STAT and VALUE while holding an item."));
            return true;
        }

        int value;
        String stat = args[0];
        ItemStack item;
        try {
            value = Integer.parseInt(args[1]);
        } catch (NumberFormatException e)
        {
            player.sendMessage(Component.text(ChatColor.DARK_RED + "Invalid command usage.\n" + ChatColor.RED + "Specify only integers for VALUE."));
            return true;
        }

        item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) {
            player.sendMessage(Component.text(ChatColor.DARK_RED + "Invalid command usage.\n" + ChatColor.RED + "You must hold an item while executing this command."));
            return true;
        }

        Stats.setCustom(item, true);
        switch(stat)
        {
            case "damage":
                Stats.setBaseDamageStat(item, value);
                break;
            case "defense":
                Stats.setBaseDefenseStat(item, value);
                break;
            case "health":
                Stats.setBaseHealthStat(item, value);
                break;
            case "crit":
                Stats.setBaseCritStat(item, value);
                break;
            case "rangeddamage":
                Stats.setBaseRangedDamageStat(item, value);
                break;
            case "magicdamage":
                Stats.setBaseMagicDamageStat(item, value);
                break;
            case "rarity":
                Stats.setRarity(item, value);
                break;
            case "mana":
                Stats.setBaseMaxManaStat(item, value);
                break;
            case "attackspeed":
                Stats.setBaseAttackSpeedStat(item, value);
                break;
            case "speed":
                Stats.setBaseSpeedStat(item, value);
                break;
            case "level":
                Stats.setItemLevel(item, value);
                break;
            case "power":
                Stats.setBasePowerStat(item, value);
                break;
            default:
                player.sendMessage(Component.text(ChatColor.RED + "Invalid stat provided."));
                return false;
        }

        return true;
    }
}
