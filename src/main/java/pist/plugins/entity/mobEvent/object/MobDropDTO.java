package main.java.pist.plugins.entity.mobEvent.object;

public class MobDropDTO {
	String entityDisplay = null;
	int dropExp = 0;
	int dropMoney = 0;
	String dropItem = null; //CustomItem Code 입력
	
	public String getEntityDisplay() {
		return entityDisplay;
	}
	public void setEntityDisplay(String entityDisplay) {
		this.entityDisplay = entityDisplay;
	}
	public int getDropExp() {
		return dropExp;
	}
	public void setDropExp(int dropExp) {
		this.dropExp = dropExp;
	}
	public int getDropMoney() {
		return dropMoney;
	}
	public void setDropMoney(int dropMoney) {
		this.dropMoney = dropMoney;
	}
	public String getDropItem() {
		return dropItem;
	}
	public void setDropItem(String dropItem) {
		this.dropItem = dropItem;
	}
	
	public MobDropDTO(String entityDisplay) {
		super();
		this.entityDisplay = entityDisplay;
	}
	
	public MobDropDTO(String entityDisplay, int dropExp, int dropMoney, String dropItem) {
		super();
		this.entityDisplay = entityDisplay;
		this.dropExp = dropExp;
		this.dropMoney = dropMoney;
		this.dropItem = dropItem;
	}
	
}
