package main.java.pist.plugins.cook;

import main.java.pist.plugins.cook.object.CookDTO;
import main.java.pist.plugins.cook.object.CookingDTO;

import java.util.HashMap;

public class CookPlugin {
	public static HashMap<String,CookDTO> cook = new HashMap<String,CookDTO>();
	
	public static HashMap<String,CookingDTO> playerCooking = new HashMap<String,CookingDTO>();
	
	public static boolean addCook(String key) {
		if(cook.get(key)!=null)
			return false;
		cook.put(key, new CookDTO(key));
		return true;
	}
	
	public static boolean removeCook(String key) {
		if(cook.get(key)==null)
			return false;
		cook.remove(key);
		return true;
	}
	
	public static CookDTO getCook(String key) {
		return cook.get(key);
	}
	
}
