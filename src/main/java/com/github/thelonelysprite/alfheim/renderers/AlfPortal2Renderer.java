package com.github.thelonelysprite.alfheim.renderers;

/**
 * Created by justin on 17/10/2014.
 */

import com.github.thelonelysprite.alfheim.blocks.BlockAlfPortal2;
import com.github.thelonelysprite.alfheim.blocks.TileAlfPortal2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import vazkii.botania.client.core.handler.ClientTickHandler;

import static org.lwjgl.opengl.GL11.*;

public class AlfPortal2Renderer extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
        TileAlfPortal2 portal = (TileAlfPortal2) tileentity;
        int meta = portal.getBlockMetadata();
        if (meta == 0)
            return;
        glPushMatrix();
        glTranslated(d0, d1, d2);
        glTranslatef(-1F, 1F, 0.25F);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glColor4f(1F, 1F, 1F, 1F);
        glDisable(GL_ALPHA_TEST);
        glEnable(GL_CULL_FACE);
        float alpha = (float) Math.min(1F, (Math.sin((ClientTickHandler.ticksInGame + f) / 8D) + 1D) / 7D + 0.6D) * (Math.min(60, portal.ticksOpen) / 60F);
        glColor4f(1F, 1F, 1F, alpha);
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        if (meta == 2) {
            glTranslatef(1.25F, 0F, 1.75F);
            glRotatef(90F, 0F, 1F, 0F);
        }
        renderIcon(0, 0, BlockAlfPortal2.portalTex, 3, 3, 240);
        if (meta == 2) {
            glTranslated(0F, 0F, 0.5F);
            renderIcon(0, 0, BlockAlfPortal2.portalTex, 3, 3, 240);
            glTranslated(0F, 0F, -0.5F);
        }
        glRotatef(180F, 0F, 1F, 0F);
        glTranslated(-3F, 0F, -0.5F);
        renderIcon(0, 0, BlockAlfPortal2.portalTex, 3, 3, 240);
        if (meta == 2) {
            glTranslated(0F, 0F, 0.5F);
            renderIcon(0, 0, BlockAlfPortal2.portalTex, 3, 3, 240);
            glTranslated(0F, 0F, -0.5F);
        }
        glDisable(GL_CULL_FACE);
        glEnable(GL_ALPHA_TEST);
        glDisable(GL_BLEND);
        glColor4f(1F, 1F, 1F, 1F);
        glPopMatrix();
    }

    public void renderIcon(int par1, int par2, IIcon par3Icon, int par4, int par5, int brightness) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setBrightness(brightness);
        tessellator.addVertexWithUV(par1 + 0, par2 + par5, 0, par3Icon.getMinU(), par3Icon.getMaxV());
        tessellator.addVertexWithUV(par1 + par4, par2 + par5, 0, par3Icon.getMaxU(), par3Icon.getMaxV());
        tessellator.addVertexWithUV(par1 + par4, par2 + 0, 0, par3Icon.getMaxU(), par3Icon.getMinV());
        tessellator.addVertexWithUV(par1 + 0, par2 + 0, 0, par3Icon.getMinU(), par3Icon.getMinV());
        tessellator.draw();
    }
}
