package com.github.thelonelysprite.alfheim.proxy;

import com.github.thelonelysprite.alfheim.blocks.AddonBlocks;
import com.github.thelonelysprite.alfheim.blocks.TileSpiritPylon;
import com.github.thelonelysprite.alfheim.items.ModItems;
import com.github.thelonelysprite.alfheim.renderers.RenderPylonItem;
import com.github.thelonelysprite.alfheim.renderers.PylonRenderer;
import com.github.thelonelysprite.alfheim.renderers.RenderTransparentItem;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

/**
 * Created by justin on 14/08/2014.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void registerItemRenderers() {
    MinecraftForgeClient.registerItemRenderer(ModItems.exampleItem, new RenderTransparentItem());
    MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(AddonBlocks.spiritPylon), new RenderPylonItem());
    }

    @Override
    public void  registerTileRenderers(){


        ClientRegistry.bindTileEntitySpecialRenderer(TileSpiritPylon.class, new PylonRenderer());
    }
}
