package com.github.thelonelysprite.alfheim.blocks;

/**
 * Created by justin on 17/10/2014.
 */


import com.github.thelonelysprite.alfheim.IconHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.wand.IWandable;
import vazkii.botania.common.lexicon.LexiconData;

public class BlockAlfPortal2 extends Container implements IWandable, ILexiconable {
    IIcon iconOff, iconOn;
    public static IIcon portalTex;

    public BlockAlfPortal2() {
        super(Material.wood);
        setHardness(10F);
        setStepSound(soundTypeWood);
        setBlockName("Portal");
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        iconOff = IconHelper.forBlock(par1IconRegister, this, 0);
        iconOn = IconHelper.forBlock(par1IconRegister, this, 1);
        portalTex = IconHelper.forBlock(par1IconRegister, this, "Inside");
    }
    @Override
    protected boolean shouldRegisterInNameSet() {
        return false;
    }
    @Override
    public Block setBlockName(String par1Str) {
        GameRegistry.registerBlock(this, ItemBlockWithMetaAndName.class, par1Str);
        return super.setBlockName(par1Str);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return meta == 0 ? iconOff : iconOn;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileAlfPortal2();
    }

    @Override
    public LexiconEntry getEntry(World world, int x, int y, int z, EntityPlayer player, ItemStack lexicon) {
        return LexiconData.alfhomancyIntro;
    }

    @Override
    public boolean onUsedByWand(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side) {
        return ((TileAlfPortal2) world.getTileEntity(x, y, z)).onWanded();
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z) == 0 ? 0 : 15;
    }

}
