package com.github.thelonelysprite.alfheim;

import com.github.thelonelysprite.alfheim.blocks.AddonBlocks;
import com.github.thelonelysprite.alfheim.dimension.CustomGenLayerBiome;
import com.github.thelonelysprite.alfheim.dimension.WorldProviderAlfheim;
import com.github.thelonelysprite.alfheim.entities.ElvenDragon;
import com.github.thelonelysprite.alfheim.entities.ElvenPixie;
import com.github.thelonelysprite.alfheim.handlers.ForgeEventHandler;
import com.github.thelonelysprite.alfheim.items.AddonItems;
import com.github.thelonelysprite.alfheim.lexicon.AddonData;
import com.github.thelonelysprite.alfheim.proxy.CommonProxy;
import com.github.thelonelysprite.alfheim.recipes.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.item.Item;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;

import java.util.Random;

@Mod(modid = Constants.MODID, version = Constants.VERSION, dependencies = Constants.DEPENDENCIES)
public class Alfheim {

    @Mod.Instance(Constants.MODID)
    public static Alfheim instance;

    @SidedProxy(clientSide = "com.github.thelonelysprite.alfheim.proxy.ClientProxy", serverSide = "com.github.thelonelysprite.alfheim.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static CreativeTabs Tab = new CreativeTabs(Constants.MODID) {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(AddonBlocks.spiritPylon);
        }
    };

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        AddonItems.init();
        AddonBlocks.init();
        CraftingRecipes.init();
        FurnaceRecipes.init();
        ModComp.init();
        InfusionRecipes.init();
        RunicRecipes.init();
        //registerEntity(ElvenDragon.class, "ElvenDragon");
        registerEntity(ElvenPixie.class, "ElvenPixie");
        //Constants.log.info(AddonItems.exampleItem.getUnlocalizedName());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        AddonData.init();
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
        proxy.registerItemRenderers();
        proxy.registerNetworkStuff();
        proxy.registerTileEntities();
        proxy.registerTileRenderers();
        proxy.registerEntityRenderers();
        DimensionManager.registerProviderType(-98, WorldProviderAlfheim.class, true);
        DimensionManager.registerDimension(-98,-98);
        EntityRegistry.addSpawn("ElvenPixie",200,1,8, EnumCreatureType.ambient, CustomGenLayerBiome.elvenPlains);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
    @SuppressWarnings("unchecked")
    public static void registerEntity(Class entityClass, String name) {
        int entityID = EntityRegistry.findGlobalUniqueEntityId();
        long seed = name.hashCode();
        Random rand = new Random(seed);
        int primaryColor = rand.nextInt() * 16777215;
        int secondaryColor = rand.nextInt() * 16777215;
        EntityRegistry.registerGlobalEntityID(entityClass, name, entityID);
        EntityRegistry.registerModEntity(entityClass, name, entityID, instance, 64, 1, true);
        EntityList.entityEggs.put(Integer.valueOf(entityID), new EntityList.EntityEggInfo(entityID, primaryColor, secondaryColor));

    }
}
