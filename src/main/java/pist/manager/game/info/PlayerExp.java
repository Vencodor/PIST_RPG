package main.java.pist.manager.game.info;

public class PlayerExp {
	
	public static double expMutiply = 1;
	
	public static double getExp(int level) {
		double need = Math.pow(((level - 1) * 50 / 49),2.5) * expMutiply;
		
		if(need<=0)
			return 0;
		return need;
	}
	
	public static int getLevel(double exp) {
		return (int) (Math.pow((exp / expMutiply),0.4) * 49 / 50 + 1);
	}
	
}
