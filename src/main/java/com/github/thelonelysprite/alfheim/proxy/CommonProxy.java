package com.github.thelonelysprite.alfheim.proxy;

import com.github.thelonelysprite.alfheim.Alfheim;
import com.github.thelonelysprite.alfheim.blocks.TileAlfPortal2;
import com.github.thelonelysprite.alfheim.blocks.TileSpiritPylon;
import com.github.thelonelysprite.alfheim.handlers.GUIHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.ForgeChunkManager;

/**
 * Created by justin on 14/08/2014.
 */
public abstract class CommonProxy {

    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileSpiritPylon.class,"TileSpiritPylon");
        GameRegistry.registerTileEntity(TileAlfPortal2.class,"TileAlfPortal2");
    }

    public void registerNetworkStuff() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Alfheim.instance, new GUIHandler());
        ForgeChunkManager.setForcedChunkLoadingCallback(Alfheim.instance, null);
    }

    public abstract void registerItemRenderers();

    public abstract void  registerTileRenderers();

    public abstract void registerEntityRenderers();
}
