package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockNTMLadder;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderRedLadder implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		GL11.glPushMatrix();
		Tessellator tessellator = Tessellator.instance;

		GL11.glTranslated(0, -0.5, 0);
		GL11.glRotated(180, 0, 1, 0);
		tessellator.startDrawingQuads();

		if(block == ModBlocks.ladder_red) {
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.ladder_red_block, "Cylinder", block.getIcon(0, 0), tessellator, 0, false);
		} else if(block == ModBlocks.ladder_red_top) {
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.ladder_red_top_block, "Cylinder", block.getIcon(0, 0), tessellator, 0, false);
		}

		tessellator.draw();
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(1, 1, 1);

		int meta = world.getBlockMetadata(x, y, z);
		float rotation = 0;

		switch(meta) {
			case 2: rotation = 0F; break;   // South
			case 3: rotation = 180F; break; // North
			case 4: rotation = 90F; break;  // East
			case 5: rotation = 270F; break; // West
		}

		tessellator.addTranslation(x + 0.5F, y, z + 0.5F);

		GL11.glPushMatrix();
		GL11.glRotatef(90, 1, 0, 0); // Rotate 90 degrees around X axis to make ladder vertical
		GL11.glRotatef(rotation, 0, 0, 1); // Rotate around Z axis for facing direction

		if(block == ModBlocks.ladder_red) {
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.ladder_red_block, "Cylinder", block.getIcon(0, 0), tessellator, 0, true);
		} else if(block == ModBlocks.ladder_red_top) {
			ObjUtil.renderPartWithIcon((WavefrontObject) ResourceManager.ladder_red_top_block, "Cylinder", block.getIcon(0, 0), tessellator, 0, true);
		}

		GL11.glPopMatrix();

		tessellator.addTranslation(-x - 0.5F, -y, -z - 0.5F);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return BlockNTMLadder.renderID;
	}
}
