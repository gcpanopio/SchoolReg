package com.onb.registration;

public enum Day {
	MONTHU( "Mon/Thu" ),
	TUESFRI ( "Tues/Fri" ),
	WEDSAT ( "Wed/Sat" );
	
	private String stringValue;
	
	Day(String stringValue){
		this.stringValue = stringValue;
	}
	
	public String toString() { return stringValue; }
}

