package me.itzkiba.pluginMST.helperfunctions;

public class RandomNumber {
    public static int generateInt(int min, int max)
    {
        return (int)(Math.random()*(max-min+1)+min);
    }
}
