package main.java.pist.plugins.system.moveCancel;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class MoveCancelPlugin {
	
	public static List<Location> blockLoc = new ArrayList<Location>();
	
	public static List<Location> getNearBlock(Location loc) {
		List<Location> list = new ArrayList<Location>();
		
		for(Location a : blockLoc) {
			if(loc.distance(a)<4) {
				list.add(a);
			}
		}
		
		return list;
	}
	
	public static boolean isContains(Location loc) {
		return blockLoc.contains(loc);
	}
	
	public static void add(Block block) {
		blockLoc.add(block.getLocation());
	}
	
}
