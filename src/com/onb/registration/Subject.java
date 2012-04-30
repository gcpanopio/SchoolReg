package com.onb.registration;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.onb.registration.exception.SubjectCreationException;

public class Subject {

	private final int courseCode;
	private final SubjectType subjectType;
	private int units = 3;
	private Set<Subject> prerequisites = new HashSet<Subject>();
	
	/**
	 * Creates a Subject with the given course code and subjectType.
	 * Contains a Set of Subjects as prerequisite.
	 * Subject has a default of 3 units which can be changed at any given time.
	 * 
	 * @param courseCode
	 * @param subjectType
	 * @see SubjectType
	 */
	public Subject(int courseCode, SubjectType subjectType) {
		checkForNullArguments(subjectType);
		this.subjectType = subjectType;
		this.courseCode = courseCode;
	}

	private void checkForNullArguments(SubjectType subjectType) 
			throws IllegalArgumentException {
		if( subjectType == null ) {
			throw new IllegalArgumentException("subjectType has value: " + subjectType );
		}
	}

	public int getCode() {
		return courseCode;
	}

	public SubjectType getSubjectType() {
		return subjectType;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int newUnits) {
		units = newUnits;
	}

	/**
	 * Computes for the total cost of the Subject based on the number of units and SubjectType's cost.
	 * @return BigDecimal containing the total cost of the Subject precise up to 2 decimal places. 
	 */
	public BigDecimal getTotalCost() {
		return subjectType.computeTotalCost(units);
	}

	/**
	 * Adds a Subject as a prerequisite to this Subject.
	 * A Student cannot enroll to a Subject without taking its prerequisites first.
	 *  
	 * @param prereq
	 * @throws SubjectCreationException
	 */
	public void addPrerequisite(Subject prereq) throws SubjectCreationException {
		checkIfPrerequisiteCanBeAdded(prereq);
		prerequisites.add(prereq);
	}

	private void checkIfPrerequisiteCanBeAdded(Subject prereq) throws SubjectCreationException {
		checkForNullPrerequisite(prereq);
		checkIfExistingPrerequisite(prereq);
	}

	private void checkIfExistingPrerequisite(Subject prereq) throws SubjectCreationException {
		if(prerequisites.contains(prereq)) {
			throw new SubjectCreationException("Subject " + prereq + "is already a prerequisite of Subject " + this);
		}
	}

	private void checkForNullPrerequisite(Subject prereq) throws IllegalArgumentException {
		if( prereq == null ){
			throw new IllegalArgumentException("prereq has value: " + prereq);
		}
	}
	
	/**
	 * Checks if the Subject has any prerequisites.
	 * @return true if the Subject contains any prerequisite Subjects, false if otherwise.
	 */
	public boolean hasPrerequisites() {
		return !prerequisites.isEmpty();
	}
	
	@Override
	public boolean equals(Object otherObject) {
		if(otherObject instanceof Subject) {
			Subject otherSubject = (Subject) otherObject;
			return this.courseCode== otherSubject.courseCode;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return courseCode;
	}
	
	@Override 
	public String toString(){
		StringBuilder retString = new StringBuilder();
		retString.append("Subject ");
		retString.append(courseCode);
		return retString.toString();
	}
	
	/**
	 * Checks the Set of Subjects if it contains all prerequisite Subjects.
	 * @param subjectsTaken
	 * @return true if all prerequisites are present in the Set, false if otherwise.
	 */
	public boolean hasTakenPrerequisites(Set<Subject> subjectsTaken) {
		checkForNullSubjectSet(subjectsTaken);
		return subjectsTaken.containsAll(prerequisites)
;	}

	private void checkForNullSubjectSet(Set<Subject> subjectsTaken) {
		if(subjectsTaken == null) {
			throw new IllegalArgumentException("Subject Set " + subjectsTaken + " has value: null ");
		}
	}
	
}
