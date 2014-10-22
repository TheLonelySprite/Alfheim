package com.github.thelonelysprite.alfheim.items;

import com.github.thelonelysprite.alfheim.Alfheim;
import com.github.thelonelysprite.alfheim.Constants;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

/**
 * Created by justin on 19/09/2014.
 */
public class AddonItem extends Item {

    public AddonItem(String name){
        super();
        setCreativeTab(Alfheim.Tab);
        setUnlocalizedName(Constants.MODID+":"+name);
        setTextureName(Constants.MODID + ":"+name);
        GameRegistry.registerItem(this, name);
    }
}
