package com.github.thelonelysprite.alfheim.recipes;

import com.github.thelonelysprite.alfheim.Constants;
import com.github.thelonelysprite.alfheim.blocks.AddonBlocks;
import com.github.thelonelysprite.alfheim.items.AddonItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.lib.LibOreDict;

/**
 * Created by justin on 28/09/2014.
 */
public class CraftingRecipes {
    public static IRecipe recipeSpiritPylon;
    public static IRecipe recipeImprovedGatewayCore;
    public static IRecipe recipeGaiaRing;
    public static IRecipe recipeGaiaHelm;
    public static IRecipe recipeGaiaHelmR;
    public static IRecipe recipeGaiaChest;
    public static IRecipe recipeGaiaLegs;
    public static IRecipe recipeGaiaBoots;

    public static void init() {
        // GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.book), "materialPaper", "materialPaper", "materialPaper", ModItems.exampleItem));
        // GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.exampleItem,10),  "A A", " B ", "A A", 'A', ModBlocks.exampleBlock, 'B',
        //"dyeBlack" ));
        ItemStack gaiaSteel = new ItemStack(AddonItems.resource, 1, 4);
        recipeSpiritPylon = addOreDictRecipe(new ItemStack(AddonBlocks.spiritPylon, 4), "ABA", "BBB", "ABA", 'B', LibOreDict.LIFE_ESSENCE, 'A', new ItemStack(ModBlocks.pylon, 1, 2));
        recipeImprovedGatewayCore = addOreDictRecipe(new ItemStack(AddonBlocks.alfPortal, 1), "AAA", "ABA", "AAA", 'B', LibOreDict.TERRA_STEEL, 'A', LibOreDict.DREAM_WOOD);
        recipeGaiaRing = addOreDictRecipe(new ItemStack(AddonItems.gaiaRing), "AB ", "B B", " B ", 'A', LibOreDict.LIFE_ESSENCE, 'B', gaiaSteel);
        recipeGaiaHelm = addOreDictRecipe(new ItemStack(AddonItems.gaiaSteelHelm), "AAA", "A A", 'A', gaiaSteel);
        recipeGaiaChest = addOreDictRecipe(new ItemStack(AddonItems.gaiaSteelChest),"A A","AAA","AAA",'A',gaiaSteel);
        recipeGaiaLegs = addOreDictRecipe(new ItemStack(AddonItems.gaiaSteelLegs),"AAA","A A","A A",'A',gaiaSteel);
        recipeGaiaBoots = addOreDictRecipe(new ItemStack(AddonItems.gaiaSteelBoots),"A A","A A",'A',gaiaSteel);
        Constants.log.info("Recipe added");
    }

    private static IRecipe addOreDictRecipe(ItemStack output, Object... recipe) {
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, recipe));
        return new ShapedOreRecipe(output, recipe);
    }

    private static IRecipe addShapelessOreDictRecipe(ItemStack output, Object... recipe) {
        CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, recipe));
        return new ShapelessOreRecipe(output, recipe);
    }
}
