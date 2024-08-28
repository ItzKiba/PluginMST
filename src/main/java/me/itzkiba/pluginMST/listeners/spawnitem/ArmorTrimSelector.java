package me.itzkiba.pluginMST.listeners.spawnitem;

import me.itzkiba.pluginMST.helperfunctions.RandomNumber;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.ArrayList;

public class ArmorTrimSelector {

    public static ArmorTrim get(int rarity, int level) {
        ArrayList<TrimPattern> trims = new ArrayList<TrimPattern>();

        if (level <= 29) {
            trims.add(TrimPattern.COAST);
            trims.add(TrimPattern.HOST);
        }
        if (level >= 30 && level <= 39) {
            trims.add(TrimPattern.SHAPER);
            trims.add(TrimPattern.SPIRE);
        }
        if (level >= 40 && level <= 49) {
            trims.add(TrimPattern.RAISER);
            trims.add(TrimPattern.RIB);
        }
        if (level >= 50 && level <= 59) {
            trims.add(TrimPattern.SNOUT);
            trims.add(TrimPattern.WARD);
        }
        if (level >= 60 && level <= 69) {
            trims.add(TrimPattern.WAYFINDER);
            trims.add(TrimPattern.WILD);
        }
        if (level >= 70 && level <= 79) {
            trims.add(TrimPattern.TIDE);
            trims.add(TrimPattern.VEX);
        }
        if (level >= 80 && level <= 89) {
            trims.add(TrimPattern.EYE);
            trims.add(TrimPattern.SENTRY);
        }
        if (level >= 90) {
            trims.add(TrimPattern.SILENCE);
            trims.add(TrimPattern.DUNE);
        }

        TrimPattern trimToAdd = trims.get(RandomNumber.generateInt(0, 1));
        TrimMaterial mat = TrimMaterial.IRON;

        switch(rarity) {
            case 1:
                mat = TrimMaterial.EMERALD;
                break;
            case 2:
                mat = TrimMaterial.LAPIS;
                break;
            case 3:
                mat = TrimMaterial.AMETHYST;
                break;
            case 4:
                mat = TrimMaterial.GOLD;
                break;
            case 5:
                mat = TrimMaterial.REDSTONE;
                break;
        }

        return new ArmorTrim(mat, trimToAdd);

    }
}
