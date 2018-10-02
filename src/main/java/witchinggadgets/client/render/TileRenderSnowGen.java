package witchinggadgets.client.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import witchinggadgets.client.ClientUtilities;
import witchinggadgets.common.blocks.tiles.TileEntitySnowGen;

public class TileRenderSnowGen extends TileEntitySpecialRenderer
{
	public void renderTileEntityAt(TileEntitySnowGen tile, double x, double y, double z, float f)
	{
		/*//Old code
		GL11.glPushMatrix();

		Tessellator tes = Tessellator.instance;
		GL11.glTranslatef((float)x, (float)y, (float)z);

		switch(tile.facing)
		{
		case NORTH:
			break;
		case SOUTH:
			GL11.glRotatef(180, 0, 1, 0);
			GL11.glTranslatef(-1, 0, -1);
			break;
		case EAST:
			GL11.glRotatef(270, 0, 1, 0);
			GL11.glTranslatef(0, 0, -1);
			break;
		case WEST:
			GL11.glRotatef(90, 0, 1, 0);
			GL11.glTranslatef(-1, 0, 0);
			break;
		default:
			break;
		}

		ClientUtilities.bindTexture("thaumcraft:textures/models/Bore.png");
		if(tile.facing.equals(ForgeDirection.UP))
		{
			TileRenderCobbleGen.renderPixelBlock(tes, 0.375, 1, 0.375, 0.625, 1.125, 0.625, 0.859375,0.65625,0.8984375,0.71875);
			TileRenderCobbleGen.renderPixelBlock(tes, 0.375, 1.125, 0.375, 0.625, 1.125, 0.625, 0.828125,0.875,0.8671875,0.953125);
		}
		else if(tile.facing.equals(ForgeDirection.DOWN))
		{
			TileRenderCobbleGen.renderPixelBlock(tes, 0.375,-0.125, 0.375, 0.625, 0, 0.625, 0.859375,0.65625,0.8984375,0.71875);
			TileRenderCobbleGen.renderPixelBlock(tes, 0.375,-0.125, 0.375, 0.625,-0.125, 0.625, 0.828125,0.875,0.8671875,0.953125);
		}
		else
		{
			TileRenderCobbleGen.renderPixelBlock(tes, 0.4075, 0.4075, 0, 0.5925, 0.5925, 0.0624, 0.859375,0.65625,0.8984375,0.71875);
			TileRenderCobbleGen.renderPixelBlock(tes, 0.375, 0.375,-0.125, 0.625, 0.625, 0, 0.859375,0.65625,0.8984375,0.71875);
			TileRenderCobbleGen.renderPixelBlock(tes, 0.375, 0.375,-0.125, 0.625, 0.625,-0.125, 0.828125,0.875,0.8671875,0.953125);
		}

		
		// TODO INDICATE ACTIVE STATUS
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		ClientUtilities.bindTexture("textures/blocks/ice.png");
		TileRenderCobbleGen.renderPixelBlock(tes, 0.1875, 0.25, 0.1875, 0.375, 0.5, 0.375, 0,0,1,1);
		TileRenderCobbleGen.renderPixelBlock(tes, 0.375, 0.25, 0.1875, 0.625, 0.5625, 0.4375, 0,0,1,1);
		TileRenderCobbleGen.renderPixelBlock(tes, 0.15625, 0.25, 0.5, 0.4375, 0.5625, 0.8125, 0,0,1,1);
		TileRenderCobbleGen.renderPixelBlock(tes, 0.5, 0.25, 0.5625, 0.75, 0.375, 0.875, 0,0,1,1);
		TileRenderCobbleGen.renderPixelBlock(tes, 0.65625, 0.25, 0.3125, 0.8125, 0.75, 0.5, 0,0,1,1);
		GL11.glDisable(3042);
		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glPopMatrix();
		*/
		
		GL11.glPushMatrix();

		Tessellator tes = Tessellator.instance;
		GL11.glDisable(GL11.GL_LIGHTING);
		//		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		int tick = tile.tick;
		GL11.glTranslatef((float)x, (float)y, (float)z);

		switch(tile.facing)
		{
		case NORTH:
			break;
		case SOUTH:
			GL11.glRotatef(180, 0, 1, 0);
			GL11.glTranslatef(-1, 0, -1);
			break;
		case EAST:
			GL11.glRotatef(270, 0, 1, 0);
			GL11.glTranslatef(0, 0, -1);
			break;
		case WEST:
			GL11.glRotatef(90, 0, 1, 0);
			GL11.glTranslatef(-1, 0, 0);
			break;
		default:
			break;
		}

		ClientUtilities.bindTexture("thaumcraft:textures/blocks/woodplain.png");
		TileRenderCobbleGen.renderPixelBlock(tes, 0, 0, 0, 1, 0.1875, 1,0,0,1,0.1875);
		TileRenderCobbleGen.renderPixelBlock(tes, 0.0625, 0.1875, 0.0625, 0.9375, 0.3125, 0.9375,0,0,1,0.125);
		TileRenderCobbleGen.renderPixelBlock(tes, 0, 0.8125, 0, 1, 0.9375, 1,0,0,1,0.1875);

		ClientUtilities.bindTexture("thaumcraft:textures/blocks/arcane_stone.png");
		TileRenderCobbleGen.renderPixelBlock(tes, 0, 1, 0, 1, 1, 1,0,0,1,1);
		TileRenderCobbleGen.renderPixelBlock(tes, 0, 0.9375, 0, 1, 0.999, 1,0,0,0.125,0.125);
		TileRenderCobbleGen.renderPixelBlock(tes, 0.0625, 0.3125, 0.0625, 0.9375, 0.3125, 0.9375,0.0625,0.0625,0.9375,0.9375);
		TileRenderCobbleGen.renderPixelBlock(tes, 0, 0.8125, 0, 1, 0.8125, 1,0,0,1,1);
		ClientUtilities.bindTexture("thaumcraft:textures/blocks/pedestal_top.png");
		TileRenderCobbleGen.renderPixelBlock(tes, 0, 0.1875, 0, 0.125, 0.8125, 0.125,0,0,0.125,1);
		TileRenderCobbleGen.renderPixelBlock(tes, 0, 0.1875, 0.875, 0.125, 0.8125, 1,0,0,0.125,1);
		TileRenderCobbleGen.renderPixelBlock(tes, 0.875, 0.1875, 0, 1, 0.8125, 0.125,0,0,0.125,1);
		TileRenderCobbleGen.renderPixelBlock(tes, 0.875, 0.1875, 0.875, 1, 0.8125, 1,0,0,0.125,1);

		if(!tile.facing.equals(ForgeDirection.UP) && !tile.facing.equals(ForgeDirection.DOWN))
			ClientUtilities.bindTexture("thaumcraft:textures/blocks/liftertop.png");
		else
			ClientUtilities.bindTexture("thaumcraft:textures/blocks/lifterside.png");
		TileRenderCobbleGen.renderPixelBlock(tes, 0.125, 0.125, 0.0624, 0.875, 0.875, 0.0625, 0.125,0.125,0.875,0.875);
		ClientUtilities.bindTexture("thaumcraft:textures/blocks/lifterside.png");
		TileRenderCobbleGen.renderPixelBlock(tes, 0.125, 0.125, 0.9375, 0.875, 0.875, 0.9376, 0.125,0.125,0.875,0.875);
		TileRenderCobbleGen.renderPixelBlock(tes, 0.9375, 0.125, 0.125, 0.9376, 0.625, 0.875, 0.125,0.125,0.875,0.625);
		TileRenderCobbleGen.renderPixelBlock(tes, 0.0624, 0.125, 0.125, 0.0625, 0.625, 0.875, 0.125,0.125,0.875,0.625);

		ClientUtilities.bindTexture("thaumcraft:textures/models/Bore.png");
		if(tile.facing.equals(ForgeDirection.UP))
		{
			TileRenderCobbleGen.renderPixelBlock(tes, 0.375, 1, 0.375, 0.625, 1.125, 0.625, 0.859375,0.65625,0.8984375,0.71875);
			TileRenderCobbleGen.renderPixelBlock(tes, 0.375, 1.125, 0.375, 0.625, 1.125, 0.625, 0.828125,0.875,0.8671875,0.953125);
		}
		else if(tile.facing.equals(ForgeDirection.DOWN))
		{
			TileRenderCobbleGen.renderPixelBlock(tes, 0.375,-0.125, 0.375, 0.625, 0, 0.625, 0.859375,0.65625,0.8984375,0.71875);
			TileRenderCobbleGen.renderPixelBlock(tes, 0.375,-0.125, 0.375, 0.625,-0.125, 0.625, 0.828125,0.875,0.8671875,0.953125);
		}
		else
		{
			TileRenderCobbleGen.renderPixelBlock(tes, 0.4075, 0.4075, 0, 0.5925, 0.5925, 0.0624, 0.859375,0.65625,0.8984375,0.71875);
			TileRenderCobbleGen.renderPixelBlock(tes, 0.375, 0.375,-0.125, 0.625, 0.625, 0, 0.859375,0.65625,0.8984375,0.71875);
			TileRenderCobbleGen.renderPixelBlock(tes, 0.375, 0.375,-0.125, 0.625, 0.625,-0.125, 0.828125,0.875,0.8671875,0.953125);
		}

		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		ClientUtilities.bindTexture("textures/blocks/ice.png");
		TileRenderCobbleGen.renderPixelBlock(tes, 0.1875, 0.25, 0.1875, 0.375, 0.5, 0.375, 0,0,1,1);
		TileRenderCobbleGen.renderPixelBlock(tes, 0.375, 0.25, 0.1875, 0.625, 0.5625, 0.4375, 0,0,1,1);
		TileRenderCobbleGen.renderPixelBlock(tes, 0.15625, 0.25, 0.5, 0.4375, 0.5625, 0.8125, 0,0,1,1);
		TileRenderCobbleGen.renderPixelBlock(tes, 0.5, 0.25, 0.5625, 0.75, 0.375, 0.875, 0,0,1,1);
		TileRenderCobbleGen.renderPixelBlock(tes, 0.65625, 0.25, 0.3125, 0.8125, 0.75, 0.5, 0,0,1,1);
		GL11.glDisable(3042);
		GL11.glEnable(GL11.GL_LIGHTING);
		/*
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		ClientUtilities.bindTexture("textures/blocks/ice.png"); //ex lava_flow
		//TileRenderCobbleGen.renderPixelBlock(tes, 0.1875, 0.3125, 0.375, 0.3125, 0.8125, 0.625, .375,(loopTick*inc),.625,((loopTick+8)*inc));
		TileRenderCobbleGen.renderPixelBlock(tes, 0.1875, 0.3125, 0.375, 0.3125, 0.8125, 0.625, 0,0,1,1);

		//ClientUtilities.bindTexture("textures/blocks/water_flow.png");
		//TileRenderCobbleGen.renderPixelBlock(tes, 0.6875, 0.3125, 0.375, 0.8125, 0.8125, 0.625, .375,(loopTick*inc),.625,((loopTick+8)*inc));
		TileRenderCobbleGen.renderPixelBlock(tes, 0.6875, 0.3125, 0.375, 0.8125, 0.8125, 0.625, 0,0,1,1);
		GL11.glDisable(3042);
		*/
		if(tile.getWorldObj()==null || tile.getWorldObj().getBlockPowerInput(tile.xCoord, tile.yCoord, tile.zCoord)<=0 && !tile.getWorldObj().isBlockIndirectlyGettingPowered(tile.xCoord, tile.yCoord, tile.zCoord))
		{
			double slowTick = tick/4;
			double loopTick = slowTick*1.65;
			double inc = 1.0/512.0;
			

			ClientUtilities.bindTexture("textures/blocks/snow.png");
			if(tick>32)
				TileRenderCobbleGen.renderPixelBlock(tes, 0.3125, 0.3125, 0.3125, 0.6875, 0.6875, 0.6875, 0,0,1,1);
		}

		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f)
	{
		renderTileEntityAt((TileEntitySnowGen)tileentity, d0, d1, d2, f);
	}

}