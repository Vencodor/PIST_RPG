package main.java.pist.plugins.stat.object.enums;

import net.md_5.bungee.api.ChatColor;

import java.util.Arrays;
import java.util.List;

public enum Stat {
	
	STR(ChatColor.DARK_RED+"","힘","근거리 공격력과 관련된 스텟입니다"
			,Arrays.asList(StatEffect.DAMAGE,StatEffect.CRITICAL,StatEffect.CRITICAL_DAMAGE),StatIcon.STR),
	DEX(ChatColor.AQUA+"","이동","이동과 관련된 스텟입니다"
			,Arrays.asList(StatEffect.SPEED,StatEffect.MISS),StatIcon.DEX),
	CON(ChatColor.RED+"","체력","HP와 관련된 스텟입니다"
			,Arrays.asList(StatEffect.HP,StatEffect.REGEN_HP),StatIcon.CON),
	INT(ChatColor.LIGHT_PURPLE+"","지능","마법의 위력과 관련된 스텟입니다"
			,Arrays.asList(StatEffect.MAGIC,StatEffect.CRITICAL_MAGIC,StatEffect.CRITICAL_DAMAGE_MAGIC),StatIcon.INT),
	WIS(ChatColor.BLUE+"","지혜","MP와 관련된 스텟입니다"
			,Arrays.asList(StatEffect.MP,StatEffect.REGEN_MP),StatIcon.WIS);
	
	private final String color;
	private final String name;
	private final String description;
	private final List<StatEffect> effect;
	private final StatIcon icon;
	
	private Stat(String color, String name, String description, List<StatEffect> effect, StatIcon icon) {
		this.color = color;
		this.name = name;
		this.description = description;
		this.effect = effect;
		this.icon = icon;
	}
	public String getColor() {
		return color;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public List<StatEffect> getEffect() {
		return effect;
	}
	public StatIcon getIcon() {
		return icon;
	}
	
	public static List<Stat> getList() {
		return Arrays.asList(STR,DEX,CON,INT,WIS);
	}
	
}
