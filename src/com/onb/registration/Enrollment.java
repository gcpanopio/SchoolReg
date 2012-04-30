package com.onb.registration;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.onb.registration.exception.*;

public class Enrollment {
	
	private final int enrollmentID;
	private final AcademicTerm term;
	private final YearLevel yearLevel;
	private final Student	student;
	private Scholarship scholarship = Scholarship.NONE;
	private Set<Section> sections = new HashSet<Section>();
	private BigDecimal miscFee = new BigDecimal(2000);
	
	/**
	 * The Enrollment class provides information regarding the registration of a student for a semester.
	 * An instance of the Enrollment must be associated to one and only one student. 
	 * It also contains data about the student's year level, scholarship and the academic term
	 * It can be uniquely defined by its enrollment ID.
	 * 
	 * @param enrollmentID	unique identifier of an enrollment instance
	 * @param term
	 * @param yearLevel
	 * @param student
	 */
    Enrollment(int enrollmentID, AcademicTerm term, YearLevel yearLevel, Student student) {
    	this.enrollmentID =enrollmentID;
		this.term = term;
		this.yearLevel = yearLevel;
		this.student = student;
	}
    
    public int getEnrollmentID(){
    	return enrollmentID;
    }
    
    public Student getStudent(){
    	return student;
    }

    public AcademicTerm getAcademicTerm(){
    	return term;
    }
    
    /**
     * Sets the value of the Scholarship for the current enrollment
     * 
     * @param scholarship	
     */
	public void setScholarship(Scholarship scholarship) {
		this.scholarship = scholarship;
	}
	
	/**
	 * 
	 * Adds a valid section to the current enrollment.
	 * 
	 * A valid section is a section which does not have a conflict with other sections.
	 * This calls the checkStudentsPreviousEnrollment method of the student to check if the subject
	 * or the subject's prerequisites were already taken by the student 
	 * 
	 * @param section	the section to be added
	 * @throws EnrollmentException	If the section you want to add is in conflict with the other sections
	 * @throws SectionCreationException
	 */
	public void addSection(Section section) throws EnrollmentException, SectionCreationException {
		ensureNotExceedMaximumLoad(section);
		ensureNoConflictOfScheduleWith(section);
		student.checkStudentsPreviousEnrollment(section);
		sections.add(section);
		section.addEnrollment(this);
	}
	
	private void ensureNoConflictOfScheduleWith(Section section) throws EnrollmentException, SectionCreationException {
		for (Section sec: sections){
			if(section.isInConflictWith(sec)){
				throw new EnrollmentException("Schedule conflict. " +section + "is in conflict with "+ sec);
			}
		}
	}
	
	private void ensureNotExceedMaximumLoad(Section section) throws EnrollmentException{
		if (yearLevel.hasExceededMaximumLoad(getTotalLoad() + section.getSubjectUnits())){
			throw new EnrollmentException("Maximum load exceeded");
		}
	}
	
	 /**
	  * Computes the tuition fee of the student.
	  * Takes into account scholarships' discounts.
	  * 
	  * @return		the computed tuition fee from the given enrollment instance
	  * @throws		MinimumLoadRequiredException	if the current load is less than the minimum load required	
	  */
	public BigDecimal computeTuitionFee() throws MinimumLoadRequiredException{
		ensureMinimumLoadObtained();
		
		BigDecimal tuitionFee = new BigDecimal(0).setScale(2);
		
		for (Section sec : sections){
			tuitionFee = tuitionFee.add(sec.getSubjectFee());
		}
		
		tuitionFee = checkScholarshipDiscount(tuitionFee).add(miscFee);
		
		return tuitionFee.setScale(2);
	}
	
	private void ensureMinimumLoadObtained() throws MinimumLoadRequiredException{
		int minimumLoad = yearLevel.getMinimumLoad();
		int currentLoad = getTotalLoad();
		
		if (currentLoad < minimumLoad){
			throw new MinimumLoadRequiredException("Your current load ("+currentLoad+" unit/s) does not satisfy the minimum load requirement ("+minimumLoad+" units).");
		}
	}
	
	private int getTotalLoad() {
		int totalUnits = 0;
		for (Section section: sections)	{
			totalUnits += section.getSubjectUnits();
		}
		return totalUnits;
	}	
			
	private BigDecimal checkScholarshipDiscount(BigDecimal tuitionFee){
		double percentageOfPayment = scholarship.getPercentageOfPayment();
		tuitionFee = tuitionFee.multiply(new BigDecimal (percentageOfPayment).setScale(2));
		return tuitionFee;
	}
	
	/**
	 * 
	 * @return	the subjects of this enrollment
	 */
	public Set<Subject> getSubjects(){
		Set<Subject> subjects = new HashSet<Subject>();
		for (Section sec: sections){
			subjects.add(sec.getSubject());
		}
		return subjects;
	}
	
	/**
	 * Checks if the section you are trying to add is already in your current set of sections
	 * 
	 * @param section
	 * @return 	if the given sections belongs to the current section set of this enrollment
	 */
	public boolean contains(Section section) {
		return sections.contains(section);
	}
	
	/**
	 * 
	 * @return the number of sections in the section list
	 */
	public int numberOfSections(){
		return sections.size();
	}
	
	@Override
	public boolean equals(Object otherObject) {
		if(this == otherObject)
			return true;
		if(this == null)
			return false;
		if(!(otherObject instanceof Enrollment))
			return false;
		Enrollment otherEnrollment = (Enrollment) otherObject;
		return this.enrollmentID == otherEnrollment.getEnrollmentID();
	}
}