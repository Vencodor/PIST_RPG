package main.java.pist.plugins.stat.object.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public enum StatIcon {
	
	STR(8,new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)14)), //짙은빨강
	DEX(11,new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)3)), //하늘
	CON(9,new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)2)), //연한빨강
	INT(12,new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)6)), //핑크
	WIS(10,new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)11)); //짙은 파랑
	
	private final int icon;
	private final ItemStack statGuiBar;
	
	
	public int getIcon() {
		return icon;
	}
	public ItemStack getStatGuiBar() {
		return statGuiBar;
	}

	private StatIcon(int icon, ItemStack statGuiBar) {
		this.icon = icon;
		this.statGuiBar = statGuiBar;
	}
	public static List<StatIcon> getList() {
		return Arrays.asList(STR,DEX,CON,INT,WIS);
	}
	
}
