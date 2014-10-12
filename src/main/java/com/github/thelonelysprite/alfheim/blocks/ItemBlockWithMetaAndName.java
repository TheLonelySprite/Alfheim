package com.github.thelonelysprite.alfheim.blocks;

/**
 * Created by justin on 06/10/2014.
 */

import com.github.thelonelysprite.alfheim.Constants;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemBlockWithMetaAndName extends ItemBlockWithMetadata {
    public ItemBlockWithMetaAndName(Block par2Block) {
        super(par2Block, par2Block);
    }

    @Override
    public String getUnlocalizedNameInefficiently(ItemStack par1ItemStack) {
        return super.getUnlocalizedNameInefficiently(par1ItemStack).replaceAll("tile.", "tile." + Constants.MODID + ":");
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return super.getUnlocalizedName(par1ItemStack) + par1ItemStack.getItemDamage();
    }



}
