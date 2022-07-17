package main.java.pist.plugins.customItem.object;

import main.java.pist.plugins.customItem.object.enums.CustomType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CustomItemDTO {
	String key = null;
	String baseName = null;
	List<String> baseLore = new ArrayList<String>();
	List<ItemStack> contents = new ArrayList<ItemStack>();
	CustomType type = CustomType.CONTAINER;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public CustomType getType() {
		return type;
	}
	public void setType(CustomType type) {
		this.type = type;
	}
	public String getBaseName() {
		return baseName;
	}
	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}
	public List<String> getBaseLore() {
		return baseLore;
	}
	public void setBaseLore(List<String> list) {
		this.baseLore = list;
	}
	public List<ItemStack> getContents() {
		return contents;
	}
	public void setContents(List<ItemStack> contents) {
		this.contents = contents;
	}
	public CustomItemDTO() {
		
	}
	public CustomItemDTO(String key) {
		this.key = key;
	}
	public CustomItemDTO(String key, String baseName, List<String> baseLore, List<ItemStack> contents,
			CustomType type) {
		super();
		this.key = key;
		this.baseName = baseName;
		this.baseLore = baseLore;
		this.contents = contents;
		this.type = type;
	}
}
