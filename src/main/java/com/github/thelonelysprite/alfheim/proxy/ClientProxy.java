package com.github.thelonelysprite.alfheim.proxy;

import com.github.thelonelysprite.alfheim.blocks.AddonBlocks;
import com.github.thelonelysprite.alfheim.blocks.TileAlfPortal2;
import com.github.thelonelysprite.alfheim.blocks.TileSpiritPylon;
import com.github.thelonelysprite.alfheim.entities.ElvenDragon;
import com.github.thelonelysprite.alfheim.entities.ElvenPixie;
import com.github.thelonelysprite.alfheim.items.AddonItems;
import com.github.thelonelysprite.alfheim.renderers.*;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

/**
 * Created by justin on 14/08/2014.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void registerItemRenderers() {
        MinecraftForgeClient.registerItemRenderer(AddonItems.exampleItem, new RenderTransparentItem());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AddonBlocks.spiritPylon), new RenderPylonItem());
    }

    @Override
    public void registerTileRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileSpiritPylon.class, new PylonRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAlfPortal2.class, new AlfPortal2Renderer());
    }

    @Override
    public void registerEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(ElvenDragon.class, new RenderDragon());
        RenderingRegistry.registerEntityRenderingHandler(ElvenPixie.class,new RenderPixie());
    }
}
