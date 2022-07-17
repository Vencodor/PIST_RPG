package main.java.pist.data.player.object;

public class PlayerInfoDTO {
	int level = 1;
	double exp = 0;
	int money = 0;
	int cash = 0;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getExp() {
		return exp;
	}

	public void setExp(double exp) {
		this.exp = exp;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public PlayerInfoDTO(int level, double exp, int money, int cash) {
		super();
		this.level = level;
		this.exp = exp;
		this.money = money;
		this.cash = cash;
	}
	
	public PlayerInfoDTO() {
		
	}

}
