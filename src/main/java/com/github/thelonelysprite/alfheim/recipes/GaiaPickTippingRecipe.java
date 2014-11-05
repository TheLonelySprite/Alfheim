package com.github.thelonelysprite.alfheim.recipes;

import com.github.thelonelysprite.alfheim.items.equipment.tools.ItemGaiaSteelPickaxe;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import vazkii.botania.common.item.ModItems;

/**
 * Created by justin on 05/11/2014.
 */
public class GaiaPickTippingRecipe implements IRecipe {
        @Override
        public boolean matches(InventoryCrafting var1, World var2) {
            boolean foundTerraPick = false;
            boolean foundElementiumPick = false;
            for(int i = 0; i < var1.getSizeInventory(); i++) {
                ItemStack stack = var1.getStackInSlot(i);
                if(stack != null) {
                    if(stack.getItem() instanceof ItemGaiaSteelPickaxe && !ItemGaiaSteelPickaxe.isTipped(stack))
                        foundTerraPick = true;
                    else if(stack.getItem() == ModItems.elementiumPick)
                        foundElementiumPick = true;
                    else return false; // Found an invalid item, breaking the recipe
                }
            }
            return foundTerraPick && foundElementiumPick;
        }
        @Override
        public ItemStack getCraftingResult(InventoryCrafting var1) {
            ItemStack terraPick = null;
            for(int i = 0; i < var1.getSizeInventory(); i++) {
                ItemStack stack = var1.getStackInSlot(i);
                if(stack != null && stack.getItem() instanceof ItemGaiaSteelPickaxe)
                    terraPick = stack;
            }
            if(terraPick == null)
                return null;
            ItemStack terraPickCopy = terraPick.copy();
            ItemGaiaSteelPickaxe.setTipped(terraPickCopy);
            return terraPickCopy;
        }
        @Override
        public int getRecipeSize() {
            return 10;
        }
        @Override
        public ItemStack getRecipeOutput() {
            return null;
        }
    }
