package main.java.pist.plugins.tool_ability.weapon;


import main.java.pist.plugins.tool_ability.weapon.effect.EffectSettingDTO;
import main.java.pist.plugins.tool_ability.weapon.effect.effects.*;
import org.bukkit.Color;

import java.util.Arrays;

public enum Weapon {//단검,장검,활,망치,도끼,스태프
	
	SHORT_SWORD("단검",new EffectSettingDTO(1,4,Arrays.asList(Color.fromRGB(160, 161, 157),Color.fromRGB(79, 84, 88)),false),Wield_Short.class),
	LONG_SWORD("장검",new EffectSettingDTO(1,7,Arrays.asList(Color.fromRGB(160, 161, 157),Color.fromRGB(79, 84, 88)),false),Wield_Long.class),
	BOW("활",new EffectSettingDTO(1,7,Arrays.asList(Color.WHITE),false),Straight_Forward.class),
	HAMMER("망치",new EffectSettingDTO(0,0,Arrays.asList(Color.fromRGB(255, 38, 38),Color.fromRGB(189, 22, 22)),false),Smash_Down.class),
	AXE("도끼",new EffectSettingDTO(0,0,Arrays.asList(Color.fromRGB(223, 246, 255),Color.fromRGB(93, 139, 244),Color.fromRGB(45, 49, 250),Color.fromRGB(5, 19, 103))
			,false),Wield_Down.class),
	STAFF("스태프",new EffectSettingDTO(0,0,null,true),null);
	
	private final String display;
	private final EffectSettingDTO setting;
	private final Class<?> effect;
	public String getDisplay() {
		return display;
	}
	public EffectSettingDTO getSetting() {
		return setting;
	}
	public Class<?> getEffect() {
		return effect;
	}
	private Weapon(String display, EffectSettingDTO setting, Class<?> effect) {
		this.display = display;
		this.setting = setting;
		this.effect = effect;
	}
	
	public static Weapon get(String display) {
		switch(display) {
			case "단검":
				return SHORT_SWORD;
			case "장검":
				return LONG_SWORD;
			case "활":
				return BOW;
			case "망치":
				return HAMMER;
			case "도끼":
				return AXE;
			case "스태프":
				return STAFF;
		}
		return null;
	}
	
}
