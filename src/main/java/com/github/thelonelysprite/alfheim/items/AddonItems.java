package com.github.thelonelysprite.alfheim.items;

import com.github.thelonelysprite.alfheim.Alfheim;
import com.github.thelonelysprite.alfheim.Constants;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

/**
 * Created by justin on 19/09/2014.
 */
public class AddonItems {

    public static Item exampleItem;

    public static void init(){
        exampleItem = new ModItem().setCreativeTab(Alfheim.Tab).setUnlocalizedName(Constants.MODID+":name").setTextureName(Constants.MODID+":name");
        GameRegistry.registerItem(exampleItem, "name");
    }
}
