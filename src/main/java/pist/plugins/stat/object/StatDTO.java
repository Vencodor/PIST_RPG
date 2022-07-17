package main.java.pist.plugins.stat.object;

public class StatDTO {
	int STR = 0;
	int DEX = 0;
	int CON = 0;
	int INT = 0;
	int WIS = 0;
	
	public void addStat(StatDTO stat) {
		STR = STR + stat.getSTR();
		DEX = DEX + stat.getDEX();
		CON = CON + stat.getCON();
		INT = INT + stat.getINT();
		WIS = WIS + stat.getWIS();
	}
	
	public int getStat(String name) {
		switch(name) {
		case "STR":
			return STR;
		case "DEX":
			return DEX;
		case "CON":
			return CON;
		case "INT":
			return INT;
		case "WIS":
			return WIS;
		default :
			break;
		}
		return -1;
	}
	public int getAllPoint() {
		return STR + DEX + CON + INT + WIS;
	}
	
	public void addStat(String name,int amount) {
		switch(name) {
		case "STR":
			STR = STR + amount;
			break;
		case "DEX":
			DEX = DEX + amount;
			break;
		case "CON":
			CON = CON + amount;
			break;
		case "INT":
			INT = INT + amount;
			break;
		case "WIS":
			WIS = WIS + amount;
			break;
		default :
			break;
		}
	}
	
	public int getSTR() {
		return STR;
	}
	public void setSTR(int sTR) {
		STR = sTR;
	}
	public int getDEX() {
		return DEX;
	}
	public void setDEX(int dEX) {
		DEX = dEX;
	}
	public int getCON() {
		return CON;
	}
	public void setCON(int cON) {
		CON = cON;
	}
	public int getINT() {
		return INT;
	}
	public void setINT(int iNT) {
		INT = iNT;
	}
	public int getWIS() {
		return WIS;
	}
	public void setWIS(int wIS) {
		WIS = wIS;
	}
	public StatDTO(int sTR, int dEX, int cON, int iNT, int wIS) {
		super();
		STR = sTR;
		DEX = dEX;
		CON = cON;
		INT = iNT;
		WIS = wIS;
	}
	public StatDTO() {
		
	}
}
