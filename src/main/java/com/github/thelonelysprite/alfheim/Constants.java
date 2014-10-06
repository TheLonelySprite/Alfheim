package com.github.thelonelysprite.alfheim;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vazkii.botania.client.lib.LibResources;

/**
 * Created by justin on 18/09/2014.
 */
public class Constants {

    public static final String MODID = "alfheim";
    public static final String VERSION = "0.0.1";
    public static final String DEPENDENCIES = "required-after:Forge@[10.12.2.1147,);required-after:Botania;after:Thaumcraft";
    public static final Logger log = LogManager.getLogger(MODID);
    public static final String MODEL_PYLON_SPIRIT_OLD = "alfheim:textures/models/pylonOld.png";
    public static final String MODEL_PYLON_SPIRIT = "alfheim:textures/models/pylon.png";

}
