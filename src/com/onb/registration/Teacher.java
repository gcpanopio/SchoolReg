package com.onb.registration;

import java.util.HashSet;
import java.util.Set;

import com.onb.registration.exception.SectionCreationException;

public class Teacher {

	private final int facultyID; 
	private Set<Schedule> scheduleSet = new HashSet<Schedule>();
	
	/**
	 * Creates a Teacher with the given facultyID.
	 * A Teacher has a Set of Schedules of the Sections it is assigned to.
	 * 
	 * @param facultyID
	 * @see Schedule
	 */
	public Teacher( int facultyID ) {
		this.facultyID = facultyID;
	}

	public int getID() {
		return facultyID;
	}

	/**
	 * Tries to add the Schedule of the Section to be assigned to the Teacher.
	 * Throws SectionCreationException when the Schedule is conflicting with already existing Schedule.
	 * @param schedule
	 * @throws SectionCreationException
	 */
	public void addSchedule(Schedule schedule) throws SectionCreationException {
		checkIfScheduleCanBeAdded(schedule);
		scheduleSet.add( schedule );
	}

	private void checkIfScheduleCanBeAdded(Schedule schedule) throws SectionCreationException {
		checkForNullArguments(schedule);
		checkConflictingScheduleWith(schedule);
	}

	private void checkConflictingScheduleWith(Schedule schedule) throws SectionCreationException {
		for( Schedule existingSchedule : scheduleSet ){
			if(schedule.hasConflict(existingSchedule)) {
				throw new SectionCreationException( "teacher " + this + "has conflict for " + schedule );
			}
		}
	}

	private void checkForNullArguments(Schedule schedule) throws IllegalArgumentException {
		if( schedule == null ) {
			throw new IllegalArgumentException("schedule " + schedule + " has value: null");
		}
		
	}

	/**
	 * Checks if Teacher has a conflicting Schedule with the Schedule passed to the parameter.
	 * 
	 * @param schedule
	 * @return true if there's a conflict, false if otherwise.
	 */
	public boolean hasScheduleConflict(Schedule schedule) {
		return scheduleSet.contains(schedule);
	}
	
	@Override
	public boolean equals(Object otherObject) {
		if(otherObject instanceof Teacher) {
			Teacher otherTeacher = (Teacher) otherObject;
			return this.facultyID == otherTeacher.facultyID;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return facultyID;
	}
	
	@Override 
	public String toString(){
		StringBuilder retString = new StringBuilder();
		retString.append("Teacher ");
		retString.append(facultyID);
		return retString.toString();
	}

}
