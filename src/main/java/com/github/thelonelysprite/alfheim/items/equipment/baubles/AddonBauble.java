package com.github.thelonelysprite.alfheim.items.equipment.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import com.github.thelonelysprite.alfheim.items.AddonItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by justin on 20/10/2014.
 */
public abstract class AddonBauble extends AddonItem implements IBauble {
    public AddonBauble(String name) {
        super(name);
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(par3EntityPlayer);
        for(int i = 0; i < baubles.getSizeInventory(); i++) {
            if(baubles.isItemValidForSlot(i, par1ItemStack)) {
                ItemStack stackInSlot = baubles.getStackInSlot(i);
                if(stackInSlot == null || ((IBauble) stackInSlot.getItem()).canUnequip(stackInSlot, par3EntityPlayer)) {
                    if(!par2World.isRemote) {
                        baubles.setInventorySlotContents(i, par1ItemStack.copy());
                        if(!par3EntityPlayer.capabilities.isCreativeMode)
                            par3EntityPlayer.inventory.setInventorySlotContents(par3EntityPlayer.inventory.currentItem, null);
                    }

                    onEquipped(par1ItemStack, par3EntityPlayer);

                    if(stackInSlot != null) {
                        ((IBauble) stackInSlot.getItem()).onUnequipped(stackInSlot, par3EntityPlayer);
                        return stackInSlot.copy();
                    }
                    break;
                }
            }
        }


        return par1ItemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        BaubleType type = getBaubleType(par1ItemStack);
        if(GuiScreen.isShiftKeyDown()) {
            addStringToTooltip(StatCollector.translateToLocal("botania.baubletype." + type.name().toLowerCase()), par3List);

            String key = vazkii.botania.client.core.helper.RenderHelper.getKeyDisplayString("Baubles Inventory");

            if(key != null)
                addStringToTooltip(StatCollector.translateToLocal("botania.baubletooltip").replaceAll("%key%", key), par3List);
        } else addStringToTooltip(StatCollector.translateToLocal("botaniamisc.shiftinfo"), par3List);
    }

    private void addStringToTooltip(String s, List<String> tooltip) {
        tooltip.add(s.replaceAll("&", "\u00a7"));
    }

    @Override
    public boolean canEquip(ItemStack stack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack stack, EntityLivingBase player) {
        return true;
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        if(player.ticksExisted == 1)
            onEquippedOrLoadedIntoWorld(stack, player);
    }

    @Override
    public void onEquipped(ItemStack stack, EntityLivingBase player) {
        if(!player.worldObj.isRemote)
            player.worldObj.playSoundAtEntity(player, "botania:equipBauble", 0.1F, 1.3F);

        onEquippedOrLoadedIntoWorld(stack, player);
    }

    public void onEquippedOrLoadedIntoWorld(ItemStack stack, EntityLivingBase player) {
        // NO-OP
    }

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player) {
        // NO-OP
    }
}
