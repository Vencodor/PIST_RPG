package main.java.pist.plugins.stat.object.enums;

public enum StatEffect {
	DAMAGE("근거리 공격력",1.0,"기본공격, 물리 스킬의 데미지를 증가시킵니다"),
	CRITICAL("치명타",0.1,"기본공격 시 적에게 추가 피해를 입히는 확률을 증가시킵니다"),
	CRITICAL_DAMAGE("치명타 피해",1,"치명타가 발동 되었을때 발동되는 추가 피해를 증가시킵니다"),
	
	MISS("회피",0.1,"피해를 입었을 때 피해를 무효화 시키는 확률을 증가시킵니다"),
	SPEED("이동속도",0.02,"플레이어의 이동속도를 증가시킵니다"),
	
	HP("체력",3,"플레이어의 최대체력량을 증가시킵니다"),
	REGEN_HP("체력회복",0.1,"자연적으로 회복되는 체력의 양을 증가시킵니다"),
	
	MAGIC("마법 데미지",1.3,"마법공격의 데미지를 증가시킵니다"),
	CRITICAL_MAGIC("마법 치명타",0.1,"마법공격 시 적에게 추가 피해를 입히는 확률을 증가시킵니다"),
	CRITICAL_DAMAGE_MAGIC("마법 치명타 피해",1.0,"마법 치명타가 발동 되었을때 발동되는 추가 피해를 증가시킵니다"),
	
	MP("마나",5,"플레이어의 최대 마나량을 증가시킵니다"),
	REGEN_MP("마나회복",0.1,"자연적으로 회복되는 마나의 양을 증가시킵니다");
	
	private final String name;
	private final double increase;
	private final String description;
	
	private StatEffect(String name, double increase, String description) {
		this.name = name;
		this.increase = increase;
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public double getIncrease() {
		return increase;
	}
	public String getDescription() {
		return description;
	}
}
