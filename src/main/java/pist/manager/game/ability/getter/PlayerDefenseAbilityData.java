package main.java.pist.manager.game.ability.getter;

import main.java.pist.manager.game.ability.Ability;
import main.java.pist.plugins.tool_ability.ToolPlugin;
import org.bukkit.entity.Player;

public final class PlayerDefenseAbilityData {
	
	public static double getDefense(Player p) {
		double val = 0;
		
		val = val + ToolPlugin.getArmorValue(p, Ability.ARMOR_DEFENSE);
		
		return val;
	}
	
	public static double getMagicDefense(Player p) {
		double val = 0;
		
		val = val + ToolPlugin.getArmorValue(p, Ability.ARMOR_DEFENSE_MAGIC);
		
		return val;
	}
	
	public static double getHealth(Player p) {
		double val = 0;
		
		val = val + ToolPlugin.getArmorValue(p, Ability.ARMOR_HP);
		
		return val;
	}
	
	public static double getSpeed(Player p) {
		double val = 0;
		
		val = val - ToolPlugin.getArmorValue(p, Ability.ARMOR_SPEED);
		
		return val;
	}
}
