package me.itzkiba.pluginMST.listeners.itemconverts;

import io.papermc.paper.event.block.CompostItemEvent;
import io.papermc.paper.event.entity.EntityCompostItemEvent;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPlaceEvent;

public class PreventFlowerPlacing implements Listener {
    @EventHandler
    public void placeFlowerCancel(BlockPlaceEvent e) {
        if (Stats.getBaseMagicDamageStat(e.getItemInHand()) > 0) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void compostFlowerCancel(EntityCompostItemEvent e) {
        if (Stats.getBaseMagicDamageStat(e.getItem()) > 0) {
            e.setCancelled(true);
        }
    }
}
