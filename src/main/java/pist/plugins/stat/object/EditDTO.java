package main.java.pist.plugins.stat.object;

import main.java.pist.plugins.stat.object.enums.Stat;

public class EditDTO {
	int increaseCount = 1;
	StatDTO increaseStat = new StatDTO();
	int statPoint = 0;
	Stat recommandStat = null;
	public int getIncreaseCount() {
		return increaseCount;
	}
	public void setIncreaseCount(int increaseCount) {
		this.increaseCount = increaseCount;
	}
	public StatDTO getIncreaseStat() {
		return increaseStat;
	}
	public void setIncreaseStat(StatDTO increaseStat) {
		this.increaseStat = increaseStat;
	}
	public int getStatPoint() {
		return statPoint;
	}
	public void setStatPoint(int statPoint) {
		this.statPoint = statPoint;
	}
	public Stat getRecommandStat() {
		return recommandStat;
	}
	public void setRecommandStat(Stat recommandStat) {
		this.recommandStat = recommandStat;
	}
	public EditDTO(int statPoint, Stat recommandStat) {
		this.statPoint = statPoint;
		this.recommandStat = recommandStat;
	}
	public EditDTO(int increaseCount, StatDTO increaseStat, int statPoint, Stat recommandStat) {
		super();
		this.increaseCount = increaseCount;
		this.increaseStat = increaseStat;
		this.statPoint = statPoint;
		this.recommandStat = recommandStat;
	}
	public EditDTO() {
	}
}
