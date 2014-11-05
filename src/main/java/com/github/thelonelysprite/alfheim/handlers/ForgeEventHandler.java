package com.github.thelonelysprite.alfheim.handlers;

import com.github.thelonelysprite.alfheim.Constants;
import com.github.thelonelysprite.alfheim.dimension.WorldTypeAlfheim;
import com.github.thelonelysprite.alfheim.items.AddonItems;
import com.github.thelonelysprite.alfheim.items.equipment.tools.ItemGaiaSteelPickaxe;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;
import net.minecraftforge.event.world.BlockEvent;
import vazkii.botania.common.item.equipment.tool.elementium.ItemElementiumPick;

/**
 * Created by justin on 09/10/2014.
 */
public class ForgeEventHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onWorldDecoration(DecorateBiomeEvent.Decorate event) {
        Constants.log.info("Biome Decorated");
        if(event.world.getWorldInfo().getTerrainType() instanceof WorldTypeAlfheim && event.type == EventType.FLOWERS){
            event.setResult(Event.Result.DENY);


        }

    }
    @SubscribeEvent
    public void onHarvestDrops(BlockEvent.HarvestDropsEvent event) {
        if(event.harvester != null) {
            ItemStack stack = event.harvester.getCurrentEquippedItem();
            if(stack != null && (stack.getItem() == AddonItems.gaiaShatterer && ItemGaiaSteelPickaxe.isTipped(stack)))
                for(int i = 0; i < event.drops.size(); i++) {
                    ItemStack drop = event.drops.get(i);
                    if(drop != null) {
                        Block block = Block.getBlockFromItem(drop.getItem());
                        if(block != null && ItemElementiumPick.isDisposable(block))
                            event.drops.remove(i);
                    }
                }
        }
    }
}
