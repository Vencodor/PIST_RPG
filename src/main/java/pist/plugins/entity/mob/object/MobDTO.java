package main.java.pist.plugins.entity.mob.object;

import main.java.pist.plugins.entity.mob.object.enums.MobPersonality;
import main.java.pist.plugins.entity.mob.object.enums.MobType;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class MobDTO {//체력,스피드,스킬,속성,타입,공격력,드랍 아이템
	
	String key = null;
	String display = null;
	String description = null;
	double health = 10;
	double power = 5;
	double speed = 0.3;
	MobType type = MobType.일반;
	MobPersonality personality = MobPersonality.중립;
	List<Block> spawner = new ArrayList<Block>();
	//자연스폰될 바이옴DTO
	//List<스킬DTO>
	//속성Enum
	//List<드랍 아이템DTO>
	
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
	public List<Block> getSpawner() {
		return spawner;
	}
	public void setSpawner(List<Block> spawner) {
		this.spawner = spawner;
	}
	public double getHealth() {
		return health;
	}
	public void setHealth(double health) {
		this.health = health;
	}
	public double getPower() {
		return power;
	}
	public void setPower(double power) {
		this.power = power;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public MobType getType() {
		return type;
	}
	public void setType(MobType type) {
		this.type = type;
	}
	public MobPersonality getPersonality() {
		return personality;
	}
	public void setPersonality(MobPersonality personality) {
		this.personality = personality;
	}
	
	public MobDTO(String key) {
		this.key = key;
	}
	
}
