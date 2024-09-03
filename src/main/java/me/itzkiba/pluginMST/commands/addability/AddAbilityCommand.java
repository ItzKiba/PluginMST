package me.itzkiba.pluginMST.commands.addability;

import me.itzkiba.pluginMST.listeners.abilities.Abilities;
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

public class AddAbilityCommand implements CommandExecutor {
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
            player.sendMessage(Component.text(ChatColor.DARK_RED + "Invalid command usage.\n" + ChatColor.RED + "Specify an ability name."));
            return true;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR)
        {
            player.sendMessage(Component.text(ChatColor.DARK_RED + "Invalid command usage.\n" + ChatColor.RED + "You must be holding an item."));
            return true;
        }

        String name = args[0];
        Stats.setCustom(item, true);

        boolean invalid = false;

        switch(name)
        {
            case "multistrike":
                Abilities.setItemAbilityID(item, 1000);
                break;
            case "heavyguard":
                Abilities.setItemAbilityID(item, 1001);
                break;
            case "execution":
                Abilities.setItemAbilityID(item, 1002);
                break;
            case "seismicslam":
                Abilities.setItemAbilityID(item, 1003);
                break;
            case "lavafissure":
                Abilities.setItemAbilityID(item, 1004);
                break;
            case "arrowstorm":
                Abilities.setItemAbilityID(item, 2000);
                break;
            case "grapplingshot":
                Abilities.setItemAbilityID(item, 2001);
                break;
            case "sculkblast":
                Abilities.setItemAbilityID(item, 2002);
                break;
            case "evasion":
                Abilities.setItemAbilityID(item, 2003);
                break;
            case "arrowstorm2":
                Abilities.setItemAbilityID(item, 2004);
                break;
            case "overcharge":
                Abilities.setItemAbilityID(item, 2005);
                break;
            case "remedy":
                Abilities.setItemAbilityID(item, 3000);
                break;
            case "manabolt":
                Abilities.setItemAbilityID(item, 3001);
                break;
            case "warp":
                Abilities.setItemAbilityID(item, 3002);
                break;
            case "soulsurge":
                Abilities.setItemAbilityID(item, 3003);
                break;
            case "inferno":
                Abilities.setItemAbilityID(item, 3004);
                break;
            case "hyperbolt":
                Abilities.setItemAbilityID(item, 3005);
                break;

            default:
                invalid = true;
                break;
        }

        if (invalid)
        {
            player.sendMessage(Component.text(ChatColor.DARK_RED + "Invalid command usage.\n" + ChatColor.RED + "That ability name doesn't exist."));
            return true;
        }

        return true;
    }
}
