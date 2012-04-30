package com.onb.registration;

import static org.junit.Assert.*;
import java.math.BigDecimal;
import org.junit.Test;

import static com.onb.registration.YearLevel.*;
import static com.onb.registration.SubjectType.*;
import static com.onb.registration.Day.*;
import static com.onb.registration.TimeSlot.*;

import com.onb.registration.exception.EnrollmentException;
import com.onb.registration.exception.MinimumLoadRequiredException;
import com.onb.registration.exception.SectionCreationException;
import com.onb.registration.exception.SubjectCreationException;

public class EnrollmentTest {

	@Test
	public void enrollmentConstructorAndAccessor() {
		Student mary = new Student(1);
		AcademicTerm currentTerm = new AcademicTerm(2012, 3);
		Enrollment enrollment = new Enrollment(1, currentTerm, FIRST, mary);
		mary.updateCurrentEnrollment(enrollment);
		
		assertEquals(1, enrollment.getEnrollmentID());
		assertTrue(mary.equals(enrollment.getStudent()));
		assertTrue(currentTerm.equals(enrollment.getAcademicTerm()));
	}
	
	@Test
	public void equalEnrollments(){
		Student mary = new Student(1);
		Enrollment enrollment = new Enrollment(1, new AcademicTerm (2000, 2), FIRST, mary);
		Enrollment enrollment2 = new Enrollment(1, new AcademicTerm (2000, 2), FIRST, mary);
		
		assertTrue(enrollment.equals(enrollment2));
		
	}
	
	@Test
	public void studentEnrollsInASection() throws SectionCreationException, EnrollmentException{
		Student mary = new Student(1);
		Enrollment enrollment = new Enrollment(1, new AcademicTerm (2000, 2), FIRST, mary);
		mary.updateCurrentEnrollment(enrollment);
		
		Section math = new Section(new Teacher(1), new Subject(1, UNDERGRADUATE), new Schedule(MONTHU, T_0830_1000));
		enrollment.addSection(math);
		
		assertTrue(math.containsEnrollment(enrollment));
	}
	
	@Test
	public void addSectionWithoutConflict() throws EnrollmentException, SectionCreationException {
		Student mary = new Student(1);
		Enrollment enrollment = new Enrollment(1, new AcademicTerm (2000, 2), FIRST, mary);
		mary.updateCurrentEnrollment(enrollment);
		
		Section math = new Section(new Teacher(1), new Subject(1, UNDERGRADUATE), new Schedule(MONTHU, T_0830_1000));
		enrollment.addSection(math);
		
		assertTrue(enrollment.contains(math));
		
		Section science = new Section(new Teacher(2), new Subject(2, UNDERGRADUATE), new Schedule(MONTHU, T_1000_1130));
		enrollment.addSection(science);
		
		assertEquals(2, enrollment.numberOfSections());
		assertTrue(enrollment.contains(math));
		assertTrue(enrollment.contains(science));	
	}
	
	@Test (expected = EnrollmentException.class)
	public void addSectionWithScheduleConflict() throws EnrollmentException, SectionCreationException {
		Student mary = new Student(1);
		Enrollment enrollment = new Enrollment(1, new AcademicTerm (2000, 2), FIRST, mary);
		mary.updateCurrentEnrollment(enrollment);
		
		Section math = new Section(new Teacher(1), new Subject(1, UNDERGRADUATE), new Schedule(MONTHU, T_0830_1000));
		enrollment.addSection(math);
		
		Section math2 = new Section(new Teacher(2), new Subject(2, UNDERGRADUATE), new Schedule(MONTHU, T_0830_1000));
		enrollment.addSection(math2);
	}
	
	@Test
	public void addSectionWithPrerequisites() throws EnrollmentException, SectionCreationException, SubjectCreationException{
		Student mary = new Student(1);
		
		Enrollment enrollment = new Enrollment(1, new AcademicTerm (2000, 2), FIRST, mary);
		mary.updateCurrentEnrollment(enrollment);
		
		Subject math1 = new Subject(1, UNDERGRADUATE);
		Section mathClass = new Section(new Teacher(1), math1, new Schedule(MONTHU, T_0830_1000));
		enrollment.addSection(mathClass);
		
		Enrollment enrollment2 = new Enrollment(2, new AcademicTerm (2001, 1), FIRST, mary);
		mary.updateCurrentEnrollment(enrollment2);
		
		Subject math2 = new Subject(2, UNDERGRADUATE);
		math2.addPrerequisite(math1);
		Section mathClass2 = new Section(new Teacher(1), math2, new Schedule(WEDSAT, T_0830_1000));
		enrollment2.addSection(mathClass2);
		
		assertTrue(enrollment2.contains(mathClass2));
	}
	
	@Test (expected = EnrollmentException.class)
	public void addSectionWithPrerequisitesNotYetTaken() throws EnrollmentException, SectionCreationException, SubjectCreationException{
		Student mary = new Student(1);
		Subject math1 = new Subject(1, UNDERGRADUATE);
		
		Enrollment enrollment2 = new Enrollment(2, new AcademicTerm (2001, 1), FIRST, mary);
		mary.updateCurrentEnrollment(enrollment2);
		
		Subject math2 = new Subject(2, UNDERGRADUATE);
		math2.addPrerequisite(math1);
		Section mathClass2 = new Section(new Teacher(1), math2, new Schedule(WEDSAT, T_0830_1000));
		enrollment2.addSection(mathClass2);
	}
	
	@Test (expected = EnrollmentException.class)
	public void addSameSectionTwice() throws EnrollmentException, SectionCreationException {
		Student mary = new Student(1);
		Enrollment enrollment = new Enrollment(1, new AcademicTerm (2000, 2), FIRST, mary);
		mary.updateCurrentEnrollment(enrollment);
		
		Section math = new Section(new Teacher(1), new Subject(1, UNDERGRADUATE), new Schedule(MONTHU, T_0830_1000));
		enrollment.addSection(math);
		enrollment.addSection(math);
	}
	
	@Test
	public void checkMaximumLoad() throws SectionCreationException, EnrollmentException{
		Student mary = new Student(1);
		Enrollment enrollment = new Enrollment(1, new AcademicTerm (2000, 2), FIRST, mary);
		Subject math = new Subject(1, UNDERGRADUATE);
		math.setUnits(18);
		Section mathClass = new Section(new Teacher(1), math, new Schedule(MONTHU, T_0830_1000));
		
		enrollment.addSection(mathClass);
		
		assertTrue(enrollment.contains(mathClass));
	}
	
	@Test (expected = EnrollmentException.class)
	public void checkIfExceedsMaximumLoad() throws SectionCreationException, EnrollmentException{
		Student mary = new Student(1);
		Enrollment enrollment = new Enrollment(1, new AcademicTerm (2000, 2), FIRST, mary);
		Subject math = new Subject(1, UNDERGRADUATE);
		math.setUnits(20);
		Section mathClass = new Section(new Teacher(1), math, new Schedule(MONTHU, T_0830_1000));
		
		enrollment.addSection(mathClass);
	}
	
	@Test (expected = EnrollmentException.class)
	public void checkIfClassExceedsMaxNumOfStudents() throws SectionCreationException, EnrollmentException{
		Section math = new Section(new Teacher(1), new Subject (1, UNDERGRADUATE), new Schedule(MONTHU, T_0830_1000));
		
		for (int i = 0; i < 41; i++){
			Enrollment enrollment = new Enrollment(1, new AcademicTerm (2000, 2), FIRST, new Student(i));
			math.addEnrollment(enrollment);
		}
	}
	
	@Test (expected = SectionCreationException.class )
	public void addSameScheduleToTeacher() throws SectionCreationException {
		Teacher teacher = new Teacher(1);
		Schedule schedule = new Schedule( MONTHU, T_0830_1000 );
		
		teacher.addSchedule(schedule);
		teacher.addSchedule(schedule);
	}
	
	@Test
	public void minimumLoadObtainedBeforeAssessment() throws Exception{
		Student mary = new Student(1);
		
		Subject math = new Subject(1, UNDERGRADUATE);
		math.setUnits(15);
		Section mathClass = new Section(new Teacher(1), math, new Schedule(MONTHU, T_0830_1000));
		
		Enrollment enrollment = new Enrollment(1, new AcademicTerm (2000, 2), FIRST, mary);
		mary.updateCurrentEnrollment(enrollment);
		
		enrollment.addSection(mathClass);
		assertEquals(new BigDecimal(32000).setScale(2), enrollment.computeTuitionFee());
	}
	
	@Test (expected = MinimumLoadRequiredException.class)
	public void lessThanMinimumLoadObtained() throws Exception{
		Student mary = new Student(1);
		Subject math = new Subject(1, UNDERGRADUATE);
		math.setUnits(12);
		Section mathClass = new Section(new Teacher(1), math, new Schedule(MONTHU, T_0830_1000));
		
		Enrollment enrollment = new Enrollment(1, new AcademicTerm (2000, 2), FIRST, mary);
		mary.updateCurrentEnrollment(enrollment);
		
		enrollment.addSection(mathClass);
		enrollment.computeTuitionFee();
	}
	
	@Test
	public void studentAssessmentWithoutScholarship() throws Exception{
		Student mary = new Student(1);
		
		Subject math = new Subject(1, UNDERGRADUATE);
		math.setUnits(9);
		Section mathClass = new Section(new Teacher(1), math, new Schedule(MONTHU, T_0830_1000));
		
		Subject math100 = new Subject(2, GRADUATE);
		math100.setUnits(6);
		Section mathClass2 = new Section(new Teacher(1), math100, new Schedule(WEDSAT, T_0830_1000));
		
		Enrollment enrollment = new Enrollment(1, new AcademicTerm (2000, 2), FIRST, mary);
		mary.updateCurrentEnrollment(enrollment);
		
		enrollment.addSection(mathClass);
		enrollment.addSection(mathClass2);
		
		assertEquals(new BigDecimal(44000.00).setScale(2), enrollment.computeTuitionFee());
	}
	
	@Test
	public void studentAssessmentWithHalfScholarship() throws Exception{
		Student mary = new Student(1);
		
		Subject math = new Subject(1, UNDERGRADUATE);
		math.setUnits(9);
		Section mathClass = new Section(new Teacher(1), math, new Schedule(MONTHU, T_0830_1000));
		
		Subject math100 = new Subject(2, GRADUATE);
		math100.setUnits(6);
		Section mathClass2 = new Section(new Teacher(1), math100, new Schedule(WEDSAT, T_0830_1000));
		
		Enrollment enrollment = new Enrollment(1, new AcademicTerm (2000, 2), FIRST, mary);
		mary.updateCurrentEnrollment(enrollment);
		
		enrollment.addSection(mathClass);
		enrollment.addSection(mathClass2);
		
		enrollment.setScholarship(Scholarship.HALF);
		
		assertEquals(new BigDecimal(23000).setScale(2), enrollment.computeTuitionFee());
	}
	
	@Test
	public void studentAssessmentWithFullScholarship() throws Exception{
		Student mary = new Student(1);
		
		Subject math = new Subject(1, UNDERGRADUATE);
		math.setUnits(9);
		Section mathClass = new Section(new Teacher(1), math, new Schedule(MONTHU, T_0830_1000));
		
		Subject math100 = new Subject(2, GRADUATE);
		math100.setUnits(6);
		Section mathClass2 = new Section(new Teacher(1), math100, new Schedule(WEDSAT, T_0830_1000));
		
		Enrollment enrollment = new Enrollment(1, new AcademicTerm (2000, 2), FIRST, mary);
		mary.updateCurrentEnrollment(enrollment);
		
		enrollment.addSection(mathClass);
		enrollment.addSection(mathClass2);
		
		enrollment.setScholarship(Scholarship.Full);
		
		assertEquals(new BigDecimal(2000.00).setScale(2), enrollment.computeTuitionFee());
	}

}
