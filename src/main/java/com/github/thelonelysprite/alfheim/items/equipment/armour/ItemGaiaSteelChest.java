package com.github.thelonelysprite.alfheim.items.equipment.armour;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * Created by justin on 03/11/2014.
 */
public class ItemGaiaSteelChest extends ItemGaiaSteelArmour {
    public ItemGaiaSteelChest() {
        super(1, "gaiaSteelChest");
    }

    @Override
    public int getHealthBoost() {
        return 12;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        super.onArmorTick(world, player, stack);
        if (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem() == this) {
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
