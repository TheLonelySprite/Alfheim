package com.github.thelonelysprite.alfheim.blocks;

import com.github.thelonelysprite.alfheim.Alfheim;
import com.github.thelonelysprite.alfheim.IconHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IShearable;


import java.util.ArrayList;

/**
 * Created by justin on 19/10/2014.
 */
public class DreamLeaves extends BlockLeavesBase implements IShearable {
    protected DreamLeaves(boolean p_i45433_2_) {
        super(Material.leaves, p_i45433_2_);
        setCreativeTab(Alfheim.Tab);
    }
    @Override
    public Block setBlockName(String par1Str) {
        if(shouldRegisterInNameSet())
            GameRegistry.registerBlock(this, ItemBlockWithMetaAndName.class, par1Str);
        return super.setBlockName(par1Str);
    }

    protected boolean shouldRegisterInNameSet() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        blockIcon = IconHelper.forBlock(par1IconRegister, this);
    }
    /**
     * Checks if the object is currently shearable
     * Example: Sheep return false when they have no wool
     *
     * @param item  The itemstack that is being used, Possible to be null
     * @param world The current world
     * @param x     The X Position
     * @param y     The Y Position
     * @param z     The Z Position
     * @return If this is shearable, and onSheared should be called.
     */
    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
        return true;
    }

    /**
     * Performs the shear function on this object.
     * This is called for both client, and server.
     * The object should perform all actions related to being sheared,
     * except for dropping of the items, and removal of the block.
     * As those are handled by ItemShears itself.
     * <p/>
     * Returns a list of items that resulted from the shearing process.
     * <p/>
     * For entities, they should trust there internal location information
     * over the values passed into this function.
     *
     * @param item    The itemstack that is being used, Possible to be null
     * @param world   The current world
     * @param x       The X Position
     * @param y       The Y Position
     * @param z       The Z Position
     * @param fortune The fortune level of the shears being used
     * @return A ArrayList containing all items from this shearing. Possible to be null.
     */
    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
        return null;
    }
}
