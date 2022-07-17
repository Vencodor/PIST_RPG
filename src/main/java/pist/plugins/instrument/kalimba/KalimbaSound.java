package main.java.pist.plugins.instrument.kalimba;

import org.bukkit.ChatColor;

public enum KalimbaSound {
	도(ChatColor.YELLOW+""+ChatColor.BOLD+"도","d"),
	레(ChatColor.YELLOW+""+ChatColor.BOLD+"레","re"),
	미(ChatColor.YELLOW+""+ChatColor.BOLD+"미","m"),
	파(ChatColor.YELLOW+""+ChatColor.BOLD+"파","pa"),
	솔(ChatColor.YELLOW+""+ChatColor.BOLD+"솔","sol"),
	라(ChatColor.YELLOW+""+ChatColor.BOLD+"라","ra"),
	시(ChatColor.YELLOW+""+ChatColor.BOLD+"시","si"),
	도2_1(ChatColor.YELLOW+""+ChatColor.BOLD+"2옥타브 도","d2"),
	도2_2(ChatColor.GOLD+""+ChatColor.BOLD+"2옥타브 도","d2"),
	레2(ChatColor.GOLD+""+ChatColor.BOLD+"2옥타브 레","re2"),
	미2(ChatColor.GOLD+""+ChatColor.BOLD+"2옥타브 미","m2"),
	파2(ChatColor.GOLD+""+ChatColor.BOLD+"2옥타브 파","pa2"),
	솔2(ChatColor.GOLD+""+ChatColor.BOLD+"2옥타브 솔","sol2"),
	라2(ChatColor.GOLD+""+ChatColor.BOLD+"2옥타브 라","ra2"),
	시2(ChatColor.GOLD+""+ChatColor.BOLD+"2옥타브 시","si2"),
	도3(ChatColor.RED+""+ChatColor.BOLD+"3옥타브 도","d22");
	
	String display;
	String soundName;
	
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getSoundName() {
		return soundName;
	}
	public void setSoundName(String soundName) {
		this.soundName = soundName;
	}
	private KalimbaSound(String display, String soundName) {
		this.display = display;
		this.soundName = soundName;
	}
	
}
