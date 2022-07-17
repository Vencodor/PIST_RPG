package main.java.pist.plugins.cook.object;

import net.minecraft.server.v1_12_R1.EntityArmorStand;

public class CookingDTO {
	CookDTO cook = null;
	long startTime = System.currentTimeMillis();
	EntityArmorStand icon = null;
	EntityArmorStand text = null;
	public CookDTO getCook() {
		return cook;
	}
	public void setCook(CookDTO cook) {
		this.cook = cook;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public EntityArmorStand getIcon() {
		return icon;
	}
	public void setIcon(EntityArmorStand icon) {
		this.icon = icon;
	}
	public EntityArmorStand getText() {
		return text;
	}
	public void setText(EntityArmorStand text) {
		this.text = text;
	}
	public CookingDTO(CookDTO cook, EntityArmorStand icon, EntityArmorStand text) {
		super();
		this.cook = cook;
		this.startTime = System.currentTimeMillis();
		this.icon = icon;
		this.text = text;
	}
}
