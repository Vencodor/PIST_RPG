package main.java.pist.manager.game.ability.getter;

import main.java.pist.data.player.PlayerData;
import main.java.pist.data.player.object.PlayerDynamicDTO;
import main.java.pist.manager.game.ability.Ability;
import main.java.pist.plugins.stat.object.StatDTO;
import main.java.pist.plugins.stat.object.enums.StatEffect;
import main.java.pist.plugins.tool_ability.ToolPlugin;
import org.bukkit.entity.Player;

public final class PlayerAbilityData {
	
	public static PlayerDynamicDTO getData(Player p) {
		return PlayerData.getData(p.getUniqueId()).getDynamic();
	}
	
	//아래 함수들은 앞으로 버프로 인한 데미지 증가 등의 변수에 대처하기 위함
	public static double getMiss(Player p) {
		StatDTO stat = PlayerData.getData(p.getUniqueId()).getStat().getStat();

		double val = 0;
		val = val + stat.getDEX() * StatEffect.MISS.getIncrease();
		val = val + ToolPlugin.getValue(p, Ability.MISS);
		
		return val;
	}
	
	public static double getCritical(Player p) {
		StatDTO stat = PlayerData.getData(p.getUniqueId()).getStat().getStat();
		
		double val = 0;
		val = val + stat.getSTR() * StatEffect.CRITICAL.getIncrease();
		val = val + ToolPlugin.getValue(p, Ability.CRITICAL);
		
		return val;
	}
	
	public static double getCriticalMagic(Player p) {
		StatDTO stat = PlayerData.getData(p.getUniqueId()).getStat().getStat();

		double val = 0;
		val = val + stat.getINT() * StatEffect.CRITICAL_MAGIC.getIncrease();
		val = val + ToolPlugin.getValue(p, Ability.CRITICAL_MAGIC);
		
		return val;
	}
	
	public static double getCriticalMagicDamage(Player p) {
		StatDTO stat = PlayerData.getData(p.getUniqueId()).getStat().getStat();
		double val = 0;
		val = val + stat.getINT() * StatEffect.CRITICAL_DAMAGE_MAGIC.getIncrease();
		val = val + ToolPlugin.getValue(p, Ability.CRITICAL_MAGIC_DAMAGE);
		
		return val;
	}
	
	public static double getCriticalDamage(Player p) {
		StatDTO stat = PlayerData.getData(p.getUniqueId()).getStat().getStat();
		
		double val = 0;
		val = val + stat.getSTR() * StatEffect.CRITICAL_DAMAGE.getIncrease();
		val = val + ToolPlugin.getValue(p, Ability.CRITICAL_DAMAGE);
		
		return val;
	}
	
	public static double getAttackDamage(Player p) {
		StatDTO stat = PlayerData.getData(p.getUniqueId()).getStat().getStat();
		
		double val = 0;
		val = val + stat.getSTR() * StatEffect.DAMAGE.getIncrease();
		val = val + ToolPlugin.getValue(p, Ability.ATTACK_DAMAGE);
		
		return val;
	}
	
	public static double getMagicDamage(Player p) {
		StatDTO stat = PlayerData.getData(p.getUniqueId()).getStat().getStat();
		
		double val = 0;
		val = val + stat.getINT() * StatEffect.MAGIC.getIncrease();
		val = val + ToolPlugin.getValue(p, Ability.MAGIC_DAMAGE);
		
		return val;
	}
	
	public static double getRegenMp(Player p) {
		StatDTO stat = PlayerData.getData(p.getUniqueId()).getStat().getStat();

		double val = 0;
		val = val + stat.getWIS() * StatEffect.REGEN_MP.getIncrease();
		val = val + ToolPlugin.getValue(p, Ability.REGEN_MP);
		
		return val;
	}
	
	public static double getMaxMp(Player p) {
		StatDTO stat = PlayerData.getData(p.getUniqueId()).getStat().getStat();
		double val = 0;
		val = val + stat.getWIS() * StatEffect.MP.getIncrease();
		val = val + ToolPlugin.getValue(p, Ability.MAX_MP);
		
		return val;
	}
	
	public static double getRegenHealth(Player p) {
		StatDTO stat = PlayerData.getData(p.getUniqueId()).getStat().getStat();
		
		double val = 20.0;
		val = val + (stat.getCON() * StatEffect.REGEN_HP.getIncrease());
		val = val + ToolPlugin.getValue(p, Ability.REGEN_HP);
		
		return val;
	}
	
	public static double getMaxHealth(Player p) {
		StatDTO stat = PlayerData.getData(p.getUniqueId()).getStat().getStat();
		
		double val = 20.0;
		val = val + (stat.getCON() * StatEffect.HP.getIncrease());
		val = val + ToolPlugin.getValue(p, Ability.MAX_HP);
		val = val + PlayerDefenseAbilityData.getHealth(p);
		
		return val;
	}
	
	public static double getSpeed(Player p) {
		StatDTO stat = PlayerData.getData(p.getUniqueId()).getStat().getStat();
		double val = 0.2;
		val = val + stat.getDEX() * StatEffect.SPEED.getIncrease();
		val = val + ToolPlugin.getValue(p, Ability.SPEED);
		val = val + PlayerDefenseAbilityData.getSpeed(p);
		
		if(val>1)
			return 1;
		return val;
	}
	
	public static double getMoreExp(Player p) {
		double val = 0;
		val = val + ToolPlugin.getValue(p, Ability.EXP);
		
		return val;
	}
	
}
