package com.github.thelonelysprite.alfheim;

/**
 * Created by justin on 18/10/2014.
 */

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import vazkii.botania.client.lib.LibResources;
public final class IconHelper {
    public static IIcon forName(IIconRegister ir, String name) {
        return ir.registerIcon("alfheim:" + name);
    }
    public static IIcon forBlock(IIconRegister ir, Block block) {
        return forName(ir, block.getUnlocalizedName().replaceAll("tile\\.", ""));
    }
    public static IIcon forBlock(IIconRegister ir, Block block, int i) {
        return forBlock(ir, block, Integer.toString(i));
    }
    public static IIcon forBlock(IIconRegister ir, Block block, int i, String dir) {
        return forBlock(ir, block, Integer.toString(i), dir);
    }
    public static IIcon forBlock(IIconRegister ir, Block block, String s) {
        return forName(ir, block.getUnlocalizedName().replaceAll("tile\\.", "") + s);
    }
    public static IIcon forBlock(IIconRegister ir, Block block, String s, String dir) {
        return forName(ir, dir + "/" + block.getUnlocalizedName().replaceAll("tile\\.", "") + s);
    }
    public static IIcon forItem(IIconRegister ir, Item item) {
        return forName(ir, item.getUnlocalizedName().replaceAll("item\\.", ""));
    }
    public static IIcon forItem(IIconRegister ir, Item item, int i) {
        return forItem(ir, item, Integer.toString(i));
    }
    public static IIcon forItem(IIconRegister ir, Item item, String s) {
        return forName(ir, item.getUnlocalizedName().replaceAll("item\\.", "") + s);
    }
}
