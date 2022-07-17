package main.java.pist.plugins.entity.mobEvent;

import main.java.pist.plugins.entity.mobEvent.object.MobDropDTO;

import java.util.ArrayList;
import java.util.List;

public class MobEventPlugin {
	
	public static List<MobDropDTO> drops = new ArrayList<MobDropDTO>();
	
	public static boolean addDrop(String display) {
		if(getMobDrop(display)!=null)
			return false;
		drops.add(new MobDropDTO(display));
		return true;
	}
	
	public static boolean removeDrop(String display) {
		if(getMobDrop(display)==null)
			return false;
		drops.remove(getMobDrop(display));
		return true;
	}
	
	public static MobDropDTO getMobDrop(String display) {
		if(display==null)
			return null;
		for(MobDropDTO a : drops) {
			if(display.contains(a.getEntityDisplay())) {
				return a;
			}
		}
		return null;
	}
	
}
