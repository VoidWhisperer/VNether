/**
Copyright (C) 2012 VoidWhisperer

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see http://www.gnu.org/licenses/.
**/
package vc.voidwhisperer.vngen.main;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class OrePopulator extends BlockPopulator {
	/*
	 * This is from the original MC code. Credit to notch!
	 */
	public boolean generate(Chunk par1World, Random par2Random, int par3, int par4, int par5,int numberOfBlocks, int bId)
    {
        float var6 = par2Random.nextFloat() * (float)Math.PI;
        double var7 = (double)((float)(par3 + 8) + MathHelper.sin(var6) * (float)numberOfBlocks / 8.0F);
        double var9 = (double)((float)(par3 + 8) - MathHelper.sin(var6) * (float)numberOfBlocks / 8.0F);
        double var11 = (double)((float)(par5 + 8) + MathHelper.cos(var6) * (float)numberOfBlocks / 8.0F);
        double var13 = (double)((float)(par5 + 8) - MathHelper.cos(var6) * (float)numberOfBlocks / 8.0F);
        double var15 = (double)(par4 + par2Random.nextInt(3) - 2);
        double var17 = (double)(par4 + par2Random.nextInt(3) - 2);

        for (int var19 = 0; var19 <= numberOfBlocks; ++var19)
        {
            double var20 = var7 + (var9 - var7) * (double)var19 / (double)numberOfBlocks;
            double var22 = var15 + (var17 - var15) * (double)var19 / (double)numberOfBlocks;
            double var24 = var11 + (var13 - var11) * (double)var19 / (double)numberOfBlocks;
            double var26 = par2Random.nextDouble() * (double)numberOfBlocks / 16.0D;
            double var28 = (double)(MathHelper.sin((float)var19 * (float)Math.PI / (float)numberOfBlocks) + 1.0F) * var26 + 1.0D;
            double var30 = (double)(MathHelper.sin((float)var19 * (float)Math.PI / (float)numberOfBlocks) + 1.0F) * var26 + 1.0D;
            int var32 = MathHelper.floor_double(var20 - var28 / 2.0D);
            int var33 = MathHelper.floor_double(var22 - var30 / 2.0D);
            int var34 = MathHelper.floor_double(var24 - var28 / 2.0D);
            int var35 = MathHelper.floor_double(var20 + var28 / 2.0D);
            int var36 = MathHelper.floor_double(var22 + var30 / 2.0D);
            int var37 = MathHelper.floor_double(var24 + var28 / 2.0D);

            for (int var38 = var32; var38 <= var35; ++var38)
            {
                double var39 = ((double)var38 + 0.5D - var20) / (var28 / 2.0D);

                if (var39 * var39 < 1.0D)
                {
                    for (int var41 = var33; var41 <= var36; ++var41)
                    {
                        double var42 = ((double)var41 + 0.5D - var22) / (var30 / 2.0D);

                        if (var39 * var39 + var42 * var42 < 1.0D)
                        {
                            for (int var44 = var34; var44 <= var37; ++var44)
                            {
                                double var45 = ((double)var44 + 0.5D - var24) / (var28 / 2.0D);

                                if (var39 * var39 + var42 * var42 + var45 * var45 < 1.0D && par1World.getBlock(var38, var41, var44).getType() == Material.NETHERRACK)
                                {
                                    par1World.getBlock(var38, var41, var44).setType(Material.getMaterial(bId));
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

	@Override
	public void populate(World w, Random rand, Chunk src) {
		for(int i = 0; i <= 300; i++)
		{
		int oreAppear = rand.nextInt(6);
		if(oreAppear <= 6)
		{
			int oreType = rand.nextInt(300);
			if(oreType < 20)
			{
				return;
			}
			if(oreType > 20 && oreType < 80)
			{
				generate(src, rand, rand.nextInt(15), rand.nextInt(90 - 30 + 1) + 30, rand.nextInt(15),12, Material.COAL_ORE.getId());
			}
			if(oreType > 80 && oreType < 120)
			{
				generate(src, rand, rand.nextInt(15), rand.nextInt(90 - 30 + 1) + 30, rand.nextInt(15),12, Material.IRON_ORE.getId());
			}
			if(oreType > 120 && oreType < 190)
			{
				generate(src, rand, rand.nextInt(15), rand.nextInt(90 - 30 + 1) + 30, rand.nextInt(15),11, Material.REDSTONE_ORE.getId());
			}
			if(oreType > 190 && oreType < 250)
			{
				generate(src, rand, rand.nextInt(15), rand.nextInt(90 - 30 + 1) + 30, rand.nextInt(15),12, Material.GLOWSTONE.getId());
			}
			if(oreType > 250 && oreType < 300)
			{
				generate(src, rand, rand.nextInt(15), rand.nextInt(90 - 30 + 1) + 30, rand.nextInt(15),30, Material.SOUL_SAND.getId());
			}
		}
		}
	}
}
