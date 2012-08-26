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
/* This code was my first populator, was playing around with lava generation. */
public class TestPopulator extends BlockPopulator {
	public void populate(World w, Random rand, Chunk src)
	{
		int chance = rand.nextInt(15);
		if(chance < 3)
		{
			int testx = rand.nextInt(15);
			int testz = rand.nextInt(15);
			Block the = w.getBlockAt( testx + src.getX() * 16,w.getHighestBlockYAt(testx + src.getX() * 16, testz + src.getZ() * 16),testz + src.getZ() * 16);
			the.setType(Material.LAVA);
		}
	}
}
