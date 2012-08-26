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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
//import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Stairs;
import org.bukkit.block.BlockFace;

public class RuinPopulator extends BlockPopulator {
	/*Using custom blockface due to bukkit's blockface being screwed up.*/
	/*Z*/
	public static BFace[] Directions = new BFace[] {
		BFace.NORTH,
		BFace.SOUTH
	};
	/*x*/
	public static BFace[] AdjacentDirections = new BFace[] {
		BFace.WEST,BFace.EAST
	};
	/*X*/
	public static BFace[] AdjacentDirections2 = new BFace[] {
		BFace.EAST,BFace.WEST
	};
	/*
	 * Ordindal:
	 * North - 1
	 * South - 3
	 * West - 0
	 * East - 2
	 */
	int ruin_chance = 75;
	Random rand = new Random();
	public void populate(World w, Random rand, Chunk src)
	{
		int height = rand.nextInt(6 - 4 + 1) + 2;
		ChunkSnapshot snap = src.getChunkSnapshot();
		int ruins = 0;
		int maxruins = 1;
		if(ruins < maxruins)
		{
		if(rand.nextInt(300) < VNetherGen.p.config.getFile().getInt("ruin-frequency"))
		{
			BFace direction1 = Directions[rand.nextInt(Directions.length)];
			BFace direction2 = AdjacentDirections[rand.nextInt(AdjacentDirections.length)];
			//GEN WALL *Z*
			int startx = 0;
			int endX = 0;
			int startz = 0;
			int endz = 0;
			int floors = rand.nextInt(2 - 1 + 1) + 1;
			//+1 x
			if(direction2 == BFace.EAST)
			{
				startx = 0;
				endX = 15;
			}
			if(direction2 == BFace.WEST)
			{
				startx = 15;
				endX = 0;
			}
			if(direction1 == BFace.NORTH)
			{
				startz = 15;
				endz = 0;
			}
			if(direction1 == BFace.SOUTH)
			{
				startz = 0;
				endz = 15;
			}
			genWall(direction1,src,startx,startz,endX,endz,height,direction2,floors);
			genWall(direction2,src,startx,startz,endX,endz,height,direction1,floors);
		}
		ruins+=1;
		}
	}
	private void genFloor(BFace direction1, BFace direction2, Chunk src,int y) {
		ChunkSnapshot src2 = src.getChunkSnapshot();
		int blazespawnerchance = new Random().nextInt(100);
		int chestchance = new Random().nextInt(100);
		boolean chestplaced = false;
		for(int x = getStart(direction1);forHelper(direction1,x,getEnd(direction1));)
		{
			//System.out.println(x + "---"  + getEnd(direction1) + "---" + modify(direction1,x));
			for(int z = getStart(direction2);forHelper(direction2,z,getEnd(direction2));)
			{
				//System.out.println(z + "---"  + getEnd(direction2) + "---" + modify(direction2,z) + direction2.name());
				if(chestchance < VNetherGen.p.config.getFile().getInt("chest-spawn-chance"))
				{
					if(chestplaced == false)
					{
					if(x == 1 && z == 1)
					{
						if(src.getBlock(x, y-4, z).getType() != Material.CHEST)
						{
						chestplaced = true;
						src.getBlock(x, y-4, z).setType(Material.CHEST);
						Chest chest = (Chest)src.getBlock(x, y-4, z).getState();
						List<String> items = (List<String>) VNetherGen.p.config.getFile().getList("items");
						//List<Integer> ids = new ArrayList<Integer>();
						List<Integer> Id = new ArrayList<Integer>();
						List<Integer> amounts = new ArrayList<Integer>();
						for(String s : items)
						{
							String[] split = s.split(",");
							int id = Integer.parseInt(split[0]);
							int amount = Integer.parseInt(split[1]);
							int rarity = Integer.parseInt(split[2]);
							for(int i = 0;i < rarity;i++)
							{
								Id.add(id);
								amounts.add(amount);
							}
							for(int i = 0;i < rand.nextInt(VNetherGen.p.config.getFile().getInt("max-itemtypes-per-chest"));i++)
							{
								int r = rand.nextInt(Id.size()-1);
								chest.getBlockInventory().addItem(new ItemStack(Id.get(r),amounts.get(r)));
							}
						}
					}
					}
					}
				}
				int floorappear = rand.nextInt(2);
				if(floorappear == 1)
				{
				//System.out.println("floor");
				if(blazespawnerchance < VNetherGen.p.config.getFile().getInt("blaze-spawner-chance"))
				{
				if(x == 8 && z == 8)
				{
					src.getBlock(x, y, z).setType(Material.MOB_SPAWNER);
					CreatureSpawner a = (CreatureSpawner)src.getBlock(x, y, z).getState();
					a.setSpawnedType(EntityType.BLAZE);
				}
				}
				src.getBlock(x, y-1, z).setType(Material.NETHER_BRICK);
				}
				z = modify(direction2,z);
			}
			x = modify(direction1,x);
		}
	}
	public void genWall(BFace d,Chunk src,int startx, int startz, int endx, int endz,int height,BFace odir,int floors)
	{
		/* -1 z per block */
		ChunkSnapshot src2 = src.getChunkSnapshot();
		int startY = src2.getHighestBlockYAt(startx, startz);
		if(floors == 1)
		{
			genFloor(d,odir,src,startY+4);
		}
		if(d == BFace.NORTH)
		{
			//System.out.println("n1");
			//int startz = 16; //starting corner
			//int endz = 0; //ending corner
			int lastY = 0;
			int startY2 = getLowestY2(src.getBlock(startx,src2.getHighestBlockYAt(startx, startz),startz));
		//	src.getBlock(startx,src2.getHighestBlockYAt(startx, startz),startz).setType(Material.WOOL);
			for(int z = startz;z >= endz;z--)
			{
				//System.out.println(lastY);
				//System.out.println("n2");
				//This is what will be used after testing. int startY = getFirstOpenY(conspos, z, src); //the lowest y point
				startY = src2.getHighestBlockYAt(startx, z);
				lastY = startY;
				/*if(lastY == 0 || (startY - lastY < 1 || startY - lastY > -1))
				{
					//System.out.println("n3");
					return; //this prevents it from trying to gen over cliff edges.
				}*/
				if(startY < 10)
				{
					//System.out.println("n4");
					return; //returns if it can't find a point. *This will eventually go to 128 to prevent them from spawning ontop. :P
				}
				
				int endY = startY + (rand.nextInt(height - 1 + 1) + 1);
				for(int y = startY;y <= endY;y++)
				{
					lastY = y;
					//System.out.println("n5");
					//System.out.println(y + "-----" + (startY2 - 2));
					if((y > (startY2 - 2)) && y < (startY2 + 5))
					{
					src.getBlock(startx, y, z).setType(Material.NETHER_BRICK);
					if(y == startY+1 && rand.nextInt(3) == 2)
					{
					src.getBlock(startx, y, z).setType(Material.NETHER_FENCE);
					src.getBlock(startx, y, z).setData((byte)0);
					}
					}
				}
			}
		}
		/* +1 z per block */
		if(d == BFace.SOUTH)
		{
			//System.out.println("s1");
			int lastY = 0;
			int startY2 = getLowestY2(src.getBlock(startx,src2.getHighestBlockYAt(startx, startz),startz));
			//src.getBlock(startx,src2.getHighestBlockYAt(startx, startz),startz).setType(Material.WOOL);
		//	int startY = src2.getHighestBlockYAt(startx, startz);
			for(int z = startz;z <= endz;z++)
			{
				//System.out.println("s2");
				//This is what will be used after testing. int startY = getFirstOpenY(conspos, z, src); //the lowest y point
				startY = src2.getHighestBlockYAt(startx, z);
				/*if(lastY != 0 && (startY - lastY < 1 || startY - lastY > -1))
				{
					//System.out.println(lastY);
					//System.out.println("n3");
					return; //this prevents it from trying to gen over cliff edges.
				}*/
				if(startY < 10)
				{
					//System.out.println("s3");
					return; //returns if it can't find a point. *This will eventually go to 128 to prevent them from spawning ontop. :P
				}
				int endY = startY + (rand.nextInt(height - 1 + 1) + 1);
				for(int y = startY;y <= endY;y++)
				{
					lastY = y;
					//System.out.println("s4");
					if((y > (startY2 - 2)) && y < (startY2 + 5))
					{
					src.getBlock(startx, y, z).setType(Material.NETHER_BRICK);
					}
					if(y == startY+1 && rand.nextInt(3) == 2)
					{
					src.getBlock(startx, y, z).setType(Material.NETHER_FENCE);
					src.getBlock(startx, y, z).setData((byte)3);
					}
				}
			}
		}
		/* +1 x per block */
		if(d == BFace.EAST)
		{
			//System.out.println("e1");
			int lastY = 0;
			int startY2 = getLowestY2(src.getBlock(startx,src2.getHighestBlockYAt(startx, startz),startz));
			//src.getBlock(startx,src2.getHighestBlockYAt(startx, startz),startz).setType(Material.WOOL);
		//	int startY = src2.getHighestBlockYAt(startx, startz);
			for(int x = startx;x <= endx;x++)
			{
				//System.out.println("e2");
				//This is what will be used after testing. int startY = getFirstOpenY(conspos, z, src); //the lowest y point
				startY = src2.getHighestBlockYAt(x, startz);
				if(lastY != 0 && (startY - lastY < 1 || startY - lastY > -1))
				{
					//System.out.println(lastY);
					//System.out.println("n3");
					return; //this prevents it from trying to gen over cliff edges.
				}
				if(startY < 10)
				{
					//System.out.println("e3");
					return; //returns if it can't find a point. *This will eventually go to 128 to prevent them from spawning ontop. :P
				}
				int endY = startY + (rand.nextInt(height - 1 + 1) + 1);
				for(int y = startY;y <= endY;y++)
				{
					lastY = y;
					//System.out.println("e4");
					if((y > (startY2 - 2)) && y < (startY2 + 5))
					{
					src.getBlock(x, y, startz).setType(Material.NETHER_BRICK);
					}
					if(y == startY+1 && rand.nextInt(3) == 2)
					{
					src.getBlock(startx, y, startz).setType(Material.NETHER_FENCE);
					src.getBlock(startx, y, startz).setData((byte)2);
					}
				}
			}
		}
		/* -1 x per block */
		if(d == BFace.WEST)
		{
			//System.out.println("w1");
			int lastY = 0;
			int startY2 = getLowestY2(src.getBlock(startx,src2.getHighestBlockYAt(startx, startz),startz));
			//int startY = src2.getHighestBlockYAt(startx, startz);
			//src.getBlock(startx,src2.getHighestBlockYAt(startx, startz),startz).setType(Material.WOOL);
			for(int x = startx;x >= endx;x--)
			{
				//System.out.println("w2");
				//This is what will be used after testing. int startY = getFirstOpenY(conspos, z, src); //the lowest y point
				startY = src2.getHighestBlockYAt(x, startz);
				if(startY < 10)
				{
				//	System.out.println("w3");
					return; //returns if it can't find a point. *This will eventually go to 128 to prevent them from spawning ontop. :P
				}
				//System.out.println(lastY);
				int endY = startY + (rand.nextInt(height - 1 + 1) + 1);
				for(int y = startY;y <= endY;y++)
				{
					lastY = y;
				//	System.out.println("w4");
				if((y > (startY2 - 2)) && y < (startY2 + 5))
				{
				src.getBlock(x, y, startz).setType(Material.NETHER_BRICK);
				}
				if(y == startY+1 && rand.nextInt(3) == 2)
				{
				src.getBlock(startx, y, startz).setType(Material.NETHER_FENCE);
				src.getBlock(startx, y, startz).setData((byte)1);
				}
				}
			}
		}
	}
	/* Gets the correct form of the for loop to be used */
	public boolean forHelper(BFace d, int i, int i2)
	{
		//i = z, i = endZ
		if(d == BFace.NORTH)
		{
			return i >= i2;
		}
		if(d == BFace.SOUTH)
		{
			return i <= i2;
		}
		if(d == BFace.WEST)
		{
			return i >= i2;
		}
		if(d == BFace.EAST)
		{
			return i <= i2;
		}
		return false;
	}
	/* Modifies the value correctly depending on direction */
	public int modify(BFace d, int i)
	{
		if(d == BFace.NORTH)
		{
			return i - 1;
		}
		if(d == BFace.SOUTH)
		{
			return i + 1;
		}
		if(d == BFace.WEST)
		{
			return i - 1;
		}
		if(d == BFace.EAST)
		{
			return i + 1;
		}
		return 0;
	}
	public int modify2(BFace d, int i)
	{
		if(d == BFace.NORTH)
		{
			return i - 7;
		}
		if(d == BFace.SOUTH)
		{
			return i + 7;
		}
		if(d == BFace.WEST)
		{
			return i - 7;
		}
		if(d == BFace.EAST)
		{
			return i + 7;
		}
		return 0;
	}
	/* Gets the correct starting value */
	public int getStart(BFace d)
	{
		if(d == BFace.EAST)
		{
			return 0;
		}
		if(d == BFace.WEST)
		{
			return 15;
		}
		if(d == BFace.NORTH)
		{
			return 15;
		}
		if(d == BFace.SOUTH)
		{
			return 0;
		}
		return 0;
	}
	/* Gets the correct ending value */
	public int getEnd(BFace d)
	{
		if(d == BFace.EAST)
		{
			return 15;
		}
		if(d == BFace.WEST)
		{
			return 0;
		}
		if(d == BFace.NORTH)
		{
			return 0;
		}
		if(d == BFace.SOUTH)
		{
			return 15;
		}
		return 0;
	}
	public int getLowestY(Block b)
	{
		int addedup = 0;
		int orginY = b.getY();
		if(!(b.getRelative(BlockFace.DOWN).getType() == Material.NETHERRACK) || !(b.getRelative(BlockFace.DOWN).getType() == Material.NETHER_BRICK))
		{
			int newY = b.getY();
			while(b.getWorld().getBlockAt(b.getX(),newY,b.getZ()).getRelative(BlockFace.DOWN).getType() != Material.NETHERRACK)
			{
				newY--;
				if(newY < 0)
				{
					return -1;
				}
			}
			addedup = newY;
			while(b.getWorld().getBlockAt(b.getX(),newY,b.getZ()).getRelative(BlockFace.UP).getType() == Material.NETHERRACK || b.getWorld().getBlockAt(b.getX(),newY,b.getZ()).getRelative(BlockFace.UP).getType() == Material.NETHER_BRICK)
			{
				newY++;
				//addedup++;
				if(newY > 256)
				{
					return -1;
				}
			}
			return newY;
		}else{
			return b.getY();
		}
	}
	public int getLowestY2(Block b)
	{
		int addedup = 0;
		int orginY = b.getY();
		if(!(b.getRelative(BlockFace.DOWN).getType() == Material.NETHERRACK))
		{
			int newY = b.getY();
			while(b.getWorld().getBlockAt(b.getX(),newY,b.getZ()).getRelative(BlockFace.DOWN).getType() != Material.NETHERRACK)
			{
				newY--;
				//System.out.println(b.getWorld().getBlockAt(b.getX(),newY,b.getZ()).getRelative(BlockFace.DOWN).getType().name());
				//System.out.println(newY);
				if(newY < 0)
				{
					return -1;
				}
			}
			/*addedup = newY;
			while(b.getWorld().getBlockAt(b.getX(),newY,b.getZ()).getRelative(BlockFace.UP).getType() == Material.NETHERRACK)
			{
				newY++;
				//addedup++;
				if(newY > 256)
				{
					return -1;
				}
			}*/
			return newY;
		}else{
			return b.getY();
		}
	}
	public int getFirstOpenY(int x, int z, Chunk src)
	{
		for(int y = 10; y < 256;y++)
		{
			if(src.getBlock(x, y, z).getRelative(0,1,0).getType() == Material.NETHERRACK && src.getBlock(x, y, z).getType() == Material.AIR)
			{
				return y;
			}
		}
		return 0;
	}
}
