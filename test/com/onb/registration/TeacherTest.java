package com.onb.registration;

import static org.junit.Assert.*;

import static com.onb.registration.SubjectType.*;

import static com.onb.registration.Day.*;

import static com.onb.registration.TimeSlot.*;

import org.junit.Test;

import com.onb.registration.exception.SectionCreationException;

public class TeacherTest {

	@Test
	public void TeacherStoresFacultyID() {
		Teacher teacher = new Teacher( 1 );
		assertEquals( 1, teacher.getID() );
	}
	
	@Test
	public void AddSectionToSectionSetSuccessfully() throws SectionCreationException {
		Teacher teacher = new Teacher( 1 );
		Subject subject = new Subject( 101, UNDERGRADUATE );
		Schedule schedule = new Schedule( MONTHU, T_0830_1000 );
		new Section( teacher, subject, schedule );
		assertTrue( teacher.hasScheduleConflict(schedule) );
	}
	
	@Test (expected = SectionCreationException.class )
	public void AddingConflictingSchedulesToScheduleSetThrowsException() throws SectionCreationException {
		Teacher teacher = new Teacher( 1 );
		Schedule schedule = new Schedule( MONTHU, T_0830_1000 );
		Schedule scheduleConflict = new Schedule( MONTHU, T_0830_1000 );
		teacher.addSchedule( schedule );
		teacher.addSchedule( scheduleConflict );
	}
	
	@Test (expected = SectionCreationException.class )
	public void AddingSameScheduleToScheduleSetThrowsException() throws SectionCreationException {
		Teacher teacher = new Teacher( 1 );
		Schedule schedule = new Schedule( MONTHU, T_0830_1000 );
		teacher.addSchedule( schedule );
		teacher.addSchedule( schedule );
	}
	
	@Test (expected = IllegalArgumentException.class )
	public void AddingNullToScheduleSetThrowsException() throws SectionCreationException {
		Teacher teacher = new Teacher( 1 );
		teacher.addSchedule( null );
	}

}
