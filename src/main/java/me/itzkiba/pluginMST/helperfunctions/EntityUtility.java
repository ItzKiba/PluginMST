package me.itzkiba.pluginMST.helperfunctions;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;
import java.util.List;

public class EntityUtility {

    public static LivingEntity getClosestLivingEntity(Location location, double radius)
    {
        LivingEntity closestEntity = null;
        double distanceSoFar = Double.MAX_VALUE;
        for (LivingEntity e : location.getWorld().getNearbyLivingEntities(location, radius))
        {
            if (e.getLocation().distance(location) < distanceSoFar)
            {
                distanceSoFar = e.getLocation().distance(location);
                closestEntity = e;
            }
        }

        return closestEntity;
    }
}
