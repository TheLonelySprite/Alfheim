package com.github.thelonelysprite.alfheim.dimension.biomes;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * Created by justin on 18/10/2014.
 */
public class ElvenPlainsBiome extends BiomeGenBase {
    public ElvenPlainsBiome(int p_i1971_1_) {
        super(p_i1971_1_);
        this.setTemperatureRainfall(0.8F, 0.4F);
        this.setHeight(height_LowPlains);
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityHorse.class, 5, 2, 6));
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.flowersPerChunk = 4;
        this.theBiomeDecorator.grassPerChunk = 10;
        this.flowers.clear();
    }
}
