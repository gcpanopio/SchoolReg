package com.onb.registration;

public class Schedule {

	private final Day day;
	private final TimeSlot timeSlot;
	
	public Schedule(Day day, TimeSlot timeSlot ) {
		checkForNullArguments( day, timeSlot );
		this.day = day;
		this.timeSlot = timeSlot;
	}

	private void checkForNullArguments(Day day, TimeSlot timeSlot) 
			throws IllegalArgumentException {
		if( day == null ){
			throw new IllegalArgumentException("day has value: " + day );
		}
		else if( timeSlot == null ){
			throw new IllegalArgumentException("timeSlot has value: " + timeSlot );
		}
	}

	public Day getDay() {
		return day;
	}

	public TimeSlot getTimeSlot() {
		return timeSlot;
	}
	
	@Override
	public boolean equals(Object otherObject) {
		if( otherObject instanceof Schedule ) {
			Schedule otherSchedule = (Schedule) otherObject;
			return this.day == otherSchedule.day &&
					this.timeSlot == otherSchedule.timeSlot;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return day.hashCode() + timeSlot.hashCode();
	}
	
	@Override
	public String toString(){
		StringBuilder retString = new StringBuilder();
		retString.append(day);
		retString.append(" ");
		retString.append(timeSlot);
		return retString.toString();
	}

	/**
	 * Checks if it has conflicts with the Schedule passed to the parameter based on the day and the results of the
	 * TimeSlot.hasConflict() method.
	 * 
	 * @param schedule
	 * @return true if there's a Schedule conflict, false if otherwise.
	 */
	public boolean hasConflict(Schedule schedule) {
		checkForNullSchedule(schedule);
		return this.day == schedule.day && this.timeSlot.hasConflict(schedule.timeSlot);
	}

	private void checkForNullSchedule(Schedule schedule) {
		if( schedule == null ) {
			throw new IllegalArgumentException("schedule has value: null" );
		}
	}

}
