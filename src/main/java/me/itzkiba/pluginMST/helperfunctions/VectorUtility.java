package me.itzkiba.pluginMST.helperfunctions;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VectorUtility {

    public static Vector getDirectionBetweenLocations(Location Start, Location End) {
        Vector from = Start.toVector();
        Vector to = End.toVector();
        return to.subtract(from);
    }

    public static Location getLocalCoord(double x, double y, double z, Location origin) {
        Location arrival = origin.clone();

        Vector dirX = new Location(arrival.getWorld(), 0, 0, 0, Location.normalizeYaw(arrival.getYaw()-90),
                arrival.getPitch()).getDirection().normalize();
        Vector dirY = new Location(arrival.getWorld(), 0, 0, 0, arrival.getYaw(),
                arrival.getPitch()-90).getDirection().normalize();
        Vector dirZ = arrival.getDirection().normalize();

        return arrival.add(dirX.multiply(x))
                .add(dirY.multiply(y))
                .add(dirZ.multiply(z));
    }
}
