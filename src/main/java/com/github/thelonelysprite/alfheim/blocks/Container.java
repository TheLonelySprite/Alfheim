package com.github.thelonelysprite.alfheim.blocks;

import com.github.thelonelysprite.alfheim.Alfheim;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import vazkii.botania.client.core.helper.IconHelper;
import vazkii.botania.common.core.BotaniaCreativeTab;
import vazkii.botania.common.item.block.ItemBlockMod;

/**
 * Created by justin on 06/10/2014.
 */
public abstract class Container extends ModBlock implements ITileEntityProvider{

    protected Container(Material par2Material) {
        super(par2Material);
        if(registerInCreative())
            setCreativeTab(Alfheim.Tab);
    }

    @Override
    public Block setBlockName(String par1Str) {
        if(shouldRegisterInNameSet())
            GameRegistry.registerBlock(this, ItemBlockMod.class, par1Str);
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

    boolean registerInCreative() {
        return true;
    }
}
