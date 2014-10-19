package com.github.thelonelysprite.alfheim.blocks;

import com.github.thelonelysprite.alfheim.Alfheim;
import com.github.thelonelysprite.alfheim.Constants;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import vazkii.botania.common.block.BlockPylon;

/**
 * Created by justin on 19/09/2014.
 */
public class AddonBlocks {
    public static Block spiritPylon;
    public static Block alfPortal;
    public static Block dreamLeaves;

    public static void init(){
        spiritPylon = new SpiritPylon();
        alfPortal = new BlockAlfPortal2();
        dreamLeaves = new DreamLeaves(true).setBlockName("dreamLeaves");

    }
}
