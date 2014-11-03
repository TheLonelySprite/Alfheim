package com.github.thelonelysprite.alfheim.entities;

import com.github.thelonelysprite.alfheim.items.AddonItems;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lib.LibOreDict;

/**
 * Created by justin on 24/10/2014.
 */
public class ElvenPixie extends EntityGhast {

    public ElvenPixie(World par1World) {
        super(par1World);
        setSize(1.0F, 1.0F);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataWatcher.addObject(20, 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0);
    }

    public void setType(int type) {
        dataWatcher.updateObject(20, type);
    }

    public int getType() {
        return dataWatcher.getWatchableObjectInt(20);
    }

    public int getMaxSpawnedInChunk() {
        return 300;
    }

    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL;
    }

    protected float getSoundVolume() {
        return 0.1F;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound() {
        return "mob.ghast.moan";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound() {
        return "mob.ghast.scream";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound() {
        return "mob.ghast.death";
    }

    protected Item getDropItem() {
        return ModItems.manaResource;
    }

    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {

    }

    protected void dropRareDrop(int p_70600_1_) {

        this.entityDropItem(new ItemStack(getDropItem(), 1, 1), 0.0F);

    }
}
