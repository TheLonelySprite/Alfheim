package com.github.thelonelysprite.alfheim.dimension.biomes;

import com.github.thelonelysprite.alfheim.dimension.trees.WorldGenDreamwoodForest;
import com.github.thelonelysprite.alfheim.dimension.trees.WorldGenDreamwoodTrees;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenForest;

import java.util.Random;

/**
 * Created by justin on 19/10/2014.
 */
public class ElvenForestBiome extends BiomeGenBase {
    protected static final WorldGenDreamwoodForest forest1 = new WorldGenDreamwoodForest(false, true);

    public ElvenForestBiome(int p_i1971_1_) {
        super(p_i1971_1_);
        this.theBiomeDecorator.treesPerChunk = 10;
        this.theBiomeDecorator.grassPerChunk = 2;
        this.worldGeneratorTrees = new WorldGenDreamwoodTrees(true);
    }

}
