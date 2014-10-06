package com.github.thelonelysprite.alfheim.recipes;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.InputMismatchException;

/**
 * Created by justin on 28/09/2014.
 */
public class ModComp {
    public static void init(){
        addOre("materialPaper", Items.paper);
    }
    private static void addOre(String name, Object ore){
        if(ore instanceof Block){
            OreDictionary.registerOre(name,(Block)ore);
        }else if (ore instanceof Item){
            OreDictionary.registerOre(name, (Item) ore);
        }else if (ore instanceof ItemStack){
            OreDictionary.registerOre(name, (ItemStack) ore);
        } else{
            throw new InputMismatchException("Ore can only be a Block, an Item or an ItemStack");
        }

    }
}
