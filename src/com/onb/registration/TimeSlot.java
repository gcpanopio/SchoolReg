package com.onb.registration;

public enum TimeSlot {
	T_0830_1000 ( "8:30am-10am", 830, 1000 ),
	T_1000_1130 ( "10am-11:30am", 1000, 1130 ),
	T_1130_1300 ( "11:30am-1:00pm", 1130, 1300 ),
	T_1300_1430 ( "1:00pm-2:30pm ", 1300, 1430 ),
	T_1430_1600 ( "2:30pm-4:00pm", 1430, 1600 ),
	T_1600_1730 ( "4:00pm-5:30pm", 1600, 1730 );
	
	private String stringValue;
	private int startTime;
	private int endTime;
	TimeSlot(String stringValue, int startTime, int endTime){
		this.stringValue = stringValue;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public String toString() { return stringValue; }

	public boolean hasConflict(TimeSlot otherTimeSlot) {
		return (this.startTime < otherTimeSlot.startTime && this.endTime >  otherTimeSlot.startTime) ||
				(this.startTime < otherTimeSlot.endTime && this.endTime > otherTimeSlot.endTime) ||
				this.equals(otherTimeSlot);
	};
}
