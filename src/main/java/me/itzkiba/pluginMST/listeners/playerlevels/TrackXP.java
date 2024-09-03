package me.itzkiba.pluginMST.listeners.playerlevels;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.listeners.itemconverts.ConvertDefaultItems;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Levels;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import me.itzkiba.pluginMST.listeners.spawnitem.CreateRandomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import java.util.Random;

public class TrackXP implements Listener {


    @EventHandler
    public void IncreaseXP(EntityDeathEvent e)
    {
        if (e.getEntity().getKiller() != null)
        {
            Player player = e.getEntity().getKiller();
            Levels.increasePlayerXP(player, e.getEntity());

            int level = Stats.getEntityLevel(e.getEntity());

            if (level == 0)
            {
                return;
            }

            Random random = new Random();

            int bound = 20;
            if (e.getEntity().getWorld().getEnvironment() == World.Environment.NETHER)
                bound += 10;
            if (e.getEntity().getWorld().getEnvironment() == World.Environment.THE_END)
                bound += 10;

            if (random.nextInt(101) < bound)
            {
                spawnItemFromEntityDeath(e.getEntity(), player);
            }

        }
    }

    private void spawnItemFromEntityDeath(LivingEntity e, Player player)
    {
        Random random = new Random();
        int level = Math.max(1, Stats.getEntityLevel(e) + random.nextInt(6) - 3);

        double chance = random.nextDouble(1);
        if (level > 75)
        {
            chance = random.nextDouble(0.6);
        }

        int rarity = 0;

        if (chance < 0.5)
        {
            //uncommon
            rarity = 1;
        }
        if (chance < 0.25)
        {
            //rare
            rarity = 2;
        }
        if (chance < 0.125)
        {
            //epic
            rarity = 3;
        }
        if (chance < 0.0375)
        {
            //legendary
            rarity = 4;
        }
        if (chance < 0.005 && level > 80)
        {
            //mythical
            rarity = 5;
        }

        // HARD CAP ON RARITY BASED ON LEVEL
        if (rarity > 3 && level <= 30)
        {
            rarity = 3;
        }

        if (level > 30 && rarity < 1)
        {
            rarity = 1;
        }

        if (level > 60 && rarity < 2)
        {
            rarity = 2;
        }

        if (level > 100 && rarity < 3)
        {
            rarity = 3;
        }

        int itemtypeRand = random.nextInt(14);
        int itemType = 0;
        String weaponClass = "";
        switch(itemtypeRand) {
            case 0:
                itemType = 0;
                weaponClass = "axe";
                break;
            case 1:
                itemType = 0;
                weaponClass = "sword";
                break;
            case 2:
            case 3:
                itemType = 1;
                weaponClass = "bow";
                break;
            case 4:
            case 5:
                itemType = 2;
                weaponClass = "magic";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                itemType = 3;
                weaponClass = "armor";
                break;
        }

        Material m = CreateRandomItem.getMaterial(weaponClass, level);
        ItemStack item = CreateRandomItem.createRandom(m, itemType, rarity, level);

        Item itemEntity = e.getWorld().dropItem(e.getLocation(), item);
        itemEntity.setPickupDelay(20);
        itemEntity.setOwner(player.getUniqueId()); // never null?
        itemEntity.setCanMobPickup(false);
        itemEntity.setVelocity(new Vector(0, 0.5, 0));

        switch(rarity) {
            case 1:
                PluginMST.getuTeam().addEntry(itemEntity.getUniqueId().toString());
                break;
            case 2:
                PluginMST.getrTeam().addEntry(itemEntity.getUniqueId().toString());
                break;
            case 3:
                PluginMST.geteTeam().addEntry(itemEntity.getUniqueId().toString());
                break;
            case 4:
                PluginMST.getlTeam().addEntry(itemEntity.getUniqueId().toString());
                break;
            case 5:
                PluginMST.getmTeam().addEntry(itemEntity.getUniqueId().toString());
                break;
        }

        itemEntity.setGlowing(true);



        if (rarity < 2)
        {
            return;
        }

        new BukkitRunnable()
        {
            int i = 0;
            public void run()
            {
                if (i == 0)
                {
                    player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 0.594604F);
                }
                if (i == 1)
                {
                    player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 0.890899F);
                }
                if (i == 2)
                {
                    player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1.334840F);
                }
                if (i == 3)
                {
                    player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1.498307F);
                    player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1.189207F);
                }

                i++;
                if (i == 4)
                {
                    this.cancel();
                }
            }
        }.runTaskTimer(PluginMST.getPlugin(), 0, 4);

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

        Bukkit.getScheduler().scheduleSyncDelayedTask(PluginMST.getPlugin(), () -> {
            ConvertDefaultItems.addEnchantsToItem(item);
            ConvertDefaultItems.setNewItemLore(item, player);
            for (Player p : Bukkit.getServer().getOnlinePlayers())
            {

                String itemName = Stats.getItemName(item);

                TextComponent loreComponent = Component.text(itemName, colorToUse).append(Component.text("\n"));
                for (Component c : item.lore())
                {
                    loreComponent = loreComponent.append(c).append(Component.text("\n"));
                }

                TextComponent text = Component.text(player.getName(), TextColor.color(250, 250, 210))
                        .append(Component.text(" found ", TextColor.color(250, 250, 130)))
                        .append(Component.text(itemName, colorToUse).hoverEvent(loreComponent))
                        .append(Component.text("!", TextColor.color(250, 250, 130)));
                p.sendMessage(text);
            }
        }, 5);



    }
}
