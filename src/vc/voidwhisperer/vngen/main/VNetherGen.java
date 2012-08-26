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
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class VNetherGen extends JavaPlugin {
	static boolean debug = true;
	//DebugPrinter dp;
	static VNetherGen p;
	public FileAccessor config;
	public void onEnable()
	{
		p = this;
		Logger l = Bukkit.getServer().getLogger();
		l.info("VNether enabled");
		config = new FileAccessor(this, "config.yml");
		//dp = new DebugPrinter();
		//Bukkit.getServer().getPluginManager().registerEvents(new ChunkListener(), this);
		/*WorldCreator wc = new WorldCreator("world2");
		wc.environment(Environment.NETHER);
		wc.generator("VNetherGen",Bukkit.getConsoleSender());
		Random r = new Random();
		wc.seed(r.nextLong());
		wc.createWorld();*/
		//getCommand("WHop").setExecutor(new CommandWHop());
	}
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
	{
		return new ChunkGen(Bukkit.getWorld(worldName));
	}
}
