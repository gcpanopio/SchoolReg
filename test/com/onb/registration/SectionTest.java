package com.onb.registration;

import static org.junit.Assert.*;

import static com.onb.registration.SubjectType.*;

import static com.onb.registration.Day.*;

import static com.onb.registration.TimeSlot.*;
import static com.onb.registration.YearLevel.FIRST;

import org.junit.Test;

import com.onb.registration.exception.EnrollmentException;
import com.onb.registration.exception.SectionCreationException;

public class SectionTest {

	@Test
	public void SectionStoresTeacherSubjectSchedule() throws SectionCreationException {
		Teacher teacher = new Teacher( 1 );
		Subject subject = new Subject( 101, UNDERGRADUATE );
		Schedule schedule = new Schedule( MONTHU, T_0830_1000 );
		Section section = new Section(teacher, subject, schedule);
		assertEquals( teacher, section.getTeacher());
		assertEquals( subject, section.getSubject());
		assertEquals( schedule, section.getSchedule());
	}
	
	@Test
	public void SectionAddsEnrollmentSuccessfully() throws SectionCreationException, EnrollmentException {
		Teacher teacher = new Teacher( 1 );
		Subject subject = new Subject( 101, UNDERGRADUATE );
		Schedule schedule = new Schedule( MONTHU, T_0830_1000 );
		Section section = new Section(teacher, subject, schedule);
		Student mary = new Student(1);
		Enrollment enrollment = new Enrollment(1, new AcademicTerm (2000, 2), FIRST, mary);
		section.addEnrollment(enrollment);
		assertTrue(section.containsEnrollment(enrollment));
	}

}
