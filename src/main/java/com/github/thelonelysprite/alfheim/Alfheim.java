package com.github.thelonelysprite.alfheim;

import com.github.thelonelysprite.alfheim.blocks.AddonBlocks;
import com.github.thelonelysprite.alfheim.dimension.WorldProviderAlfheim;
import com.github.thelonelysprite.alfheim.items.ModItems;
import com.github.thelonelysprite.alfheim.proxy.CommonProxy;
import com.github.thelonelysprite.alfheim.recipes.CraftingRecipes;
import com.github.thelonelysprite.alfheim.recipes.FurnaceRecipes;
import com.github.thelonelysprite.alfheim.recipes.ModComp;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.DimensionManager;

@Mod(modid = Constants.MODID, version = Constants.VERSION, dependencies = Constants.DEPENDENCIES)
public class Alfheim {

    @Mod.Instance(Constants.MODID)
    public static Alfheim instance;

    @SidedProxy(clientSide = "com.github.thelonelysprite.alfheim.proxy.ClientProxy", serverSide = "com.github.thelonelysprite.alfheim.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static CreativeTabs Tab = new CreativeTabs(Constants.MODID) {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.bedrock);
        }
    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModItems.init();
        AddonBlocks.init();
        CraftingRecipes.init();
        FurnaceRecipes.init();
        ModComp.init();
        Constants.log.info(ModItems.exampleItem.getUnlocalizedName());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerItemRenderers();
        proxy.registerNetworkStuff();
        proxy.registerTileEntities();
        proxy.registerTileRenderers();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
       DimensionManager.registerProviderType(-98, WorldProviderAlfheim.class, true);
       DimensionManager.registerDimension(-98,-98);
    }
}
