package main.java.pist.plugins.customItem.object.enums;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public enum CustomType {
	CONTAINER(Material.CHEST,"아이템들을 보관합니다",false),
	EDITOR(Material.WORKBENCH,"아이템들의 로어를 수정하여 보관합니다",true),
	QUEST_REWARD(Material.WRITTEN_BOOK,"퀘스트 보상을 설정합니다",false),
	RANDOM(Material.DISPENSER,"단계에 따라 확률적으로 아이템을 도출할 수 있도록 설정합니다",false);
	
	private final Material icon;
	private final String description;
	private final boolean edit;
	public Material getIcon() {
		return icon;
	}
	public String getDescription() {
		return description;
	}
	public boolean isEdit() {
		return edit;
	}
	private CustomType(Material icon, String description, boolean edit) {
		this.icon = icon;
		this.description = description;
		this.edit = edit;
	}
	public List<CustomType> getList() {
		return Arrays.asList(CONTAINER,EDITOR);
	}
	
	private static CustomType[] vals = values();
	public CustomType next() {
		return vals[(this.ordinal()+1) % vals.length];
	}
}
