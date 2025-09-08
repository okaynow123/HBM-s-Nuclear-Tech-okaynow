package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.BlockLadder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class BlockNTMLadder extends BlockLadder {

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	public BlockNTMLadder() {
		super();
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, int x, int y, int z, int side) {
		if(this == ModBlocks.ladder_red_top) {
			return true; // Allow placement anywhere for top piece
		}
		return super.canPlaceBlockOnSide(worldIn, x, y, z, side);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		if(this == ModBlocks.ladder_red_top) {
			this.setBlockBoundsBasedOnState(world, x, y, z);
			return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
		}
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		if(this == ModBlocks.ladder_red_top) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
			return;
		}
		super.setBlockBoundsBasedOnState(world, x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
		if(this == ModBlocks.ladder_red_top) {
			int meta = MathHelper.floor_double((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			int facing = meta;

			// Set metadata based on horizontal facing
			switch(facing) {
				case 0: world.setBlockMetadataWithNotify(x, y, z, 2, 2); break; // South
				case 1: world.setBlockMetadataWithNotify(x, y, z, 5, 2); break; // West
				case 2: world.setBlockMetadataWithNotify(x, y, z, 3, 2); break; // North
				case 3: world.setBlockMetadataWithNotify(x, y, z, 4, 2); break; // East
			}
		} else {
			super.onBlockPlacedBy(world, x, y, z, placer, stack);
		}
	}

	@Override
	public int getRenderType() {
		if(this == ModBlocks.ladder_red || this == ModBlocks.ladder_red_top) {
			return renderID;
		}
		return super.getRenderType();
	}
}
