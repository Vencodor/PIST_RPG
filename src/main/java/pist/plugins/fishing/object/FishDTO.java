package main.java.pist.plugins.fishing.object;

import main.java.pist.api.object.ItemDTO;

public class FishDTO {
	String key = null;
	String spawnBiome = null;
	ItemDTO item = null;
	FishPercentage percent = FishPercentage.MEDIUM;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSpawnBiome() {
		return spawnBiome;
	}
	public void setSpawnBiome(String spawnBiome) {
		this.spawnBiome = spawnBiome;
	}
	public ItemDTO getItem() {
		return item;
	}
	public void setItem(ItemDTO item) {
		this.item = item;
	}
	public FishPercentage getPercent() {
		return percent;
	}
	public void setPercent(FishPercentage percent) {
		this.percent = percent;
	}
	public FishDTO(String key) {
		super();
		this.key = key;
	}
}
