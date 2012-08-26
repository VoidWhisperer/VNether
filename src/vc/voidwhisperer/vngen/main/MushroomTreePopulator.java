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
import org.bukkit.generator.BlockPopulator;

public class MushroomTreePopulator extends BlockPopulator {

	@Override
	public void populate(World w, Random rand, Chunk src) {
		int numtrees = rand.nextInt(VNetherGen.p.config.getFile().getInt("max-shrooms-per-chunk") - VNetherGen.p.config.getFile().getInt("min-shrooms-per-chunk") + 1) + VNetherGen.p.config.getFile().getInt("min-shrooms-per-chunk");
		for(int i = 0; i <= numtrees;i++)
		{
			int xpos = rand.nextInt(15);
			int zpos = rand.nextInt(15);
			int ypos = src.getChunkSnapshot().getHighestBlockYAt(xpos, zpos);
			Location treepos = src.getBlock(xpos, ypos, zpos).getLocation();
			int type = rand.nextInt(10);
			if(type > 2)
			{
				if(!VNetherGen.p.config.getFile().getBoolean("derpy-shrooms") && ((w.getBlockAt(new Location(w,treepos.getX(),treepos.getY()-1,treepos.getZ())).getTypeId() == 99) || (w.getBlockAt(new Location(w,treepos.getX(),treepos.getY()-1,treepos.getZ())).getTypeId() == 100)))
				{
					return; //no derpy shrooms :(
				}
				w.getBlockAt(new Location(w,treepos.getX(),treepos.getY()-1,treepos.getZ())).setType(Material.DIRT);
				w.generateTree(treepos, TreeType.BROWN_MUSHROOM);	
			}else{
				if(!VNetherGen.p.config.getFile().getBoolean("derpy-shrooms") && ((w.getBlockAt(new Location(w,treepos.getX(),treepos.getY()-1,treepos.getZ())).getTypeId() == 99) || (w.getBlockAt(new Location(w,treepos.getX(),treepos.getY()-1,treepos.getZ())).getTypeId() == 100)))
				{
					return; //no derpy shrooms :(
				}
				w.getBlockAt(new Location(w,treepos.getX(),treepos.getY()-1,treepos.getZ())).setType(Material.DIRT);
				w.generateTree(treepos, TreeType.RED_MUSHROOM);	
			}
		}
	}
}

