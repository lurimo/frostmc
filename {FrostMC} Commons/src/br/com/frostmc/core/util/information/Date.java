package br.com.frostmc.core.util.information;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Date {
	
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy - HH:mm");
    public static Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("America/Sao_Paulo"));
    
    public static String getDate() {
    	TimeZone timeZone = TimeZone.getTimeZone("America/Sao_Paulo");
    	Calendar calendar = GregorianCalendar.getInstance(timeZone);
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy - HH:mm");
    	return simpleDateFormat.format(calendar.getTime());
    }
    
    public static String getMonth() {
    	TimeZone timeZone = TimeZone.getTimeZone("America/Sao_Paulo");
    	Calendar calendar = GregorianCalendar.getInstance(timeZone);
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
    	return simpleDateFormat.format(calendar.getTime());
    }
    
    public static String getHour() {
    	TimeZone timeZone = TimeZone.getTimeZone("America/Sao_Paulo");
    	Calendar calendar = GregorianCalendar.getInstance(timeZone);
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
    	return simpleDateFormat.format(calendar.getTime());
    }
}
