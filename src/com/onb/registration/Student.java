//EDIT FOR GIT!

package com.onb.registration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.onb.registration.exception.EnrollmentException;

public class Student {

	private final int studentNum;
	private final Map<Subject, Enrollment> subjectsTaken;
	private Enrollment currentEnrollment;
	
	/**
	 * The student class contains the current enrollment record and previous enrollment records of a student.
	 * A student can be uniquely identified with his student number. 
	 * @param studentNum unique identifier of a student instance
	 */
	public Student(int studentNum) {
		this.studentNum = studentNum;
		this.subjectsTaken = new HashMap<Subject, Enrollment>();
		this.currentEnrollment = null;
	}

	public int getStudentNum() {
		return studentNum;
	}
	
	public Enrollment getCurrentEnrollment() {
		return currentEnrollment;
	}
	
	/**
	 * Sets up a new (active) enrollment record.	 * 
	 * 
	 * If there is a current active enrollment record, the subjects will be archived as "subjects already taken".
	 * 
	 * @param enrollment	the new enrollment record
	 */
	public void updateCurrentEnrollment(Enrollment enrollment) {
		archiveCurrentEnrollment();
		currentEnrollment = enrollment;
	}
	
	private void archiveCurrentEnrollment() {
		if (currentEnrollment != null) {
			for (Subject subject : currentEnrollment.getSubjects())
				subjectsTaken.put(subject, currentEnrollment);
		}
	}
	
	/**
	 * Checks if the student has already taken the given section or not. If not, then it checks if the student has already taken all the
	 * necessary prerequisites.
	 * 
	 * @param section	the section the student is trying to add
	 * @throws EnrollmentException	If the student has already taken the subject or has not yet taken its prerequisites
	 */
	public void checkStudentsPreviousEnrollment(Section section) throws EnrollmentException {
		Subject subject = section.getSubject();
		
		ensureWasNotTakenYet(subject);
		ensurePrerequisitesWereAlreadyTaken(section);
	}
	
	private void ensureWasNotTakenYet(Subject subject) throws EnrollmentException {
		Set<Subject> previousSubjectsTaken = subjectsTaken.keySet();

		if (previousSubjectsTaken.contains(subject))
			throw new EnrollmentException("Subject " + subject + " already taken");
	}
		
	private void ensurePrerequisitesWereAlreadyTaken(Section section) throws EnrollmentException {
		Set<Subject> previousSubjectsTaken = subjectsTaken.keySet();
		if (!section.hasTakenSubjectPrerequisites(previousSubjectsTaken)){
			throw new EnrollmentException("Prerequisite/s for "+section.getSubject()+": Not yet taken");
		}
	}

	@Override
	public int hashCode() {
		return getStudentNum();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Student)
		{
			Student student = (Student)obj;
			return this.getStudentNum() == student.getStudentNum();
		}
		else
			return false;		
	}
	
	
}
