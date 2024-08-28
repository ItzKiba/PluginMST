package me.itzkiba.pluginMST.listeners.spawnitem;

import me.itzkiba.pluginMST.helperfunctions.RandomNumber;

public class BowName {
    private static final String[] bowArray = {
            "Bow",
            "Shortbow",
            "Longbow",
            "Flatbow",
            "Repeater",
            "Arbalest",
            "Ballista"
    };

    public static String get()
    {
        int index = RandomNumber.generateInt(0, bowArray.length-1);
        return bowArray[index];
    }
}
