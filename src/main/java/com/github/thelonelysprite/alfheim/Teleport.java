package com.github.thelonelysprite.alfheim;

import com.github.thelonelysprite.alfheim.blocks.TileAlfPortal2;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;

import java.util.Iterator;
import java.util.List;

/**
 * Created by justin on 07/10/2014.
 */
public class Teleport {
    public static void teleportEntity(Entity entity, int destinationDimension) {
        if (entity instanceof EntityPlayerMP) {
            EntityPlayerMP par1EntityPlayerMP = (EntityPlayerMP) entity;
            int j = par1EntityPlayerMP.dimension;
            WorldServer worldserver = par1EntityPlayerMP.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);
            par1EntityPlayerMP.dimension = destinationDimension;
            WorldServer worldserver1 = par1EntityPlayerMP.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);
            S07PacketRespawn respawnPacket = new S07PacketRespawn(par1EntityPlayerMP.dimension, par1EntityPlayerMP.worldObj.difficultySetting, worldserver1.getWorldInfo().getTerrainType(), par1EntityPlayerMP.theItemInWorldManager.getGameType());
            par1EntityPlayerMP.playerNetServerHandler.sendPacket(respawnPacket);
            worldserver.removePlayerEntityDangerously(par1EntityPlayerMP);
            par1EntityPlayerMP.isDead = false;
            transferEntityToWorld(par1EntityPlayerMP, j, worldserver, worldserver1);
            WorldServer worldserver2 = par1EntityPlayerMP.getServerForPlayer();
            worldserver.getPlayerManager().removePlayer(par1EntityPlayerMP);
            worldserver2.getPlayerManager().addPlayer(par1EntityPlayerMP);
            worldserver2.theChunkProviderServer.loadChunk((int) par1EntityPlayerMP.posX >> 4, (int) par1EntityPlayerMP.posZ >> 4);
            par1EntityPlayerMP.playerNetServerHandler.setPlayerLocation(par1EntityPlayerMP.posX, par1EntityPlayerMP.posY, par1EntityPlayerMP.posZ, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);
            par1EntityPlayerMP.theItemInWorldManager.setWorld(worldserver1);
            par1EntityPlayerMP.mcServer.getConfigurationManager().updateTimeAndWeatherForPlayer(par1EntityPlayerMP, worldserver1);
            par1EntityPlayerMP.mcServer.getConfigurationManager().syncPlayerInventory(par1EntityPlayerMP);
            Iterator<?> iterator = par1EntityPlayerMP.getActivePotionEffects().iterator();
            while (iterator.hasNext()) {
                PotionEffect potioneffect = (PotionEffect) iterator.next();
                par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(par1EntityPlayerMP.getEntityId(), potioneffect));
            }
        } else if (!entity.worldObj.isRemote && !entity.isDead) {
            MinecraftServer minecraftserver = MinecraftServer.getServer();
            int j = entity.dimension;
            WorldServer worldserver = minecraftserver.worldServerForDimension(j);
            WorldServer worldserver1 = minecraftserver.worldServerForDimension(destinationDimension);
            entity.dimension = destinationDimension;
            entity.worldObj.removeEntity(entity);
            entity.isDead = false;
            transferEntityToWorld(entity, j, worldserver, worldserver1);
            Entity newEntity = EntityList.createEntityByName(EntityList.getEntityString(entity), worldserver1);
            if (newEntity != null) {
                newEntity.copyDataFrom(entity, true);
                worldserver1.spawnEntityInWorld(newEntity);
            }
            entity.isDead = true;
            worldserver.resetUpdateEntityTick();
            worldserver1.resetUpdateEntityTick();
        }
    }

    public static void transferEntityToWorld(Entity entity, int dimension, WorldServer worldserver, WorldServer worldserver1) {
        //if (worldserver1.provider.dimensionId == -98) {
            WorldProvider pOld = worldserver.provider;
            WorldProvider pNew = worldserver1.provider;
            if (entity.isEntityAlive()) {
                worldserver1.spawnEntityInWorld(entity);
                ChunkCoordinates chunkcoordinates = worldserver1.getSpawnPoint();
                chunkcoordinates.posY = pNew.worldObj.getTopSolidOrLiquidBlock(chunkcoordinates.posX, chunkcoordinates.posZ);
                entity.setLocationAndAngles((double) chunkcoordinates.posX, (double) chunkcoordinates.posY, (double) chunkcoordinates.posZ, entity.rotationYaw, entity.rotationPitch);
                worldserver1.updateEntityWithOptionalForce(entity, false);
            }
            entity.setWorld(worldserver1);
        /*}else {
            TileEntity tileEntity = getPortal(worldserver1);

            if (tileEntity != null) {
                int x = tileEntity.xCoord;
                int z = tileEntity.zCoord;
                int y = tileEntity.yCoord;
                if (entity.isEntityAlive()) {
                    worldserver1.spawnEntityInWorld(entity);
                    entity.setLocationAndAngles((double) x, (double) y, (double) z, entity.rotationYaw, entity.rotationPitch);
                    worldserver1.updateEntityWithOptionalForce(entity, false);
                }
                entity.setWorld(worldserver1);
            }else{
                WorldProvider pOld = worldserver.provider;
                WorldProvider pNew = worldserver1.provider;
                if (entity.isEntityAlive()) {
                    worldserver1.spawnEntityInWorld(entity);
                    ChunkCoordinates chunkcoordinates = worldserver1.getSpawnPoint();
                    chunkcoordinates.posY = pNew.worldObj.getTopSolidOrLiquidBlock(chunkcoordinates.posX, chunkcoordinates.posZ);
                    entity.setLocationAndAngles((double) chunkcoordinates.posX, (double) chunkcoordinates.posY, (double) chunkcoordinates.posZ, entity.rotationYaw, entity.rotationPitch);
                    worldserver1.updateEntityWithOptionalForce(entity, false);
                }
                entity.setWorld(worldserver1);
            }

        }*/
    }

    static TileEntity getPortal(World worldserver1){
        for (TileEntity te : (List<TileEntity>)worldserver1.loadedTileEntityList){
            if (te instanceof TileAlfPortal2){
                return te;
            }
        }
        return null;
    }
}
