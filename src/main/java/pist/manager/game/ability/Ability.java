package main.java.pist.manager.game.ability;

public enum Ability {
	
	ATTACK_DAMAGE("물리 공격력"),
	MAGIC_DAMAGE("마법 공격력"),
	MISS("회피"),
	CRITICAL("크리티컬"),
	CRITICAL_DAMAGE("크리티컬 데미지"),
	CRITICAL_MAGIC("마법 크리티컬"),
	CRITICAL_MAGIC_DAMAGE("마법 크리티컬 데미지"),
	REGEN_MP("마나 회복"),
	REGEN_HP("체력 회복"),
	MAX_MP("최대 마나량"),
	MAX_HP("최대 체력"),
	SPEED("속도"),
	
	ARMOR_DEFENSE("방어력"),
	ARMOR_DEFENSE_MAGIC("마법 방어력"),
	ARMOR_HP("최대 체력"),
	ARMOR_SPEED("속도"),
	
	EXP("추가 경험치");
	
	private String display;
	
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}

	private Ability(String display) {
		this.display = display;
	}
	
}