package main.java.pist.plugins.quest.integrated.object.questDefault;

public class QuestRewardDTO {
	String rewardItemContainer = null; //CustomItem 사용
	int rewardMoney = 0;
	int rewardExp = 0;
	
	public String getRewardItemContainer() {
		return rewardItemContainer;
	}
	public void setRewardItemContainer(String rewardItemContainer) {
		this.rewardItemContainer = rewardItemContainer;
	}
	public int getRewardMoney() {
		return rewardMoney;
	}
	public void setRewardMoney(int rewardMoney) {
		this.rewardMoney = rewardMoney;
	}
	public int getRewardExp() {
		return rewardExp;
	}
	public void setRewardExp(int rewardExp) {
		this.rewardExp = rewardExp;
	}
	public QuestRewardDTO() {
		
	}
}
