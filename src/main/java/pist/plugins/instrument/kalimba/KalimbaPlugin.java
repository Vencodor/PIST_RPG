package main.java.pist.plugins.instrument.kalimba;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KalimbaPlugin {
	public static HashMap<String, Boolean> kalimba = new HashMap<String, Boolean>();
	public static List<String> sounds = new ArrayList<String>();
	
	public static void add(Player p) {
		kalimba.put(p.getName(), false);
	}
	
	public static void remove(Player p) {
		kalimba.remove(p.getName());
	}
	
	public static KalimbaSound getSound(int slot) {
		return KalimbaSound.values()[slot];
	}
	
}
