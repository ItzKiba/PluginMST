package me.itzkiba.pluginMST.listeners.spawnitem;

import me.itzkiba.pluginMST.helperfunctions.RandomNumber;

public class MagicName {
    private static final String[] magicArray = {
            "Staff",
            "Sceptre",
            "Wand",
            "Rod"
    };

    public static String get()
    {
        int index = RandomNumber.generateInt(0, magicArray.length-1);
        return magicArray[index];
    }
}
