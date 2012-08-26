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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class NetherWartPopulator extends BlockPopulator {

	@Override
	public void populate(World w, Random rand, Chunk src) {
		int circleradiusint = rand.nextInt(VNetherGen.p.config.getFile().getInt("max-netherwart-circle-radius"));
		double circleradius = (double)circleradiusint;
		int startheight = rand.nextInt(90 - 60 + 1) + 60;
		int actualstartheight = findOpenSpace(rand.nextInt(15),startheight,rand.nextInt(15),src);
		int genchance = rand.nextInt(50);
		//int netherwarts = 0;
		if(genchance > 30)
		{
		if(actualstartheight != -1)
		{
		Location centercircle = new Location(w,8,actualstartheight,8);
		if(actualstartheight != 0)
		{
			for(int x = 0; x<16;x++)
			{
				for(int z = 0; z<16;z++)
				{
					if(inCircle(centercircle,circleradius,new Location(w,x,actualstartheight,z)))
					{
						int heighttoplace = getLowestY(x,actualstartheight,z,src);
						if(heighttoplace != -1)
						{
						src.getBlock(x,heighttoplace,z).setType(Material.SOUL_SAND);
						src.getBlock(x,heighttoplace+1,z).setType(Material.NETHER_WARTS);
						}
					}
				}
			}
		}
	}
	}
	}
	public int findOpenSpace(int x, int y, int z,Chunk src)
	{
		Block b = src.getBlock(x, y, z);
		if(b.getType() != Material.NETHERRACK && b.getRelative(0,1,0).getType() != Material.AIR && b.getRelative(0,2,0).getType() != Material.AIR)
		{
			while(b.getType() != Material.NETHERRACK && b.getRelative(0,1,0).getType() != Material.AIR && b.getRelative(0,2,0).getType() != Material.AIR)
			{
			//	//System.out.println(y+1);
			//	System.out.println(b.getType().name());
				b = src.getBlock(x, ++y, z);
				if(b.getLocation().getY() == 256)
				{
					return -1;
				}
			}
			return y;
		}
		return 0;
	}
	public int getLowestY(int x, int y, int z,Chunk src)
	{
		Block b = src.getBlock(x, y, z);
		if(b.getType() != Material.NETHERRACK)
		{
			while(b.getType() != Material.NETHERRACK)
			{
				//System.out.println(b.getY()-1);
				//System.out.println(b.getType().name());
				
				b = src.getBlock(b.getX(),--y,b.getZ());
				if(b.getLocation().getY()-1 == 0)
				{
					return -1;
				}
			}
		}
		return y;
	}
	public boolean inCircle(Location center,double radius, Location point)
	{
		double x2x1squared = (point.getX() - center.getX())*(point.getX() - center.getX());
		double z2z1squared = (point.getZ() - center.getZ())*(point.getZ() - center.getZ());
		//Using z in place of y as it's 2d but the height is on the z axis as it's ontop.
		double distancefromcenter = Math.sqrt(x2x1squared + z2z1squared);
		if(distancefromcenter > radius)
		{
			return false; // not in the circle
		}else{
			return true; // in the circle
		}
	}
}

