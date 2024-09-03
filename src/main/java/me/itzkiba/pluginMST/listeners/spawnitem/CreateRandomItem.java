package me.itzkiba.pluginMST.listeners.spawnitem;

import me.itzkiba.pluginMST.PluginMST;
import me.itzkiba.pluginMST.helperfunctions.RandomNumber;
import me.itzkiba.pluginMST.listeners.abilities.Abilities;
import me.itzkiba.pluginMST.listeners.persistentdatakeys.Stats;
import net.kyori.adventure.key.Key;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import static net.kyori.adventure.key.Key.MINECRAFT_NAMESPACE;

public class CreateRandomItem implements Listener {

    public static ItemStack createRandom(Material material, int type, int rarity, int levelInput)
    {
        Random random = new Random();
        ItemStack item = new ItemStack(material);

        Stats.setCustom(item, true);
        Stats.setItemLevel(item, levelInput);

        // Tokens are the max number of stats an item can have.
        int tokens = rarity + 1;
        if (rarity >= 5)
            tokens += 1;

        double meleeDamage = 35;
        double rangedDamage = 35;
        double magicDamage = 35;
        double armor = 0;
        double crit = 0;
        double health = 0;
        double mana = 0;
        double attackspeed = 0;
        double speed = 0;
        double power = 0;

        double level = (levelInput * 1.75) + 1;
        // TYPE 0 --> melee
        if (type == 0) {
            for (int i = 0; i < tokens; i++) {
                if (i == 0) {
                    meleeDamage += level;
                    continue;
                }
                if (i >= 2)
                {
                    crit += level * 0.025;
                }

                int randomNum = random.nextInt(3);
                switch (randomNum) {
                    case 0:
                        meleeDamage += level * random.nextDouble(0.4) * 0.3;
                        break;
                    case 1:
                        crit += level * random.nextDouble(0.4) * 0.1;
                        break;
                    case 2:
                        if (rarity >= 2) {
                            attackspeed += level * random.nextDouble(0.25) * 0.1;
                        }
                }
            }
            rangedDamage = 0;
            magicDamage = 0;
        }

        // TYPE 1 --> ranged
        if (type == 1) {
            for (int i = 0; i < tokens; i++) {
                if (i == 0) {
                    rangedDamage += level * 1.25;
                    continue;
                }
                if (i >= 2)
                {
                    crit += level * 0.02;
                }

                int randomNum = random.nextInt(4);
                switch (randomNum) {
                    case 0:
                        rangedDamage += level * random.nextDouble(0.2) * 0.55;
                        break;
                    case 1:
                        crit += level * random.nextDouble(0.1) * 0.03;
                        break;
                    case 2:
                        if (rarity >= 2) {
                            speed += level * random.nextDouble(0.15) * 0.1;
                        }
                    case 3:
                        if (rarity >= 1) {
                            attackspeed += level * random.nextDouble(0.1) * 0.1;
                        }
                }
            }
            meleeDamage = 0;
            magicDamage = 0;
        }

        // TYPE 2 --> magic
        if (type == 2) {
            for (int i = 0; i < tokens; i++) {
                if (i == 0) {
                    magicDamage += level * 0.8;
                    continue;
                }
                if (i >= 2)
                {
                    mana += level * 0.035;
                }

                int randomNum = random.nextInt(3);
                switch (randomNum) {
                    case 0:
                        magicDamage += level * random.nextDouble(0.2) * 0.5;
                        break;
                    case 1:
                        if (rarity >= 2) {
                            mana += level * random.nextDouble(0.1) * 0.35;
                        }
                        break;
                    case 2:
                        if (rarity >= 3) {
                            attackspeed += level * random.nextDouble(0.25) * 0.1;
                        }
                }
            }
            meleeDamage = 0;
            rangedDamage = 0;
        }

        // TYPE 3 --> armor
        if (type == 3) {

            int armorClass = random.nextInt(2);

            for (int i = 0; i < tokens; i++) {
                if (i == 0) {
                    armor += level * 0.16;
                    continue;
                }
                if (i == 2)
                {
                    health += level * 0.06;
                }
                if (i >= 2) {
                    if (armorClass == 0) {
                        crit += level * 0.012;
                    }
                    if (armorClass == 1) {
                        mana += level * 0.012;
                    }
                }

                int randomNum = random.nextInt(5);
                switch (randomNum) {
                    case 0:
                        armor += level * random.nextDouble(0.25) * 0.15;
                        break;
                    case 1:
                        if (rarity >= 2) {
                            health += level * random.nextDouble(0.15) * 0.15;
                        }
                        break;
                    case 2:
                        if (rarity >= 1) {
                            if (armorClass == 0) {
                                crit += level * random.nextDouble(0.15) * 0.09;
                            }
                            if (armorClass == 1) {
                                mana += level * random.nextDouble(0.15) * 0.09;
                            }
                        }
                        break;
                    case 3:
                        if (rarity >= 1) {
                            speed += level * random.nextDouble(0.07) * 0.15;
                        }
                    case 4:
                        if (rarity >= 2) {
                            power += level * random.nextDouble(0.07) * 0.15;
                        }
                }
            }
            armor += 1;
            meleeDamage = 0;
            rangedDamage = 0;
            magicDamage = 0;
        }

        // scuff
        double scuff = 0.35;
        double rarityBoost = (rarity * 0.30) + 1;
        if (rarity >= 5)
            rarityBoost += 0.15;

        meleeDamage *= (random.nextDouble(scuff) - (scuff / 2.0) + 1) * rarityBoost;
        rangedDamage *= (random.nextDouble(scuff) - (scuff / 2.0) + 1) * rarityBoost;
        magicDamage *= (random.nextDouble(scuff) - (scuff / 2.0) + 1) * rarityBoost;
        crit *= (random.nextDouble(scuff) - (scuff / 2.0) + 1) * rarityBoost;
        armor *= (random.nextDouble(scuff) - (scuff / 2.0) + 1) * rarityBoost;
        health *= (random.nextDouble(scuff) - (scuff / 2.0) + 1) * rarityBoost;
        mana *= (random.nextDouble(scuff) - (scuff / 2.0) + 1) * rarityBoost;
        attackspeed *= (random.nextDouble(scuff) - (scuff / 2.0) + 1) * rarityBoost;
        speed *= (random.nextDouble(scuff) - (scuff / 2.0) + 1) * rarityBoost;
        power *= (random.nextDouble(scuff) - (scuff / 2.0) + 1) * rarityBoost;

        meleeDamage /= 5;
        rangedDamage /= 5;
        magicDamage /= 5;

        if (getType(item).equals("axe"))
        {
            crit *= 1.3;
        }
        if (getType(item).equals("sword"))
        {
            meleeDamage *= 1.4;
        }

        Stats.setBaseDamageStat(item, ((int)meleeDamage) * 5);
        Stats.setBaseRangedDamageStat(item, ((int)rangedDamage) * 5);
        Stats.setBaseMagicDamageStat(item, ((int)magicDamage) * 5);
        Stats.setBaseCritStat(item, (int)crit * 5);
        Stats.setBaseDefenseStat(item, (int)armor * 5);
        Stats.setBaseHealthStat(item, (int)health * 5);
        Stats.setBaseMaxManaStat(item, (int)mana * 5);
        Stats.setBaseAttackSpeedStat(item, (int)attackspeed * 5);
        Stats.setBaseSpeedStat(item, (int)speed * 5);
        Stats.setBasePowerStat(item, (int)power * 5);

        Stats.setRarity(item, rarity);

        Stats.setItemName(item, createName(getType(item), rarity, item, Stats.getItemLevel(item)));

        ItemMeta meta = item.getItemMeta();

        //meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", 0, AttributeModifier.Operation.ADD_NUMBER));
        //meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armorToughness", 0, AttributeModifier.Operation.ADD_NUMBER));
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(Stats.getArmorNamespace(), 0, AttributeModifier.Operation.ADD_NUMBER));
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(Stats.getArmorToughNamespace(), 0, AttributeModifier.Operation.ADD_NUMBER));
        if (Stats.getRarity(item) > 1)
        {
            meta.setUnbreakable(true);
        }

        item.setItemMeta(meta);

        if (getType(item).equals("armor") && rarity > 1) {
            ArmorMeta am = (ArmorMeta) item.getItemMeta();
            am.setTrim(ArmorTrimSelector.get(rarity, levelInput));
            am.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
            item.setItemMeta(am);
        }

        addAbility(item);

        return item;
    }

    public static Material getMaterial(String itemType, int level)
    {
        ArrayList<Material> materialList = new ArrayList<>();
        Random random = new Random();
        if (itemType.equals("sword"))
        {
            if (level < 25)
            {
                materialList.add(Material.WOODEN_SWORD);
                materialList.add(Material.STONE_SWORD);
            }
            else if (level < 50)
            {
                materialList.add(Material.GOLDEN_SWORD);
                materialList.add(Material.IRON_SWORD);
            }
            else if (level < 75)
            {
                materialList.add(Material.DIAMOND_SWORD);
            }
            else
            {
                materialList.add(Material.DIAMOND_SWORD);
                materialList.add(Material.NETHERITE_SWORD);
            }
        }
        if (itemType.equals("axe"))
        {
            if (level < 25)
            {
                materialList.add(Material.WOODEN_AXE);
                materialList.add(Material.STONE_AXE);
            }
            else if (level < 50)
            {
                materialList.add(Material.GOLDEN_AXE);
                materialList.add(Material.IRON_AXE);
            }
            else if (level < 75)
            {
                materialList.add(Material.DIAMOND_AXE);
            }
            else
            {
                materialList.add(Material.DIAMOND_AXE);
                materialList.add(Material.NETHERITE_AXE);
            }
        }
        if (itemType.equals("bow"))
        {
            materialList.add(Material.BOW);
        }
        if (itemType.equals("magic"))
        {
            if (level > 0) {
                materialList.add(Material.STICK);
                materialList.add(Material.POPPY);
                materialList.add(Material.WHITE_TULIP);
                materialList.add(Material.BLUE_ORCHID);
                materialList.add(Material.ALLIUM);
                materialList.add(Material.BONE);
            }
            if (level > 25) {
                materialList.add(Material.BAMBOO);
                materialList.add(Material.RED_TULIP);
                materialList.add(Material.ORANGE_TULIP);
                materialList.add(Material.PINK_TULIP);
                materialList.add(Material.CORNFLOWER);
                materialList.add(Material.SPRUCE_SAPLING);
                materialList.add(Material.BIRCH_SAPLING);
            }
            if (level > 50) {
                materialList.add(Material.AMETHYST_SHARD);
                materialList.add(Material.TORCHFLOWER);
                materialList.add(Material.WITHER_ROSE);
                materialList.add(Material.BLAZE_ROD);
                materialList.add(Material.RED_TULIP);
            }
            if (level > 75) {
                materialList.add(Material.DEAD_BUSH);
                materialList.add(Material.LILY_OF_THE_VALLEY);
                materialList.add(Material.CHERRY_SAPLING);
                materialList.add(Material.ECHO_SHARD);
                materialList.add(Material.BREEZE_ROD);
                materialList.add(Material.LARGE_AMETHYST_BUD);
                materialList.add(Material.AMETHYST_CLUSTER);
            }
        }
        if (itemType.equals("armor"))
        {
            if (level < 12)
            {
                materialList.add(Material.LEATHER_HELMET);
                materialList.add(Material.LEATHER_CHESTPLATE);
                materialList.add(Material.LEATHER_LEGGINGS);
                materialList.add(Material.LEATHER_BOOTS);
            }
            else if (level < 25)
            {
                materialList.add(Material.LEATHER_HELMET);
                materialList.add(Material.LEATHER_CHESTPLATE);
                materialList.add(Material.LEATHER_LEGGINGS);
                materialList.add(Material.LEATHER_BOOTS);

                materialList.add(Material.CHAINMAIL_HELMET);
                materialList.add(Material.CHAINMAIL_CHESTPLATE);
                materialList.add(Material.CHAINMAIL_LEGGINGS);
                materialList.add(Material.CHAINMAIL_BOOTS);
            }
            else if (level < 40)
            {
                materialList.add(Material.CHAINMAIL_HELMET);
                materialList.add(Material.CHAINMAIL_CHESTPLATE);
                materialList.add(Material.CHAINMAIL_LEGGINGS);
                materialList.add(Material.CHAINMAIL_BOOTS);

                materialList.add(Material.GOLDEN_HELMET);
                materialList.add(Material.GOLDEN_CHESTPLATE);
                materialList.add(Material.GOLDEN_LEGGINGS);
                materialList.add(Material.GOLDEN_BOOTS);
            }
            else if (level < 55)
            {
                materialList.add(Material.GOLDEN_HELMET);
                materialList.add(Material.GOLDEN_CHESTPLATE);
                materialList.add(Material.GOLDEN_LEGGINGS);
                materialList.add(Material.GOLDEN_BOOTS);

                materialList.add(Material.IRON_HELMET);
                materialList.add(Material.IRON_CHESTPLATE);
                materialList.add(Material.IRON_LEGGINGS);
                materialList.add(Material.IRON_BOOTS);
            }
            else if (level < 70)
            {
                materialList.add(Material.IRON_HELMET);
                materialList.add(Material.IRON_CHESTPLATE);
                materialList.add(Material.IRON_LEGGINGS);
                materialList.add(Material.IRON_BOOTS);

                materialList.add(Material.DIAMOND_HELMET);
                materialList.add(Material.DIAMOND_CHESTPLATE);
                materialList.add(Material.DIAMOND_LEGGINGS);
                materialList.add(Material.DIAMOND_BOOTS);
            }
            else
            {
                materialList.add(Material.DIAMOND_HELMET);
                materialList.add(Material.DIAMOND_CHESTPLATE);
                materialList.add(Material.DIAMOND_LEGGINGS);
                materialList.add(Material.DIAMOND_BOOTS);

                materialList.add(Material.NETHERITE_HELMET);
                materialList.add(Material.NETHERITE_CHESTPLATE);
                materialList.add(Material.NETHERITE_LEGGINGS);
                materialList.add(Material.NETHERITE_BOOTS);
            }
        }



        int randomNum = random.nextInt(materialList.size());
        return materialList.get(randomNum);
    }

    public static String createName(String itemType, int rarity, ItemStack item, int level)
    {
        String mainName = "";
        String prefix = "";
        String suffix = "";

        if (itemType.equals("armor"))
        {
            mainName = item.getType().toString().toLowerCase();
            mainName = capitalize(mainName.substring(mainName.indexOf('_') + 1));
        }
        if (itemType.equals("sword"))
        {
            mainName = SwordName.get();
        }
        if (itemType.equals("axe"))
        {
            mainName = AxeName.get();
        }
        if (itemType.equals("bow"))
        {
            mainName = BowName.get();
        }
        if (itemType.equals("magic"))
        {
            mainName = MagicName.get();
        }

        if (rarity == 0)
        {
            prefix = MaterialName.get(level) + " ";
        }
        if (rarity >= 1)
        {
            prefix = DescriptorName.get() + " " + MaterialName.get(level) + " ";
        }
        if (rarity >= 3)
        {
            suffix = " " + SuffixName.get();
        }

        return prefix + mainName + suffix;
    }


    private static String capitalize(String message) {

        // stores each characters to a char array
        char[] charArray = message.toCharArray();
        boolean foundSpace = true;

        for (int i = 0; i < charArray.length; i++) {

            // if the array element is a letter
            if (Character.isLetter(charArray[i])) {

                // check space is present before the letter
                if (foundSpace) {

                    // change the letter into uppercase
                    charArray[i] = Character.toUpperCase(charArray[i]);
                    foundSpace = false;
                }
            } else {
                // if the new character is not character
                foundSpace = true;
            }
        }

        // convert the char array to the string
        message = String.valueOf(charArray);
        return message;
    }

    private static String getType(ItemStack item)
    {
        String type = "magic";
        Material m = item.getType();

        switch (m)
        {
            case WOODEN_SWORD:
            case STONE_SWORD:
            case IRON_SWORD:
            case GOLDEN_SWORD:
            case DIAMOND_SWORD:
            case NETHERITE_SWORD:
                type = "sword";
                break;

            case WOODEN_AXE:
            case STONE_AXE:
            case IRON_AXE:
            case GOLDEN_AXE:
            case DIAMOND_AXE:
            case NETHERITE_AXE:
                type = "axe";
                break;

            case BOW:
            case CROSSBOW:
                type = "bow";
                break;

            case LEATHER_HELMET:
            case LEATHER_CHESTPLATE:
            case LEATHER_LEGGINGS:
            case LEATHER_BOOTS:
            case CHAINMAIL_HELMET:
            case CHAINMAIL_CHESTPLATE:
            case CHAINMAIL_LEGGINGS:
            case CHAINMAIL_BOOTS:
            case IRON_HELMET:
            case IRON_CHESTPLATE:
            case IRON_LEGGINGS:
            case IRON_BOOTS:
            case GOLDEN_HELMET:
            case GOLDEN_CHESTPLATE:
            case GOLDEN_LEGGINGS:
            case GOLDEN_BOOTS:
            case DIAMOND_HELMET:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case DIAMOND_BOOTS:
            case NETHERITE_HELMET:
            case NETHERITE_CHESTPLATE:
            case NETHERITE_LEGGINGS:
            case NETHERITE_BOOTS:
            case TURTLE_HELMET:
                type = "armor";
                break;

        }

        return type;
    }

    public static void addAbility(ItemStack item)
    {
        int level = Stats.getItemLevel(item);
        int rarity = Stats.getRarity(item);
        double chance = ((level - 10) / 40.0) + (rarity * 0.1);

        if (Stats.getBaseMagicDamageStat(item) > 0)
        {
            chance += 0.5;
        }

        Random random = new Random();
        if (random.nextDouble(1) > chance)
        {
            return;
        }

        int id = 0;

        // ability setting
        ArrayList<Integer> validAbilities = new ArrayList<Integer>();
        if (Stats.getBaseDamageStat(item) > 0)
        {
            if (level >= 0) {
                validAbilities.add(1000);
                validAbilities.add(1001);
            }
            if (level >= 25) {
                validAbilities.add(1002);
                validAbilities.add(1003);
                validAbilities.add(1004);
            }
        }

        if (Stats.getBaseRangedDamageStat(item) > 0)
        {
            if (level >= 0) {
                validAbilities.add(2000);
                validAbilities.add(2001);
                validAbilities.add(2005);
            }
            if (level >= 25) {
                validAbilities.add(2004);
                validAbilities.add(2003);
            }
            if (level >= 50) {
                validAbilities.add(2002);
            }
        }
        if (Stats.getBaseMagicDamageStat(item) > 0)
        {
            if (level >= 0) {
                validAbilities.add(3000);
                validAbilities.add(3001);
            }
            if (level >= 25) {
                validAbilities.add(3002);
                validAbilities.add(3004);
            }
            if (level >= 50) {
                validAbilities.add(3003);
                validAbilities.add(3005);
            }
        }

        if (!validAbilities.isEmpty()) {
            int randIndex = RandomNumber.generateInt(0, validAbilities.size() - 1);
            id = validAbilities.get(randIndex);
        }
        Abilities.setItemAbilityID(item, id);
    }
}
