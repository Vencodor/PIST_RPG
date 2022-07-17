package main.java.pist.plugins.cook.object;

public class CookDataDTO {
	String key = null;
	int cookCount = 0;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getCookCount() {
		return cookCount;
	}
	public void setCookCount(int cookCount) {
		this.cookCount = cookCount;
	}
	public CookDataDTO(String key) {
		super();
		this.key = key;
	}

}
