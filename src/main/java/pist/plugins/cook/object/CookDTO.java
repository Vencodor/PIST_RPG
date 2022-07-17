package main.java.pist.plugins.cook.object;

import main.java.pist.api.object.ItemDTO;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CookDTO {
	String key = null;
	String display = "없음";
	String description = null;
	int time = 1;
	ItemDTO icon = null;
	List<ItemDTO> material = new ArrayList<ItemDTO>();
	ItemDTO tool = null;
	ItemDTO result = null;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public ItemDTO getIcon() {
		return icon;
	}
	public void setIcon(ItemDTO icon) {
		this.icon = icon;
	}
	public List<ItemDTO> getMaterial() {
		return material;
	}
	public void setMaterial(List<ItemDTO> material) {
		this.material = material;
	}
	public ItemDTO getTool() {
		return tool;
	}
	public void setTool(ItemDTO tool) {
		this.tool = tool;
	}
	public ItemStack getResult() {
		return result.getItem();
	}
	public void setResult(ItemStack result) {
		this.result = new ItemDTO(result);
	}
	
	public CookDTO(String key) {
		this.key = key;
	}
	
	public CookDTO(String key, String display, String description, int time, ItemDTO icon, List<ItemDTO> material,
			ItemDTO tool, ItemDTO result) {
		super();
		this.key = key;
		this.display = display;
		this.description = description;
		this.time = time;
		this.icon = icon;
		this.material = material;
		this.tool = tool;
		this.result = result;
	}
	
	public CookDTO() {
		
	}
	
}
