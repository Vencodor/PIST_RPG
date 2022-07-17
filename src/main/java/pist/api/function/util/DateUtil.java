package main.java.pist.api.function.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {
	
	public static final int SEC = 60;
	public static final int MIN = 60;
	public static final int HOUR = 24;
	public static final int DAY = 30;
	public static final int MONTH = 12;

	public static String getMonthDate(Date d) {
		DateFormat format = new SimpleDateFormat("MM월 dd일");
		return format.format(d);
	}
	
	public static String getHourDate(Date d) {
		DateFormat format = new SimpleDateFormat("hh시 mm분");
		return format.format(d);
	}
	
	public static String getDetailSecondDate(Date d) {
		DateFormat format = new SimpleDateFormat("hh시 mm분 ss초");
		return format.format(d);
	}
	
	public static String getSecondDate(Date d) {
		DateFormat format = new SimpleDateFormat("mm분 ss초");
		return format.format(d);
	}
	
	public static String getSecondFormatDate(Date d) {
		DateFormat format = new SimpleDateFormat("mm:ss");
		return format.format(d);
	}
	
	public static Date toDate(int time) {
		Date date = new Date(time*1000);
		return date;
	}
	
	public static String toStrTime(int time) {
		String text = "";
		
		if(time/60>0) {
			text = text+time/60+"분";
			time = time % 60;
		}
		if(time>0) {
			text = text+time+" 초";
		}
		
		return text;
	}

	public static String getLastDate(Date d) {
		long curTime = System.currentTimeMillis();

		long regTime = d.getTime();

		long diffTime = (curTime - regTime) / 1000;

		String msg = null;

		if (diffTime < SEC) {
			msg = diffTime + "초전";
		} else if ((diffTime /= SEC) < MIN) {
			msg = diffTime + "분 전";
		} else if ((diffTime /= MIN) < HOUR) {
			msg = (diffTime) + "시간 전";
		} else if ((diffTime /= HOUR) < DAY) {
			msg = (diffTime) + "일 전";
		} else if ((diffTime /= DAY) < MONTH) {
			msg = (diffTime) + "개월 전";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String curYear = sdf.format(curTime);
			String passYear = sdf.format(d);
			int diffYear = Integer.parseInt(curYear) - Integer.parseInt(passYear);
			msg = diffYear + "년 전";
		}

		return msg;

	}

}
