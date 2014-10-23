package com.github.thelonelysprite.alfheim.renderers;

import com.github.thelonelysprite.alfheim.Constants;
import com.github.thelonelysprite.alfheim.blocks.TileSpiritPylon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL12;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.core.helper.ShaderHelper;
import vazkii.botania.client.model.IPylonModel;
import vazkii.botania.client.model.ModelPylon;
import vazkii.botania.client.model.ModelPylonOld;
import vazkii.botania.common.core.handler.ConfigHandler;

import java.awt.*;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by justin on 06/10/2014.
 */
public class PylonRenderer extends TileEntitySpecialRenderer {


    private static final ResourceLocation textureSpiritOld = new ResourceLocation(Constants.MODEL_PYLON_SPIRIT_OLD);
    private static final ResourceLocation textureSpirit = new ResourceLocation(Constants.MODEL_PYLON_SPIRIT);

    IPylonModel model;

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float pticks) {
        TileSpiritPylon tile = ((TileSpiritPylon) tileentity);
        double time = ClientTickHandler.ticksInGame + pticks;
        Color color = Color.getHSBColor((float) ((time * 5 + new Random(tile.xCoord ^ tile.yCoord ^ tile.zCoord).nextInt(10000)) % 360) / 360F, 0.4F, 0.9F);
        if (model == null)
            model = ConfigHandler.oldPylonModel ? new ModelPylonOld() : new ModelPylon();

        glPushMatrix();
        glEnable(GL12.GL_RESCALE_NORMAL);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glColor4f(1F, 1F, 1F, 1F);

        if (ConfigHandler.oldPylonModel)
            Minecraft.getMinecraft().renderEngine.bindTexture(textureSpiritOld);
        else
            Minecraft.getMinecraft().renderEngine.bindTexture(textureSpirit);

        double worldTime = tileentity.getWorldObj() == null ? 0 : (double) (ClientTickHandler.ticksInGame + pticks);

        if (tileentity != null)
            worldTime += new Random(tileentity.xCoord ^ tileentity.yCoord ^ tileentity.zCoord).nextInt(360);

        if (ConfigHandler.oldPylonModel) {
            glTranslated(d0 + 0.5, d1 + 2.2, d2 + 0.5);
            glScalef(1F, -1.5F, -1F);
        } else {
            glTranslated(d0 + 0.2, d1 + 0.05, d2 + 0.8);
            float scale = 0.6F;
            glScalef(scale, 0.6F, scale);
        }


        glPushMatrix();
        if (!ConfigHandler.oldPylonModel)
            glTranslatef(0.5F, 0F, -0.5F);
        glRotatef((float) worldTime * 1.5F, 0F, 1F, 0F);
        if (!ConfigHandler.oldPylonModel)
            glTranslatef(-0.5F, 0F, 0.5F);

        model.renderRing();
        glTranslated(0D, Math.sin(worldTime / 20D) / 20 - 0.025, 0D);
        model.renderGems();
        glPopMatrix();


        glPushMatrix();
        glTranslated(0D, Math.sin(worldTime / 20D) / 17.5, 0D);

        if (!ConfigHandler.oldPylonModel)
            glTranslatef(0.5F, 0F, -0.5F);

        glRotatef((float) -worldTime, 0F, 1F, 0F);
        if (!ConfigHandler.oldPylonModel)
            glTranslatef(-0.5F, 0F, 0.5F);


        glDisable(GL_CULL_FACE);


        glColor3f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
        model.renderCrystal();

        glColor4f(1F, 1F, 1F, 1F);
        if (!ShaderHelper.useShaders()) {
            int light = 15728880;
            int lightmapX = light % 65536;
            int lightmapY = light / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);
            float alpha = (float) ((Math.sin(worldTime / 20D) / 2D + 0.5) / (ConfigHandler.oldPylonModel ? 1D : 2D));
            glColor4f(1F, 1F, 1F, alpha + 0.183F);
        }

        glDisable(GL_ALPHA_TEST);
        glScalef(1.1F, 1.1F, 1.1F);
        if (!ConfigHandler.oldPylonModel)
            glTranslatef(-0.05F, -0.1F, 0.05F);
        else glTranslatef(0F, -0.09F, 0F);

        ShaderHelper.useShader(ShaderHelper.pylonGlow);

        model.renderCrystal();

        ShaderHelper.releaseShader();

        glEnable(GL_ALPHA_TEST);
        glEnable(GL_CULL_FACE);
        glPopMatrix();

        glDisable(GL_BLEND);
        glEnable(GL12.GL_RESCALE_NORMAL);
        glPopMatrix();
    }
}
