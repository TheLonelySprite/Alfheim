package com.github.thelonelysprite.alfheim.items;

import net.minecraft.item.Item;

/**
 * Created by justin on 19/09/2014.
 */
public class AddonItems {

    public static Item exampleItem;
    public static Item resource;
    public static Item gaiaRing;

    public static void init(){
        exampleItem = new AddonItem("name");
        resource = new AlfheimRescources("resource");
        gaiaRing = new GaiaRing();

    }
}
