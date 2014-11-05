package com.github.thelonelysprite.alfheim.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import vazkii.botania.common.item.ModItems;

/**
 * Created by justin on 24/10/2014.
 */
public class ElvenPixie extends EntityFlyingCreature implements IMob {
    public int courseChangeCooldown;
    public double waypointX;
    public double waypointY;
    public double waypointZ;
    private Entity targetedEntity;
    /**
     * Cooldown time between target loss and new target aquirement.
     */
    private int aggroCooldown;
    public int prevAttackCounter;
    public int attackCounter;

    public ElvenPixie(World par1World) {
        super(par1World);
        setSize(1.0F, 1.0F);
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this,EntityPlayer.class,1.2D,false));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataWatcher.addObject(20, 0);
    }

    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
        if (this.isEntityInvulnerable()) {
            return false;

        } else {
            return super.attackEntityFrom(p_70097_1_, p_70097_2_);
        }
    }

    protected void updateEntityActionState() {
        if (!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
            this.setDead();
        }

        this.despawnEntity();
        this.prevAttackCounter = this.attackCounter;
        double d0 = this.waypointX - this.posX;
        double d1 = this.waypointY - this.posY;
        double d2 = this.waypointZ - this.posZ;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;

        if (d3 < 1.0D || d3 > 3600.0D) {
            this.waypointX = this.posX + (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.waypointY = this.posY + (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.waypointZ = this.posZ + (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
        }

        if (this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown += this.rand.nextInt(5) + 2;
            d3 = (double) MathHelper.sqrt_double(d3);

            if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3)) {
                this.motionX += d0 / d3 * 0.1D;
                this.motionY += d1 / d3 * 0.1D;
                this.motionZ += d2 / d3 * 0.1D;
            } else {
                this.waypointX = this.posX;
                this.waypointY = this.posY;
                this.waypointZ = this.posZ;
            }
        }

        if (this.targetedEntity != null && this.targetedEntity.isDead) {
            this.targetedEntity = null;
        }

        if (this.targetedEntity == null || this.aggroCooldown-- <= 0) {
            this.targetedEntity = this.worldObj.getClosestVulnerablePlayerToEntity(this, 100.0D);

            if (this.targetedEntity != null) {
                this.aggroCooldown = 20;
            }
        }

        double d4 = 64.0D;

        if (this.targetedEntity != null && this.targetedEntity.getDistanceSqToEntity(this) < d4 * d4) {
            double d5 = this.targetedEntity.posX - this.posX;
            double d6 = this.targetedEntity.boundingBox.minY + (double) (this.targetedEntity.height / 2.0F) - (this.posY + (double) (this.height / 2.0F));
            double d7 = this.targetedEntity.posZ - this.posZ;
            this.renderYawOffset = this.rotationYaw = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;

            if (this.canEntityBeSeen(this.targetedEntity)) {
                if (this.attackCounter == 10) {
                    //this.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1007, (int) this.posX, (int) this.posY, (int) this.posZ, 0);
                }

                ++this.attackCounter;

                if (this.attackCounter == 20) {
                   // this.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1008, (int) this.posX, (int) this.posY, (int) this.posZ, 0);
                    double d8 = 4.0D;
                    Vec3 vec3 = this.getLook(1.0F);
                    this.getNavigator().tryMoveToEntityLiving(this.targetedEntity, 4.0f );
                    this.attackCounter = -40;
                }
            } else if (this.attackCounter > 0) {
                --this.attackCounter;
            }
        } else {
            this.renderYawOffset = this.rotationYaw = -((float) Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float) Math.PI;

            if (this.attackCounter > 0) {
                --this.attackCounter;
            }
        }

        if (!this.worldObj.isRemote) {
            int b1 =  this.dataWatcher.getWatchableObjectInt(20);
            int  b0 = (this.attackCounter > 10 ? 1 : 0);

            if (b1 != b0) {
                this.setType(b0);
            }
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0);
    }

    /**
     * True if the ghast has an unobstructed line of travel to the waypoint.
     */
    private boolean isCourseTraversable(double p_70790_1_, double p_70790_3_, double p_70790_5_, double p_70790_7_) {
        double d4 = (this.waypointX - this.posX) / p_70790_7_;
        double d5 = (this.waypointY - this.posY) / p_70790_7_;
        double d6 = (this.waypointZ - this.posZ) / p_70790_7_;
        AxisAlignedBB axisalignedbb = this.boundingBox.copy();

        for (int i = 1; (double) i < p_70790_7_; ++i) {
            axisalignedbb.offset(d4, d5, d6);

            if (!this.worldObj.getCollidingBoundingBoxes(this, axisalignedbb).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public void setType(int type) {
        dataWatcher.updateObject(20, type);
    }

    public int getType() {
        return dataWatcher.getWatchableObjectInt(20);
    }

    public int getMaxSpawnedInChunk() {
        return 5;
    }

    public boolean getCanSpawnHere() {
        return this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
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
