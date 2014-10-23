package com.github.thelonelysprite.alfheim.renderers;

/**
 * Created by justin on 26/08/2014.
 */

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import static org.lwjgl.opengl.GL11.*;

public class RenderTransparentItem implements IItemRenderer {
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type != ItemRenderType.INVENTORY;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.ENTITY_BOBBING;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case ENTITY: {
                glPushMatrix();
                glTranslatef(-0.5F, 0F, 0F);
                if (item.isOnItemFrame())
                    glTranslatef(0F, -0.3F, 0.01F);
                render(item);
                glPopMatrix();
                break;
            }
            case EQUIPPED: {
                render(item);
                break;
            }
            case EQUIPPED_FIRST_PERSON: {
                render(item);
                break;
            }
            default:
                break;
        }
    }

    public static void render(ItemStack item) {
        int dmg = item.getItemDamage();
        IIcon icon = item.getItem().getIconFromDamageForRenderPass(dmg, 0);
        float f = icon.getMinU();
        float f1 = icon.getMaxU();
        float f2 = icon.getMinV();
        float f3 = icon.getMaxV();
        float scale = 1F / 16F;
        glPushMatrix();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glColor4f(1F, 1F, 1F, 1F);
        ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), scale);
        glDisable(GL_BLEND);
        glPopMatrix();
        glColor4f(1F, 1F, 1F, 1F);
    }
}