package me.itzkiba.pluginMST.listeners.spawnitem;

import me.itzkiba.pluginMST.helperfunctions.RandomNumber;

public class MaterialName {
    private static final String[] materialArray = {
            "Oakwood",
            "Mahoganywood",
            "Pinewood",
            "Walnutwood",
            "Birchwood",
            "Ashwood",
            "Ebonywood",
            "Maplewood",
            "Cherrywood",
            "Rosewood",
            "Oakwood",
            "Mahoganywood",
            "Pinewood",
            "Walnutwood",
            "Birchwood",
            "Ashwood",
            "Ebonywood",
            "Maplewood",
            "Cherrywood",
            "Rosewood",
            "Oakwood",
            "Mahoganywood",
            "Pinewood",
            "Walnutwood",
            "Birchwood",
            "Ashwood",
            "Ebonywood",
            "Maplewood",
            "Cherrywood",
            "Rosewood",
            "Oakwood",
            "Mahoganywood",
            "Pinewood",
            "Walnutwood",
            "Birchwood",
            "Ashwood",
            "Ebonywood",
            "Maplewood",
            "Cherrywood",
            "Rosewood",
            "Iron",
            "Steel",
            "Bronze",
            "Copper",
            "Tin",
            "Silver",
            "Platinum",
            "Lead",
            "Zinc",
            "Nickel",
            "Cobalt",
            "Iron",
            "Steel",
            "Bronze",
            "Copper",
            "Tin",
            "Silver",
            "Platinum",
            "Lead",
            "Zinc",
            "Nickel",
            "Cobalt",
            "Manganese",
            "Tungsten",
            "Palladium",
            "Chromium",
            "Iridium",
            "Titanium",
            "Bismuth",
            "Onyx",
            "Granite",
            "Marble",
            "Limestone",
            "Obsidian",
            "Sandstone",
            "Slate",
            "Basalt",
            "Topaz",
            "Amethyst",
            "Emerald",
            "Sapphire",
            "Ruby",
            "Diamond",
            "Agate",
            "Citrine",
            "Quartz",
            "Jasper",
            "Garnet",
            "Jade",
            "Moonstone",
            "Opal",
            "Coral",
            "Beryl",
            "Hematite",
            "Pyrite",
            "Malachite",
            "Turquoise",
            "Zincite",
            "Rhodonite",
            "Mica",
            "Olivine",
            "Serpentine",
            "Gypsum",
            "Calcite",
            "Magnetite",
            "Feldspar",
            "Celestite",
            "Bauxite",
            "Chalcedony",
            "Galena",
            "Peridot",
            "Amber",
            "Zircon",
            "Amazonite",
            "Aragonite",
            "Azurite",
            "Cassiterite",
            "Barite",
            "Borax",
            "Bornite",
            "Cinnabar",
            "Cryolite",
            "Dolomite",
            "Fluorite",
            "Graphite",
            "Halite",
            "Hornblende",
            "Kyanite",
            "Magnetite",
            "Olivine",
            "Pumice",
            "Sphalerite",
            "Stibnite",
            "Talc",
            "Vanadinite",
            "Wulfenite",
            "Ebony",
            "Obsidian",
            "Ashwood",
            "Iron",
            "Bloodstone",
            "Adamantite",
            "Maple",
            "Goldleaf",
            "Steelplate",
            "Sapphire",
            "Onyx",
            "Crystal",
            "Topaz",
            "Mithril",
            "Jade",
            "Copper",
            "Elmwood",
            "Bronze",
            "Silvermoon",
            "Amethyst",
            "Yew",
            "Starsteel",
            "Garnet",
            "Bloodwood",
            "Brass",
            "Froststeel",
            "Coral",
            "Moonstone",
            "Rosewood",
            "Blackiron",
            "Opal",
            "Mahogany",
            "Meteorite",
            "Sunsteel",
            "Agate",
            "Cherrywood",
            "Titanium",
            "Emerald",
            "Pine",
            "Cobalt",
            "Electrum",
            "Teak",
            "Quartz",
            "Platinum",
            "Birchwood",
            "Nickel",
            "Ruby",
            "Ancientwood",
            "Damascus",
            "Jasper",
            "Bloodsteel",
            "Bone",
            "Walnut",
            "Platinumplate",
            "Bismuth",
            "Starwood",
            "Citrine",
            "Ebonywood",
            "Shadowsteel",
            "Malachite",
            "Glimmersteel",
            "Alderwood",
            "Palladium",
            "Rosegold",
            "Driftwood",
            "Limestone",
            "Stormsteel",
            "Bluewood",
            "Zircon",
            "Abalone",
            "Dragonwood",
            "Cobaltite",
            "Ghoststeel",
            "Olivewood",
            "Pyrite",
            "Scorpionwood",
            "Titaniumplate",
            "Redwood",
            "Moonsteel",
            "Peridot",
            "Ironwood",
            "Silverplate",
            "Celestialwood",
            "Iridium",
            "Bloodrock",
            "Cedarwood",
            "Moonrock",
            "Argentium",
            "Redstone",
            "Pearlwood",
            "Cobaltplate",
            "Starstone",
            "Heartwood",
            "Tungsten",
            "Fierywood",
            "Brassplate",
            "Lapis"
    };

    public static String get(int level)
    {
        int index;
        if (level < 25)
        {
            index = RandomNumber.generateInt(0, 39);
        }
        else {
            index = RandomNumber.generateInt(40, (materialArray.length - 1));
        }

        return materialArray[index];
    }
}
