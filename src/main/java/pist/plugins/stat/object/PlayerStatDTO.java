package main.java.pist.plugins.stat.object;

public class PlayerStatDTO {
	int statPoint = 0;
	StatDTO stat = new StatDTO();
	
	public void addStat(StatDTO stat) {
		this.stat.addStat(stat);
	}
	
	public int getStatPoint() {
		return statPoint;
	}
	public void setStatPoint(int statPoint) {
		this.statPoint = statPoint;
	}
	public StatDTO getStat() {
		return stat;
	}
	public void setStat(StatDTO stat) {
		this.stat = stat;
	}
	
	public PlayerStatDTO(int statPoint, StatDTO stat) {
		super();
		this.statPoint = statPoint;
		this.stat = stat;
	}

	public PlayerStatDTO() {
		
	}
}
