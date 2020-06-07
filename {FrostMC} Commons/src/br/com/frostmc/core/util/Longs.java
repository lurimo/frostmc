package br.com.frostmc.core.util;

public class Longs {
	
	public static long fromString(String paramOfString) {
		String[] arrayOfString;
		Integer templateInteger;
		Integer integer;
		String[] timeString = paramOfString.split(",");
		Integer day = 0, hour = 0, minute = 0, second = 0;
		
		templateInteger = (arrayOfString = timeString).length;
		for(integer = 0; integer < templateInteger; integer++) {
			String string = arrayOfString[integer];
			if(string.contains("d") || string.contains("D")) {
				day = Integer.valueOf(string.replace("d", "").replace("D", "")).intValue();
			}
			if(string.contains("h") || string.contains("H")) {
				hour = Integer.valueOf(string.replace("h", "").replace("H", "")).intValue();
			}
			if(string.contains("m") || string.contains("M")) {
				minute = Integer.valueOf(string.replace("m", "").replace("M", "")).intValue();
			}
			if(string.contains("s") || string.contains("S")) {
				second = Integer.valueOf(string.replace("s", "").replace("S", "")).intValue();
			}
		}
		Long time = convert(day, hour, minute, second);
		return time;
	}
	
	public static String messageSmall(long paramOfLong) {
		String message = "";
		long now = System.currentTimeMillis();
		long diff = paramOfLong - now;
		int seconds = (int) (diff / 1000L);
		if (seconds >= 2592000) {
			int meses = seconds / 2592000;
			seconds %= 2592000;
			if (meses == 1) {
				message = String.valueOf(message) + meses + " mês";
			} else {
				message = String.valueOf(message) + meses + " mêses";
			}
		} else if (seconds >= 86400) {
			int days = seconds / 86400;
			seconds %= 86400;
			if (days == 1) {
				message = String.valueOf(message) + days + " dia";
			} else {
				message = String.valueOf(message) + days + " dias";
			}
		} else if (seconds >= 3600) {
			int hours = seconds / 3600;
			seconds %= 3600;
			if (hours == 1) {
				message = String.valueOf(message) + hours + " hora";
			} else {
				message = String.valueOf(message) + hours + " horas";
			}
		} else if (seconds >= 60) {
			int min = seconds / 60;
			seconds %= 60;
			if (min == 1) {
				message = String.valueOf(message) + min + " minuto";
			} else {
				message = String.valueOf(message) + min + " minutos";
			}
		} else if (seconds >= 0) {
			if (seconds == 1) {
				message = String.valueOf(message) + seconds + " segundo";
			} else {
				message = String.valueOf(message) + seconds + " segundos";
			}
		} else if(seconds < 0) {
			message = "Expirado";
		}
		return message;
	}
	
	public static String message(long paramOfLong) {
		String message = "";
		long now = System.currentTimeMillis();
		long diff = paramOfLong - now;
		int seconds = (int) (diff / 1000L);
		if (seconds >= 2592000) {
			int meses = seconds / 2592000;
			seconds %= 2592000;
			if (meses == 1) {
				message = String.valueOf(message) + meses + " mês, ";
			} else {
				message = String.valueOf(message) + meses + " mêses, ";
			}
		}
		if (seconds >= 86400) {
			int days = seconds / 86400;
			seconds %= 86400;
			if (days == 1) {
				message = String.valueOf(message) + days + " dia, ";
			} else {
				message = String.valueOf(message) + days + " dias, ";
			}
		}
		if (seconds >= 3600) {
			int hours = seconds / 3600;
			seconds %= 3600;
			if (hours == 1) {
				message = String.valueOf(message) + hours + " hora, ";
			} else {
				message = String.valueOf(message) + hours + " horas, ";
			}
		}
		if (seconds >= 60) {
			int min = seconds / 60;
			seconds %= 60;
			if (min == 1) {
				message = String.valueOf(message) + min + " minuto e ";
			} else {
				message = String.valueOf(message) + min + " minutos e ";
			}
		}
		if (seconds >= 0) {
			if (seconds == 1) {
				message = String.valueOf(message) + seconds + " segundo ";
			} else {
				message = String.valueOf(message) + seconds + " segundos ";
			}
		}
		if(seconds < 0) {
			message = "Expirado";
		}
		return message;
	}

	public static long convert(long days, long hours, long minutes, long seconds) {
		long x = 0L;
		long minute = minutes * 60L;
		long hour = hours * 3600L;
		long day = days * 86400L;
		x = minute + hour + day + seconds;
		long time = System.currentTimeMillis() + x * 1000L;
		return time;
	}

}
