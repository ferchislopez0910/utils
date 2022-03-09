package com.reto.utils;

import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;

public class RetoDiasHabiles {
	
	int numMonth, numYear;
	
	public RetoDiasHabiles() {
		Date d=new Date();  
        int year=d.getYear();
        this.numMonth  = d.getMonth();
        this.numYear  = (year+1900);
        
	}
	
	
	
	public int fechaPago(int numDate) {
		// dia final de pago
		int dayPay = -1;
		// contador temporal para el pago - incrementar o disminuir
		int dayPayTemporal = -1;
		if (numDate > this.countDayMonth() || numDate < 1)
			return dayPay;
		// validar si el dia enviado es festivo y que dia de la semana es (D,L,M,M,...)
		boolean esFest = this.isHoliday(numDate);
		int dayWeek = this.dayWeek(numDate);
		// validar si el dia enviado es quincena
		boolean esQuincena = (numDate == 15 || numDate == 30 ? true: false);
		
		/// si no es festivo y no es sabado o domingo, se paga el mismo dia
		if(esQuincena) {
			if (!esFest && (dayWeek != 1 || dayWeek !=7))
				return numDate;
		}
	
		// contador de dias -- inicia en que quincena
		dayPayTemporal = (numDate < 15 ? 15 : 30);
		while (dayPay == -1) {
			int numDayWTemp = this.dayWeek(dayPayTemporal);
			boolean esFestTemp = this.isHoliday(dayPayTemporal);
			if(!this.isHoliday(dayPayTemporal) && (numDayWTemp != 1 || numDayWTemp !=7))
				dayPay = dayPayTemporal;
			if (numDayWTemp == 1)
				dayPayTemporal += 1;
			if (numDayWTemp == 7)
				dayPayTemporal -= 1;
			if (esFestTemp && numDayWTemp == 6) {
				dayPayTemporal -= 1;
				break;					
			}else {
				dayPayTemporal += 1;
			}

		}
			
		
		return dayPay;
	}
	
	
	
	
	private  int countDayMonth() {
		YearMonth yearMonthObject = YearMonth.of(this.numYear, this.numMonth);
		return yearMonthObject.lengthOfMonth();
		
	}
	
	private  boolean isHoliday(int day) {
		HolidayUtil utilDay = new HolidayUtil(this.numYear);
		return (utilDay.isHoliday(this.numMonth, day));

		
	}

	private int dayWeek(int fechaInt) {
		Calendar today = Calendar.getInstance();
		today.set(this.numYear, this.numMonth, fechaInt);
		return today.get(Calendar.DAY_OF_WEEK);
	}
	
	public static void main(String[] args) {
		
		RetoDiasHabiles diasHabiles = new RetoDiasHabiles();
		
		int diaPago = diasHabiles.fechaPago(20);
		
		System.out.println(diaPago);

	}

}
