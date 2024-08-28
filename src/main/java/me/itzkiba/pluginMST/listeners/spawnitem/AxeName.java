package me.itzkiba.pluginMST.listeners.spawnitem;

import me.itzkiba.pluginMST.helperfunctions.RandomNumber;

public class AxeName {
    private static final String[] axeArray = {
            "Axe",
            "Battleaxe",
            "Doubleaxe",
            "Waraxe",
            "Executioner's Axe",
            "Viking Axe",
            "Crescent Axe",
            "Greataxe",
            "Hatchet",
            "Halberd",
            "Maul",
            "Warhammer"
    };

    public static String get()
    {
        int index = RandomNumber.generateInt(0, axeArray.length-1);
        return axeArray[index];
    }
}
