package main.java.pist.api.function.util;

import org.bukkit.ChatColor;

import java.text.DecimalFormat;

public final class FormatUtil {
	
	public static String format(double var) {
		DecimalFormat format = new DecimalFormat("###,###.#");
		return format.format(var);
	}
	
	public static String format(float var) {
		DecimalFormat format = new DecimalFormat("###,###.#");
		return format.format(var);
	}
	
	public static String format(int var) {
		DecimalFormat format = new DecimalFormat("###,###.#");
		return format.format(var);
	}
	
	public static String levelColor(double var, double max) {
		if(max*0.8<var) { //초록
			return ChatColor.GREEN + "";
		} else if(max*0.6<var) { //노랑
			return ChatColor.YELLOW + "";
		} else if(max*0.4<var) { //주황
			return ChatColor.GOLD + "";
		} else if(max*0.2<var) { //빨강
			return ChatColor.RED + "";
		} else { //진한빨강
			return ChatColor.DARK_RED + "";
		}
	}
	
	public static String colorFormat(double var, double max) {
		String format = format(var);
		if(var<=max) {
			return levelColor(var, max) + format;
		} else {
			return levelColor(max, var) + format;
		}
	}
}
