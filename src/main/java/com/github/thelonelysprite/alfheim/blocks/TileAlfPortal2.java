package com.github.thelonelysprite.alfheim.blocks;

/**
 * Created by justin on 17/10/2014.
 */

import com.github.thelonelysprite.alfheim.Alfheim;
import com.github.thelonelysprite.alfheim.Teleport;
import com.github.thelonelysprite.alfheim.lexicon.AddonData;
import com.google.common.base.Function;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeChunkManager;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.ILexicon;
import vazkii.botania.api.recipe.IElvenItem;
import vazkii.botania.api.recipe.RecipeElvenTrade;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.TileMod;
import vazkii.botania.common.block.tile.mana.TilePool;
import vazkii.botania.common.core.handler.ConfigHandler;
import vazkii.botania.common.item.ItemLexicon;
import vazkii.botania.common.lexicon.LexiconData;

import java.util.ArrayList;
import java.util.List;

public class TileAlfPortal2 extends TileMod {
    private static final int[][] DREAMWOOD_POSITIONS = {
            {-1, 0, 0}, {1, 0, 0}, {-2, 1, 0}, {2, 1, 0}, {-2, 3, 0}, {2, 3, 0}, {-1, 4, 0}, {1, 4, 0}
    };
    private static final int[][] GLIMMERING_DREAMWOOD_POSITIONS = {
            {-2, 2, 0}, {2, 2, 0}, {0, 4, 0}
    };
    private static final int[][] PYLON_POSITIONS = {
            {-3, 1, 3}, {3, 1, 3}
    };
    private static final int[][] POOL_POSITIONS = {
            {-3, 0, 3}, {3, 0, 3}
    };
    private static final int[][] AIR_POSITIONS = {
            {-1, 1, 0}, {0, 1, 0}, {1, 1, 0}, {-1, 2, 0}, {0, 2, 0}, {1, 2, 0}, {-1, 3, 0}, {0, 3, 0}, {1, 3, 0}
    };
    private static final String TAG_TICKS_OPEN = "ticksOpen";
    private static final String TAG_TICKS_SINCE_LAST_ITEM = "ticksSinceLastItem";
    private static final String TAG_STACK_COUNT = "stackCount";
    private static final String TAG_STACK = "portalStack";
    private static final String TAG_PORTAL_FLAG = "_elvenPortal";
    List<ItemStack> stacksIn = new ArrayList();
    public int ticksOpen = 0;
    int ticksSinceLastItem = 0;
    private boolean closeNow = false;
    private static final Function<int[], int[]> CONVERTER_X_Z = new Function<int[], int[]>() {
        @Override
        public int[] apply(int[] input) {
            return new int[]{input[2], input[1], input[0]};
        }
    };
    private static final Function<double[], double[]> CONVERTER_X_Z_FP = new Function<double[], double[]>() {
        @Override
        public double[] apply(double[] input) {
            return new double[]{input[2], input[1], input[0]};
        }
    };
    private static final Function<int[], int[]> CONVERTER_Z_SWAP = new Function<int[], int[]>() {
        @Override
        public int[] apply(int[] input) {
            return new int[]{input[0], input[1], -input[2]};
        }
    };
    private ForgeChunkManager.Ticket ticket;

    @Override
    public void updateEntity() {
        int meta = getBlockMetadata();
        if (meta == 0) {
            ticksOpen = 0;
            return;
        }
        ticksOpen++;
        if (ticksOpen > 60) {
            if (!worldObj.isRemote && this.ticket == null) {
                this.ticket = ForgeChunkManager.requestTicket(Alfheim.instance, worldObj, ForgeChunkManager.Type.NORMAL);

                if (this.ticket != null) {
                    ForgeChunkManager.forceChunk(this.ticket, new ChunkCoordIntPair(xCoord >> 4, zCoord >> 4));

                }
            }
            ticksSinceLastItem++;
            if (ConfigHandler.elfPortalParticlesEnabled)
                blockParticle(meta);
            AxisAlignedBB aabb = getPortalAABB();
            List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, aabb);
            if (!worldObj.isRemote)
                for (EntityItem item : items) {
                    if (item.isDead)
                        continue;
                    ItemStack stack = item.getEntityItem();
                    if (stack != null && (!(stack.getItem() instanceof IElvenItem) || !((IElvenItem) stack.getItem()).isElvenItem(stack)) && !item.getEntityData().hasKey(TAG_PORTAL_FLAG)) {
                        item.setDead();
                        addItem(stack);
                        ticksSinceLastItem = 0;
                    }
                }
            if (ticksSinceLastItem >= 20) {
                if (!worldObj.isRemote)
                    resolveRecipes();
            }

            List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
            for (EntityLivingBase entityLivingBase : entities) {
                if (entityLivingBase.dimension == -98) {
                    Teleport.teleportEntity(entityLivingBase, 0);
                } else {
                    Teleport.teleportEntity(entityLivingBase, -98);
                }
            }

        }
        int newMeta = getValidMetadata();
        if (closeNow) {
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 1 | 2);
            for (int i = 0; i < 36; i++)
                blockParticle(meta);
            closeNow = false;
        } else if (newMeta != meta) {
            if (newMeta == 0)
                for (int i = 0; i < 36; i++)
                    blockParticle(meta);
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, newMeta, 1 | 2);
        }

    }


    private void blockParticle(int meta) {
        int i = worldObj.rand.nextInt(AIR_POSITIONS.length);
        double[] pos = new double[]{
                AIR_POSITIONS[i][0] + 0.5F, AIR_POSITIONS[i][1] + 0.5F, AIR_POSITIONS[i][2] + 0.5F
        };
        if (meta == 2)
            pos = CONVERTER_X_Z_FP.apply(pos);
        float motionMul = 0.2F;
        Botania.proxy.wispFX(getWorldObj(), xCoord + pos[0], yCoord + pos[1], zCoord + pos[2], (float) (Math.random() * 0.25F), (float) (Math.random() * 0.5F + 0.5F), (float) (Math.random() * 0.25F), (float) (Math.random() * 0.15F + 0.1F), (float) (Math.random() - 0.5F) * motionMul, (float) (Math.random() - 0.5F) * motionMul, (float) (Math.random() - 0.5F) * motionMul);
    }

    public boolean onWanded() {
        int meta = getBlockMetadata();
        if (meta == 0) {
            int newMeta = getValidMetadata();
            if (newMeta != 0) {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, newMeta, 1 | 2);
                return true;
            }
        }
        return false;
    }

    AxisAlignedBB getPortalAABB() {
        AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord + 1, zCoord, xCoord + 2, yCoord + 4, zCoord + 1);
        if (getBlockMetadata() == 2)
            aabb = AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1, zCoord - 1, xCoord + 1, yCoord + 4, zCoord + 2);
        return aabb;
    }

    void addItem(ItemStack stack) {
        int size = stack.stackSize;
        stack.stackSize = 1;
        for (int i = 0; i < size; i++)
            stacksIn.add(stack.copy());
    }

    void resolveRecipes() {
        int i = 0;
        for (ItemStack stack : stacksIn) {
            if (stack.getItem() instanceof ILexicon) {
                    ((ILexicon) stack.getItem()).unlockKnowledge(stack, AddonData.aaKnowledge);
                    spawnItem(stack);
                    stacksIn.remove(i);
                    return;

            }
            i++;
        }
        for (RecipeElvenTrade recipe : BotaniaAPI.elvenTradeRecipes) {
            if (recipe.matches(stacksIn, false)) {
                recipe.matches(stacksIn, true);
                spawnItem(recipe.getOutput().copy());
                break;
            }
        }
    }

    void spawnItem(ItemStack stack) {
        EntityItem item = new EntityItem(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, stack);
        item.getEntityData().setBoolean(TAG_PORTAL_FLAG, true);
        worldObj.spawnEntityInWorld(item);
        ticksSinceLastItem = 0;
    }

    @Override
    public void writeToNBT(NBTTagCompound cmp) {
        super.writeToNBT(cmp);
        cmp.setInteger(TAG_STACK_COUNT, stacksIn.size());
        int i = 0;
        for (ItemStack stack : stacksIn) {
            NBTTagCompound stackcmp = new NBTTagCompound();
            stack.writeToNBT(stackcmp);
            cmp.setTag(TAG_STACK + i, stackcmp);
            i++;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound cmp) {
        super.readFromNBT(cmp);
        int count = cmp.getInteger(TAG_STACK_COUNT);
        stacksIn.clear();
        for (int i = 0; i < count; i++) {
            NBTTagCompound stackcmp = cmp.getCompoundTag(TAG_STACK + i);
            ItemStack stack = ItemStack.loadItemStackFromNBT(stackcmp);
            stacksIn.add(stack);
        }
    }

    @Override
    public void writeCustomNBT(NBTTagCompound cmp) {
        cmp.setInteger(TAG_TICKS_OPEN, ticksOpen);
        cmp.setInteger(TAG_TICKS_SINCE_LAST_ITEM, ticksSinceLastItem);
    }

    @Override
    public void readCustomNBT(NBTTagCompound cmp) {
        ticksOpen = cmp.getInteger(TAG_TICKS_OPEN);
        ticksSinceLastItem = cmp.getInteger(TAG_TICKS_SINCE_LAST_ITEM);
    }

    private int getValidMetadata() {
        if (checkConverter(null))
            return 1;
        if (checkConverter(CONVERTER_X_Z))
            return 2;
        return 0;
    }

    private boolean checkConverter(Function<int[], int[]> baseConverter) {
        return checkMultipleConverters(baseConverter) || checkMultipleConverters(CONVERTER_Z_SWAP, baseConverter);
    }

    private boolean checkMultipleConverters(Function<int[], int[]>... converters) {
        if (!check2DArray(AIR_POSITIONS, Blocks.air, -1, converters))
            return false;
        if (!check2DArray(DREAMWOOD_POSITIONS, ModBlocks.dreamwood, 0, converters))
            return false;
        if (!check2DArray(GLIMMERING_DREAMWOOD_POSITIONS, ModBlocks.dreamwood, 5, converters))
            return false;
        if (worldObj.provider.dimensionId != -98) {
            if (!check2DArray(PYLON_POSITIONS, AddonBlocks.spiritPylon, -1, converters))
                return false;
            if (!check2DArray(POOL_POSITIONS, ModBlocks.pool, -1, converters))
                return false;
            lightPylons(converters);
        }
        return true;
    }

    private void lightPylons(Function<int[], int[]>... converters) {
        if (ticksOpen < 50)
            return;
        int cost = ticksOpen == 50 ? 75000 : 2;
        for (int[] pos : PYLON_POSITIONS) {
            for (Function<int[], int[]> f : converters)
                if (f != null)
                    pos = f.apply(pos);
            TileEntity tile = worldObj.getTileEntity(xCoord + pos[0], yCoord + pos[1], zCoord + pos[2]);
            if (tile instanceof TileSpiritPylon) {
                TileSpiritPylon pylon = (TileSpiritPylon) tile;
                pylon.activated = true;
                pylon.centerX = xCoord;
                pylon.centerY = yCoord;
                pylon.centerZ = zCoord;
            }
            tile = worldObj.getTileEntity(xCoord + pos[0], yCoord + pos[1] - 1, zCoord + pos[2]);
            if (tile instanceof TilePool) {
                TilePool pool = (TilePool) tile;
                if (pool.getCurrentMana() < cost)
                    closeNow = true;
                else if (!worldObj.isRemote)
                    pool.recieveMana(-cost);
            }
        }
    }

    private boolean check2DArray(int[][] positions, Block block, int meta, Function<int[], int[]>... converters) {
        for (int[] pos : positions) {
            for (Function<int[], int[]> f : converters)
                if (f != null)
                    pos = f.apply(pos);
            if (!checkPosition(pos, block, meta))
                return false;
        }
        return true;
    }

    private boolean checkPosition(int[] pos, Block block, int meta) {
        int x = xCoord + pos[0];
        int y = yCoord + pos[1];
        int z = zCoord + pos[2];
        Block blockat = worldObj.getBlock(x, y, z);
        if (block == Blocks.air ? blockat.isAir(worldObj, x, y, z) : blockat == block) {
            if (meta == -1)
                return true;
            int metaat = worldObj.getBlockMetadata(x, y, z);
            return meta == metaat;
        }
        return false;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }
}
