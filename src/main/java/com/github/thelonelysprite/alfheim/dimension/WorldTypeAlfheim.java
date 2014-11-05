package com.github.thelonelysprite.alfheim.dimension;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerZoom;

public class WorldTypeAlfheim extends WorldType {
	public WorldTypeAlfheim() {
		super("Alfheim");

	}

	@Override
	public GenLayer getBiomeLayer(long worldSeed, GenLayer parentLayer) {
		GenLayer ret = new CustomGenLayerBiome(200L, parentLayer, this);

		ret = GenLayerZoom.magnify(1000L, ret, 2);
		return ret;
	}

	@Override
	public WorldChunkManager getChunkManager(World world) {
		WorldChunkManager wcm = new WorldChunkManagerAlfheim(world);
		return wcm;
	}

	@Override
	public double getHorizon(World world) {
		return 63.0D;
	}

	public IChunkProvider getChunkGenerator(World world, String generatorOptions) {
		return new ChunkProviderAlfheim(world, world.getSeed());
	}
}