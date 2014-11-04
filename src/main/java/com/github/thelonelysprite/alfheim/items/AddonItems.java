package com.github.thelonelysprite.alfheim.items;

import com.github.thelonelysprite.alfheim.items.equipment.armour.*;
import com.github.thelonelysprite.alfheim.items.equipment.tools.ItemGaiaSteelSword;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

/**
 * Created by justin on 19/09/2014.
 */
public class AddonItems {
    public static ArmorMaterial gaiaSteelArmorMaterial = EnumHelper.addArmorMaterial("GAIASTEEL", 34, new int[]{3, 8, 6, 3}, 52);
    public static Item.ToolMaterial gaiaSteelToolMAterial = EnumHelper.addToolMaterial("GAIASTEEL", 6, 4600, 18F, 6F, 52);

    public static Item exampleItem;
    public static Item resource;
    public static Item gaiaRing;
    public static Item gaiaSteelHelm;
    public static Item gaiaSteelHelmRevealing;
    public static Item gaiaSteelChest;
    public static Item gaiaSteelLegs;
    public static Item gaiaSteelBoots;
    public static Item gaiaSteelSword;

    public static void init() {
        exampleItem = new AddonItem("name");
        resource = new AlfheimRescources("resource");
        gaiaRing = new GaiaRing();
        gaiaSteelHelm = new ItemGaiaSteelHelm();
        gaiaSteelHelmRevealing = new ItemGaiaSteelHelmRevealing();
        gaiaSteelChest = new ItemGaiaSteelChest();
        gaiaSteelLegs = new ItemGaiaSteelLegs();
        gaiaSteelBoots = new ItemGaiaSteelBoots();
        gaiaSteelSword = new ItemGaiaSteelSword();
    }
}
