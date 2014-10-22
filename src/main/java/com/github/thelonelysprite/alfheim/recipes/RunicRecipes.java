package com.github.thelonelysprite.alfheim.recipes;

import com.github.thelonelysprite.alfheim.items.AddonItem;
import com.github.thelonelysprite.alfheim.items.AddonItems;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.block.tile.mana.TilePool;
import vazkii.botania.common.lib.LibOreDict;

/**
 * Created by justin on 20/10/2014.
 */
public class RunicRecipes {

    public static void init(){
        BotaniaAPI.registerRuneAltarRecipe(new ItemStack(AddonItems.resource,1,3), TilePool.MAX_MANA*10,new ItemStack(AddonItems.resource,1,0),new ItemStack(AddonItems.resource,1,1),new ItemStack(AddonItems.resource,1,2), LibOreDict.LIFE_ESSENCE);
    }
}
