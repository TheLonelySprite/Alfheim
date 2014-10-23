package com.github.thelonelysprite.alfheim.blocks;

import com.github.thelonelysprite.alfheim.Alfheim;
import com.github.thelonelysprite.alfheim.IconHelper;
import com.github.thelonelysprite.alfheim.items.AddonItems;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by justin on 19/10/2014.
 */
public class DreamLeaves extends BlockLeavesBase implements IShearable {
    protected DreamLeaves(boolean p_i45433_2_) {
        super(Material.leaves, p_i45433_2_);
        setCreativeTab(Alfheim.Tab);
        this.setHardness(0.2F);
        this.setLightOpacity(1);
        this.setStepSound(soundTypeGrass);
    }

    @Override
    public Block setBlockName(String par1Str) {
        if (shouldRegisterInNameSet())
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

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return AddonItems.resource;
    }

    public int damageDropped(int p_149692_1_) {
        return 2;
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    public int quantityDroppedWithBonus(int p_149679_1_, Random p_149679_2_) {
        return this.quantityDropped(p_149679_2_);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random p_149745_1_) {
        Random rand = new Random();

        return rand.nextInt(40) == 1 ? 1 : 0;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {
        super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, p_149690_7_);
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
