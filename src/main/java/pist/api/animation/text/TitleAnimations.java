package main.java.pist.api.animation.text;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TitleAnimations {
	
	CLEAR(20*1.7,Arrays.asList(
			"&8&l<<<  &r&lCLEAR  &8&l>>>",//NULL
			"&8&l<<<  &r&lCLEAR  &8&l>>>",//NULL
			"&8&l<&8&l<&7&l<  &8&lC&r&lLEAR  &7&l>&8&l>&8&l>",//-C
			"&8&l<&8&l<&7&l<  &6&lC&8&lL&r&lEAR  &7&l>&8&l>&8&l>",//C
			"&8&l<&7&l<&8&l<  &r&lC&6&lL&8&lE&r&lAR  &8&l>&7&l>&8&l>",//L
			"&8&l<&7&l<&8&l<  &r&lCL&6&lE&8&lA&r&lR  &8&l>&7&l>&8&l>",//E
			"&7&l<&8&l<&8&l<  &r&lCLE&6&lA&8&lR  &8&l>&8&l>&7&l>",//A
			"&7&l<&8&l<&8&l<  &r&lCLEA&6&lR&8&l  &8&l>&8&l>&7&l>",//R
			"&7&l<<<  &6&lCLEAR  &7&l>>>",//ALL
			"&7&l<<<  &6&lCLEAR  &7&l>>>"//ALL
	),true),
	
	ERROR(20*0.5,Arrays.asList(
			"&8&l<<<  &4&l오류  &8&l>>>",
			"&8&l<<&7&l<  &4&l오류  &7&l>&8&l>>",
			"&8&l<&7&l<<  &4&l오류  &7&l>>&8&l>",
			"&7&l<<<  &4&l오류  &7&l>>>"
	),false),
	
	WARNING(20*0.8,Arrays.asList(
			"&8&l<<<  &c&l주의  &8&l>>>",
			"&8&l<<&b&l<  &c&l주의  &b&l>&8&l>>",
			"&8&l<&b&l<<  &c&l주의  &b&l>>&8&l>",
			"&b&l<<<  &c&l주의  &b&l>>>",
			"&8&l<<<  &c&l주의  &8&l>>>",
			"&8&l<<&b&l<  &c&l주의  &b&l>&8&l>>",
			"&8&l<&b&l<<  &c&l주의  &b&l>>&8&l>",
			"&b&l<<<  &c&l주의  &b&l>>>"
	),false);
	
	private double duration;
	private List<String> sceneText;
	private boolean lastSceneAnimation;

	public List<String> getSceneText() {
		List<String> list = new ArrayList<String>();
		for(String a : sceneText) {
			list.add(ChatColor.translateAlternateColorCodes('&', a));
		}
		return list;
	}
	
	public double getDuration() {
		return duration;
	}
	
	public int getSceneDuration() {
		if(sceneText.size()==0)
			return (int) (20*1.5);
		return (int) (duration / sceneText.size());
	}
	
	public boolean isLastSceneAnimation() {
		return lastSceneAnimation;
	}

	private TitleAnimations(double duration, List<String> sceneText, boolean lastSceneAnimation) {
		this.duration = duration;
		this.sceneText = sceneText;
		this.lastSceneAnimation = lastSceneAnimation;
	}
	
}
