package com.github.thelonelysprite.alfheim.recipes;

import com.github.thelonelysprite.alfheim.items.AddonItems;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.block.tile.mana.TilePool;

/**
 * Created by justin on 20/10/2014.
 */
public class InfusionRecipes {
    public static void init() {
        BotaniaAPI.registerManaInfusionRecipe(new ItemStack(AddonItems.resource,1,4), new ItemStack(AddonItems.resource,1,3), TilePool.MAX_MANA);    }
}
