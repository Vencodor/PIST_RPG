package main.java.pist.api.function.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Vector3D {
	
	public static boolean isLookingLoc(Player p, Location loc, double range) {
		range = 1 - range;
		Vector look = p.getEyeLocation().getDirection().normalize(); //플레이어가 바라보는 방향
		
		Location head = p.getLocation().add(0,p.getEyeHeight(),0); //플레이어 머리 위치
		Vector direction = loc.subtract(head).toVector().normalize(); 
		
		double dot = look.dot(direction);
		
		return dot > range;
	}
	
	
}
