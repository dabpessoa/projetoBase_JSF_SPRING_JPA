package br.com.digitoglobal.projeto.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DateUtils {

	private static final String DEFAULT_DATE_PATTERN = "dd/MM/yyyy";
	private static final String DEFAULT_DATETIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
	
	public static Integer  getAnoCorrente(){
		GregorianCalendar DataAtual = new GregorianCalendar();
		return DataAtual.get(GregorianCalendar.YEAR);
	}
	
	public static Date dataAtual() {
		return new Date();
	}
	
	public static Date addDaysToCurrent(int quantidadeDias){
		return addDays(dataAtual(), quantidadeDias);
	}
	
	public static Date addMonthsToCurrent(int quantidadeDias){
		return addMonths(dataAtual(), quantidadeDias);
	}

	public static Date addDays(Date date, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, amount);
		return c.getTime();
	}
	
	public static Date addMonths(Date date, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, amount);
		return c.getTime();
	}
	
	public static boolean after(Date d1, Date d2, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			d1 = sdf.parse(sdf.format(d1));
			d2 = sdf.parse(sdf.format(d2));
			return d1.compareTo(d2) > 0;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean after(Date d1, Date d2) {
		return after(d1, d2, "dd/MM/yyyy");
	}
	
	public static boolean afterNow(Date d1){
		return after(d1, DateUtils.dataAtual());
	}
	
	public static boolean before(Date d1, Date d2, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			d1 = sdf.parse(sdf.format(d1));
			d2 = sdf.parse(sdf.format(d2));
			return d1.compareTo(d2) < 0;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean before(Date d1, Date d2) {
		return before(d1, d2, "dd/MM/yyyy");
	}
	
	public static boolean beforeNow(Date d1){
		return before(d1, DateUtils.dataAtual());
	}
	
	public static boolean beforeOrEquals(Date d1, Date d2) {
		return before(d1, d2) || equals(d1, d2);
	}
	
	public static boolean afterOrEquals(Date d1, Date d2) {
		return after(d1, d2) || equals(d1, d2);
	}

	public static boolean afterOrEquals(Date d1, Date d2, String pattern) {
		return after(d1, d2, pattern) || equals(d1, d2, pattern);
	}
	
	public static boolean equals(Date d1, Date d2, String format) {
		try {
			if (d1 == null && d2 == null) return true;
			if (d1 == null || d2 == null) return false;
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			d1 = sdf.parse(sdf.format(d1));
			d2 = sdf.parse(sdf.format(d2));
			return d1.compareTo(d2) == 0;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean equals(Date d1, Date d2) {
		return equals(d1, d2, "dd/MM/yyyy");
	}
	
	public static boolean between(Date data, Date dataInicio, Date dataFim){
		if(!before(data, dataInicio) && !after(data, dataFim)){
			return true;
		}
		return false;
	}

	public static long daysBetween(Date d1, Date d2) {
		d1 = retirarHoras(d1);
		d2 = retirarHoras(d2);
		long diff = d2.getTime() - d1.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}

	public static Date retirarHoras(Date date) {
		return formatDate(date, "dd/MM/yyyy");
	}

	public static Date somenteHoras(Date date) {
		return formatDate(date, "HH:mm");
	}

	public static Date formatDate(Date date, String pattern) {
		return parse(format(date, pattern), pattern);
	}

	public static String format(Date date) {
		return format(date, DEFAULT_DATE_PATTERN);
	}
	
	public static String formatDateTime(Date date) {
		return format(date, DEFAULT_DATETIME_PATTERN);
	}
	
	public static String format(Date date, String pattern){
	    if(date == null){ return null; }
		return new SimpleDateFormat(pattern).format(date);
	}
	
	public static Calendar parseDateToCalendar(Date date){
		if(date==null)
			return  null;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	public static Date parse(String date, String pattern){
		try {
			return new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date parse(String date) {
		return parse(date, DEFAULT_DATE_PATTERN);
	}

	public static Integer getYear(Date date){
		return getDateField(date, Calendar.YEAR);
	}
	
	public static Integer getMonth(Date date){
		return getDateField(date, Calendar.MONTH);
	}
	
	public static Integer getDayOfMonth(Date date){
		return getDateField(date, Calendar.DAY_OF_MONTH);
	}
	
	private static Integer getDateField(Date date, Integer dateField){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(dateField);
	}

    public static LocalDate addDiasUteis(LocalDate date, int workdays) {
        if (workdays < 1) {
            return date;
        }

        LocalDate result = date;
        int addedDays = 0;
        while (addedDays < workdays) {
            result = result.plusDays(1);
            if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                ++addedDays;
            }
        }

        return result;
    }

	public static Date toDate(LocalDate date){
		if(date == null)
		{
			return null;
		}

		Instant instant = Instant.from(date.atStartOfDay(ZoneId.systemDefault()));
		return Date.from(instant);
	}

    public static LocalDate toLocalDate(Date date){
        if(date == null){
            return null;
        }

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
	
	public static Date toDate(LocalDateTime date){
		if(date == null){
			return null;
		}
		
		Instant instant = Instant.from(date.atZone(ZoneId.systemDefault()));
		return Date.from(instant);
	}

	public static  List<Integer> anosEntre(Integer anoDeInicio, Integer anoDeFim){
		if(anoDeInicio == null || anoDeFim == null){
			return null;
		}
		
		List<Integer> anosList = new ArrayList<Integer>();
		
		if(anoDeInicio.equals(anoDeFim)){
			anosList.add(anoDeInicio);
			return anosList;
		}
		
		Set<Integer> anosSet = new LinkedHashSet<Integer>();
		anosSet.add(anoDeInicio);
		anosSet.add(anoDeFim);
		
		anosList = new ArrayList<Integer>(anosSet);
		Collections.sort(anosList);
		
		anoDeInicio = anosList.get(0);
		anoDeFim = anosList.get(1);
		
		anosList.clear();
		for (int i = anoDeInicio; i <= anoDeFim; i++) {
			anosList.add(i);
		}
		
		return anosList;
	}

}