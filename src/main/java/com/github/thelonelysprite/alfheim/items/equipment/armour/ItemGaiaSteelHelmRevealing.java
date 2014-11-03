package com.github.thelonelysprite.alfheim.items.equipment.armour;

import cpw.mods.fml.common.Optional;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import thaumcraft.api.IGoggles;
import thaumcraft.api.nodes.IRevealer;
import vazkii.botania.client.lib.LibResources;

/**
 * Created by justin on 03/11/2014.
 */
@Optional.InterfaceList({
        @Optional.Interface(modid = "Thaumcraft", iface = "thaumcraft.api.IGoggles", striprefs = true),
        @Optional.Interface(modid = "Thaumcraft", iface = "thaumcraft.api.nodes.IRevealer", striprefs = true)})
public class ItemGaiaSteelHelmRevealing extends ItemGaiaSteelHelm implements IGoggles, IRevealer {
    public ItemGaiaSteelHelmRevealing() {
        super("gaiaSteelHelmReveal");
    }

    @Override
    public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return LibResources.MODEL_TERRASTEEL_2;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        super.onArmorTick(world, player, stack);
        if (player.getCurrentArmor(3) != null && player.getCurrentArmor(3).getItem() == this) {
            int level = -1;
            for (int i = 0; i < 4; i++) {
                if (player.getCurrentArmor(i) != null && player.getCurrentArmor(i).getItem() instanceof ItemGaiaSteelArmour) {
                    level++;
                }
            }

            player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 20, level));
        }
    }
}