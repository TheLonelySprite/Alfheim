package com.github.thelonelysprite.alfheim.renderers;

import com.github.thelonelysprite.alfheim.blocks.TileSpiritPylon;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by justin on 06/10/2014.
 */
public class RenderPylonItem implements IItemRenderer {


    /**
     * Checks if this renderer should handle a specific item's render type
     *
     * @param item The item we are trying to render
     * @param type A render type to check if this renderer handles
     * @return true if this renderer should handle the given render type,
     * otherwise false
     */
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    /**
     * Checks if certain helper functionality should be executed for this renderer.
     * See ItemRendererHelper for more info
     *
     * @param type   The render type
     * @param item   The ItemStack being rendered
     * @param helper The type of helper functionality to be ran
     * @return True to run the helper functionality, false to not.
     */
    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    /**
     * Called to do the actual rendering, see ItemRenderType for details on when specific
     * types are run, and what extra data is passed into the data parameter.
     *
     * @param type The render type
     * @param item The ItemStack being rendered
     * @param data Extra Type specific data
     */
    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

        switch (type) {
            case INVENTORY:
                glPushMatrix();
                glTranslatef(-0.5F, -0.7F, -0.5F);
                TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileSpiritPylon(), 0.0D, 0.0D, 0.0D, 0.0F);
                glPopMatrix();
                break;
            case ENTITY:
                glPushMatrix();
                glTranslatef(-0.5F, -0.0F, -0.5F);
                TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileSpiritPylon(), 0.0D, 0.0D, 0.0D, 0.0F);
                glPopMatrix();
                break;
            case EQUIPPED_FIRST_PERSON:
                glPushMatrix();
                glTranslatef(-0.5F, -0.0F, -0.0F);
                TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileSpiritPylon(), 0.0D, 0.0D, 0.0D, 0.0F);
                glPopMatrix();
                break;
            case EQUIPPED:
                glPushMatrix();
                glTranslatef(0.0F, -0.0F, -0.0F);
                TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileSpiritPylon(), 0.0D, 0.0D, 0.0D, 0.0F);
                glPopMatrix();
                break;
        }

    }


}
