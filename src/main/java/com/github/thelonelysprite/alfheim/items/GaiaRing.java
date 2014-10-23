package com.github.thelonelysprite.alfheim.items;

import baubles.api.BaubleType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.common.core.helper.ItemNBTHelper;

import java.util.List;

/**
 * Created by justin on 20/10/2014.
 */
public class GaiaRing extends AddonBauble implements IManaItem {

    protected static final int MAX_MANA = 10000;

    private static final String TAG_MANA = "mana";

    public GaiaRing() {
        super("gaiaRing");
        setMaxDamage(1000);
        setNoRepair();
    }
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(par1, 1, 10000));
    }

    @Override
    public int getDamage(ItemStack stack) {
        float mana = getMana(stack);
        return 1000 - (int) (mana / getMaxMana(stack) * 1000);
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        super.onWornTick(stack, player);
        if (player instanceof EntityPlayer && player.worldObj.getTotalWorldTime() % 2 == 0)
            this.addMana(stack, 35);
    }

    /**
     * This method return the type of bauble this is.
     * Type is used to determine the slots it can go into.
     *
     * @param itemstack
     */
    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.RING;
    }

    /**
     * Gets the amount of mana this item contains
     *
     * @param stack
     */
    @Override
    public int getMana(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_MANA, 0);
    }

    public static void setMana(ItemStack stack, int mana) {
        ItemNBTHelper.setInt(stack, TAG_MANA, mana);
    }

    /**
     * Gets the max amount of mana this item can hold.
     *
     * @param stack
     */
    @Override
    public int getMaxMana(ItemStack stack) {
        return MAX_MANA;
    }

    /**
     * Adds mana to this item.
     *
     * @param stack
     * @param mana
     */
    @Override
    public void addMana(ItemStack stack, int mana) {
        setMana(stack, Math.min(getMana(stack) + mana, getMaxMana(stack)));
        stack.setItemDamage(getDamage(stack));
    }

    /**
     * Can this item receive mana from a mana Pool?
     *
     * @param stack
     * @param pool  The pool it's receiving mana from, can be casted to IManaPool.
     * @see vazkii.botania.api.mana.IManaPool#isOutputtingPower()
     */
    @Override
    public boolean canReceiveManaFromPool(ItemStack stack, TileEntity pool) {
        return false;
    }

    /**
     * Can this item recieve mana from another item?
     *
     * @param stack
     * @param otherStack
     */
    @Override
    public boolean canReceiveManaFromItem(ItemStack stack, ItemStack otherStack) {
        return false;
    }

    /**
     * Can this item export mana to a mana Pool?
     *
     * @param stack
     * @param pool  The pool it's exporting mana to, can be casted to IManaPool.
     * @see vazkii.botania.api.mana.IManaPool#isOutputtingPower()
     */
    @Override
    public boolean canExportManaToPool(ItemStack stack, TileEntity pool) {
        return false;
    }

    /**
     * Can this item export mana to another item?
     *
     * @param stack
     * @param otherStack
     */
    @Override
    public boolean canExportManaToItem(ItemStack stack, ItemStack otherStack) {
        return true;
    }

    /**
     * If this item simply does not export mana at all, set this to true. This is
     * used to skip items that contain mana but can't export it when drawing the
     * mana bar above the XP bar.
     *
     * @param stack
     */
    @Override
    public boolean isNoExport(ItemStack stack) {
        return false;
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return Integer.MAX_VALUE;
    }
}
