package com.onb.registration;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.onb.registration.exception.EnrollmentException;
import com.onb.registration.exception.SectionCreationException;

public class Section {

	private final Teacher teacher;
	private final Subject subject;
	private final Schedule schedule;
	private int maxCapacity = 40;
	private Set<Enrollment> enrollmentSet = new HashSet<Enrollment>();
	
	/**
	 * Creates a Section class that contains pointers to the Teacher, Subject, and Schedule associated to it.
	 * Each Section has a default maximum capacity of 40 student enrollees which can be changed at any given time.
	 * Each Section also holds a set of Enrollments to have references to all Students enrolled in it.
	 * A Section cannot be created when the Teacher assigned to it handles other Sections with a Schedule conflict.
	 * 
	 * @param teacher
	 * @param subject
	 * @param schedule
	 * @throws SectionCreationException
	 * @see Subject Schedule Teacher Enrollment
	 */
	public Section(Teacher teacher, Subject subject, Schedule schedule) throws SectionCreationException {
		checkIfSectionCanBeCreated(teacher, subject, schedule);
		this.teacher = teacher;
		this.subject = subject;
		this.schedule = schedule;
	}

	private void checkIfSectionCanBeCreated(Teacher teacher, Subject subject,Schedule schedule) 
			throws SectionCreationException {
		checkForNullArguments(teacher,subject,schedule);
		checkForTeacherScheduleConflict(teacher, schedule);
	}

	private void checkForTeacherScheduleConflict(Teacher teacher,
			Schedule schedule) throws SectionCreationException {
			teacher.addSchedule(schedule);
	}

	private void checkForNullArguments(Teacher teacher, Subject subject,
			Schedule schedule) throws IllegalArgumentException {
		if( teacher == null) {
			throw new IllegalArgumentException("teacher has value: null");
		}
		else if( subject == null ) {
			throw new IllegalArgumentException("subject has value: null");
		}
		else if( schedule == null ) {
			throw new IllegalArgumentException("schedule has value: null");
		}
	}

	/**
	 * Checks if another Section has a Schedule conflict with it.
	 *  
	 * @param existingSection
	 * @return true if there's a Schedule conflict, false if there's no conflict.
	 */
	public boolean isInConflictWith(Section existingSection) {
		checkForNullSection(existingSection);
		return schedule.hasConflict(existingSection.getSchedule());
	}
	
	private void checkForNullSection(Section section) {
		if(section == null){
			throw new IllegalArgumentException("section has value: null");
		} 
	}

	/**
	 * Changes the maximum capacity of the Section into the specified integer value.
	 * Lowering the maximum capacity might cause various problems with the class methods.
	 * @param max
	 */
	public void setMaxCapacity(int max) {
		maxCapacity = max;
	}
	
	public int getMaxCapacity() {
		return maxCapacity;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public Subject getSubject() {
		return subject;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	/**
	 * Adds an enrollee to the Section.
	 * Throws EnrollmentException when the Section if full or the enrollee is already registered to the Section.
	 * @param enrollment
	 * @throws EnrollmentException
	 */
	public void addEnrollment(Enrollment enrollment) throws EnrollmentException {
		checkIfEnrollmentCanBeAdded(enrollment);
		enrollmentSet.add(enrollment);
	}
	
	private void checkIfEnrollmentCanBeAdded(Enrollment enrollment) throws EnrollmentException {
		checkForNullEnrollment(enrollment);
		checkIfEnrollmentAlreadyExist(enrollment);
		checkIfSectionIsAlreadyFull();
	}

	private void checkIfSectionIsAlreadyFull() throws EnrollmentException {
		if(enrollmentSet.size() >= maxCapacity) {
			throw new EnrollmentException("Section " + this + " is already full");
		}
	}

	private void checkIfEnrollmentAlreadyExist(Enrollment enrollment) throws EnrollmentException {
		if(enrollmentSet.contains(enrollment)) {
			throw new EnrollmentException("Enrollment " + enrollment + "already exist in Section " + this);
		}
	}

	private void checkForNullEnrollment(Enrollment enrollment) {
		if( enrollment == null ){
			throw new IllegalArgumentException("Enrollment " + enrollment + " has value: null");
		}
	}

	public BigDecimal getSubjectFee() {
		return subject.getTotalCost();
	}
	
	public int getSubjectUnits() {
		return subject.getUnits();
	}
	
	/**
	 * Checks if all the prerequisites of the Subject associated to this Section is listed in the Set of Subjects 
	 * passed to the parameter.
	 * @param subjectsTaken
	 * @return true if all prerequisites can be found in the Set subjectsTaken, false if otherwise.
	 */
	public boolean hasTakenSubjectPrerequisites(Set<Subject> subjectsTaken) {
		checkForNullSubjectSet(subjectsTaken);
		return subject.hasTakenPrerequisites(subjectsTaken);
	}
	
	private void checkForNullSubjectSet(Set<Subject> subjectsTaken) {
		if(subjectsTaken == null) {
			throw new IllegalArgumentException("Subject Set " + subjectsTaken + " has value: null ");
		}
	}

	/**
	 * Checks if an Enrollment is registered to the Section.
	 * @param enrollment
	 * @return true if the Enrollment is contained in the Section, false if otherwise.
	 */
	public boolean containsEnrollment(Enrollment enrollment) {
		checkForNullEnrollment(enrollment);
		return enrollmentSet.contains(enrollment);
	}
	
	@Override
	public boolean equals(Object otherObject) {
		if(otherObject instanceof Section) {
			Section otherSection = (Section) otherObject;
			return teacher.equals(otherSection.getTeacher()) &&
					subject.equals(otherSection.getSubject()) &&
					schedule.equals(otherSection.getSchedule());
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return teacher.hashCode() + subject.hashCode() + schedule.hashCode();
	}
	
	@Override 
	public String toString(){
		StringBuilder retString = new StringBuilder();
		retString.append("Section: Code ");
		retString.append(subject);
		retString.append(" - Faculty ");
		retString.append(teacher);
		retString.append(" - Time ");
		retString.append(schedule);
		return retString.toString();
	}

}
