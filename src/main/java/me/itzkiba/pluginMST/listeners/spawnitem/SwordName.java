package me.itzkiba.pluginMST.listeners.spawnitem;

import me.itzkiba.pluginMST.helperfunctions.RandomNumber;

public class SwordName {
    private static final String[] swordArray = {
            "Katana",
            "Longsword",
            "Gladius",
            "Falchion",
            "Scimitar",
            "Cutlass",
            "Saber",
            "Claymore",
            "Rapier",
            "Broadsword",
            "Sword",
            "Blade",
            "Dagger",
            "Edge"
    };

    public static String get()
    {
        int index = RandomNumber.generateInt(0, swordArray.length-1);
        return swordArray[index];
    }
}
