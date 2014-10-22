package com.github.thelonelysprite.alfheim.dimension;

import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import vazkii.botania.api.item.IFlowerlessWorld;

/**
 * Created by justin on 06/10/2014.
 */
public class WorldProviderAlfheim extends WorldProvider implements IFlowerlessWorld {
    /**
     * Returns the dimension's name, e.g. "The End", "Nether", or "Overworld".
     */
    @Override
    public String getDimensionName() {
        return "Alfheim";
    }

    /**
     * creates a new world chunk manager for WorldProvider
     */
    public void registerWorldChunkManager()
    {
        this.worldChunkMgr = new WorldChunkManagerAlfheim(worldObj);
        this.dimensionId = -98;
    }

    /**
     * Returns a new chunk provider which generates chunks for this world
     */
    public IChunkProvider createChunkGenerator()
    {
        return new ChunkProviderAlfheim(this.worldObj, this.worldObj.getSeed());
    }

    /**
     * Returns 'true' if in the "main surface world", but 'false' if in the Nether or End dimensions.
     */
    public boolean isSurfaceWorld()
    {
        return false;
    }

    /**
     * Will check if the x, z position specified is alright to be set as the map spawn point
     */
    public boolean canCoordinateBeSpawn(int p_76566_1_, int p_76566_2_)
    {
        return false;
    }


    /**
     * @return False to not have flowers generate, True to have them generate
     */
    @Override
    public boolean generateFlowers(World world) {
        return false;
    }
}
