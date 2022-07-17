package main.java.pist.data.player.object;

public class PlayerDynamicDTO {
	double maxMp = 10;
	double mp = 0;
	String biomeKey = null;

	public double getMp() {
		return mp;
	}
	public void setMp(double mp) {
		this.mp = mp;
	}
	public double getMaxMp() {
		return maxMp;
	}
	public void setMaxMp(double maxMp) {
		this.maxMp = maxMp;
	}
	public void addMp(double mp) {
		this.mp = this.mp + mp;
	}
	public String getBiomeKey() {
		return biomeKey;
	}
	public void setBiomeKey(String biomeKey) {
		this.biomeKey = biomeKey;
	}
	public PlayerDynamicDTO() {
		
	}
}
