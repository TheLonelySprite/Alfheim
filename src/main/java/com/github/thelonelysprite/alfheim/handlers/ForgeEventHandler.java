package com.github.thelonelysprite.alfheim.handlers;

import com.github.thelonelysprite.alfheim.Constants;
import com.github.thelonelysprite.alfheim.dimension.WorldTypeAlfheim;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;

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
}
