package com.github.thelonelysprite.alfheim.items.equipment.tools;

import com.github.thelonelysprite.alfheim.Alfheim;
import com.github.thelonelysprite.alfheim.IconHelper;
import com.github.thelonelysprite.alfheim.items.AddonItems;
import com.github.thelonelysprite.alfheim.recipes.GaiaPickTippingRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.RecipeSorter;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.ItemSpark;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.equipment.bauble.ItemAuraRing;
import vazkii.botania.common.item.equipment.bauble.ItemGreaterAuraRing;
import vazkii.botania.common.item.equipment.tool.ToolCommons;

import java.awt.*;
import java.util.List;


/**
 * Created by justin on 05/11/2014.
 */
public class ItemGaiaSteelPickaxe extends ItemPickaxe implements IManaItem {


    private static final String TAG_ENABLED = "enabled";
    private static final String TAG_MANA = "mana";
    private static final String TAG_TIPPED = "tipped";
    private static final int MAX_MANA = 2000000000;
    private static final Material[] MATERIALS = new Material[] { Material.rock, Material.iron, Material.ice, Material.glass, Material.piston, Material.anvil, Material.grass, Material.ground, Material.sand, Material.snow, Material.craftedSnow, Material.clay };
    private static final int[] LEVELS = new int[] {
            0, 10000, 1000000, 10000000, 100000000, 1000000000
    };
    private static final int[] CREATIVE_MANA = new int[] {
            10000 - 1, 1000000 - 1, 10000000 - 1, 100000000 - 1, 1000000000 - 1, MAX_MANA
    };
    IIcon iconTool, iconOverlay, iconTipped;
    public ItemGaiaSteelPickaxe() {
        super(AddonItems.gaiaSteelToolMAterial);
        setCreativeTab(Alfheim.Tab);
        GameRegistry.addRecipe(new GaiaPickTippingRecipe());
        RecipeSorter.register("alfheim:gaiaPickTipping",GaiaPickTippingRecipe.class, RecipeSorter.Category.SHAPELESS, "");
        setUnlocalizedName("GaiaShatterer");
    }
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for(int mana : CREATIVE_MANA) {
            ItemStack stack = new ItemStack(item);
            setMana(stack, mana);
            list.add(stack);
        }
    }
    @Override
    public Item setUnlocalizedName(String par1Str) {
        GameRegistry.registerItem(this, par1Str);
        return super.setUnlocalizedName(par1Str);
    }
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        String rankFormat = StatCollector.translateToLocal("botaniamisc.toolRank");
        String rank = StatCollector.translateToLocal("botania.rank" + getLevel(par1ItemStack));
        par3List.add(String.format(rankFormat, rank).replaceAll("&", "\u00a7"));
    }
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        getMana(par1ItemStack);
        int level = getLevel(par1ItemStack);
        if(level != 0) {
            setEnabled(par1ItemStack, !isEnabled(par1ItemStack));
            if(!par2World.isRemote)
                par2World.playSoundAtEntity(par3EntityPlayer, "botania:terraPickMode", 0.5F, 0.4F);
        }
        return par1ItemStack;
    }
    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
        if(isEnabled(par1ItemStack)) {
            int level = getLevel(par1ItemStack);
            if(level == 0)
                setEnabled(par1ItemStack, false);
            else if(par3Entity instanceof EntityPlayer && !((EntityPlayer) par3Entity).isSwingInProgress) {
                addMana(par1ItemStack, -level);
                ((EntityPlayer) par3Entity).addPotionEffect(new PotionEffect(Potion.blindness.getId(),600,0));
                ((EntityPlayer) par3Entity).addPotionEffect(new PotionEffect(Potion.weakness.getId(),600,2));
                //((EntityPlayer) par3Entity).addPotionEffect(new PotionEffect(Potion.nightVision.getId(),180,0));
                //((EntityPlayer) par3Entity).addPotionEffect(new PotionEffect(Potion.digSlowdown.getId(),600,4));
            }
        }
    }
    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        if(!isEnabled(stack))
            return false;
        World world = player.worldObj;
        Material mat = world.getBlock(x, y, z).getMaterial();
        if(!ToolCommons.isRightMaterial(mat, MATERIALS))
            return false;
        MovingObjectPosition block = ToolCommons.raytraceFromEntity(world, player, true, 4.5);
        if(block == null)
            return false;
        ForgeDirection direction = ForgeDirection.getOrientation(block.sideHit);
        int fortune = EnchantmentHelper.getFortuneModifier(player);
        boolean silk = EnchantmentHelper.getSilkTouchModifier(player);
        boolean doX = direction.offsetX == 0;
        boolean doY = direction.offsetY == 0;
        boolean doZ = direction.offsetZ == 0;
        int level = getLevel(stack);
        int range = Math.max(0, level+5);
        int rangeY = Math.max(1, range);
        if(range == 0 && level != 1)
            return false;
        ToolCommons.removeBlocksInIteration(player, stack, world, x, y, z, doX ? -range : 0, doY ? -1 : 0, doZ ? -range : 0, doX ? range + 1 : 1, doY ? rangeY * 2 : 1, doZ ? range + 1 : 1, null, MATERIALS, silk, fortune, isTipped(stack));
        return false;
    }
    @Override
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return Integer.MAX_VALUE;
    }
    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        iconTool = IconHelper.forItem(par1IconRegister, this, 0);
        iconOverlay = IconHelper.forItem(par1IconRegister, this, 1);
        iconTipped = IconHelper.forItem(par1IconRegister, this, 2);
    }
    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return pass == 1 && isEnabled(stack) ? iconOverlay : isTipped(stack) ? iconTipped : iconTool;
    }
    public static boolean isTipped(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, TAG_TIPPED, false);
    }
    public static void setTipped(ItemStack stack) {
        ItemNBTHelper.setBoolean(stack, TAG_TIPPED, true);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
        if(par2 == 0 || !isEnabled(par1ItemStack))
            return 0xFFFFFF;
        return Color.HSBtoRGB(0.375F, (float) Math.min(1F, Math.sin(System.currentTimeMillis() / 200D) * 0.5 + 1F), 1F);
    }
    boolean isEnabled(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, TAG_ENABLED, false);
    }
    void setEnabled(ItemStack stack, boolean enabled) {
        ItemNBTHelper.setBoolean(stack, TAG_ENABLED, enabled);
    }
    public static void setMana(ItemStack stack, int mana) {
        ItemNBTHelper.setInt(stack, TAG_MANA, mana);
    }
    @Override
    public int getMana(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_MANA, 0);
    }
    public int getLevel(ItemStack stack) {
        int mana = getMana(stack);
        for(int i = LEVELS.length - 1; i > 0; i--)
            if(mana >= LEVELS[i])
                return i;
        return 0;
    }
    @Override
    public int getMaxMana(ItemStack stack) {
        return MAX_MANA;
    }
    @Override
    public void addMana(ItemStack stack, int mana) {
        setMana(stack, Math.min(getMana(stack) + mana, MAX_MANA));
    }
    @Override
    public boolean canReceiveManaFromPool(ItemStack stack, TileEntity pool) {
        return true;
    }
    @Override
    public boolean canReceiveManaFromItem(ItemStack stack, ItemStack otherStack) {
        return !(otherStack.getItem() instanceof ItemSpark) && !(otherStack.getItem() instanceof ItemAuraRing) && !(otherStack.getItem() instanceof ItemGreaterAuraRing);
    }
    @Override
    public boolean canExportManaToPool(ItemStack stack, TileEntity pool) {
        return false;
    }
    @Override
    public boolean canExportManaToItem(ItemStack stack, ItemStack otherStack) {
        return false;
    }
    @Override
    public boolean isNoExport(ItemStack stack) {
        return true;
    }
    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
        return par2ItemStack.getItem() == ModItems.manaResource && par2ItemStack.getItemDamage() == 4 ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
}
