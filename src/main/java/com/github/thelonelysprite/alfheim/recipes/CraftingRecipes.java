package com.github.thelonelysprite.alfheim.recipes;

import com.github.thelonelysprite.alfheim.Constants;
import com.github.thelonelysprite.alfheim.blocks.AddonBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.lib.LibOreDict;

/**
 * Created by justin on 28/09/2014.
 */
public class CraftingRecipes {
    public static IRecipe recipeSpiritPylon;
    public static IRecipe recipeImprovedGatewayCore;
    public static void init() {
        // GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.book), "materialPaper", "materialPaper", "materialPaper", ModItems.exampleItem));
        // GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.exampleItem,10),  "A A", " B ", "A A", 'A', ModBlocks.exampleBlock, 'B',
        //"dyeBlack" ));
        addOreDictRecipe(new ItemStack(AddonBlocks.spiritPylon,4),"ABA", "BBB", "ABA",'B', LibOreDict.LIFE_ESSENCE,'A', new ItemStack(ModBlocks.pylon,1,2));
        recipeSpiritPylon= BotaniaAPI.getLatestAddedRecipe();
        addOreDictRecipe(new ItemStack(AddonBlocks.alfPortal,1),"AAA", "ABA", "AAA",'B', LibOreDict.TERRA_STEEL,'A', LibOreDict.DREAM_WOOD);
        recipeImprovedGatewayCore= BotaniaAPI.getLatestAddedRecipe();
        Constants.log.info("Recipe added");
    }

    private static void addOreDictRecipe(ItemStack output, Object... recipe) {
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, recipe));
    }

    private static void addShapelessOreDictRecipe(ItemStack output, Object... recipe) {
        CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, recipe));
    }
}
