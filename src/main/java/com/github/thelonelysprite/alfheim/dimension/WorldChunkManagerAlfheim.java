package com.github.thelonelysprite.alfheim.dimension;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;

/**
 * Created by justin on 06/10/2014.
 */
public class WorldChunkManagerAlfheim extends WorldChunkManager {

    protected WorldChunkManagerAlfheim()
    {
        super();
    }

    public WorldChunkManagerAlfheim(long p_i1975_1_, WorldType p_i1975_3_)
    {
        super(p_i1975_1_, p_i1975_3_);
    }

    public WorldChunkManagerAlfheim(World p_i1976_1_)
    {
        this(p_i1976_1_.getSeed(), p_i1976_1_.getWorldInfo().getTerrainType());
    }
}
