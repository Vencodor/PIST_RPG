package main.java.pist.plugins.system.biome.object;

import org.bukkit.block.Biome;

public class BiomeDTO {
	String key = null;
	String display = null;
	String description = null;
	Biome base = null;
	BiomeType type = BiomeType.던전;
	BiomeEffect effect = null;
	
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
	public Biome getBase() {
		return base;
	}
	public void setBase(Biome base) {
		this.base = base;
	}
	public BiomeType getType() {
		return type;
	}
	public void setType(BiomeType type) {
		this.type = type;
	}
	public BiomeEffect getEffect() {
		return effect;
	}
	public void setEffect(BiomeEffect effect) {
		this.effect = effect;
	}
	
	public BiomeDTO(String key, Biome base) {
		super();
		this.key = key;
		this.base = base;
	}
	
}
