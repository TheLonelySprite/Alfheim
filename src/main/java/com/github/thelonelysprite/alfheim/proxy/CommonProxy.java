package com.github.thelonelysprite.alfheim.proxy;

import com.github.thelonelysprite.alfheim.Alfheim;
import com.github.thelonelysprite.alfheim.blocks.TileSpiritPylon;
import com.github.thelonelysprite.alfheim.handlers.GUIHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by justin on 14/08/2014.
 */
public abstract class CommonProxy {

    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileSpiritPylon.class,"TileSpiritPylon");
    }

    public void registerNetworkStuff() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Alfheim.instance, new GUIHandler());
    }

    public abstract void registerItemRenderers();

    public abstract void  registerTileRenderers();
}
