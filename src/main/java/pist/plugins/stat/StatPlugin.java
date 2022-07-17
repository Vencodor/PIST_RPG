package main.java.pist.plugins.stat;

import main.java.pist.plugins.stat.object.EditDTO;

import java.util.HashMap;

public class StatPlugin {
	public static HashMap<String,EditDTO> editInfo = new HashMap<String,EditDTO>();
	
	public static EditDTO getEdit(String name) {
		if(editInfo.get(name)!=null)
			return editInfo.get(name);
		return null;
	}
	
	public static void removeEditInfo(String name) {
		if(editInfo.get(name)!=null)
			editInfo.remove(name);
	}
	
	
}
