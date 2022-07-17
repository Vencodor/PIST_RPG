package main.java.pist.plugins.entity.mob;

import main.java.pist.plugins.entity.mob.object.MobDTO;

import java.util.HashMap;

public class MobPlugin {
	public static HashMap<String,MobDTO> mobs = new HashMap<String,MobDTO>();
	
	public static MobDTO getMob(String key) {
		return mobs.get(key);
	}
	
	public static boolean addMob(String key) {
		if(mobs.get(key)!=null)
			return false;
		mobs.put(key, new MobDTO(key));
		return true;
	}
	
	public static boolean removeMob(String key) {
		if(mobs.get(key)==null)
			return false;
		mobs.remove(key);
		return true;
	}
	
	
	
}
