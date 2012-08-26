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
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class GlowstonePopulator extends BlockPopulator {

	@Override
	public void populate(World arg0, Random rand, Chunk arg2) {
		//int hasglowStone = rand.nextInt(5);
		for (int var7 = 0; var7 < 8; ++var7)
        {
            int var8 = rand.nextInt(15);
            int var9 = rand.nextInt(120) + 4;
            int var10 = rand.nextInt(15);
           generate(arg2, rand, var8, var9, var10);
        }
	}
	
	 public boolean generate(Chunk par1World, Random par2Random, int par3, int par4, int par5)
	    {
	        if (!(par1World.getBlock(par3, par4, par5).getType() == Material.AIR))
	        {
	            return false;
	        }
	        else if (par1World.getBlock(par3, par4 + 1, par5).getType() != Material.NETHERRACK)
	        {
	            return false;
	        }
	        else
	        {
	            par1World.getBlock(par3, par4, par5).setType(Material.GLOWSTONE);

	            for (int var6 = 0; var6 < 1500; ++var6)
	            {
	                int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
	                int var8 = par4 - par2Random.nextInt(12);
	                int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

	                if (par1World.getBlock(var7, var8, var9).getTypeId() == 0)
	                {
	                    int var10 = 0;

	                    for (int var11 = 0; var11 < 6; ++var11)
	                    {
	                        int var12 = 0;

	                        if (var11 == 0)
	                        {
	                            var12 = par1World.getBlock(var7 - 1, var8, var9).getTypeId();
	                        }

	                        if (var11 == 1)
	                        {
	                            var12 = par1World.getBlock(var7 + 1, var8, var9).getTypeId();
	                        }

	                        if (var11 == 2)
	                        {
	                            var12 = par1World.getBlock(var7, var8 - 1, var9).getTypeId();
	                        }

	                        if (var11 == 3)
	                        {
	                            var12 = par1World.getBlock(var7, var8 + 1, var9).getTypeId();
	                        }

	                        if (var11 == 4)
	                        {
	                            var12 = par1World.getBlock(var7, var8, var9 - 1).getTypeId();
	                        }

	                        if (var11 == 5)
	                        {
	                            var12 = par1World.getBlock(var7, var8, var9 + 1).getTypeId();
	                        }

	                        if (var12 == Material.GLOWSTONE.getId())
	                        {
	                            ++var10;
	                        }
	                    }

	                    if (var10 == 1)
	                    {
	                        par1World.getBlock(var7, var8, var9).setType(Material.GLOWSTONE);
	                    }
	                }
	            }

	            return true;
	        }
	    }
	
	
	
	
	
	
/*	public void generate(Chunk src, Random rand, int x, int y, int z)
	{
		if(!(src.getBlock(x,y,z).getType() == Material.AIR))
		{
			return;
		}
		else if(src.getBlock(x,y+1,z).getType() != Material.NETHERRACK)
		{
			return;
		}
		else
		{
			Block b = src.getBlock(x,y,z);
			src.getBlock(x, y, z).setType(Material.GLOWSTONE);
			for(int i = 0; i < 1500; i++)
			{
				int var1 = x + rand.nextInt(8) - rand.nextInt(8);
				int var2 = y - rand.nextInt(12);
				int var3 = z + rand.nextInt(8) - rand.nextInt(8);
				if(src.getBlock(var1, var2, var3).getType() == Material.AIR)
				{
					int var4 = 0;
					for(int i2 = 0; i2 < 6; ++i2)
					{
						int var5 = 0;
						if(i2 == 0)
						{
							var5 = src.getBlock(x - 1,y, z).getTypeId();
						}
						if(i2 == 1)
						{
							var5 = src.getBlock(x + 1,y, z).getTypeId();
						}
						if(i2 == 2)
						{
							var5 = src.getBlock(x,y - 1, z).getTypeId();
						}
						if(i2 == 3)
						{
							var5 = src.getBlock(x,y + 1, z).getTypeId();
						}
						if(i2 == 4)
						{
							var5 = src.getBlock(x,y, z - 1).getTypeId();
						}
						if(i2 == 5)
						{
							var5 = src.getBlock(x,y, z + 1).getTypeId();
						}
						if(var5 == Material.GLOWSTONE.getId())
						{
							var4++;
						}
					}
					if(var4 == 1)
					{
						src.getBlock(x, y, z).setType(Material.GLOWSTONE);
					}
				}
			}
		}
	}*/
	
}
