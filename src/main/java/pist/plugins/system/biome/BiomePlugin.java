package main.java.pist.plugins.system.biome;

import main.java.pist.plugins.system.biome.listener.effect.NoAirEffectListener;
import main.java.pist.plugins.system.biome.listener.effect.NoWaterEffectListener;
import main.java.pist.plugins.system.biome.object.BiomeDTO;
import org.bukkit.block.Biome;

import java.util.HashMap;

public class BiomePlugin {
	public static HashMap<String,BiomeDTO> biomes = new HashMap<String,BiomeDTO>();
	
	public static BiomeDTO getBiome(String key) {
		return biomes.get(key);
	}
	
	public static BiomeDTO getBiomeUseBiome(Biome biome) {
		for(BiomeDTO a : biomes.values())
			if(a.getBase() == biome)
				return a;
		return null;
	}
	
	public static boolean addBiome(String key, Biome biome) {
		if(biomes.get(key)!=null)
			return false;
		for(BiomeDTO a : biomes.values())
			if(a.getBase() == biome)
				return false;
		
		biomes.put(key, new BiomeDTO(key, biome));
		return true;
	}
	
	public static boolean removeBiome(String key) {
		if(biomes.get(key)==null)
			return false;
		biomes.remove(key);
		return true;
	}
	
	public static void settingEffects() {
		(new NoWaterEffectListener()).checkScheduler();
		(new NoAirEffectListener()).checkScheduler();
	}
	
}
