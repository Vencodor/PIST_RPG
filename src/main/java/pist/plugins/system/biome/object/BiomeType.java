package main.java.pist.plugins.system.biome.object;

public enum BiomeType {
	
	마을(false,false),
	성(true,false),
	던전(true,false),
	무법지(true,true);
	
	boolean showTitle = false;
	boolean pvp = false;
	
	public boolean isShowTitle() {
		return showTitle;
	}
	public void setShowTitle(boolean showTitle) {
		this.showTitle = showTitle;
	}
	public boolean isPvp() {
		return pvp;
	}
	public void setPvp(boolean pvp) {
		this.pvp = pvp;
	}
	
	private BiomeType(boolean showTitle, boolean pvp) {
		this.showTitle = showTitle;
		this.pvp = pvp;
	}
}
