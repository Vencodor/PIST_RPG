package main.java.pist.plugins.fishing;

import main.java.pist.plugins.fishing.object.FishDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class FishingPlugin {
	
	public static List<String> fishing = new ArrayList<String>();
	public static HashMap<String, FishDTO> fish = new HashMap<String, FishDTO>();
	
	public static FishDTO getFish(String key) {
		return fish.get(key);
	}
	
	public static boolean addFish(String key) {
		if(fish.get(key)!=null)
			return false;
		fish.put(key, new FishDTO(key));
		return true;
	}
	
	public static boolean removeFish(String key) {
		if(fish.get(key)==null)
			return false;
		fish.remove(key);
		return true;
	}
	
	public static FishDTO getFishRandom() {
		int totalPer = 0;
		for(FishDTO a : fish.values()) {
			totalPer = totalPer + a.getPercent().getPer();
		}
		int randomPer = (new Random()).nextInt(totalPer)+1;
		
		int currentPer = 0;
		for(FishDTO a : fish.values()) {
			currentPer = currentPer + a.getPercent().getPer();
			if(currentPer>=randomPer)
				return a;
		}
		return null;
	}
	
}
