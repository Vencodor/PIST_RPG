package main.java.pist.plugins.fishing.object;

public enum FishPercentage {
	
	LOWEST(1),
	LOW(2),
	MEDIUM(3),
	HIGH(4),
	HIGHEST(5);
	
	int per;
	
	
	
	public int getPer() {
		return per;
	}
	public void setPer(int per) {
		this.per = per;
	}

	private FishPercentage(int per) {
		this.per = per;
	}
}
