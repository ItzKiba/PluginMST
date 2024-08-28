package me.itzkiba.pluginMST.commands.showitem;

import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ShowItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player))
        {
            System.out.println("You must be a player to execute this command.");
            return false;
        }

        Player player = (Player) sender;

        if (args.length > 0)
        {
            player.sendMessage(Component.text(ChatColor.DARK_RED + "Invalid command usage.\n" + ChatColor.RED + "Don't include any arguments, ya bimbo."));
            return true;
        }

        if (player.getEquipment().getItemInMainHand().getType() == Material.AIR)
        {
            player.sendMessage(Component.text(ChatColor.DARK_RED + "Invalid command usage.\n" + ChatColor.RED + "You must be holding an item."));
            return true;
        }

        ItemStack item = player.getEquipment().getItemInMainHand();

        if (!Stats.isCustom(item))
        {
            player.sendMessage(Component.text(ChatColor.DARK_RED + "Invalid command usage.\n" + ChatColor.RED + "This item cannot be broadcasted."));
            return true;
        }

        NamedTextColor rarityColor = NamedTextColor.WHITE;

        switch(Stats.getRarity(item))
        {
            case 1:
                rarityColor = NamedTextColor.GREEN;
                break;
            case 2:
                rarityColor = NamedTextColor.BLUE;
                break;
            case 3:
                rarityColor = NamedTextColor.DARK_PURPLE;
                break;
            case 4:
                rarityColor = NamedTextColor.GOLD;
                break;
            case 5:
                rarityColor = NamedTextColor.DARK_RED;
                break;
        }

        final NamedTextColor colorToUse = rarityColor;

        for (Player p : Bukkit.getServer().getOnlinePlayers())
        {

            String itemName = Stats.getItemName(item);

            TextComponent loreComponent = Component.text(itemName, colorToUse).append(Component.text("\n"));
            for (Component c : item.lore())
            {
                loreComponent = loreComponent.append(c).append(Component.text("\n"));
            }

            TextComponent text = (Component.text(itemName, colorToUse).hoverEvent(loreComponent));

            p.sendMessage(Component.text("<" + player.getName() + "> ", TextColor.color(207, 255, 180)).append(text));
        }

        return true;
    }
}
