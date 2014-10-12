package com.github.thelonelysprite.alfheim.blocks;

/**
 * Created by justin on 06/10/2014.
 */

import com.github.thelonelysprite.alfheim.Teleport;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thaumcraft.api.crafting.IInfusionStabiliser;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.common.lexicon.LexiconData;

@Optional.Interface(modid = "Thaumcraft", iface = "thaumcraft.api.crafting.IInfusionStabiliser", striprefs = true)
public class SpiritPylon extends Container implements ILexiconable, IInfusionStabiliser {

    public SpiritPylon() {
        super(Material.iron);
        setHardness(5.5F);
        setStepSound(soundTypeMetal);
        setBlockName("SpiritPylon");

        float f = 1F / 16F * 2F;
        setBlockBounds(f, 0F, f, 1F - f, 1F / 16F * 21F, 1F - f);
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
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        // NO-OP
    }

    @Override
    public int damageDropped(int par1) {
        return par1;
    }


    @Override
    public IIcon getIcon(int par1, int par2) {
        return par2 == 0 ? Blocks.diamond_block.getIcon(0, 0) : vazkii.botania.common.block.ModBlocks.storage.getIcon(0, par2);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return RenderingRegistry.getNextAvailableRenderId();
    }

    @Override
    public float getEnchantPowerBonus(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z) == 0 ? 8 : 15;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileSpiritPylon();
    }

    @Override
    public LexiconEntry getEntry(World world, int x, int y, int z, EntityPlayer player, ItemStack lexicon) {
        int meta = world.getBlockMetadata(x, y, z);
        return meta == 0 ? LexiconData.pylon : meta == 1 ? LexiconData.alfhomancyIntro : LexiconData.gaiaRitual;
    }

    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if(p_149727_5_.dimension == -98){
            Teleport.teleportEntity(p_149727_5_, 0);
        }else {
            Teleport.teleportEntity(p_149727_5_, -98);
        }
        return false;
    }

    @Override
    public boolean canStabaliseInfusion(World world, int x, int y, int z) {
        return true;
    }
}
