package com.github.thelonelysprite.alfheim.items.equipment.tools;

import com.github.thelonelysprite.alfheim.Alfheim;
import com.github.thelonelysprite.alfheim.Constants;
import com.github.thelonelysprite.alfheim.IconHelper;
import com.github.thelonelysprite.alfheim.items.AddonItems;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

/**
 * Created by justin on 03/11/2014.
 */
public class ItemGaiaSteelSword extends ItemSword implements IManaUsingItem {
    public static final int MANA_PER_DAMAGE = 51;

    IIcon elucidatorIcon;

    public ItemGaiaSteelSword() {
        this(AddonItems.gaiaSteelToolMAterial, "gaiaSteelSword");
    }

    public ItemGaiaSteelSword(ToolMaterial mat, String name) {
        super(mat);
        setCreativeTab(Alfheim.Tab);
        setUnlocalizedName(name);
    }

    @Override
    public Item setUnlocalizedName(String par1Str) {
        GameRegistry.registerItem(this, par1Str);
        return super.setUnlocalizedName(par1Str);
    }

    @Override
    public String getUnlocalizedNameInefficiently(ItemStack par1ItemStack) {
        return super.getUnlocalizedNameInefficiently(par1ItemStack).replaceAll("item.", "item." + (Constants.MODID + ":"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
        itemIcon = IconHelper.forItem(par1IconRegister, this);
        elucidatorIcon = vazkii.botania.client.core.helper.IconHelper.forName(par1IconRegister, "elucidator");
    }

    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
        if(usesMana(par1ItemStack))
            ToolCommons.damageItem(par1ItemStack, 1, par3EntityLivingBase, MANA_PER_DAMAGE);
        return true;
    }

    @Override
    public IIcon getIconIndex(ItemStack par1ItemStack) {
        return par1ItemStack.getDisplayName().equalsIgnoreCase("the elucidator") ? elucidatorIcon : super.getIconIndex(par1ItemStack);
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return getIconIndex(stack);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int x, int y, int z, EntityLivingBase entity) {
        if(usesMana(stack) && block.getBlockHardness(world, x, y, z) != 0F)
            ToolCommons.damageItem(stack, 1, entity, MANA_PER_DAMAGE);

        return true;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        if(!world.isRemote && player instanceof EntityPlayer && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExact(stack, (EntityPlayer) player, MANA_PER_DAMAGE * 2, true))
            stack.setItemDamage(stack.getItemDamage() - 1);
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
        return par2ItemStack.getItem() == ModItems.manaResource && par2ItemStack.getItemDamage() == 0 ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

    @Override
    public boolean usesMana(ItemStack stack) {
        return true;
    }
}
