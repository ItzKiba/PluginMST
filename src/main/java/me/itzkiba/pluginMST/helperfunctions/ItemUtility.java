package me.itzkiba.pluginMST.helperfunctions;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ItemUtility {

    public static HashMap<String, Integer> getDefaultItemStats(ItemStack item)
    {
        HashMap<String, Integer> map = new HashMap<>();
        int damage = 0;
        int defense = 0;
        int ranged_damage = 0;

        int rarity = 0;

        // ATTACK DAMAGE
        switch(item.getType())
        {
            case WOODEN_SWORD:
            case GOLDEN_SWORD:
            case IRON_PICKAXE:
                damage = 20;
                break;
            case WOODEN_AXE:
            case GOLDEN_AXE:
                damage = 35;
                break;
            case DIAMOND_SWORD:
                damage = 50;
                rarity = 1;
                break;
            case WOODEN_PICKAXE:
            case GOLDEN_PICKAXE:
                damage = 10;
                break;
            case WOODEN_SHOVEL:
            case GOLDEN_SHOVEL:
                damage = 12;
                break;
            case WOODEN_HOE:
            case GOLDEN_HOE:
            case STONE_HOE:
            case IRON_HOE:
                damage = 5;
                break;
            case DIAMOND_HOE:
            case NETHERITE_HOE:
                damage = 5;
                rarity = 1;
                break;
            case STONE_SWORD:
                damage = 25;
                break;
            case DIAMOND_PICKAXE:
                damage = 25;
                rarity = 1;
                break;
            case STONE_AXE:
            case IRON_AXE:
                damage = 45;
                break;
            case DIAMOND_AXE:
                damage = 55;
                rarity = 1;
                break;
            case TRIDENT:
                damage = 45;
                rarity = 1;
                break;
            case STONE_PICKAXE:
                damage = 15;
                break;
            case STONE_SHOVEL:
                damage = 17;
                break;
            case IRON_SWORD:
                damage = 30;
                break;
            case NETHERITE_PICKAXE:
                damage = 30;
                rarity = 1;
                break;
            case IRON_SHOVEL:
                damage = 22;
                break;
            case DIAMOND_SHOVEL:
                damage = 27;
                rarity = 1;
                break;
            case NETHERITE_SWORD:
                damage = 70;
                rarity = 1;
                break;
            case NETHERITE_AXE:
                damage = 80;
                rarity = 1;
                break;
            case NETHERITE_SHOVEL:
                damage = 32;
                rarity = 1;
                break;
            case BOW:
                ranged_damage = 30;
                break;
            case CROSSBOW:
                ranged_damage = 40;
                break;
        }

        // DEFENSE
        switch(item.getType())
        {
            case LEATHER_HELMET:
            case LEATHER_BOOTS:
                defense = 5;
                break;
            case LEATHER_CHESTPLATE:
                defense = 15;
                break;
            case LEATHER_LEGGINGS:
                defense = 10;
                break;
            case GOLDEN_HELMET:
                defense = 10;
                break;
            case GOLDEN_CHESTPLATE:
                defense = 25;
                break;
            case GOLDEN_LEGGINGS:
                defense = 15;
                break;
            case GOLDEN_BOOTS:
                defense = 5;
                break;
            case CHAINMAIL_HELMET:
                defense = 5;
                break;
            case CHAINMAIL_CHESTPLATE:
                defense = 25;
                break;
            case CHAINMAIL_LEGGINGS:
                defense = 20;
                break;
            case CHAINMAIL_BOOTS:
                defense = 5;
                break;
            case IRON_HELMET:
                defense = 15;
                break;
            case IRON_CHESTPLATE:
                defense = 35;
                break;
            case IRON_LEGGINGS:
                defense = 30;
                break;
            case IRON_BOOTS:
                defense = 15;
                break;
            case DIAMOND_HELMET:
                defense = 25;
                rarity = 1;
                break;
            case DIAMOND_CHESTPLATE:
                defense = 50;
                rarity = 1;
                break;
            case DIAMOND_LEGGINGS:
                defense = 40;
                rarity = 1;
                break;
            case DIAMOND_BOOTS:
                defense = 25;
                rarity = 1;
                break;
            case NETHERITE_HELMET:
                defense = 40;
                rarity = 1;
                break;
            case NETHERITE_CHESTPLATE:
                defense = 70;
                rarity = 1;
                break;
            case NETHERITE_LEGGINGS:
                defense = 55;
                rarity = 1;
                break;
            case NETHERITE_BOOTS:
                defense = 35;
                rarity = 1;
                break;
            case TURTLE_HELMET:
                defense = 10;
                rarity = 1;
                break;
        }

        if (damage > 0)
        {
            map.put("damage", damage);
        }
        if (defense > 0)
        {
            map.put("defense", defense);
        }
        if (ranged_damage > 0)
        {
            map.put("ranged_damage", ranged_damage);
        }
        map.put("rarity", rarity);

        return map;
    }
}
