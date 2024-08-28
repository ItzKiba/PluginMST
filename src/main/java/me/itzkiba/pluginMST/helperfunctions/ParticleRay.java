package me.itzkiba.pluginMST.helperfunctions;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class ParticleRay {

    public static void createLine(Location startPosition, Vector direction, double distance, int particlesPerBlock, Particle particle, World world)
    {

        double step = 1.0 / particlesPerBlock;

        for (double i = 0.5; i < distance * particlesPerBlock; i++)
        {
            direction.normalize();
            direction.multiply(step * i);
            startPosition.add(direction);
            world.spawnParticle(particle, startPosition, 1, 0, 0, 0, 0);
            startPosition.subtract(direction);
        }
    }

    public static void createCircle(Location center, double radius, int particleDensity, Particle particle)
    {
        double step = 360.0 / particleDensity;
        for (double i = 0; i < 360; i+=step)
        {
            double addX = radius * Math.sin(Math.toRadians(i));
            double addZ = radius * Math.cos(Math.toRadians(i));

            Location newLoc = new Location(center.getWorld(), center.getX() + addX, center.getY(), center.getZ() + addZ);
            newLoc.getWorld().spawnParticle(particle, newLoc, 1, 0, 0, 0, 0);
        }
    }

    public static void particleRay2Points(Location start, Location end, int particles, Particle particle, double speed)
    {
        double xStep = (end.getX() - start.getX()) / particles;
        double yStep = (end.getY() - start.getY()) / particles;
        double zStep = (end.getZ() - start.getZ()) / particles;

        World world = start.getWorld();

        for (double i = 0; i < particles; i++)
        {
            world.spawnParticle(particle, start.getX() + xStep*i, start.getY() + yStep*i, start.getZ() + zStep*i, 1, 0, 0, 0, speed);
        }

    }

    public static void particleRay2Points(Location start, Location end, int particles, Particle particle, double speed, boolean force)
    {
        double xStep = (end.getX() - start.getX()) / particles;
        double yStep = (end.getY() - start.getY()) / particles;
        double zStep = (end.getZ() - start.getZ()) / particles;

        World world = start.getWorld();

        for (double i = 0; i < particles; i++)
        {
            world.spawnParticle(particle, start.getX() + xStep*i, start.getY() + yStep*i, start.getZ() + zStep*i, 1, 0, 0, 0, speed, null, force);
        }

    }

    public static void particleRay2PointsDust(Location start, Location end, int particles, Particle.DustOptions options, boolean force)
    {
        double xStep = (end.getX() - start.getX()) / particles;
        double yStep = (end.getY() - start.getY()) / particles;
        double zStep = (end.getZ() - start.getZ()) / particles;

        World world = start.getWorld();

        for (double i = 0; i < particles; i++)
        {
            world.spawnParticle(Particle.DUST, start.getX() + xStep*i, start.getY() + yStep*i, start.getZ() + zStep*i, 1, 0, 0, 0, 0, options, force);
        }

    }
}
