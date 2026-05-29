package com.auth.uam.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeCompaireInHr {
	public static void main(String s[]) {
	LocalDateTime ticketTime = LocalDateTime.of(2023, 02, 27, 14, 45, 0, 0);
	LocalDateTime currentTime = LocalDateTime.now();
	
//	LocalDateTime tempDateTime = LocalDateTime.from(currentTime1);
//
//	long years = tempDateTime.until(toDateTime, ChronoUnit.YEARS);
//
//	long months = tempDateTime.until(toDateTime, ChronoUnit.MONTHS);
//
//	long days = tempDateTime.until(toDateTime, ChronoUnit.DAYS);
//
//	long hours = tempDateTime.until(toDateTime, ChronoUnit.HOURS);
//
//	long minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES);

	
	
	long seconds = currentTime.until(ticketTime, ChronoUnit.SECONDS);
	
	long pluseBufferTime = seconds ;

	System.out.println( pluseBufferTime );
	

	if(pluseBufferTime<0) {
		System.out.println( "Time Out" );
	}
	if(pluseBufferTime>0) {
		System.out.println( "Time In" );
	}

	}
}
