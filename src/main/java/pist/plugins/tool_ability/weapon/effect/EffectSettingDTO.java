package main.java.pist.plugins.tool_ability.weapon.effect;

import org.bukkit.Color;

import java.util.List;

public class EffectSettingDTO implements Cloneable {
	double damage = 1;
	double range = 5;
	List<Color> color = null;
	boolean magic = false;
	public double getDamage() {
		return damage;
	}
	public void setDamage(double damage) {
		this.damage = damage;
	}
	public double getRange() {
		return range;
	}
	public void setRange(double range) {
		this.range = range;
	}
	public List<Color> getColor() {
		return color;
	}
	public void setColor(List<Color> color) {
		this.color = color;
	}
	public boolean isMagic() {
		return magic;
	}
	public void setMagic(boolean magic) {
		this.magic = magic;
	}
	public EffectSettingDTO(double damage, double range, List<Color> color, boolean magic) {
		super();
		this.damage = damage;
		this.range = range;
		this.color = color;
		this.magic = magic;
	}
	
	@Override
	public EffectSettingDTO clone() throws CloneNotSupportedException {
		return (EffectSettingDTO) super.clone();
	}
	
}
