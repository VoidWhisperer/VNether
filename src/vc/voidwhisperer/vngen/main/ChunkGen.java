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
/* credit to jtjj222 for a tutorial on this! :)
 * http://forums.bukkit.org/threads/intermediate-wgen-more-interesting-terrain-using-3d-simplex-noise.71813/
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.util.noise.PerlinNoiseGenerator;
import org.bukkit.util.noise.PerlinOctaveGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class ChunkGen extends ChunkGenerator {
	double scale = 35.0;
	World w;
	Random rand = new Random();
	PerlinOctaveGenerator gen;
	//long seed = Long.valueOf("-3453432457");
	public ChunkGen(World w)
	{
		this.w = w;
	}
	public List<BlockPopulator> getDefaultPopulators(World w)
	{
		List<BlockPopulator> pops = new ArrayList<BlockPopulator>();
		pops.add(new TestPopulator());
		if(VNetherGen.p.config.getFile().getBoolean("ruins-enabled"))
		{
		pops.add(new RuinPopulator());
		}
		if(VNetherGen.p.config.getFile().getBoolean("shrooms-enabled"))
		{
		pops.add(new MushroomTreePopulator());
		}
		if(VNetherGen.p.config.getFile().getBoolean("netherwart-enabled"))
		{
		pops.add(new NetherWartPopulator());
		}
		if(VNetherGen.p.config.getFile().getBoolean("ores-enabled"))
		{
		pops.add(new OrePopulator());
		}
		if(VNetherGen.p.config.getFile().getBoolean("glowstone-enabled"))
		{
		pops.add(new GlowstonePopulator());
		}
		return pops;
	}
	/*I have no idea how the heck this works :D */
	void setBlock(int x, int y, int z, byte[][] chunk, Material material)
	{
		if(chunk[y>>4] == null)
		{
			chunk[y>>4] = new byte[16*16*16];
		}
		if(!(y<= 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0))
		{
			return;
		}
		try {
			chunk[y>>4][((y & 0xF) << 8) | (z << 4) | x] = (byte)material.getId();
		}catch(Exception e)
		{
			
		}
	}
	@Override
	public byte[][] generateBlockSections(World world, Random random, int ChunkX,int ChunkZ,BiomeGrid biomes)
	{
		rand.setSeed(world.getSeed());
		gen = new PerlinOctaveGenerator((long) (rand.nextLong() * rand.nextGaussian()), 8);
		byte[][] chunk = new byte[(world.getMaxHeight()/2) /16][];
		gen.setScale(1/VNetherGen.p.config.getFile().getDouble("scale"));
		double threshold = 0.0;
		for(int x=0;x<16;x++)
		{
			for(int z=0;z<16;z++)
			{
				int real_x = x+ChunkX * 16;
				int real_z = z+ChunkZ * 16;
				for(int y=1;y<4;y++)
				{
					if(y == 0 || y == 1)
					{
						setBlock(x,y,z,chunk,Material.BEDROCK);
					}else{
						setBlock(x,y,z,chunk,Material.LAVA);
					}
				}
				for(int y=4;y<128;y++)
				{
					if(gen.noise(real_x, y, real_z,0.5,0.5) > VNetherGen.p.config.getFile().getDouble("threshold"))
					{
						setBlock(x,y,z,chunk,Material.NETHERRACK);
					}
				}
			}
		}
		
		world.setBiome(ChunkX, ChunkZ, Biome.HELL);
		
		return chunk;
	}
}