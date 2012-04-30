package com.onb.registration;

public enum Hour {
	FIRSTPERIOD( "8:30am-10am" ),
	SECONDPERIOD( "10am-11:30am" ),
	THIRDPERIOD( "11:30am-1:00pm" ),
	FOURTHPERIOD( "1:00pm-2:30pm " ),
	FIFTHPERIOD( "2:30pm-4:00pm" ),
	SIXTHPERIOD( "4:00pm-5:30pm" );
	
	private String stringValue;
	
	Hour(String stringValue){
		this.stringValue = stringValue;
	}
	
	public String toString(){ return stringValue; };
}
