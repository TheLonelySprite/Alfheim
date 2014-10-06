package com.github.thelonelysprite.alfheim.recipes;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.InputMismatchException;

/**
 * Created by justin on 28/09/2014.
 */
public class FurnaceRecipes{

    public static void init(){
        //addSmelting(ModItems.exampleItem, new ItemStack(ModBlocks.exampleBlock),0.7f);
    }
    private static void addSmelting(Object input,ItemStack output,Float xp){
        if(input instanceof Block){
            GameRegistry.addSmelting((Block)input, output, xp);
        }else if (input instanceof Item){
            GameRegistry.addSmelting((Item)input, output, xp);
        }else if (input instanceof ItemStack){
            GameRegistry.addSmelting((ItemStack)input, output, xp);
        } else{
            throw new InputMismatchException("Input can only be a Block, an Item or an ItemStack");
        }

    }
}
