package com.github.thelonelysprite.alfheim.items;

import com.github.thelonelysprite.alfheim.Constants;
import com.github.thelonelysprite.alfheim.IconHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import vazkii.botania.api.recipe.IElvenItem;
import vazkii.botania.api.recipe.IFlowerComponent;
import vazkii.botania.common.Botania;
import vazkii.botania.common.lib.LibItemNames;

import java.awt.*;
import java.util.List;

/**
 * Created by justin on 20/10/2014.
 */
public class AlfheimRescources extends AddonItem implements IElvenItem {

    final int types = 5;
    IIcon[] icons;

    public AlfheimRescources(String name) {
        super(name);
        setHasSubtypes(true);
    }
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        for(int i = 0; i < types; i++)
            par3List.add(new ItemStack(par1, 1, i));
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        icons = new IIcon[types];
        for(int i = 0; i < icons.length; i++)
            icons[i] = IconHelper.forName(par1IconRegister, Constants.ALFHEIM_RESOURCE_NAMES[i]);
    }
    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return "item." + Constants.ALFHEIM_RESOURCE_NAMES[Math.min(types - 1, par1ItemStack.getItemDamage())];
    }

    @Override
    public IIcon getIconFromDamage(int par1) {
        return icons[Math.min(icons.length - 1, par1)];
    }

    @Override
    public boolean isElvenItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return itemStack.getItemDamage() == 11 ? itemStack.copy() : null;
    }
    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        //if(par1ItemStack.getItemDamage() == 3|| par1ItemStack.getItemDamage() == 4)
            return Color.HSBtoRGB(Botania.proxy.getWorldElapsedTicks() * 2 % 360 / 360F, 0.25F, 1F);

       // return 0xFFFFFF;
    }
}
